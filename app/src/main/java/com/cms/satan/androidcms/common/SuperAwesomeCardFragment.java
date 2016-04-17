package com.cms.satan.androidcms.common;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cms.satan.androidcms.R;
import com.cms.satan.androidcms.bll.NewsBLL;
import com.cms.satan.androidcms.model.News;
import com.cms.satan.androidcms.ui.NewInfoActivity;
import com.cms.satan.androidcms.utils.NetSources;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by l on 2016/4/2.
 */
public class SuperAwesomeCardFragment extends Fragment {
    private static final String ARG_POSITION = "newsType";
    private int newsType;
    TextView _tv;
    private String LogTag="SuperAwesomeCardFragment";
    private int pageSize=10;

    public static SuperAwesomeCardFragment newInstance(int newsType) {
        SuperAwesomeCardFragment fragment = new SuperAwesomeCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION,newsType);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsType=getArguments().getInt(ARG_POSITION);
        currentPage=0;
        newsBLL=new NewsBLL(getActivity());
    }
    public  ListView _lv;
    public PtrFrameLayout  ptrFrame;
    private MaterialHeader header;
    public MyNewsAdapter adapter;
    public  boolean isScrollTop=false;
    public ProgressBar pb;
    public boolean isForce;             //是否强制刷新
    public boolean isNetAvailable;     //是否有网络
    public int currentPage;             //当前页
    public boolean isFirstLoad;           //是否第一次加载
    public List<News> newsItems=new ArrayList<News>();
    public NewsBLL newsBLL;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_card,container,false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        ptrFrame= (PtrFrameLayout) rootView.findViewById(R.id.ptr_frame);
        Activity parentActivity = getActivity();

        header = new MaterialHeader(parentActivity.getBaseContext());

        header.setPadding(0, 20, 0, 20);
        header.setPtrFrameLayout(ptrFrame);


        ptrFrame.setLoadingMinTime(1000);
        ptrFrame.setDurationToCloseHeader(300);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);

        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                return _lv.getFirstVisiblePosition() == 0;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
                GetNewsInfo(adapter, currentPage, true);
            }
        });
        _lv= (ListView) rootView.findViewById(R.id.lv_news);
        adapter = new MyNewsAdapter(getActivity());
        adapter.addNews(new ArrayList<News>());
        _lv.setAdapter(adapter);
        _lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity().getApplicationContext(),NewInfoActivity.class);
                        intent.putExtra("url", newsItems.get(position).getArticle_url());
                        intent.putExtra("source", newsItems.get(position).getSource());
                        startActivity(intent);
                    }
                }
        );
        //得到数据
        GetNewsInfo(adapter, currentPage, false);
    }

    private void GetNewsInfo( MyNewsAdapter adapter, int currentPage, boolean isForce) {
        int total = newsItems.size();
        if (!isForce && total > 0 && newsItems.get(newsItems.size() - 1).getPage_index() >= currentPage) {
            //不强制刷新时，如果此页已存在则直接从内存中加载
            adapter.addNews(newsItems);
            adapter.notifyDataSetChanged();
            return;
        }
        if (isForce && newsItems.size() > 0) {
            newsItems.clear();
        }
        LoadNewsListTask task=new LoadNewsListTask(adapter,newsType,isForce);
        task.execute(currentPage);
    }
    class LoadNewsListTask extends AsyncTask<Integer, Integer,List<News> > {
        public MyNewsAdapter myNewsAdapter;
        public int type;
        public boolean forced;
        public LoadNewsListTask(MyNewsAdapter mAdapter,int type,boolean forced)
        {
            myNewsAdapter=mAdapter;
            this.type=type;
            this.forced=forced;
        }
        @Override
        protected List<News> doInBackground(Integer... params) {
            List<News> _templist=new ArrayList<News>();
            try {
                     isNetAvailable=NetSources.IsNetAvailable(getActivity());
                    if (isNetAvailable&&isFirstLoad)
                    {
                        isFirstLoad=false;
                        return newsBLL.GetNewsCache(type,params[0],true);
                    }
                   _templist=newsBLL.GetNewsItems(type,params[0],isNetAvailable);
                }
            catch (SQLException e) {
                e.printStackTrace();

            }
            finally {
                return _templist;
            }
        }

        @Override
        protected void onPostExecute(List<News> newses) {
            if (forced)
            {
                myNewsAdapter.getNewsList().clear();
            }
            newsItems.addAll(newses);
            myNewsAdapter.addNews(newsItems);
            myNewsAdapter.notifyDataSetChanged();
            ptrFrame.refreshComplete();
        }
    }
}

