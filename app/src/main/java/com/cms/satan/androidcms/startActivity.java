package com.cms.satan.androidcms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.litesuits.http.utils.HttpUtil;

import java.util.Date;

public class startActivity extends AppCompatActivity {

    private FrameLayout fl_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        //初始化字体
        initFont();
        fl_main=(FrameLayout)findViewById(R.id.fl_main);

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
        TextView home = (TextView)findViewById(R.id.tv_home);
        TextView pacman = (TextView)findViewById(R.id.tv_pacman);
        TextView user = (TextView)findViewById(R.id.tv_user);
        TextView home2 = (TextView)findViewById(R.id.tv_home2);
        TextView pacman2 = (TextView)findViewById(R.id.tv_pacman2);
        TextView user2 = (TextView)findViewById(R.id.tv_user2);
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
            HttpUtil.showTips(this.getApplicationContext(),"提示","颜色不可用");
        }

    }
}
