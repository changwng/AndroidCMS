package com.cms.satan.androidcms.model;

import java.util.ArrayList;

/**
 * Created by lzs on 2016/3/29.
 */
public class News {
    protected String status;
    protected String desc;
    protected ArrayList<Detail> detail;
    public static class Detail
    {
        public String title;
        public String source;
        public String article_url;
        public long publish_time;
        public long behot_time;
        public long create_time;
        public int digg_count;
        public int bury_count;
        public int repin_count;
        public String group_id;
    }
}
