package com.cms.satan.androidcms.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cms.satan.androidcms.R;
import com.cms.satan.androidcms.common.LiteHttpConfigs;
import com.cms.satan.androidcms.model.User;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.param.HttpRichParamModel;
import com.litesuits.http.response.Response;
import com.litesuits.http.utils.HttpUtil;


public class MainActivity extends AppCompatActivity {
    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 1:
                    Intent intent = new Intent(MainActivity.this,startActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //软件版本信息文件路径
    protected final String url="http://192.168.6.133/update.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        Intent intent = new Intent(MainActivity.this,startActivity.class);
        startActivity(intent);
        finish();
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(3000);
                    LiteHttpConfigs _config=new LiteHttpConfigs(MainActivity.this);
                    LiteHttp liteHttp = LiteHttp.newApacheHttpClient(_config.config);
                    @HttpUri(url)
                   class LoginParam extends HttpRichParamModel<User> {
//                        private String name;
//                        private String password;
//                        public LoginParam(String name, String password) {
//                            this.name = name;
//                            this.password = password;
//                        }
                    }
                    liteHttp.executeAsync(new LoginParam().setHttpListener(
                            new HttpListener<User>() {
                                @Override
                                public void onSuccess(User user, Response<User> response) {
                                    //HttpUtil.showTips(MainActivity.this, "对象自动转化", user.Version.toString());
                                    PackageManager manager = getApplicationContext().getPackageManager();
                                    String pName = "com.cms.satan.androidcms";
                                    String versionName = "";
                                    //int versionCode = 0;
                                    try {
                                        PackageManager pm = getApplicationContext().getPackageManager();

                                        PackageInfo pinfo = pm.getPackageInfo(pName, PackageManager.GET_CONFIGURATIONS);
                                        versionName = pinfo.versionName;
                                        //versionCode = pinfo.versionCode;
                                    }
                                    catch (PackageManager.NameNotFoundException e) {
                                        Log.d("MainActivityError",e.getMessage());
                                    }
                                    if (user.Version .equals(versionName) ) {
                                        //已是最新的版本
                                        //启动首页
                                        Message msg = new Message();
                                        msg.what = 1;
                                        mHandler.sendMessage(msg);
                                    }
                                    else
                                    {
                                        //下载最新的版本
                                        HttpUtil.showTips(MainActivity.this, "下载最新的版本", user.Version);
                                    }
                                }

                                @Override
                                public void onLoading(AbstractRequest<User> request, long total, long len) {
                                    super.onLoading(request, total, len);
                                }

                                @Override
                                public void onFailure(HttpException e, Response<User> response) {
                                    super.onFailure(e, response);
                                    Log.d("MainActivityError", e.getMessage());
                                    HttpUtil.showTips(MainActivity.this, "启动失败",  e.getMessage());
                                }

                                @Override
                                public void onEnd(Response<User> response) {
                                    super.onEnd(response);
                                }
                            }
                    ));

                } catch (InterruptedException e) {
                    Log.d("MainActivityError",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        //thread.start();
    }
}
