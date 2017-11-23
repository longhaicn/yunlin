package hnu.fooma.yunlin.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hnu.fooma.yunlin.entity.BigType;
import hnu.fooma.yunlin.entity.SmallType;

/**
 * Created by Fooma on 2016/4/26.
 */
public class SmallTypeDao {
    private DBHelper dbHelper;
    public SmallTypeDao(Context context){
        dbHelper = DBHelper.getDBHelper(context);
    }

    public String[] querySmallType(String bigType) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select smallType from tb_smalltype where bigType=?";
        String args[]={bigType};
        Cursor cs = db.rawQuery(sql,args);
        if(cs!=null){
            while (cs.moveToNext()){
                list.add(cs.getString(cs.getColumnIndex("smallType")));
            }
            cs.close();
        }
        int size = list.size();
        String[] smallTypeItems=new String[size];
        for (int i = 0;i<size ;i++){
            smallTypeItems[i]=list.get(i);
        }
        return smallTypeItems;
    }
    public void createSmallType(SmallType smallType){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql="insert into tb_smalltype(bigType,smallType) values(?,? );";
        Object args[]=new Object[]{smallType.bigType,smallType.smallType};
        db.execSQL(sql,args);
    }
    public void initSmallType(){
        String bigName[]={"手机/平板","笔记本/台式机","数码/摄像","家居生活","箱包",
                "鞋履","服饰","珠宝饰品","奢侈品","美容美体","文体用品",
                "收藏/文玩","其他"};


        createSmallType(new SmallType(0,bigName[0],"小米"));
        createSmallType(new SmallType(0,bigName[0],"苹果"));
        createSmallType(new SmallType(0,bigName[0],"华为"));
        createSmallType(new SmallType(0,bigName[0],"魅族"));
        createSmallType(new SmallType(0,bigName[0],"其他"));

        createSmallType(new SmallType(0,bigName[1],"华硕"));
        createSmallType(new SmallType(0,bigName[1],"苹果"));
        createSmallType(new SmallType(0,bigName[1],"联想"));
        createSmallType(new SmallType(0,bigName[1],"其他"));

        createSmallType(new SmallType(0,bigName[2],"索尼"));
        createSmallType(new SmallType(0,bigName[2],"尼康"));
        createSmallType(new SmallType(0,bigName[2],"奥克斯"));
        createSmallType(new SmallType(0,bigName[2],"其他"));

        createSmallType(new SmallType(0,bigName[3],"沙发"));
        createSmallType(new SmallType(0,bigName[3],"电视机"));
        createSmallType(new SmallType(0,bigName[3],"柜子"));
        createSmallType(new SmallType(0,bigName[3],"其他"));

        createSmallType(new SmallType(0,bigName[4],"男包"));
        createSmallType(new SmallType(0,bigName[4],"女包"));
        createSmallType(new SmallType(0,bigName[4],"书包"));
        createSmallType(new SmallType(0,bigName[4],"其他"));

        createSmallType(new SmallType(0,bigName[5],"平底鞋"));
        createSmallType(new SmallType(0,bigName[5],"皮鞋"));
        createSmallType(new SmallType(0,bigName[5],"高跟鞋"));
        createSmallType(new SmallType(0,bigName[5],"其他"));

        createSmallType(new SmallType(0,bigName[6],"运动服"));
        createSmallType(new SmallType(0,bigName[6],"夏季女装"));
        createSmallType(new SmallType(0,bigName[6],"冬季棉衣"));
        createSmallType(new SmallType(0,bigName[6],"晚礼服"));
        createSmallType(new SmallType(0,bigName[6],"其他"));

        createSmallType(new SmallType(0,bigName[7],"玉器"));
        createSmallType(new SmallType(0,bigName[7],"金银器"));
        createSmallType(new SmallType(0,bigName[7],"珍珠"));
        createSmallType(new SmallType(0,bigName[7],"名表"));
        createSmallType(new SmallType(0,bigName[7],"其他"));
        //"奢侈品","美容美体","文体用品","收藏/文玩","车/车用品","其他"};
        createSmallType(new SmallType(0,bigName[8],"LV"));
        createSmallType(new SmallType(0,bigName[8],"Dior"));
        createSmallType(new SmallType(0,bigName[8],"LH"));
        createSmallType(new SmallType(0,bigName[8],"其他"));

        createSmallType(new SmallType(0,bigName[9],"化妆用品"));
        createSmallType(new SmallType(0,bigName[9],"其他"));

        createSmallType(new SmallType(0,bigName[10],"文具"));
        createSmallType(new SmallType(0,bigName[10],"办公"));
        createSmallType(new SmallType(0,bigName[10],"体育用品"));
        createSmallType(new SmallType(0,bigName[10],"其他"));

        createSmallType(new SmallType(0,bigName[11],"古代名画"));
        createSmallType(new SmallType(0,bigName[11],"瓷器"));
        createSmallType(new SmallType(0,bigName[11],"其他"));

        createSmallType(new SmallType(0,bigName[12],"其他"));



    }
}
