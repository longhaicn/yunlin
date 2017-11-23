package hnu.fooma.yunlin.activity;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.dao.MemberDao;
import hnu.fooma.yunlin.dao.MessageDao;
import hnu.fooma.yunlin.entity.Member;

/**
 * Created by Fooma on 2016/5/4.
 */
public class CenterUpdateActivity extends Activity {
    public LocationManager lm;
    private static final String TAG = "GPS Services";
    private String longitude="LONGITUDE";
    private String latitude="LATITUDE";
    private boolean flag=false;
    private SharedPreferences sp;
    private MemberDao memberDao;
    private Member member;
    private int memberId;
    private ImageView iv_top_return;
    private TextView tv_longitude,tv_latitude,tv_phone,tv_gender,tv_new_phone,tv_password,tv_submit,tv_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        memberDao=new MemberDao(this);
        sp=getSharedPreferences("config", Context.MODE_PRIVATE);
        memberId=sp.getInt("memberId",0);
        memberDao=new MemberDao(this);
        member=memberDao.readMember(memberId);
        initView();
        registerGPS();
        setListener();
    }

    private void setListener() {
        tv_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(CenterUpdateActivity.this);
                builder2.setTitle("性别：");
                final String genders[]=new String[]{"男","女"};
                builder2.setItems(genders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_gender.setText(genders[which]);
                    }
                });
                builder2.show();
            }
        });
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CenterUpdateActivity.this);
                View view = getLayoutInflater().inflate(R.layout.view_phone,null);
                tv_new_phone= (TextView) view.findViewById(R.id.tv_new_phone);
                builder.setTitle("请填写新的手机号：");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_phone.setText(tv_new_phone.getText());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view);
                builder.show();

            }
        });
        tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CenterUpdateActivity.this);
                View view = getLayoutInflater().inflate(R.layout.view_phone,null);
                tv_new_phone= (TextView) view.findViewById(R.id.tv_new_phone);
                builder.setTitle("请填写新的密码：");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_password.setText(tv_new_phone.getText());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view);
                builder.show();

            }
        });
        iv_top_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CenterUpdateActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Member m = new Member();
                    m.memberId=memberId;
                    m.phone = tv_phone.getText().toString();
                    m.gender = tv_gender.getText().toString();
                    m.password = tv_password.getText().toString();
                    m.mlongitude = longitude;
                    m.mlatitude = latitude;
                    memberDao.updateMember(m);
                    Toast.makeText(CenterUpdateActivity.this,"修改成功，请重新登录",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(CenterUpdateActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initView(){
        iv_top_return= (ImageView) findViewById(R.id.iv_top_return);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
        tv_phone.setText(member.phone);
        tv_account= (TextView) findViewById(R.id.tv_account);
        tv_account.setText(member.account);
        tv_password= (TextView) findViewById(R.id.tv_password);
        tv_password.setText(member.password);
        tv_gender= (TextView) findViewById(R.id.tv_gender);
        tv_gender.setText(member.gender);
        tv_longitude= (TextView) findViewById(R.id.tv_longitude);
        tv_latitude= (TextView) findViewById(R.id.tv_latitude);
        tv_submit= (TextView) findViewById(R.id.tv_submit);

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
            tv_longitude.setText(longitude);
            this.latitude=String.valueOf(location.getLatitude());
            tv_latitude.setText(latitude);
            if (!flag) {
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
