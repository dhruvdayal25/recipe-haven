package com.recipe.model;

import java.sql.Timestamp;

public class Comment {
    private String author, commentText;
    private Timestamp createdAt;
    public Comment(String author, String commentText, Timestamp createdAt) {
        this.author=author; this.commentText=commentText; this.createdAt=createdAt;
    }
    public String getAuthor(){return author;} public String getCommentText(){return commentText;}
    public Timestamp getCreatedAt(){return createdAt;}
}
