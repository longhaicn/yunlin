package hnu.fooma.yunlin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.activity.GoodsItemActivity;
import hnu.fooma.yunlin.activity.MassageActivity;

/**
 * Created by Fooma on 2016/4/26.
 */
public class FragmentMessage extends Fragment{
    private View view;
    private TextView tv_receive,tv_sent,tv_collection,tv_record;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = View.inflate(getContext(), R.layout.fragment_message, null);
        findView();
        return view;
    }

    private void findView() {
        tv_receive= (TextView) view.findViewById(R.id.tv_receive);
        tv_sent= (TextView) view.findViewById(R.id.tv_sent);
        tv_collection= (TextView) view.findViewById(R.id.tv_collection);
        tv_record= (TextView) view.findViewById(R.id.tv_record);
        tv_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MassageActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("message",1);
                startActivityForResult(intent1,500);
            }
        });
        tv_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), MassageActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("message",2);
                startActivityForResult(intent2,500);
            }
        });
        tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), MassageActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.putExtra("message",3);
                startActivityForResult(intent3,500);
            }
        });
        tv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(), MassageActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent4.putExtra("message",4);
                startActivityForResult(intent4,500);
            }
        });
    }


}
