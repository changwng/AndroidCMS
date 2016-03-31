package com.cms.satan.androidcms;

import android.content.Intent;
import android.graphics.Color;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cms.satan.androidcms.common.MyAdapter;
import com.cms.satan.androidcms.common.LiteHttpConfigs;
import com.cms.satan.androidcms.common.MyNewsAdapter;
import com.cms.satan.androidcms.model.News;
import com.cms.satan.androidcms.model.User;
import com.litesuits.http.utils.HttpUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.readystatesoftware.systembartint.SystemBarTintManager;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;

public class startActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mNewsList;
    private ArrayList<String> arrText;
    private ArrayList<Integer> arrIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    private LinearLayout ll_leftContainer;
    private SystemBarTintManager tintManager;
    private FrameLayout fl_main;
    private TextView home;
    private TextView pacman ;
    private TextView user ;
    private TextView home2;
    private TextView pacman2;
    private TextView user2;
    protected  String url_news="http://api.1-blog.com/biz/bizserver/news/list.do";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        mTitle = (String) getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mNewsList= (ListView) findViewById(R.id.lv_news);
        ll_leftContainer= (LinearLayout) findViewById(R.id.ll_leftContainer);

        //设置抽屉效果
        initDrawer();
        //初始化字体
        initFont();
        //新闻
        GetHttpNews();
        fl_main=(FrameLayout)findViewById(R.id.fl_main);
        home = (TextView)findViewById(R.id.tv_home);
        pacman = (TextView)findViewById(R.id.tv_pacman);
        user = (TextView)findViewById(R.id.tv_user);
        home2 = (TextView)findViewById(R.id.tv_home2);
        pacman2 = (TextView)findViewById(R.id.tv_pacman2);
        user2 = (TextView)findViewById(R.id.tv_user2);
    }

    private void GetHttpNews() {
        Log.d("startActivity","start_get");
        // 创建客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url_news, new JsonHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onFailure(Throwable e, JSONArray errorResponse) {
                super.onFailure(e, errorResponse);
                Log.d("startActivity", "errorResponse====="+errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                Log.d("startActivity", "statusCode=====" + statusCode);
                if (statusCode == 200){
                try {
                JSONArray array= response.getJSONArray("detail");
                    ArrayList<String> titles=new ArrayList<String>();
                    ArrayList<String> sources=new ArrayList<String>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj=array.getJSONObject(i);
                        Log.d("startActivity", "标题:" + obj.get("title")
                                        + "--------来源：" + obj.get("source")
                                        + "--------url地址：" + obj.get("article_url")
                                        + "--------发布时间：" + obj.get("publish_time")
                                        + "--------收录时间：" + obj.get("behot_time")
                                        + "--------创建时间：" + obj.get("create_time")
                                        + "--------赞的次数：" + obj.get("digg_count")
                                        + "--------踩的次数：" + obj.get("bury_count")
                                        + "--------收藏次数：" + obj.get("repin_count")
                                        + "--------新闻id：" + obj.get("group_id")
                        );
                    titles.add(obj.get("title").toString());
                    sources.add(obj.get("source").toString());
                   // Toast.makeText(startActivity.this,response.toString(),Toast.LENGTH_LONG).show();

                }
                    MyNewsAdapter adapter=new MyNewsAdapter(startActivity.this,null,titles,sources);
                    mNewsList.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                        e.printStackTrace();
                }
            }
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
        arrText.add("百度");
        arrText.add("豆瓣");
        arrText.add("谷歌");
        MyAdapter adapter=new MyAdapter(startActivity.this,arrIcons,arrText);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.office, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                int color = Color.argb(0, 200, 200, 200);
                tintManager.setTintColor(color);
                try {
                    getActionBar().setTitle("请选择");
                }
                catch (Exception ex)
                {

                }
                invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                try {
                    getActionBar().setTitle(mTitle);
                }
                catch (Exception ex)
                {

                }
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void home_click(View view)
    {
        setNavColor(1);
        View v=getLayoutInflater().inflate(R.layout.home_layout, null);
        // View v= View.inflate(getApplicationContext(),R.layout.user_layout,null);
        fl_main.removeAllViews();
        fl_main.addView(v, FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
    }
    public void pacman_click(View view)
    {
        setNavColor(2);
    }
    public void user_click(View view)
    {
        setNavColor(3);
        View v=getLayoutInflater().inflate(R.layout.user_layout, null);
       // View v= View.inflate(getApplicationContext(),R.layout.user_layout,null);
        fl_main.removeAllViews();
        fl_main.addView(v, FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
