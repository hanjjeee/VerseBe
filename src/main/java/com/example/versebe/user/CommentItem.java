package com.example.versebe.user;

public class CommentItem {

    private int article_num;
    private String type;
    private String content;
    private String user_id;

    private String content0;

    private String image_path;



    public CommentItem() {
    }

    public CommentItem(int article_num, String type, String content, String user_id, String image_path, String content0) {

        this.article_num = article_num;
        this.type = type;
        this.content = content;
        this.user_id = user_id;

        this.content0 = content0;

        this.image_path = image_path;
    }


    public void setArticle_num(int article_num){ this.article_num=article_num; }
    public int getArticle_num(){ return this.article_num; }

    public void setType(String type){ this.type= type; }
    public String getType(){ return this.type; }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return this.content;
    }

    public void setId(String user_id) {
        this.user_id = user_id;
    }
    public String getId() {
        return this.user_id;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    public String getImage_path() {
        return this.image_path;
    }

    public void setContent0(String content0) {
        this.content0 = content0;
    }
    public String getContent0() {
        return this.content0;
    }

}
