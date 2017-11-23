package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hnu.fooma.yunlin.entity.Want;

/**
 * Created by Fooma on 2016/5/1.
 */
public class WantDao {
    private DBHelper dbHelper;
    public WantDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }
    public Want readWant(Want want){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_want where goodsId=? and memberId=?";
        String ss[]=new String[]{""+want.goodsId,""+want.memberId};
        Cursor cs = db.rawQuery(sql,ss);
        Want w = null;
        if(cs!=null){
            while (cs.moveToNext()){
                w=new Want();
                w.wantId=cs.getInt(cs.getColumnIndex("_id"));
                w.gMemberId=cs.getInt(cs.getColumnIndex("gMemberId"));
                w.goodsId=cs.getInt(cs.getColumnIndex("goodsId"));
                w.memberId=cs.getInt(cs.getColumnIndex("memberId"));
            }
            cs.close();
        }
        return w;

    }
    public void creatWant(Want want){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="insert into tb_want(gMemberId,goodsId,memberId) values(?,?,?);";
        Object args[]=new Object[]{want.gMemberId,want.goodsId,want.memberId};
        db.execSQL(sql,args);
    }
    public void deleteWant(int wantId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="delete from tb_want where _id = ?;";
        Object args[]=new Object[]{wantId};
        db.execSQL(sql,args);
    }
    public List<Want> queryWant(int gMemberId){
        List<Want> list = new ArrayList<>();
        Want want=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_want where gMemberId=?";
        String args[]=new String[]{""+gMemberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                want=new Want();
                want.wantId=cs.getInt(cs.getColumnIndex("_id"));
                want.gMemberId=cs.getInt(cs.getColumnIndex("gMemberId"));
                want.goodsId=cs.getInt(cs.getColumnIndex("goodsId"));
                want.memberId=cs.getInt(cs.getColumnIndex("memberId"));
                list.add(want);
            }
            cs.close();
        }
        return list;
    }
}
