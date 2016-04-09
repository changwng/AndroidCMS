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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuIcon;
import com.cms.satan.androidcms.R;
import com.cms.satan.androidcms.common.MyAdapter;
import com.cms.satan.androidcms.common.MyPagerAdapter;
import com.litesuits.http.utils.HttpUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;


import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class startActivity extends BaseActivity  implements View.OnClickListener,AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    private RelativeLayout rl_leftContainer;
    private TextView home;
    private TextView pacman ;
    private TextView user ;
    private TextView home2;
    private TextView pacman2;
    private TextView user2;
    private ArrayList<String> arrText;
    private ArrayList<Integer> arrIcons;
    private Toolbar bar;
    private MaterialMenuDrawable materialMenu;
    private FragmentHome fragmentHome;
    private FragmentCircle fragmentCircle;
    private FragmentUser fragmentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置工具栏
        bar= (Toolbar) findViewById(R.id.toolbar);
        bar.setTitle("创联资讯");
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
        rl_leftContainer= (RelativeLayout) findViewById(R.id.rl_leftContainer);
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

        //底部按钮点击
        TableRow tr_home= (TableRow) findViewById(R.id.home_click);
        TableRow tr_cycle= (TableRow) findViewById(R.id.cycle_click);
        TableRow tr_user= (TableRow) findViewById(R.id.user_click);
        tr_home.setOnClickListener(this);
        tr_cycle.setOnClickListener(this);
        tr_user.setOnClickListener(this);
        //首页
        init_home();
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

    public void initFont()
    {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconify/icomoon.ttf");
        Typeface iconfont1= Typeface.createFromAsset(getAssets(), "iconify/icomoon1.ttf");
        TextView home = (TextView)findViewById(R.id.tv_home);
        TextView pacman = (TextView)findViewById(R.id.tv_pacman);
        TextView user = (TextView)findViewById(R.id.tv_user);
        home.setTypeface(iconfont1);
        pacman.setTypeface(iconfont1);
        user.setTypeface(iconfont1);

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

    @Override
    public void onClick(View v) {
        android.app.FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId())
        {
            case R.id.home_click:
                init_home();
                setNavColor(1);
                break;
            case R.id.cycle_click:
               init_circle();
                setNavColor(2);
                break;
            case R.id.user_click:
                init_user();
                setNavColor(3);
                break;
        }

    }
    public void init_home()
    {
        android.app.FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
//        if (fragmentHome==null)
//        {
            FragmentHome.fm=getSupportFragmentManager();
            fragmentHome=new FragmentHome();
//        }
        transaction.replace(R.id.fl_content,fragmentHome);
        transaction.commit();
    }
    public void init_circle()
    {
        android.app.FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        if (fragmentCircle==null)
        {
            fragmentCircle=new FragmentCircle();
        }
        transaction.replace(R.id.fl_content,fragmentCircle);
        transaction.commit();
    }
    public void init_user()
    {
        android.app.FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        if (fragmentUser==null)
        {
             fragmentUser=new FragmentUser();
        }
        transaction.replace(R.id.fl_content,fragmentUser);
        transaction.commit();
    }
}
