package hnu.fooma.yunlin.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fooma on 2016/4/22.
 */
public class Tools {
    public static boolean isEnpty(String test){
        if (test.length()<=0||"".equals(test)){
            return true;
        }
        return false;

    }
    public static Bitmap decodeBitmapFromRes(Resources res, int resId, int reqWith, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,resId,options);
        options.inSampleSize = calculateInSampleSize(options,reqWith,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);


    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWith, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height>reqHeight||width>reqHeight){
            final int halfHeight = options.outHeight;
            final int halfWidth = options.outWidth;
            while ((halfHeight/inSampleSize)>=reqHeight  &&  (halfWidth / inSampleSize)>= reqWith){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public static String getDateTime() {
        Date date= new Date();//创建一个时间对象，获取到当前的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间显示格式
        String str = sdf.format(date);//将当前时间格式化为需要的类型
        System.out.println(str);//输出结果
        return str;

    }

}
