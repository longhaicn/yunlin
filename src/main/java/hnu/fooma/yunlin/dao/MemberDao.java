package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.entity.Member;

/**
 * Created by Fooma on 2016/4/22.
 */
public class MemberDao {
    private DBHelper dbHelper;
    public MemberDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }
    public void creatMember(Member member){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //account text,password text,head int,phone text ,mlongitude text,mlatitude text,gender text ,registTime text ,enable Integer
        String sql = "insert into tb_member(account,password,head,phone,mlongitude,mlatitude,gender,registTime,enable)values(?,?,?,?,?,?,?,?,?);";
        Object args[] = new Object[]{member.account,member.password,member.head,member.phone,member.mlongitude,member.mlatitude,member.gender,member.registTime,member.enable};
        db.execSQL(sql,args);
    }
    public void updateMember(Member member){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "update tb_member set phone=?,gender=?,password=?,mlongitude=?,mlatitude=? where _id=?;";
        Object args[] = new Object[]{member.phone,member.gender,member.password,member.mlongitude,member.mlatitude,member.memberId};
        db.execSQL(sql,args);
    }
    public Member readMember(Member member) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select * from tb_member where account=? and password=?";
        String args[] = new String[]{ member.account, member.password};
        Cursor cs = db.rawQuery(sql, args);
        Member m = null;
        if(cs!=null){
            m = new Member();
            while (cs.moveToNext()){
                m.memberId=cs.getInt(cs.getColumnIndex("_id"));
                m.head=cs.getInt(cs.getColumnIndex("head"));
                m.account=cs.getString(cs.getColumnIndex("account"));
                m.password=cs.getString(cs.getColumnIndex("password"));
                m.phone=cs.getString(cs.getColumnIndex("phone"));
                m.mlongitude=cs.getString(cs.getColumnIndex("mlongitude"));
                m.mlatitude=cs.getString(cs.getColumnIndex("mlatitude"));

            }
            cs.close();
        }
        return  m;
    }
    public String readMember(String account) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select * from tb_member where account=?";
        String args[] = new String[]{account};
        Cursor cs = db.rawQuery(sql, args);
        String acc=null;
        if(cs!=null){
            while (cs.moveToNext()){
                acc=cs.getString(cs.getColumnIndex("account"));
            }
            cs.close();
        }
        return  acc;
    }
    public Member readMember(int memberId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select * from tb_member where _id=?";
        String args[] = new String[]{ ""+memberId};
        Cursor cs = db.rawQuery(sql, args);
        Member member=null;
        if(cs!=null){
            member = new Member();
            while (cs.moveToNext()){
                member.memberId=cs.getInt(cs.getColumnIndex("_id"));
                member.head=cs.getInt(cs.getColumnIndex("head"));
                member.account=cs.getString(cs.getColumnIndex("account"));
                member.gender=cs.getString(cs.getColumnIndex("gender"));
                member.password=cs.getString(cs.getColumnIndex("password"));
                member.phone=cs.getString(cs.getColumnIndex("phone"));
                member.mlongitude=cs.getString(cs.getColumnIndex("mlongitude"));
                member.mlatitude=cs.getString(cs.getColumnIndex("mlatitude"));

            }
            cs.close();
        }
        return  member;
    }
    public void initMember(){
        Member member=new Member();
        member.account="long";
        member.password="123";
        member.phone="18373125374";
        member.head= R.drawable.head3;
        member.gender="男";
        member.mlongitude="112.99814";
        member.mlatitude="28.13564";
        member.registTime="2016-04-28 00:00:00";
        member.enable=1;
        creatMember(member);

        Member member2=new Member();
        member2.account="lin";
        member2.password="123";
        member2.phone="18373125370";
        member2.head= R.drawable.head2;
        member2.gender="女";
        member2.mlongitude="110.99814";
        member2.mlatitude="28.13564";
        member2.registTime="2016-04-28 00:00:00";
        member2.enable=1;
        creatMember(member2);

        Member member3=new Member();
        member3.account="csy";
        member3.password="123";
        member3.phone="13237960520";
        member3.head= R.drawable.head1;
        member3.gender="女";
        member3.mlongitude="110.99814";
        member3.mlatitude="29.13564";
        member3.registTime="2016-04-28 00:00:00";
        member3.enable=1;
        creatMember(member3);

    }
}
