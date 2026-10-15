package com.recipe.dao;

import com.recipe.model.Comment;
import com.recipe.util.DBConnection;
import java.sql.*;
import java.util.*;

public class CommunityDAO {
    public void rate(long userId,long recipeId,int rating) throws SQLException {
        if(rating<1||rating>5)throw new IllegalArgumentException("Rating must be from 1 to 5.");
        String sql="INSERT INTO ratings(user_id,recipe_id,rating) VALUES(?,?,?) ON DUPLICATE KEY UPDATE rating=VALUES(rating)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setLong(1,userId);p.setLong(2,recipeId);p.setInt(3,rating);p.executeUpdate();
        }
    }
    public double averageRating(long recipeId) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT AVG(rating) FROM ratings WHERE recipe_id=?")){
            p.setLong(1,recipeId);try(ResultSet r=p.executeQuery()){r.next();return r.getDouble(1);}
        }
    }
    public void addComment(long userId,long recipeId,String comment) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO comments(user_id,recipe_id,comment_text) SELECT ?,?,? WHERE EXISTS (SELECT 1 FROM recipes WHERE recipe_id=? AND status='APPROVED')")){
            p.setLong(1,userId);p.setLong(2,recipeId);p.setString(3,comment.trim());p.setLong(4,recipeId);
            if(p.executeUpdate()!=1)throw new SQLException("Recipe not found or not approved.");
        }
    }
    public List<Comment> comments(long recipeId) throws SQLException {
        List<Comment> list=new ArrayList<>();
        String sql="SELECT u.name,c.comment_text,c.created_at FROM comments c JOIN users u ON u.user_id=c.user_id WHERE c.recipe_id=? ORDER BY c.created_at DESC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setLong(1,recipeId);try(ResultSet r=p.executeQuery()){while(r.next())list.add(new Comment(r.getString(1),r.getString(2),r.getTimestamp(3)));}
        }return list;
    }
}
