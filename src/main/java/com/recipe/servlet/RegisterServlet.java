package com.recipe.servlet;

import com.recipe.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        String name=req.getParameter("name"),email=req.getParameter("email"),password=req.getParameter("password");
        if(name==null||name.isBlank()||email==null||!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")||password==null||password.length()<8){
            req.setAttribute("error","Enter a name, valid email, and password of at least 8 characters.");req.getRequestDispatcher("/register.jsp").forward(req,resp);return;
        }
        try {
            new UserDAO().register(name,email,password);
            resp.sendRedirect(req.getContextPath()+"/login?registered=1");
        } catch(SQLException e) {
            req.setAttribute("error",e.getErrorCode()==1062?"That email is already registered.":"Registration failed. Check database configuration.");
            req.getRequestDispatcher("/register.jsp").forward(req,resp);
        }
    }
}
