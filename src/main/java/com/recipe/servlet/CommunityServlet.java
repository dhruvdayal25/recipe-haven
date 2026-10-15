package com.recipe.servlet;

import com.recipe.dao.CommunityDAO;
import com.recipe.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns={"/rating","/comment"})
public class CommunityServlet extends HttpServlet {
    private final CommunityDAO dao=new CommunityDAO();
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        User user=(User)req.getSession().getAttribute("user");
        if(user==null){resp.sendRedirect(req.getContextPath()+"/login");return;}
        try {
            long recipeId=Long.parseLong(req.getParameter("recipeId"));
            if("/rating".equals(req.getServletPath()))dao.rate(user.getUserId(),recipeId,Integer.parseInt(req.getParameter("rating")));
            else {
                String comment=req.getParameter("comment");
                if(comment==null||comment.isBlank()||comment.length()>1000)throw new IllegalArgumentException("Comment must be 1–1000 characters.");
                dao.addComment(user.getUserId(),recipeId,comment);
            }
            resp.sendRedirect(req.getContextPath()+"/recipe?id="+recipeId);
        } catch(IllegalArgumentException e){resp.sendError(400,e.getMessage());}
          catch(SQLException e){throw new ServletException("Unable to save feedback.",e);}
    }
}
