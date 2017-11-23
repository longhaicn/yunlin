package hnu.fooma.yunlin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import hnu.fooma.yunlin.activity.SendInforActivity;
import hnu.fooma.yunlin.dao.BigTypeDao;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.dao.MemberDao;
import hnu.fooma.yunlin.dao.SmallTypeDao;
import hnu.fooma.yunlin.entity.Goods;
import hnu.fooma.yunlin.fragment.FragmentHome;
import hnu.fooma.yunlin.fragment.FragmentMe;
import hnu.fooma.yunlin.fragment.FragmentMessage;
import hnu.fooma.yunlin.fragment.FragmentQuery;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private ImageView iv_top_send;
    private LayoutInflater mInflater;
    private String mTabText[] = {"首页", "查询", "消息", "我"};
    private int mImage[] = {R.drawable.tab_selector_home,R.drawable.tab_selector_query,R.drawable.tab_selector_message,R.drawable.tab_selector_me};
    private Class mFragment[] = { FragmentHome.class, FragmentQuery.class, FragmentMessage.class, FragmentMe.class};
    private Fragment[] mFragments = new Fragment[]{new FragmentHome(),new FragmentQuery(),new FragmentMessage(),new FragmentMe()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initData();
        initView();
        setListener();
    }

    private void setListener() {
        iv_top_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SendInforActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent,100);
            }
        });
    }

    private void initData() {
        MemberDao memberDao = new MemberDao(this);
        memberDao.initMember();
        BigTypeDao bigTypeDao =new BigTypeDao(this);
        bigTypeDao.initBigType();
        SmallTypeDao smallTypeDao = new SmallTypeDao(this);
        smallTypeDao.initSmallType();
        GoodsDao goodsDao =new GoodsDao(this);
        goodsDao.initGoods();

    }

    private void initView() {
        mInflater=LayoutInflater.from(this);
        mViewPager = (ViewPager) findViewById(R.id.act_main_view_pager);
        iv_top_send = (ImageView) findViewById(R.id.iv_top_send);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.act_main_view_pager);
        mTabHost.setHorizontalScrollBarEnabled(true);
        int count = mTabText.length;
        for(int i = 0;i < count;i++){
            TabHost.TabSpec tabSpec ;
            if(i == 0){
                //生成一个tab标签，i=0是默认选中的
                tabSpec = mTabHost.newTabSpec(mTabText[i]).setIndicator(getView(i));
            }else{
                tabSpec = mTabHost.newTabSpec(mTabText[i]).setIndicator(getView(i));
            }
            //去除分割线
            mTabHost.getTabWidget().setDividerDrawable(null);
            //给tabspec添加fragment
            mTabHost.addTab(tabSpec,mFragment[i],null);
            //给mTabHost添加点击事件
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_selector_checked_bg);
        }
        //当点击Tab时，用ViewPager对fragment进行切换，否则fragment将会叠加
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int position = mTabHost.getCurrentTab();
                mViewPager.setCurrentItem(position);
            }
        });


        //默认选择第一个
        mTabHost.setCurrentTab(0);
        //四个页面
        mViewPager.setOffscreenPageLimit(4);
        //准备Adapter v4包就用getSupportFragmentManager()
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        //为ViewPager添加事件监听
        mViewPager.addOnPageChangeListener(new ViewPagerListener());
    }
    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.tabspec, null);
        //View view = mInflater.inflate( R.layout.tabspec, null);
        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
        TextView textView = (TextView) view.findViewById(R.id.tv_text);
        //设置图标
        imageView.setImageResource(mImage[i]);
        //设置标题
        textView.setText(mTabText[i]);
        return view;
    }


    /**
     * ViewPager适配器
     * 继承自PagerAdapter，将页面信息持续的保存在fragment manager中，方便用户返回该页面
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(android.support.v4.app.FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }

    /**
     * ViewPager的监听事件
     * 当前选择页面发生变化时的回调接口
     */
    class ViewPagerListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            mTabHost.setCurrentTab(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 100:
                if(data != null) {
                    if (resultCode == 200) {
                        mTabHost.setCurrentTab(0);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
