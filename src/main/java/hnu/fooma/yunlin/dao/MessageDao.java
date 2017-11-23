package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hnu.fooma.yunlin.entity.Message;

/**
 * Created by Fooma on 2016/5/4.
 */
public class MessageDao {

    private DBHelper dbHelper;
    public MessageDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }
    //我收到的消息
    public List<Message> qureyWanted(int gMemberId){
        List<Message> list = new ArrayList<>();
        Message message=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_member.head, tb_member.account,tb_member.phone from tb_want,tb_member where tb_want.gMemberId=? and tb_want.memberId=tb_member._id";
        String args[]=new String[]{""+gMemberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                message=new Message();
                message.head=cs.getInt(cs.getColumnIndex("tb_member.head"));
                message.account=cs.getString(cs.getColumnIndex("tb_member.account"));
                message.phone=cs.getString(cs.getColumnIndex("tb_member.phone"));
                list.add(message);
            }
            cs.close();
        }
        return list;
    }
    //我收到的消息
    public List<Message> qureyWant(int memberId){
        List<Message> list = new ArrayList<>();
        Message message=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_want.goodsId,tb_member.head,tb_member.account,tb_goods.description from tb_want,tb_member,tb_goods where tb_want.memberId=? and tb_want.goodsId=tb_goods._id and tb_goods.memberId=tb_member._id";
        String args[]=new String[]{""+memberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                message=new Message();
                message.goodsId=cs.getInt(cs.getColumnIndex("tb_want.goodsId"));
                message.head=cs.getInt(cs.getColumnIndex("tb_member.head"));
                message.account=cs.getString(cs.getColumnIndex("tb_member.account"));
                message.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                list.add(message);
            }
            cs.close();
        }
        return list;
    }


    public List<Message> qureyCollection(int memberId) {
        List<Message> list = new ArrayList<>();
        Message message=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_collection.goodsId,tb_member.head,tb_member.account,tb_goods.description from tb_collection,tb_goods,tb_member where tb_collection.memberId=? and tb_collection.goodsId=tb_goods._id and tb_goods.memberId=tb_member._id";
        String args[]=new String[]{""+memberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                message=new Message();
                message.goodsId=cs.getInt(cs.getColumnIndex("tb_collection.goodsId"));
                message.head=cs.getInt(cs.getColumnIndex("tb_member.head"));
                message.account=cs.getString(cs.getColumnIndex("tb_member.account"));
                message.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                list.add(message);
            }
            cs.close();
        }
        return list;



    }

    public List<Message> qureyGoods(int memberId) {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select tb_goods._id,tb_goods.description,tb_member.head,tb_member.account from tb_goods,tb_member where tb_goods.memberId =? and tb_goods.memberId = tb_member._id;";
        String args[]=new String[]{""+memberId};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            Message message=null;
            while (cs.moveToNext()){
                message=new Message();
                message.goodsId=cs.getInt(cs.getColumnIndex("tb_goods._id"));
                message.description=cs.getString(cs.getColumnIndex("tb_goods.description"));
                message.head=cs.getInt(cs.getColumnIndex("tb_member.head"));
                message.account=cs.getString(cs.getColumnIndex("tb_member.account"));
                list.add(message);
            }
            cs.close();
        }
        return list;

    }

    public void deleteGoods(int goodsId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="delete from tb_goods where _id = ?;";
        Object args[]=new Object[]{goodsId};
        db.execSQL(sql,args);
    }
}
