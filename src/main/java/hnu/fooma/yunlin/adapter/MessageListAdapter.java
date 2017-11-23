package hnu.fooma.yunlin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.entity.Message;
import hnu.fooma.yunlin.entity.MemberGoods;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/5/3.
 */
public class MessageListAdapter extends BaseAdapter {
    private List<Message> lists;
    private Context context;
    private int message;
    private LayoutInflater mInflater;
    public MessageListAdapter(Context context, List<Message> lists,int message) {
        this.lists = lists;
        this.context = context;
        this.message=message;
        mInflater = LayoutInflater.from(context);
    }
    public void setLists(List<Message> lists) {
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
            convertView = mInflater.inflate(R.layout.lv_message_item_1,null);
            //创建ViewHolder对象
            mHolder = new ViewHolder();
            //找到控件
            mHolder.iv_head=(ImageView) convertView.findViewById(R.id.iv_head);
            mHolder.tv_account=(TextView) convertView.findViewById(R.id.tv_account);
            mHolder.tv_description=(TextView) convertView.findViewById(R.id.tv_description);
            //复用
            convertView.setTag(mHolder);
        }else {
            //2.减少findViewById的次数
            mHolder =(ViewHolder) convertView.getTag();
        }
        //        //3.准备要绑定的数据
        Message msg = lists.get(position);
        //4.组装
        switch (message){
            case 1://我收到的消息
                mHolder.iv_head.setImageBitmap(Tools.decodeBitmapFromRes(context.getResources(),msg.head,50,50));
                mHolder.tv_account.setText(msg.account);
                mHolder.tv_description.setText("联系卖家："+msg.phone);

                break;
            case 2://我发出的消息
                mHolder.iv_head.setImageBitmap(Tools.decodeBitmapFromRes(context.getResources(),msg.head,50,50));
                mHolder.tv_account.setText(msg.account);
                mHolder.tv_description.setText(msg.description);
                break;
            case 3://我的收藏
                mHolder.iv_head.setImageBitmap(Tools.decodeBitmapFromRes(context.getResources(),msg.head,50,50));
                mHolder.tv_account.setText(msg.account);
                mHolder.tv_description.setText(msg.description);
                break;
            case 4://我的
                mHolder.iv_head.setImageBitmap(Tools.decodeBitmapFromRes(context.getResources(),msg.head,50,50));
                mHolder.tv_account.setText(msg.account);
                mHolder.tv_description.setText(msg.description);
                break;
        }

        return convertView;
    }
    private final static class ViewHolder{
        ImageView iv_head;
        TextView tv_account,tv_description;
    }
}
