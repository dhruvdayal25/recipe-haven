package com.recipe.model;

public class User {
    private long userId;
    private String name, email, role;
    public User(long userId, String name, String email, String role) {
        this.userId = userId; this.name = name; this.email = email; this.role = role;
    }
    public long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isAdmin() { return "ADMIN".equals(role); }
}
