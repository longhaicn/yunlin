package hnu.fooma.yunlin.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Iterator;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.dao.SmallTypeDao;
import hnu.fooma.yunlin.entity.Goods;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/4/28.
 */
public class SendInforActivity extends FragmentActivity {
    public LocationManager lm;
    private static final String TAG = "GPS Services";
    private String longitude="LONGITUDE";
    private String latitude="LATITUDE";
    private EditText et_description;
    private ImageView iv_send_confirm,iv_top_return,iv_location;
    private Button btn_type;
    private boolean flag=false;
    private SharedPreferences sp;
    private String bigTypeItems[]={"手机/平板","笔记本/台式机","数码/摄像","家居生活","箱包","鞋履","服饰","珠宝饰品","奢侈品","美容美体","文体用品","收藏/文玩","车/车用品","其他"};
    private String smallTypeItems[]={"其他"};
    private String bigType="";
    private String smallType="";
    private SmallTypeDao smallTypeDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_infor);
        smallTypeDao=new SmallTypeDao(this);
        initView();
        setListener();
        registerGPS();
        sp = getSharedPreferences("config", this.MODE_PRIVATE);
    }

    private void setListener() {
        iv_top_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_send_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String de = et_description.getText().toString().trim();
                if (flag&&!Tools.isEnpty(de)&&!Tools.isEnpty(longitude)&&!Tools.isEnpty(smallType)) {
                    Goods goods = new Goods();
                    goods.description = de;
                    goods.picture=R.drawable.default_goods_picture;
                    goods.memberId=sp.getInt("memberId",404);
                    goods.longitude = longitude;
                    goods.latitude=latitude;
                    goods.issueTime=Tools.getDateTime();
                    goods.cancle=1;
                    goods.bigType=bigType;
                    goods.smallType=smallType;
                    GoodsDao goodsDao =new GoodsDao(getBaseContext());
                    goodsDao.creatGoods(goods);
                    Toast.makeText(SendInforActivity.this,"恭喜！发布成功！",Toast.LENGTH_LONG).show();
                    Log.i(SendInforActivity.TAG,"======"+goods.memberId+goods.latitude);
                    Intent intent = new Intent();
                    intent.putExtra("answer","success");
                    setResult(200,intent);//能够回到原始页
                    finish();
                }else {
                    Toast.makeText(SendInforActivity.this,"请填写完整的信息！",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SendInforActivity.this);
                builder.setTitle("请选择大类别：");

                builder.setItems(bigTypeItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SendInforActivity.this.bigType=bigTypeItems[which];
                        SendInforActivity.this.smallTypeItems=smallTypeDao.querySmallType(bigTypeItems[which]);
                        //小类别
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(SendInforActivity.this);
                        builder2.setTitle(bigTypeItems[which]+":");
                        builder2.setItems(smallTypeItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SendInforActivity.this.smallType=smallTypeItems[which];
                                btn_type.setText(bigType+":"+smallType);
                            }
                        });
                        builder2.show();
                    }
                });
                builder.show();
            }
        });
    }


    private void initView() {
        et_description= (EditText) findViewById(R.id.et_description);
        iv_top_return= (ImageView) findViewById(R.id.iv_top_return);
        iv_send_confirm= (ImageView) findViewById(R.id.iv_send_confirm);
        iv_location= (ImageView) findViewById(R.id.iv_location);
        btn_type= (Button) findViewById(R.id.btn_type);
    }


    private void registerGPS() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //判断GPS是否正常启动
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            //返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }
        //为获取地理位置信息时设置查询条件
        String bestProvider = lm.getBestProvider(getCriteria(), true);
        //获取位置信息
        //如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        Location location = lm.getLastKnownLocation(bestProvider);
//        Location location= lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        updateView(location);
        lm.addGpsStatusListener(listener);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
    }
    private LocationListener locationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
            updateView(location);
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
        }

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i(TAG, "当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i(TAG, "当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Location location = lm.getLastKnownLocation(provider);
            updateView(location);
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {
            updateView(null);
        }
    };

    //状态监听
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                //第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.i(TAG, "第一次定位");
                    break;
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.i(TAG, "卫星状态改变");
                    //获取当前状态
                    GpsStatus gpsStatus = lm.getGpsStatus(null);
                    //获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
                    System.out.println("搜索到：" + count + "颗卫星");
                    break;
                //定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.i(TAG, "定位启动");
                    break;
                //定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.i(TAG, "定位结束");
                    break;
            }
        }

        ;
    };

    /**
     * 实时更新文本内容
     *
     * @param location
     */
    private void updateView(Location location) {
        if (location != null) {
            this.longitude=String.valueOf(location.getLongitude());
            this.latitude=String.valueOf(location.getLatitude());
            if (!flag) {
                iv_location.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.location_done));
                flag=true;
            }
            }
    }

    /**
     * 返回查询条件
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.removeUpdates(locationListener);
    }
}
