package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hnu.fooma.yunlin.entity.Ads;
import hnu.fooma.yunlin.entity.BigType;

/**
 * Created by Fooma on 2016/4/29.
 */
public class AdsDao {
    private DBHelper dbHelper;
    public AdsDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }
    public void creatAds(Ads ads){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="insert into tb_ads(ads) values(?);";
        Object args[]=new Object[]{ads.ads};
        db.execSQL(sql,args);
    }
    public List<String> queryAds(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_ads;";
        Cursor cs = db.rawQuery(sql,null);
        if(cs!=null){
            while (cs.moveToNext()){
                list.add(cs.getString(cs.getColumnIndex("ads")));
            }
            cs.close();
        }
        return list;
    }
    public void initAds(){
        creatAds(new Ads("今天天气不错~"));
        creatAds(new Ads("明天不会下雨"));
        creatAds(new Ads("这个季节比较适合短袖~"));
        creatAds(new Ads("南方天气太湿，注意防潮。"));
    }

}
