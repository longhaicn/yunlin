package hnu.fooma.yunlin.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.activity.GoodsItemActivity;
import hnu.fooma.yunlin.adapter.ListViewAdapter;
import hnu.fooma.yunlin.dao.AdsDao;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.entity.MemberGoods;
import hnu.fooma.yunlin.utils.Tools;

/**
 * Created by Fooma on 2016/4/26.
 */
public class FragmentHome extends Fragment {
    private View view;
    private ListView lv_home;
    private ListViewAdapter mAdapter;
    private List<MemberGoods> lists;
    private GoodsDao goodsDao;
    private int memberId;
    private SharedPreferences sp;
    private String mlongitude, mlatitude;
    private TextView tv_fresh,tv_ads_set,tv_ads;
    private int i =0;
    private int GOODS_ITEM=888;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.view = View.inflate(getContext(), R.layout.fragment_home, null);
        sp=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mlongitude=sp.getString("mlongitude","0");
        mlatitude=sp.getString("mlatitude","0");
        this.memberId=sp.getInt("memberId",0);
        goodsDao = new GoodsDao(getActivity());
        lists=goodsDao.queryMemberGoods(memberId);
        final AdsDao adsDao = new AdsDao(getActivity());
        final List<String> list = adsDao.queryAds();
        initView();
        mAdapter=new ListViewAdapter(getContext(),lists,mlongitude,mlatitude);
        lv_home.setAdapter(mAdapter);
        Tools.setListViewHeightBasedOnChildren(lv_home);
        tv_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lists=goodsDao.queryMemberGoods(memberId);
                mAdapter.setLists(lists);
                mAdapter.notifyDataSetChanged();
            }
        });
        tv_ads_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i<list.size()){
                    tv_ads.setText(list.get(i));
                    i++;
                }else {
                    tv_ads.setText(list.get(i-1));
                    i=0;
                }
            }
        });
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int goodsId = lists.get(position).goodsId;
                Intent intent = new Intent(getActivity(),GoodsItemActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("goodsId",goodsId);
                startActivityForResult(intent,GOODS_ITEM);
//                Toast.makeText(getContext(),position+"====="+lists.get(position).goodsId,Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void initView() {
        lv_home= (ListView) view.findViewById(R.id.lv_home);
        tv_fresh= (TextView) view.findViewById(R.id.tv_fresh);
        tv_ads_set= (TextView) view.findViewById(R.id.tv_ads_set);
        tv_ads= (TextView) view.findViewById(R.id.tv_ads);

    }

}
