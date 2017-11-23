package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hnu.fooma.yunlin.entity.BigType;

/**
 * Created by Fooma on 2016/4/26.
 */
public class BigTypeDao {
    private DBHelper dbHelper;
    public BigTypeDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }
    public List<Map<String ,BigType>> queryBigType(){
        List<Map<String ,BigType>> list = new ArrayList<>();
        Map<String ,BigType> map = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_bigtype;";
        Cursor cs = db.rawQuery(sql,null);
        if(cs!=null){
            while (cs.moveToNext()){
                map=new HashMap<String ,BigType>();
                BigType bigType = new BigType();
                bigType.bigId = cs.getInt(cs.getColumnIndex("_id"));
                bigType.bigType=cs.getString(cs.getColumnIndex("bigType"));
                map.put("bigType",bigType);
                list.add(map);
            }
            cs.close();
        }
        return list;
    }
    public void creatBigType(BigType bigType){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="INSERT INTO tb_bigtype(bigType) values(?);";
        Object args[]=new Object[]{bigType.bigType};
        db.execSQL(sql,args);

    }
    public void initBigType(){
        String bigName[]={"手机/平板","笔记本/台式机","数码/摄像","家居生活","箱包",
                "鞋履","服饰","珠宝饰品","奢侈品","美容美体","文体用品",
                "收藏/文玩","其他"};
        BigType bigType=null;
        for (int i =0;i<bigName.length;i++){
            bigType= new BigType();
            bigType.bigType=bigName[i];
            creatBigType(bigType);

        }
    }
}
