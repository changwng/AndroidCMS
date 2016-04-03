package com.cms.satan.androidcms.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by l on 2016/4/3.
 */
public class BaseActivity  extends AppCompatActivity {
    protected final int CURRENT_VERSION = Build.VERSION.SDK_INT;
    protected final int VERSION_KITKAT = Build.VERSION_CODES.KITKAT;
    protected final int VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    private SystemBarTintManager tintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //填充窗体标题栏颜色
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        int color = Color.argb(0, 200, 200, 200);
        tintManager.setTintColor(color);
    }
    /**
     * 是否将导航栏以及状态栏设为透明（API大于19小与21)
     * @return
     */
    protected boolean isNavBarTransparent(){
        return CURRENT_VERSION >= VERSION_KITKAT && VERSION_LOLLIPOP > CURRENT_VERSION;
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId>0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * 获取navigation bar的高度
     * @return
     */
    protected int getNavigationBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height","dimen","android");
        if (resourceId>0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
