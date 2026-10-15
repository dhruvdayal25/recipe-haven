package com.recipe.filter;

import com.recipe.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns={"/my-recipes","/recipe/create","/recipe/update","/recipe/delete","/rating","/comment","/admin"})
public class AuthenticationFilter implements Filter {
    @Override public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException {
        HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse resp=(HttpServletResponse)response;
        HttpSession session=req.getSession(false);User user=session==null?null:(User)session.getAttribute("user");
        if(user==null){resp.sendRedirect(req.getContextPath()+"/login");return;}
        chain.doFilter(request,response);
    }
}
