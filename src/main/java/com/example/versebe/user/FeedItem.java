package com.example.versebe.user;

public class FeedItem {

    String image_path;
    String id;
    String email;

    String article_num;


    public FeedItem() {
    }

    public FeedItem(String image_path, String id, String email, String article_num) {
        this.image_path = image_path;
        this.id = id;
        this.email = email;
        this.article_num = article_num;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_path() {
        return this.image_path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setDetail(String detail) {
        this.email = email;
    }

    public String getDetail() {
        return this.email;
    }

    public void setPosterNum(String article_num) {
        this.article_num = article_num;
    }

    public String getPosterNum() {
        return this.article_num;
    }

}
