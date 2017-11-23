package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fooma on 2016/4/22.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "yunlin.db";
    private static final int VERSION = 1;
    private static DBHelper dbHelper;
    public static DBHelper getDBHelper(Context context){
        if (dbHelper==null){
            dbHelper= new DBHelper(context, DB_NAME,null,VERSION);
        }
        return dbHelper;
    }
    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建库，建表，建立视图
        //用户
        String sqlMember = "create table tb_member(_id Integer primary key autoincrement,account text,password text,head int,phone text ,mlongitude text,mlatitude text,gender text ,registTime text ,enable Integer )";
        db.execSQL(sqlMember);
        //商品
        String sqlGoods = "create table tb_goods(_id Integer primary key autoincrement,description text, " +
                "picture int,memberId int ," +
                "longitude text,latitude text,issueTime text,cancle int ,bigType text,smallType text)";
        db.execSQL(sqlGoods);
        //大类别
        String sqlBigType = "create table tb_bigtype(_id Integer primary key autoincrement,bigType text)";
        db.execSQL(sqlBigType);
        //小类别
        String sqlSmallType = "create table tb_smalltype(_id Integer primary key autoincrement,bigType text,smallType text)";
        db.execSQL(sqlSmallType);
        //收藏
        String sqlCollection = "create table tb_collection(_id Integer primary key autoincrement,gMemberId int,goodsId int,memberId int)";
        db.execSQL(sqlCollection);
        //记录
        String sqlRecord = "create table tb_record(_id Integer primary key autoincrement,gMemberId int,goodsId int,memberId int)";
        db.execSQL(sqlRecord);
        //求购
        String sqlWant = "create table tb_want(_id Integer primary key autoincrement,gMemberId int,goodsId int,memberId int)";
        db.execSQL(sqlWant);
        //求购
        String sqlAds = "create table tb_ads(_id Integer primary key autoincrement,ads text)";
        db.execSQL(sqlAds);
        //求购
        String sqlAdmin = "create table tb_admin(_id Integer primary key autoincrement,admin text,password)";
        db.execSQL(sqlAdmin);

    }
    //数据库升级时，触发调用此方法。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}



