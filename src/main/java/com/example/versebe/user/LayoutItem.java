package com.example.versebe.user;

import javax.security.auth.callback.CallbackHandler;

public class LayoutItem {

    private String type;
    private String thumb_image;
    private String user_id;
    private String update_date;
    private String last_date;
    private int article_num;
    private String hash_tag;
    private String poster_image;
    private String title;






    public LayoutItem(String type,String thumb_image, String user_id, String update_date,String last_date,
                    String poster_image,String hash_tag,int article_num, String title) {

        this.type=type;

        this.thumb_image = thumb_image;
        this.user_id = user_id;
        this.update_date = update_date;
        this.last_date = last_date;
        this.poster_image = poster_image;
        this.hash_tag = hash_tag;
        this.article_num = article_num;
        this.title = title;

    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType(){
        return this.type;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
    public String getThumb_image() {
        return this.thumb_image;
    }

    public void setUser_id(String id) {
        this.user_id = user_id;
    }
    public String getUser_id() { return this.user_id; }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
    public String getUpdate_date() { return this.update_date; }

    public void setLast_date(String update_date) {
        this.last_date = last_date;
    }
    public String getLast_date() { return this.last_date; }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }
    public String getPoster_image() { return this.poster_image; }

    public void setHash_tag(String hash_tag) {
        this.hash_tag = hash_tag;
    }
    public String getHash_tag() { return this.hash_tag; }

    public void setArticle_num(int article_num) {
        this.article_num = article_num;
    }
    public int getArticle_num() {
        return this.article_num;
    }

    public void setTitle(String detail) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }
}
