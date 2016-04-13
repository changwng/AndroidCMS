package com.cms.satan.androidcms.ui;

import android.content.Context;
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
import com.cms.satan.androidcms.widget.RoundImageView;
import com.litesuits.http.utils.HttpUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;


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
    private TextView home_image;
    private TextView home_text ;
    private TextView video_image ;
    private TextView video_text;
    private TextView collection_image;
    private TextView collection_text;
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
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, true);
        // 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
        // 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
        // 具体可参考详细的开发指南
        // 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context,new XGIOperateCallback()
        {
            @Override
            public void onSuccess(Object o, int i) {
                Log.d("TPUSH",o.toString());
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Log.d("TPUSH",i+s);
            }
        });

// 2.36（不包括）之前的版本需要调用以下2行代码
//        Intent service = new Intent(context, XGPushService.class);
//        context.startService(service);


// 其它常用的API：
// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
// 反注册（不再接收消息）：unregisterPush(context)
// 设置标签：setTag(context, tagName)
// 删除标签：deleteTag(context, tagName)
        //设置工具栏
        bar= (Toolbar) findViewById(R.id.toolbar);
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
        RoundImageView user_logo= (RoundImageView) findViewById(R.id.w_user_logo);
        user_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "功能正在开发中!", Toast.LENGTH_LONG);
            }
        });
        //设置抽屉效果
        initDrawer();


        home_image = (TextView)findViewById(R.id.tv_home_image);
        home_text = (TextView)findViewById(R.id.tv_home_text);
        video_image = (TextView)findViewById(R.id.tv_video_image);
        video_text = (TextView)findViewById(R.id.tv_video_text);
        collection_image = (TextView)findViewById(R.id.tv_collection_image);
        collection_text = (TextView)findViewById(R.id.tv_collection_text);
        //初始化字体
        initFont();
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
        arrIcons.add(R.string.icons_home_normal);
        arrIcons.add(R.string.icons_video_mormal);
        arrIcons.add(R.string.icons_collection_normal);
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
        home_image.setTypeface(iconfont);
        video_image.setTypeface(iconfont);
        collection_image.setTypeface(iconfont);

    }
    public void setTextProperty(TextView tv,int color,int text)
    {
        tv.setTextColor(this.getResources().getColor(color));
        tv.setText(text);
    }

    public void setNavColor(int i)
    {
        try
        {
            switch (i)
            {
                case 1:
                    setTextProperty(home_image,R.color.colorIcons_Image_focus,R.string.icons_home_focus);
                    setTextProperty(home_text,R.color.colorIcons_text_focus,R.string.text_home);
                    setTextProperty(video_image,R.color.colorIcons_Image_normal,R.string.icons_video_mormal);
                    setTextProperty(video_text,R.color.colorIcons_text_normal,R.string.text_video);
                    setTextProperty(collection_image,R.color.colorIcons_Image_normal,R.string.icons_collection_normal);
                    setTextProperty(collection_text,R.color.colorIcons_text_normal,R.string.text_collection);
                    break;
                case 2:
                    setTextProperty(home_image, R.color.colorIcons_Image_normal, R.string.icons_home_normal);
                    setTextProperty(home_text, R.color.colorIcons_text_normal, R.string.text_home);
                    setTextProperty(video_image, R.color.colorIcons_Image_focus, R.string.icons_video_focus);
                    setTextProperty(video_text, R.color.colorIcons_text_focus, R.string.text_video);
                    setTextProperty(collection_image, R.color.colorIcons_Image_normal, R.string.icons_collection_normal);
                    setTextProperty(collection_text, R.color.colorIcons_text_normal, R.string.text_collection);
                    break;
                case 3:
                    setTextProperty(home_image, R.color.colorIcons_Image_normal, R.string.icons_home_normal);
                    setTextProperty(home_text, R.color.colorIcons_text_normal, R.string.text_home);
                    setTextProperty(video_image, R.color.colorIcons_Image_normal, R.string.icons_video_mormal);
                    setTextProperty(video_text, R.color.colorIcons_text_normal, R.string.text_video);
                    setTextProperty(collection_image, R.color.colorIcons_Image_focus, R.string.icons_collection_focus);
                    setTextProperty(collection_text, R.color.colorIcons_text_focus, R.string.text_collection);
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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //工具栏的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent =new Intent(startActivity.this,FragmentPreferences.class);
//            startActivity(intent);
//            return true;
//        }
//        if (id==android.R.id.home) {
//
//        }
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
        if (fragmentHome==null)
        {
            FragmentHome.fm=getSupportFragmentManager();
            fragmentHome=new FragmentHome();
        }
        else
        {
            transaction.remove(fragmentHome);
            FragmentHome.fm=getSupportFragmentManager();
            fragmentHome=new FragmentHome();
        }
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
