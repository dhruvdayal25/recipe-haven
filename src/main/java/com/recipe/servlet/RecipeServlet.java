package com.recipe.servlet;

import com.recipe.dao.RecipeDAO;
import com.recipe.model.Recipe;
import com.recipe.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns={"/recipes","/recipe","/my-recipes","/recipe/create","/recipe/update","/recipe/delete"})
public class RecipeServlet extends HttpServlet {
    private final RecipeDAO dao=new RecipeDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try {
            String path=req.getServletPath();
            if("/recipe".equals(path)){
                long id=Long.parseLong(req.getParameter("id"));Recipe recipe=dao.findById(id);
                if(recipe==null){resp.sendError(404);return;}
                req.setAttribute("recipe",recipe);req.getRequestDispatcher("/recipe-details.jsp").forward(req,resp);
            } else if("/my-recipes".equals(path)){
                User user=(User)req.getSession().getAttribute("user");
                req.setAttribute("recipes",dao.findByUser(user.getUserId()));req.getRequestDispatcher("/my-recipes.jsp").forward(req,resp);
            } else {
                req.setAttribute("recipes",dao.findApproved(req.getParameter("q")));req.getRequestDispatcher("/recipes.jsp").forward(req,resp);
            }
        } catch(NumberFormatException e){resp.sendError(400);}
          catch(SQLException e){throw new ServletException("Unable to load recipes.",e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        User user=(User)req.getSession().getAttribute("user");
        if(user==null){resp.sendRedirect(req.getContextPath()+"/login");return;}
        String path=req.getServletPath();
        try {
            if("/recipe/create".equals(path)){
                if(valid(req)){dao.create(user.getUserId(),req.getParameter("title"),req.getParameter("description"),req.getParameter("ingredients"),req.getParameter("instructions"),req.getParameter("imageUrl"));}
                resp.sendRedirect(req.getContextPath()+"/my-recipes");
            } else if("/recipe/update".equals(path)){
                if(valid(req))dao.update(Long.parseLong(req.getParameter("id")),user.getUserId(),req.getParameter("title"),req.getParameter("description"),req.getParameter("ingredients"),req.getParameter("instructions"),req.getParameter("imageUrl"));
                resp.sendRedirect(req.getContextPath()+"/my-recipes");
            } else if("/recipe/delete".equals(path)){
                dao.delete(Long.parseLong(req.getParameter("id")),user.getUserId());resp.sendRedirect(req.getContextPath()+"/my-recipes");
            } else resp.sendError(405);
        } catch(SQLException|NumberFormatException e){throw new ServletException("Recipe operation failed.",e);}
    }
    private boolean valid(HttpServletRequest r) {
        return has(r,"title")&&has(r,"ingredients")&&has(r,"instructions")
          &&r.getParameter("title").length()<=160;
    }
    private boolean has(HttpServletRequest r,String k){return r.getParameter(k)!=null&&!r.getParameter(k).isBlank();}
}
