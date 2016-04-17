package com.cms.satan.androidcms.common;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cms.satan.androidcms.R;
import com.cms.satan.androidcms.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzs on 2016/3/30.
 */
public class MyNewsAdapter extends BaseAdapter
{
    //当前显示的数据
    public List<News> newsitems=new ArrayList<News>();
    public Context context;
    public LayoutInflater layoutInflater;
    public MyNewsAdapter(Context cxt)
    {
        this.context=cxt;
        this.layoutInflater= LayoutInflater.from(context);
    }

    public List<News> getNewsList() {
        return newsitems;
    }

    public final class ViewHolder{
        public ImageView icons;
        public TextView title;
        public TextView source;
    }
    /**
     * 添加新闻列表
     * @param news 要添加的新闻列表
     */
    public void addNews(List<News> news){
        newsitems.addAll(news);
    }
    @Override
    public int getCount() {
        return newsitems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.list_news, null);
            //zujian.icons= (TextView) convertView.findViewById(R.id.icons);
            viewHolder.title=(TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.source=(TextView)convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //zujian.icons.setText(arr_Icon.get(position));
       // Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconify/icomoon1.ttf");
        //zujian.icons.setTypeface(iconfont);
        viewHolder.title.setText(newsitems.get(position).getTitle());
        viewHolder.source.setText(newsitems.get(position).getSource());
        return convertView;
    }
}
