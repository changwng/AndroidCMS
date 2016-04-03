package com.cms.satan.androidcms.common;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cms.satan.androidcms.R;

import java.util.ArrayList;

/**
 * Created by lzs on 2016/3/30.
 */
public class MyAdapter extends BaseAdapter
{
    public ArrayList<Integer> arr_Icon=new ArrayList<Integer>();
    public ArrayList<String> arr_text=new ArrayList<String>();
    public Context context;
    public LayoutInflater layoutInflater;
    public MyAdapter(Context cxt,ArrayList<Integer> arr1,ArrayList<String> arr2)
    {
        this.context=cxt;
        this.arr_Icon=arr1;
        this.arr_text=arr2;
        this.layoutInflater= LayoutInflater.from(context);
    }
    public final class Zujian{
        public TextView icons;
        public TextView title;
    }
    @Override
    public int getCount() {
        return arr_Icon.size();
    }

    @Override
    public Object getItem(int position) {
        return arr_text.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian=null;
        if (convertView==null)
        {
            zujian=new Zujian();
            convertView=layoutInflater.inflate(R.layout.list, null);
            zujian.icons= (TextView) convertView.findViewById(R.id.icons);
            zujian.title=(TextView)convertView.findViewById(R.id.title);
            convertView.setTag(zujian);
        }
        else
        {
            zujian=(Zujian)convertView.getTag();
        }
        zujian.icons.setText(arr_Icon.get(position));
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconify/icomoon1.ttf");
        zujian.icons.setTypeface(iconfont);
        zujian.title.setText(arr_text.get(position));
        return convertView;
    }
}
