package hnu.fooma.yunlin.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.activity.CenterUpdateActivity;
import hnu.fooma.yunlin.activity.GoodsItemActivity;
import hnu.fooma.yunlin.activity.SendInforActivity;
import hnu.fooma.yunlin.dao.MemberDao;
import hnu.fooma.yunlin.entity.Member;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/4/26.
 */
public class FragmentMe extends Fragment {
    private SharedPreferences sp;
    private View view;
    private TextView tv_center,tv_account,tv_latitude,tv_longitude,tv_gender,tv_phone;
    private ImageView iv_head;
    private Button btn_logout;
    private int memberId;
    private Member member;
    private MemberDao memberDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = View.inflate(getContext(), R.layout.fragment_me, null);
        initView();
        sp=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        memberId=sp.getInt("memberId",0);
        memberDao=new MemberDao(getActivity());
        member=memberDao.readMember(memberId);
        setView();

        tv_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),CenterUpdateActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent,1);
                getActivity().finish();

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }
    private void setView() {
        iv_head.setImageBitmap(Tools.decodeBitmapFromRes(getContext().getResources(),member.head,100,100));
        tv_account.setText(member.account);
        tv_phone.setText(member.phone);
        tv_gender.setText(member.gender);
        tv_longitude.setText(member.mlongitude);
        tv_latitude.setText(member.mlatitude);
    }

    private void initView() {
        iv_head= (ImageView) view.findViewById(R.id.iv_head);
        tv_center= (TextView) view.findViewById(R.id.tv_center);
        tv_account= (TextView) view.findViewById(R.id.tv_account);
        tv_phone= (TextView) view.findViewById(R.id.tv_phone);
        tv_gender= (TextView) view.findViewById(R.id.tv_gender);
        tv_longitude= (TextView) view.findViewById(R.id.tv_longitude);
        tv_latitude= (TextView) view.findViewById(R.id.tv_latitude);
        btn_logout= (Button) view.findViewById(R.id.btn_logout);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==100){
            getActivity().finish();

        }
    }
}
