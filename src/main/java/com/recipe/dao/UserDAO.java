package com.recipe.dao;

import com.recipe.model.User;
import com.recipe.util.DBConnection;
import com.recipe.util.PasswordUtil;
import java.sql.*;

public class UserDAO {
    public boolean register(String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO users(name,email,password_hash) VALUES(?,?,?)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,name.trim()); p.setString(2,email.trim().toLowerCase()); p.setString(3,PasswordUtil.hash(password));
            return p.executeUpdate()==1;
        }
    }
    public User login(String email, String password) throws SQLException {
        String sql="SELECT user_id,name,email,password_hash,role FROM users WHERE email=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,email.trim().toLowerCase());
            try(ResultSet r=p.executeQuery()) {
                if(r.next() && PasswordUtil.verify(password,r.getString("password_hash")))
                    return new User(r.getLong("user_id"),r.getString("name"),r.getString("email"),r.getString("role"));
            }
        }
        return null;
    }
}
