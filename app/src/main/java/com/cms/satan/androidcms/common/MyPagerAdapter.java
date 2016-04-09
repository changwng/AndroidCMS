package com.cms.satan.androidcms.common;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.cms.satan.androidcms.R;
/**
 * Created by l on 2016/4/2.
 */
public  class MyPagerAdapter extends FragmentPagerAdapter
{
    public String[] titles={"新闻动态", "科技资讯", "天天美女", "笑话大全", "爱情故事"};
    public SuperAwesomeCardFragment superAwesomeCardFragment;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        //改变字体颜色
        //先构造SpannableString
        SpannableString spanString = new SpannableString(titles[position]);
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, 0, titles[position].length() - 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spanString;
    }
    @Override
    public Fragment getItem(int position) {
        superAwesomeCardFragment =SuperAwesomeCardFragment.newInstance(position);
        return  superAwesomeCardFragment;
    }
    public boolean checkCanDoRefresh()
    {
        return true;
    }
    public void updateData()
    {
        if (superAwesomeCardFragment!=null)
        {
            superAwesomeCardFragment.updateData();
        }
    }
    @Override
    public int getCount() {
        return titles.length;
    }
}

