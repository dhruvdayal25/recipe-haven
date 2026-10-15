package com.recipe.servlet;

import com.recipe.dao.RecipeDAO;
import com.recipe.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final RecipeDAO dao=new RecipeDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        User user=(User)req.getSession().getAttribute("user");
        if(user==null||!user.isAdmin()){resp.sendError(403);return;}
        try {req.setAttribute("recipes",dao.findPending());req.getRequestDispatcher("/admin-dashboard.jsp").forward(req,resp);}
        catch(SQLException e){throw new ServletException("Unable to load admin dashboard.",e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        User user=(User)req.getSession().getAttribute("user");
        if(user==null||!user.isAdmin()){resp.sendError(403);return;}
        try {
            dao.moderate(Long.parseLong(req.getParameter("id")),req.getParameter("status"));
            resp.sendRedirect(req.getContextPath()+"/admin");
        } catch(Exception e){throw new ServletException("Moderation failed.",e);}
    }
}
