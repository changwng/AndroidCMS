package com.cms.satan.androidcms.common;

import android.content.Context;

import com.litesuits.http.HttpConfig;

/**
 * Created by lzs on 2016/3/29.
 */
public class LiteHttpConfigs {
    private Context currentContext;
    public  HttpConfig config ;
    public LiteHttpConfigs(Context _context)
    {
        currentContext=_context;
        config= new HttpConfig(currentContext) // configuration quickly
                .setDebugged(true)                   // log output when debugged
                .setDetectNetwork(true)              // detect network before connect
                .setDoStatistics(true)               // statistics of time and traffic
                .setUserAgent("Mozilla/5.0 (...)")   // set custom User-Agent
                .setTimeOut(10000, 10000);             // connect and socket timeout: 10s
    }
}
