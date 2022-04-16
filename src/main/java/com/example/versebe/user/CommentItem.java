package com.example.versebe.user;

public class CommentItem {

    String image_path;
    String id;
    String comment;


    public CommentItem() {
    }

    public CommentItem(String image_path, String id, String comment) {
        this.image_path = image_path;
        this.id = id;
        this.comment = comment;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return this.comment;
    }

}
