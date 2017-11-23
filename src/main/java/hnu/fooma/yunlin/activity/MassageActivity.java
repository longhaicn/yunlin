package hnu.fooma.yunlin.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.adapter.MessageListAdapter;
import hnu.fooma.yunlin.dao.CollectionDao;
import hnu.fooma.yunlin.dao.MessageDao;
import hnu.fooma.yunlin.dao.WantDao;
import hnu.fooma.yunlin.entity.Collection;
import hnu.fooma.yunlin.entity.Message;
import hnu.fooma.yunlin.entity.Want;

/**
 * Created by Fooma on 2016/5/2.
 */
public class MassageActivity extends Activity {
    private MessageListAdapter mAdapter;
    private MessageDao messageDao;
    private List<Message> lists;
    private SharedPreferences sp;
    private int memberId;
    private ListView lv_message;
    private ImageView iv_top_return;
    private TextView tv_second_top_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        sp = getSharedPreferences("config", this.MODE_PRIVATE);
        this.memberId=sp.getInt("memberId",404);
        messageDao=new MessageDao(this);
        lv_message = (ListView) findViewById(R.id.lv_message);
        iv_top_return = (ImageView) findViewById(R.id.iv_top_return);
        tv_second_top_title= (TextView) findViewById(R.id.tv_second_top_title);
        tv_second_top_title.setText("消息");
        Intent intent = getIntent();
        final int message = intent.getIntExtra("message", 0);//0代表没有，1代表我收到的消息，2代表我发出的消息，3代表我的收藏
//        Toast.makeText(MassageActivity.this,"===="+memberId,Toast.LENGTH_LONG).show();

        switch (message){
            case 1:
                lists=messageDao.qureyWanted(memberId);
                this.mAdapter=new MessageListAdapter(MassageActivity.this,lists,message);
                lv_message.setAdapter(mAdapter);
                lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String c = lists.get(position).phone;
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + c));
                        if (ActivityCompat.checkSelfPermission(MassageActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MassageActivity.this, "没有权限", Toast.LENGTH_LONG).show();
                            return;
                        }
                        startActivity(intent);

                    }
                });
                break;
            case 2:
                lists=messageDao.qureyWant(memberId);
                this.mAdapter=new MessageListAdapter(MassageActivity.this,lists,message);
                lv_message.setAdapter(mAdapter);
                lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int goodsId = lists.get(position).goodsId;
                        Intent intent = new Intent(MassageActivity.this,GoodsItemActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("goodsId",goodsId);
                        startActivityForResult(intent,500);


                    }
                });
                break;
            case 3:
                lists=messageDao.qureyCollection(memberId);
                this.mAdapter=new MessageListAdapter(MassageActivity.this,lists,message);
                lv_message.setAdapter(mAdapter);
                lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int goodsId = lists.get(position).goodsId;
                        Intent intent = new Intent(MassageActivity.this,GoodsItemActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("goodsId",goodsId);
                        startActivityForResult(intent,500);

                    }
                });
                break;
            case 4:
                lists=messageDao.qureyGoods(memberId);
                this.mAdapter=new MessageListAdapter(MassageActivity.this,lists,message);
                lv_message.setAdapter(mAdapter);
                lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final AlertDialog.Builder build = new AlertDialog.Builder(MassageActivity.this);
                        build.setTitle("您确定要删除？");
                        build.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int goodsId = lists.get(position).goodsId;
                                lists.remove(position);
                                messageDao.deleteGoods(goodsId);
                                mAdapter.setLists(lists);
                                mAdapter.notifyDataSetChanged();

                            }
                        });
                        build.show();
                    }
                });
                break;
        }

        iv_top_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
