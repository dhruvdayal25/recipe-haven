package com.recipe.servlet;

import com.recipe.dao.UserDAO;
import com.recipe.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        req.getRequestDispatcher("/login.jsp").forward(req,resp);
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try {
            User user=new UserDAO().login(req.getParameter("email"),req.getParameter("password"));
            if(user==null){req.setAttribute("error","Invalid email or password.");req.getRequestDispatcher("/login.jsp").forward(req,resp);return;}
            req.getSession(true).setAttribute("user",user);
            resp.sendRedirect(req.getContextPath()+"/recipes");
        } catch(SQLException e) {
            req.setAttribute("error","Unable to sign in. Check database configuration.");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
}
