package com.cms.satan.androidcms.common;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cms.satan.androidcms.R;

import java.util.List;

/**
 * Created by l on 2016/4/2.
 */
public  class MyPagerAdapter extends FragmentStatePagerAdapter
{

    public SuperAwesomeCardFragment superAwesomeCardFragment;
    private String LogTag="MyPagerAdapter";
    private List<SuperAwesomeCardFragment> mList;
    public MyPagerAdapter(FragmentManager fm,List<SuperAwesomeCardFragment> list) {
        super(fm);
        mList=list;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        return MyAppConfig.titles[position];
    }
    @Override
    public Fragment getItem(int position) {
        return  mList.get(position);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
}

