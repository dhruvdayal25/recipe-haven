package com.recipe.dao;

import com.recipe.model.Recipe;
import com.recipe.util.DBConnection;
import java.sql.*;
import java.util.*;

public class RecipeDAO {
    private Recipe map(ResultSet r) throws SQLException {
        return new Recipe(r.getLong("recipe_id"),r.getLong("user_id"),r.getString("author"),r.getString("title"),
          r.getString("description"),r.getString("ingredients"),r.getString("instructions"),r.getString("image_url"),
          r.getString("status"),r.getTimestamp("created_at"));
    }
    private static final String SELECT="SELECT r.*,u.name AS author FROM recipes r JOIN users u ON u.user_id=r.user_id ";
    public List<Recipe> findApproved(String search) throws SQLException {
        List<Recipe> list=new ArrayList<>();
        String sql=SELECT+"WHERE r.status='APPROVED' AND (r.title LIKE ? OR r.description LIKE ?) ORDER BY r.created_at DESC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            String q="%"+(search==null?"":search.trim())+"%";p.setString(1,q);p.setString(2,q);
            try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        } return list;
    }
    public Recipe findById(long id) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(SELECT+"WHERE r.recipe_id=? AND r.status='APPROVED'")){
            p.setLong(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}
        }
    }
    public List<Recipe> findByUser(long userId) throws SQLException {
        List<Recipe> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(SELECT+"WHERE r.user_id=? ORDER BY r.created_at DESC")){
            p.setLong(1,userId);try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        } return list;
    }
    public boolean create(long userId,String title,String description,String ingredients,String instructions,String imageUrl) throws SQLException {
        String sql="INSERT INTO recipes(user_id,title,description,ingredients,instructions,image_url) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setLong(1,userId);p.setString(2,title.trim());p.setString(3,description);p.setString(4,ingredients);
            p.setString(5,instructions);p.setString(6,imageUrl);return p.executeUpdate()==1;
        }
    }
    public boolean update(long id,long userId,String title,String description,String ingredients,String instructions,String imageUrl) throws SQLException {
        String sql="UPDATE recipes SET title=?,description=?,ingredients=?,instructions=?,image_url=?,status='PENDING' WHERE recipe_id=? AND user_id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,title.trim());p.setString(2,description);p.setString(3,ingredients);p.setString(4,instructions);
            p.setString(5,imageUrl);p.setLong(6,id);p.setLong(7,userId);return p.executeUpdate()==1;
        }
    }
    public boolean delete(long id,long userId) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM recipes WHERE recipe_id=? AND user_id=?")){
            p.setLong(1,id);p.setLong(2,userId);return p.executeUpdate()==1;
        }
    }
    public List<Recipe> findPending() throws SQLException {
        List<Recipe> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(SELECT+"WHERE r.status='PENDING' ORDER BY r.created_at")){
            try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        }return list;
    }
    public boolean moderate(long id,String status) throws SQLException {
        if(!"APPROVED".equals(status)&&!"REJECTED".equals(status))return false;
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE recipes SET status=? WHERE recipe_id=? AND status='PENDING'")){
            p.setString(1,status);p.setLong(2,id);return p.executeUpdate()==1;
        }
    }
}
