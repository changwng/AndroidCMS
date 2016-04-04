package com.cms.satan.androidcms.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cms.satan.androidcms.R;
import com.cms.satan.androidcms.ui.NewInfoActivity;
import com.cms.satan.androidcms.utils.NetSources;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by l on 2016/4/2.
 */
public class SuperAwesomeCardFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private int position;
    TextView _tv;
    public static SuperAwesomeCardFragment newInstance(int position) {
        SuperAwesomeCardFragment fragment = new SuperAwesomeCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position=getArguments().getInt(ARG_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_card,container,false);
        //FrameLayout _layout= (FrameLayout) rootView.findViewById(R.id.child_container);
//        //ViewCompat.setElevation(rootView, 50);
//        View childView= inflater.inflate(R.layout.home_layout,container,false);
        final ListView _lv= (ListView) rootView.findViewById(R.id.lv_news);
        final ProgressBar pb= (ProgressBar) rootView.findViewById(R.id.pb_loading);
        pb.setVisibility(View.VISIBLE);
        GetHttpNews(0, 10, 1, new NewsCallBack() {
            @Override
            public void onNewsInfo(ArrayList<String> title, final ArrayList<String> source, final ArrayList<String> url) {
                pb.setVisibility(View.INVISIBLE);
                MyNewsAdapter adapter = new MyNewsAdapter(getActivity().getApplicationContext(), null, title, source);
                _lv.setAdapter(adapter);
                _lv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("Super","_lv_itemclicked");
                                Intent intent = new Intent(getActivity().getApplicationContext(),NewInfoActivity.class);
                                intent.putExtra("url", url.get(position));
                                intent.putExtra("source", source.get(position));
                                startActivity(intent);
                            }
                        }
                );
            }
        });
        return rootView;
    }
    public interface NewsCallBack {
        public void onNewsInfo(ArrayList<String> title,ArrayList<String> source,ArrayList<String> url);
    }
    //获取新闻API
    private void GetHttpNews(int type,int pageSize,int pageIndex, final NewsCallBack callBacks) {
        String _url="";
        switch (type)
        {
            case 0:
                _url= NetSources.Urls[0];
                default:
                    _url=NetSources.Urls[0];
        }
        // 创建客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(_url, new JsonHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onFailure(Throwable e, JSONArray errorResponse) {
                super.onFailure(e, errorResponse);
                Log.d("startActivity", "errorResponse=====" + errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                if (statusCode == 200) {
                    ArrayList<String> titles = new ArrayList<String>();
                    ArrayList<String> sources = new ArrayList<String>();
                    ArrayList<String> urls = new ArrayList<String>();
                    try {
                        JSONArray array = response.getJSONArray("detail");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
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
                            urls.add(obj.get("article_url").toString());
                        }
                        callBacks.onNewsInfo(titles, sources,urls);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

