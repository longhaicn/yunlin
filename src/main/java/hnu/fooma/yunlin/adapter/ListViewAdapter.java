package hnu.fooma.yunlin.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.entity.MemberGoods;
import hnu.fooma.yunlin.utils.GPSDistance;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/4/28.
 */
public class ListViewAdapter extends BaseAdapter{
    private List<MemberGoods> lists;
    private LayoutInflater mInflater;
    private Context context;
    private String mlongitude,mlatitude;

    public ListViewAdapter(Context context, List<MemberGoods> lists,String mlongitude,String mlatitude) {
        this.lists = lists;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mlongitude=mlongitude;
        this.mlatitude=mlatitude;


    }
    public void setLists(List<MemberGoods> lists) {
        this.lists = lists;
    }
    @Override
    public int getCount() {
        return lists.size();
    }
    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder=null;
        if(convertView==null){
            //加载布局
            convertView = mInflater.inflate(R.layout.lv_home_item,null);
            //创建ViewHolder对象
            mHolder = new ViewHolder();
            //找到控件
            mHolder.iv_head=(ImageView) convertView.findViewById(R.id.iv_head);
            mHolder.tv_account=(TextView) convertView.findViewById(R.id.tv_account);
            mHolder.tv_issue_time=(TextView) convertView.findViewById(R.id.tv_issue_time);
            mHolder.tv_description=(TextView) convertView.findViewById(R.id.tv_description);
            mHolder.iv_picture=(ImageView) convertView.findViewById(R.id.iv_picture);
            mHolder.tv_distance=(TextView) convertView.findViewById(R.id.tv_distance);
            //复用
            convertView.setTag(mHolder);
        }else {
            //2.减少findViewById的次数
            mHolder =(ViewHolder) convertView.getTag();
        }
        //        //3.准备要绑定的数据
        MemberGoods mGoods = lists.get(position);
        //4.组装
//        mHolder.iv_head.setImageDrawable(context.getResources().getDrawable(mGoods.picture));
        mHolder.iv_head.setImageBitmap(Tools.decodeBitmapFromRes(context.getResources(),mGoods.head,100,100));
        mHolder.tv_account.setText(mGoods.account);
        mHolder.tv_issue_time.setText(mGoods.issueTime);
        mHolder.tv_description.setText(mGoods.description);
        mHolder.iv_picture.setImageBitmap(Tools.decodeBitmapFromRes(context.getResources(),mGoods.picture,200,100));
//        mHolder.tv_distance.setText(Tools.getDistance(mGoods.longitude,mGoods.latitude));

        mHolder.tv_distance.setText(
                GPSDistance.getDistance(Double.parseDouble(mGoods.longitude),
                        Double.parseDouble(mGoods.latitude),Double.parseDouble(mlongitude),Double.parseDouble(mlatitude)));
        return convertView;
    }
    //第一
    private final static class ViewHolder{
        ImageView iv_head,iv_picture;
        TextView tv_account,tv_issue_time,tv_description,tv_distance;
    }
}
