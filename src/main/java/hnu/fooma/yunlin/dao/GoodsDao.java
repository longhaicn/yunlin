package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.entity.BigType;
import hnu.fooma.yunlin.entity.Goods;
import hnu.fooma.yunlin.entity.MemberGoods;

/**
 * Created by Fooma on 2016/4/28.
 */
public class GoodsDao {
    private DBHelper dbHelper;
    public GoodsDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }

    public void creatGoods(Goods goods){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="insert into tb_goods(description,picture,memberId,longitude,latitude,issueTime,cancle,bigType,smallType) values(?,?,?,?,?,?,?,?,?);";
        Object args[]=new Object[]{goods.description,goods.picture,goods.memberId,goods.longitude,goods.latitude,goods.issueTime,goods.cancle,goods.bigType,goods.smallType};
        db.execSQL(sql,args);
    }
    public void initGoods(){
        Goods goods= null;
        for (int i = 0 ; i<4;i++){
            goods=new Goods();
            goods.description="诚意出手一台苹果手机，去年买的6，现在换了SE，所以想出手原来的手机。9.5成新，价格随意"+i;
            goods.picture= R.drawable.iphone;
            goods.memberId=1;
            goods.longitude="112.99771";
            goods.latitude="28.13666";
            goods.issueTime="2016-05-12 00:02:01";
            goods.cancle=1;
            goods.bigType="手机/平板";
            goods.smallType="苹果";
            creatGoods(goods);
            goods=new Goods();
            goods.description="名牌包包出手一个，LV2013春款，价格公道，如果有缘分，免费赠送也行。"+i;
            goods.picture= R.drawable.lv;
            goods.memberId=2;
            goods.longitude="111.99771";
            goods.latitude="28.13666";
            goods.issueTime="2016-05-12 00:02:01";
            goods.cancle=1;
            goods.bigType="奢侈品";
            goods.smallType="LV";
            creatGoods(goods);
            goods=new Goods();
            goods.description="聚利时手表，女款出手一个，喜欢的朋友看过来，价格公道哦~"+i;
            goods.picture= R.drawable.julius;
            goods.memberId=3;
            goods.longitude="113.99771";
            goods.latitude="28.13666";
            goods.issueTime="2016-05-12 00:02:01";
            goods.cancle=1;
            goods.bigType="珠宝饰品";
            goods.smallType="名表";
            creatGoods(goods);
        }

    }


    public List<MemberGoods> queryMemberGoods(int memberId) {
        List<MemberGoods> list = new ArrayList<>();
        MemberGoods mGoods=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_goods._id, tb_goods.issueTime,tb_goods.description,tb_goods.picture," +
                "tb_goods.longitude,tb_goods.latitude,tb_member.head,tb_member.account from tb_goods," +
                "tb_member where tb_goods.memberId != ? and tb_goods.memberId = tb_member._id   order by tb_goods._id desc";
        String args[]=new String[]{""+memberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                mGoods=new MemberGoods();

                //goods
                mGoods.goodsId=cs.getInt(cs.getColumnIndex("tb_goods._id"));
                mGoods.issueTime=cs.getString(cs.getColumnIndex("tb_goods.issueTime"));
                mGoods.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                mGoods.picture=cs.getInt(cs.getColumnIndex("tb_goods.picture"));
                mGoods.longitude=cs.getString(cs.getColumnIndex("tb_goods.longitude"));
                mGoods.latitude=cs.getString(cs.getColumnIndex("tb_goods.latitude"));
                mGoods.head= cs.getInt(cs.getColumnIndex("tb_member.head"));
                mGoods.account= cs.getString(cs.getColumnIndex("tb_member.account"));

                list.add(mGoods);
            }
            cs.close();
        }
        return list;
    }

    public MemberGoods readGoods(int goodsId) {
        MemberGoods mGoods = new MemberGoods();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_goods._id, " +
                "tb_goods.issueTime," +
                "tb_goods.description," +
                "tb_goods.picture," +
                "tb_goods.longitude," +
                "tb_goods.latitude," +
                "tb_goods.smallType,"+
                "tb_goods.bigType,"+
                "tb_goods.memberId,"+
                "tb_member.head," +
                "tb_member.phone," +
                "tb_member.account from tb_goods,tb_member where tb_goods.memberId = tb_member._id and tb_goods._id=?";
        String args[] =new String[]{""+goodsId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                mGoods.head= cs.getInt(cs.getColumnIndex("tb_member.head"));
                mGoods.phone= cs.getString(cs.getColumnIndex("tb_member.phone"));
                mGoods.account= cs.getString(cs.getColumnIndex("tb_member.account"));
                mGoods.goodsId=cs.getInt(cs.getColumnIndex("tb_goods._id"));
                mGoods.memberId=cs.getInt(cs.getColumnIndex("tb_goods.memberId"));
                mGoods.issueTime=cs.getString(cs.getColumnIndex("tb_goods.issueTime"));
                mGoods.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                mGoods.picture=cs.getInt(cs.getColumnIndex("tb_goods.picture"));
                mGoods.smallType=cs.getString(cs.getColumnIndex("tb_goods.smallType"));
                mGoods.bigType=cs.getString(cs.getColumnIndex("tb_goods.bigType"));
                mGoods.longitude=cs.getString(cs.getColumnIndex("tb_goods.longitude"));
                mGoods.latitude=cs.getString(cs.getColumnIndex("tb_goods.latitude"));
            }
            cs.close();
        }
        return mGoods;
    }

    public List<MemberGoods> searchMemberGoods(int memberId,String search) {
        List<MemberGoods> list = new ArrayList<>();
        MemberGoods mGoods=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_goods._id, tb_goods.issueTime,tb_goods.description,tb_goods.picture," +
                "tb_goods.longitude,tb_goods.latitude,tb_member.head,tb_member.account from tb_goods," +
                "tb_member where tb_goods.memberId != ? and tb_goods.description like '%"+search+"%' and tb_goods.memberId = tb_member._id order by tb_goods._id desc";
        String args[]=new String[]{""+memberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                mGoods=new MemberGoods();

                //goods
                mGoods.goodsId=cs.getInt(cs.getColumnIndex("tb_goods._id"));
                mGoods.issueTime=cs.getString(cs.getColumnIndex("tb_goods.issueTime"));
                mGoods.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                mGoods.picture=cs.getInt(cs.getColumnIndex("tb_goods.picture"));
                mGoods.longitude=cs.getString(cs.getColumnIndex("tb_goods.longitude"));
                mGoods.latitude=cs.getString(cs.getColumnIndex("tb_goods.latitude"));
                mGoods.head= cs.getInt(cs.getColumnIndex("tb_member.head"));
                mGoods.account= cs.getString(cs.getColumnIndex("tb_member.account"));

                list.add(mGoods);
            }
            cs.close();
        }
        return list;
    }

    public List<MemberGoods> searchMemberGoodsKind( String small) {
        List<MemberGoods> list = new ArrayList<>();
        MemberGoods mGoods=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_goods._id, tb_goods.issueTime,tb_goods.description,tb_goods.picture," +
                "tb_goods.longitude,tb_goods.latitude,tb_member.head,tb_member.account from tb_goods," +
                "tb_member where tb_goods.memberId = tb_member._id and  tb_goods.smallType=? order by tb_goods._id desc";
        String args[]=new String[]{""+small};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                mGoods=new MemberGoods();

                //goods
                mGoods.goodsId=cs.getInt(cs.getColumnIndex("tb_goods._id"));
                mGoods.issueTime=cs.getString(cs.getColumnIndex("tb_goods.issueTime"));
                mGoods.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                mGoods.picture=cs.getInt(cs.getColumnIndex("tb_goods.picture"));
                mGoods.longitude=cs.getString(cs.getColumnIndex("tb_goods.longitude"));
                mGoods.latitude=cs.getString(cs.getColumnIndex("tb_goods.latitude"));
                mGoods.head= cs.getInt(cs.getColumnIndex("tb_member.head"));
                mGoods.account= cs.getString(cs.getColumnIndex("tb_member.account"));

                list.add(mGoods);
            }
            cs.close();
        }
        return list;
    }
}
