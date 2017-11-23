package hnu.fooma.yunlin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;

import hnu.fooma.yunlin.activity.LoginActivity;
import hnu.fooma.yunlin.adapter.ViewPagerAdapter;
import hnu.fooma.yunlin.dao.AdsDao;
import hnu.fooma.yunlin.dao.BigTypeDao;
import hnu.fooma.yunlin.dao.GoodsDao;
import hnu.fooma.yunlin.dao.MemberDao;
import hnu.fooma.yunlin.dao.SmallTypeDao;

public class GuideActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    // 定义ViewPager对象
    private ViewPager viewPager;
    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;
    // 定义一个ArrayList来存放View
    private ArrayList<View> views;
    // 引导图片资源
    private static final int[] pics = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3, R.drawable.guide4};
    // 底部小点的图片
    private ImageView[] points;
    // 记录当前选中位置
    private int currentIndex;
    private Button btn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        btn = (Button) findViewById(R.id.btn);
        initView();
        initData();
        add();
    }
    private void add() {
        MemberDao memberDao = new MemberDao(this);
        memberDao.initMember();
        BigTypeDao bigTypeDao =new BigTypeDao(this);
        bigTypeDao.initBigType();
        SmallTypeDao smallTypeDao = new SmallTypeDao(this);
        smallTypeDao.initSmallType();
        GoodsDao goodsDao =new GoodsDao(this);
        goodsDao.initGoods();
        AdsDao adsDao = new AdsDao(this);
        adsDao.initAds();



    }


    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<View>();
        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // 实例化ViewPager适配器
        vpAdapter = new ViewPagerAdapter(views);
    }

    private void initData() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        viewPager.setAdapter(vpAdapter);
        viewPager.setOnPageChangeListener(this);
        // 初始化底部小点
        initPoint();
    }

    /**
     * 初始化底部小点
     */
    private void initPoint() {
        //找到布局控件
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

        points = new ImageView[pics.length];
        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) linearLayout.getChildAt(i);
            // 默认都设为灰色
            points[i].setEnabled(true);
            // 给每个小点设置监听
            points[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }
        // 设置当面默认的位置
        currentIndex = 0;
        // 设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        if (arg0 == pics.length) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
        }
        if(arg0!=3){
            btn.setVisibility(View.GONE);
        }
        setCurDot(arg0);
    }


    //监听
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        //小圆点监听
        setCurView(position);
        //小圆点位置设置
        setCurDot(position);
    }

    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        viewPager.setCurrentItem(position);
    }
    private void setCurDot(int positon) {

        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            if(positon!=3) {
                btn.setVisibility(View.GONE);
            }
            return;
        }
        if (positon == 3) {
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(GuideActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);
        currentIndex = positon;


    }
}
