package com.recipe.model;

import java.sql.Timestamp;

public class Recipe {
    private long recipeId, userId;
    private String author, title, description, ingredients, instructions, imageUrl, status;
    private Timestamp createdAt;
    public Recipe(long recipeId, long userId, String author, String title, String description,
                  String ingredients, String instructions, String imageUrl, String status, Timestamp createdAt) {
        this.recipeId=recipeId; this.userId=userId; this.author=author; this.title=title; this.description=description;
        this.ingredients=ingredients; this.instructions=instructions; this.imageUrl=imageUrl; this.status=status; this.createdAt=createdAt;
    }
    public long getRecipeId(){return recipeId;} public long getUserId(){return userId;}
    public String getAuthor(){return author;} public String getTitle(){return title;}
    public String getDescription(){return description;} public String getIngredients(){return ingredients;}
    public String getInstructions(){return instructions;} public String getImageUrl(){return imageUrl;}
    public String getStatus(){return status;} public Timestamp getCreatedAt(){return createdAt;}
}
