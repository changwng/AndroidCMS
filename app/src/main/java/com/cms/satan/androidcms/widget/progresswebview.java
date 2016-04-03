package com.cms.satan.androidcms.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by l on 2016/4/3.
 */
@SuppressWarnings("deprecation")
public class progresswebview extends WebView{
    private ProgressBar progressBar;

    public progresswebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar=new ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 10, 0, 0));
        addView(progressBar);
        setWebChromeClient(new WebChromeClient());
    }
    public class WebChromeClient extends android.webkit.WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100)
            {
                progressBar.setVisibility(GONE);
            }
            else
            {
                if (progressBar.getVisibility()==GONE)
                {
                    progressBar.setVisibility(VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp= (LayoutParams) progressBar.getLayoutParams();
        lp.x=1;
        lp.y=t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
