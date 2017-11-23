package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hnu.fooma.yunlin.entity.Collection;
/**
 * Created by Fooma on 2016/5/1.
 */
public class CollectionDao {
    private DBHelper dbHelper;
    public CollectionDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }
    public void creatCollection(Collection collection){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="insert into tb_collection(gMemberId,goodsId,memberId) values(?,?,?);";
        Object args[]=new Object[]{collection.gMemberId,collection.goodsId,collection.memberId};
        db.execSQL(sql,args);
    }
    public void deleteCollection(int collectionId){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="delete from tb_collection where _id = ?;";
        Object args[]=new Object[]{collectionId};
        db.execSQL(sql,args);
    }
    public List<Collection> queryCollection(int memberId){
        List<Collection> list = new ArrayList<>();
        Collection collection=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_collection where memberId=?; ";
        String args[]=new String[]{""+memberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                collection=new Collection();
                collection.collectionId=cs.getInt(cs.getColumnIndex("_id"));
                collection.gMemberId=cs.getInt(cs.getColumnIndex("gMemberId"));
                collection.goodsId=cs.getInt(cs.getColumnIndex("goodsId"));
                collection.memberId=cs.getInt(cs.getColumnIndex("memberId"));
                list.add(collection);
            }
            cs.close();
        }
        return list;
    }


    public Collection readCollection(Collection collection) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from tb_collection where goodsId=? and memberId=?";
        String ss[]=new String[]{""+collection.goodsId,""+collection.memberId};
        Cursor cs = db.rawQuery(sql,ss);
        Collection w = null;
        if(cs!=null){
            while (cs.moveToNext()){
                w=new Collection();
                w.collectionId=cs.getInt(cs.getColumnIndex("_id"));
                w.goodsId=cs.getInt(cs.getColumnIndex("goodsId"));
                w.memberId=cs.getInt(cs.getColumnIndex("memberId"));
            }
            cs.close();
        }
        return w;
    }
}
