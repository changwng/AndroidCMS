package com.cms.satan.androidcms.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuIcon;
import com.cms.satan.androidcms.R;
import com.cms.satan.androidcms.common.MyAdapter;
import com.cms.satan.androidcms.common.MyPagerAdapter;
import com.litesuits.http.utils.HttpUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;


import java.util.ArrayList;

public class startActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mNewsList;
    private ArrayList<String> arrText;
    private ArrayList<Integer> arrIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    private RelativeLayout rl_leftContainer;
    private TextView home;
    private TextView pacman ;
    private TextView user ;
    private TextView home2;
    private TextView pacman2;
    private TextView user2;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private Toolbar bar;
    private MaterialMenuDrawable materialMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置工具栏
        bar= (Toolbar) findViewById(R.id.toolbar);
        bar.setTitle("点滴移动");
        setSupportActionBar(bar);
        bar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    }
                }
        );
        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        bar.setNavigationIcon(materialMenu);

        ViewGroup mDrawer = (ViewGroup) findViewById(R.id.left_drawer);
        ViewGroup mMainContent = (ViewGroup) findViewById(R.id.main_content);
        //因为导航栏透明，要让出顶部和底部空间
        if (isNavBarTransparent()) {
            mMainContent.setPadding(0, getStatusBarHeight(), 0, getNavigationBarHeight());
            mDrawer.setPadding(0, 0, 0, getNavigationBarHeight());
        }
        //抽屉控件
        mTitle = (String) getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.lv_drawer);
        mNewsList= (ListView) findViewById(R.id.lv_news);
        rl_leftContainer= (RelativeLayout) findViewById(R.id.rl_leftContainer);

        //可滑动卡式控件创建
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
       // tabs.setBackgroundResource(R.color.colorWhite);
        tabs.setIndicatorColorResource(R.color.colorPrimary);
        tabs.setTabBackground(R.color.colorBuleBig);
        tabs.setTextColor(R.color.colorBlack);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
        final int pageMargin=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(1);
        tabs.setOnTabReselectedListener((new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                pager.setCurrentItem(position);
            }
        }));

        //设置抽屉效果
        initDrawer();
        //初始化字体
        initFont();

        home = (TextView)findViewById(R.id.tv_home);
        pacman = (TextView)findViewById(R.id.tv_pacman);
        user = (TextView)findViewById(R.id.tv_user);
        home2 = (TextView)findViewById(R.id.tv_home2);
        pacman2 = (TextView)findViewById(R.id.tv_pacman2);
        user2 = (TextView)findViewById(R.id.tv_user2);

        //侧滑设置
        RelativeLayout _rlsetting= (RelativeLayout) findViewById(R.id.rl_bottom);
        _rlsetting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(startActivity.this,FragmentPreferences.class);
                startActivity(intent);
            }
        });
    }

    public void initDrawer()
    {
        arrIcons=new ArrayList<Integer>();
        arrIcons.add(R.string.icon_user2);
        arrIcons.add(R.string.icon_twitter);
        arrIcons.add(R.string.icon_cycle);
        arrText=new ArrayList<String>();
        arrText.add("用户资料");
        arrText.add("分享应用");
        arrText.add("关于我们");
        MyAdapter adapter=new MyAdapter(startActivity.this,arrIcons,arrText);
        mDrawerList.setAdapter(adapter);

        //设置侧滑菜单的监听
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {
                materialMenu.setTransformationOffset(MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        v);
            }

            @Override
            public void onDrawerOpened(View view) {
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER);
            }

            @Override
            public void onDrawerClosed(View view) {
                materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        mDrawerList.setOnItemClickListener(this);
    }

    public void home_click(View view)
    {
        setNavColor(1);
        View v=getLayoutInflater().inflate(R.layout.home_layout, null);
    }
    public void pacman_click(View view)
    {
        setNavColor(2);
    }
    public void user_click(View view)
    {
        setNavColor(3);
        View v=getLayoutInflater().inflate(R.layout.user_layout, null);
    }
    public void initFont()
    {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconify/icomoon.ttf");
        TextView home = (TextView)findViewById(R.id.tv_home);
        TextView pacman = (TextView)findViewById(R.id.tv_pacman);
        TextView user = (TextView)findViewById(R.id.tv_user);
        home.setTypeface(iconfont);
        pacman.setTypeface(iconfont);
        user.setTypeface(iconfont);

    }
    public void setNavColor(int i)
    {
        try
        {
            switch (i)
            {
                case 1:
                    home.setTextColor(this.getResources().getColor(R.color.colorIconsFocus));
                    home2.setTextColor(this.getResources().getColor(R.color.colorIconsFocus2));
                    pacman.setTextColor(this.getResources().getColor(R.color.colorIconsBlur));
                    pacman2.setTextColor(this.getResources().getColor(R.color.colorIconsBlur2));
                    user.setTextColor(this.getResources().getColor(R.color.colorIconsBlur));
                    user2.setTextColor(this.getResources().getColor(R.color.colorIconsBlur2));
                    break;
                case 2:
                    home.setTextColor(this.getResources().getColor(R.color.colorIconsBlur));
                    home2.setTextColor(this.getResources().getColor(R.color.colorIconsBlur2));
                    pacman.setTextColor(this.getResources().getColor(R.color.colorIconsFocus));
                    pacman2.setTextColor(this.getResources().getColor(R.color.colorIconsFocus2));
                    user.setTextColor(this.getResources().getColor(R.color.colorIconsBlur));
                    user2.setTextColor(this.getResources().getColor(R.color.colorIconsBlur2));
                    break;
                case 3:
                    home.setTextColor(this.getResources().getColor(R.color.colorIconsBlur));
                    home2.setTextColor(this.getResources().getColor(R.color.colorIconsBlur2));
                    pacman.setTextColor(this.getResources().getColor(R.color.colorIconsBlur));
                    pacman2.setTextColor(this.getResources().getColor(R.color.colorIconsBlur2));
                    user.setTextColor(this.getResources().getColor(R.color.colorIconsFocus));
                    user2.setTextColor(this.getResources().getColor(R.color.colorIconsFocus2));
                    break;
            }
        }
        catch (Exception ex)
        {
            HttpUtil.showTips(this.getApplicationContext(), "提示", "颜色不可用");
        }

    }
    //抽屉的ListView的一项点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position)
            {
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    Intent intent=new Intent(startActivity.this,AbortUs.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //工具栏的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent =new Intent(startActivity.this,FragmentPreferences.class);
            startActivity(intent);
            return true;
        }
        if (id==android.R.id.home) {

        }
        return super.onOptionsItemSelected(item);
    }
}
