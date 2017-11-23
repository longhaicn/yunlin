package hnu.fooma.yunlin.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.dao.CollectionDao;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.dao.WantDao;
import hnu.fooma.yunlin.entity.Collection;
import hnu.fooma.yunlin.entity.Goods;
import hnu.fooma.yunlin.entity.MemberGoods;
import hnu.fooma.yunlin.entity.Want;
import hnu.fooma.yunlin.utils.GPSDistance;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/5/1.
 */
public class GoodsItemActivity extends FragmentActivity{
    private String mlongitude,mlatitude;
    private int goodsId,memberId;
    private ImageView iv_head,iv_picture,iv_top_return;
    private TextView tv_issue_time,tv_description,tv_type,tv_distance,tv_account;
    private Button btn_collect,btn_want;
    private WantDao wantDao;
    private Want want;
    private CollectionDao collectionDao;
    private Collection collection;
    private GoodsDao goodsDao;
    private MemberGoods mGoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_item);
        Intent intent = getIntent();
        this.goodsId= intent.getIntExtra("goodsId",goodsId);
        Toast.makeText(this,"===="+goodsId,Toast.LENGTH_LONG);
        SharedPreferences sp = getSharedPreferences("config", this.MODE_PRIVATE);
        this.mlongitude=sp.getString("mlongitude","0");
        this.mlatitude=sp.getString("mlatitude","0");
        this.memberId=sp.getInt("memberId",0);
        goodsDao = new GoodsDao(this);
        wantDao=new WantDao(this);
        collectionDao=new CollectionDao(this);
        mGoods = goodsDao.readGoods(goodsId);
        initView();
        iv_head.setImageBitmap(Tools.decodeBitmapFromRes(getResources(),mGoods.head,100,100));
        tv_account.setText(mGoods.account);
        tv_description.setText(mGoods.description);
        tv_issue_time.setText(mGoods.issueTime);
        iv_picture.setImageBitmap(Tools.decodeBitmapFromRes(getResources(),mGoods.picture,300,300));
        tv_type.setText(mGoods.bigType+":"+mGoods.smallType);
        tv_distance.setText(GPSDistance.getDistance(Double.parseDouble(mGoods.longitude),Double.parseDouble(mGoods.latitude),Double.parseDouble(mlongitude),Double.parseDouble(mlatitude)));
        collection = new Collection();
        collection.gMemberId=mGoods.memberId;
        collection.goodsId= goodsId;
        collection.memberId=memberId;
        Collection c = collectionDao.readCollection(collection);
        if (c!=null){
            btn_collect.setText("撤销收藏");
        }
        iv_top_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collection c = collectionDao.readCollection(collection);
                if (c==null){
                    collectionDao.creatCollection(collection);
                    btn_collect.setText("撤销收藏");
                    Toast.makeText(GoodsItemActivity.this,"收藏成功",Toast.LENGTH_LONG).show();
                }else {
                    collectionDao.deleteCollection(c.collectionId);
                    btn_collect.setText("收藏");
                    Toast.makeText(GoodsItemActivity.this,"撤销成功",Toast.LENGTH_LONG).show();
                }
            }
        });
        want = new Want();
        want.gMemberId=mGoods.memberId;
        want.goodsId= goodsId;
        want.memberId=memberId;
        Want w = wantDao.readWant(want);
        if (w!=null){
            btn_want.setText("撤销求购");
        }
        btn_want.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Want w = wantDao.readWant(want);
                if (w==null){
                    wantDao.creatWant(want);
                    btn_want.setText("撤销求购");
                    Toast.makeText(GoodsItemActivity.this,"求购成功",Toast.LENGTH_LONG).show();
                }else {
                    wantDao.deleteWant(w.wantId);
                    btn_want.setText("求购");
                    Toast.makeText(GoodsItemActivity.this,"撤销成功",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView() {
        btn_collect= (Button) findViewById(R.id.btn_collect);
        btn_want= (Button) findViewById(R.id.btn_want);
        iv_top_return= (ImageView) findViewById(R.id.iv_top_return);
        iv_head= (ImageView) findViewById(R.id.iv_head);
        iv_picture= (ImageView) findViewById(R.id.iv_picture);
        tv_account= (TextView) findViewById(R.id.tv_account);
        tv_issue_time= (TextView) findViewById(R.id.tv_issue_time);
        tv_description= (TextView) findViewById(R.id.tv_description);
        tv_type= (TextView) findViewById(R.id.tv_type);
        tv_distance= (TextView) findViewById(R.id.tv_distance);
    }
}
