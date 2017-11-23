package hnu.fooma.yunlin.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import hnu.fooma.yunlin.R;
import hnu.fooma.yunlin.activity.GoodsItemActivity;
import hnu.fooma.yunlin.adapter.ListViewAdapter;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.dao.SmallTypeDao;
import hnu.fooma.yunlin.entity.MemberGoods;

/**
 * Created by Fooma on 2016/4/26.
 */
public class FragmentQuery extends Fragment {
    private View view;
    private ListViewAdapter mAdapter;
    private List<MemberGoods> lists;
    private GoodsDao goodsDao;
    private SharedPreferences sp;
    private String mlongitude, mlatitude;
    private EditText et_search;
    private String bigTypeItems[]={"手机/平板","笔记本/台式机","数码/摄像","家居生活","箱包","鞋履","服饰","珠宝饰品","奢侈品","美容美体","文体用品","收藏/文玩","其他"};
    private String smallTypeItems[]={"其他"};
    private String smallType;
    private SmallTypeDao smallTypeDao;
    private ImageView iv_search,iv_search_category;
    private ListView lv_query;
    private int memberId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = View.inflate(getContext(), R.layout.fragment_query, null);
        initView();
        sp=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        mlongitude=sp.getString("mlongitude","0");
        mlatitude=sp.getString("mlatitude","0");
        memberId=sp.getInt("memberId",0);
        goodsDao = new GoodsDao(getActivity());
        smallTypeDao=new SmallTypeDao(getActivity());
        lists=goodsDao.queryMemberGoods(0);
        mAdapter=new ListViewAdapter(getContext(),lists,mlongitude,mlatitude);
        lv_query.setAdapter(mAdapter);
        return view;
    }


    private void initView() {
        et_search= (EditText) view.findViewById(R.id.et_search);
        iv_search= (ImageView) view.findViewById(R.id.iv_search);
        iv_search_category= (ImageView) view.findViewById(R.id.iv_search_category);
        lv_query= (ListView) view.findViewById(R.id.lv_query);

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = et_search.getText().toString().trim();
                lists=goodsDao.searchMemberGoods(memberId,search);
//                lists=goodsDao.queryMemberGoods();
                mAdapter.setLists(lists);
                mAdapter.notifyDataSetChanged();

            }
        });
        iv_search_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请选择大类别：");

                builder.setItems(bigTypeItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        smallTypeItems = smallTypeDao.querySmallType(bigTypeItems[which]);

                        //小类别
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                        builder2.setTitle(bigTypeItems[which]+":");
                        builder2.setItems(smallTypeItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lists=goodsDao.searchMemberGoodsKind(smallTypeItems[which]);
                                mAdapter.setLists(lists);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                        builder2.show();
                    }
                });
                builder.show();


            }
        });
        lv_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int goodsId = lists.get(position).goodsId;
                Intent intent = new Intent(getActivity(),GoodsItemActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("goodsId",goodsId);
                startActivityForResult(intent,888);
//                Toast.makeText(getContext(),position+"====="+lists.get(position).goodsId,Toast.LENGTH_LONG).show();
            }
        });


    }
}
