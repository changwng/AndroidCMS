package com.cms.satan.androidcms.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lzs on 2016/3/29.
 */
@DatabaseTable(tableName = "tb_news")
public class News {
        @DatabaseField(generatedId = true)
        private int id;
        @DatabaseField(columnName ="title")
        private String title;
        @DatabaseField(columnName = "source")
        private String source;
        @DatabaseField(columnName = "article_url")
        private String article_url;
        @DatabaseField(columnName = "publish_time")
        private Date publish_time;
        @DatabaseField(columnName = "behot_time")
        private Date behot_time;
        @DatabaseField(columnName = "create_time")
        private Date create_time;
        @DatabaseField(columnName = "digg_count")
        private int digg_count;
        @DatabaseField(columnName = "bury_count")
        private int bury_count;
        @DatabaseField(columnName = "repin_count")
        private int repin_count;
        @DatabaseField(columnName = "group_id")
        private String group_id;
        @DatabaseField(columnName = "page_index")
        private int page_index;  //页号
        @DatabaseField(columnName ="type" )
        private int type;         //类型

        public int getType() {
                return type;
        }

        public void setType(int type) {
                this.type = type;
        }



        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getSource() {
                return source;
        }

        public void setSource(String source) {
                this.source = source;
        }

        public String getArticle_url() {
                return article_url;
        }

        public void setArticle_url(String article_url) {
                this.article_url = article_url;
        }

        public Date getPublish_time() {
                return publish_time;
        }

        public void setPublish_time(Date publish_time) {
                this.publish_time = publish_time;
        }

        public Date getCreate_time() {
                return create_time;
        }

        public void setCreate_time(Date create_time) {
                this.create_time = create_time;
        }

        public Date getBehot_time() {
                return behot_time;
        }

        public void setBehot_time(Date behot_time) {
                this.behot_time = behot_time;
        }

        public int getDigg_count() {
                return digg_count;
        }

        public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
        }

        public int getBury_count() {
                return bury_count;
        }

        public void setBury_count(int bury_count) {
                this.bury_count = bury_count;
        }

        public int getRepin_count() {
                return repin_count;
        }

        public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
        }

        public String getGroup_id() {
                return group_id;
        }

        public void setGroup_id(String group_id) {
                this.group_id = group_id;
        }

        public int getPage_index() {
                return page_index;
        }

        public void setPage_index(int page_index) {
                this.page_index = page_index;
        }

}
