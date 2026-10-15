package com.recipe.filter;

import com.recipe.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns={"/admin"})
public class AdminFilter implements Filter {
    @Override public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException {
        HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse resp=(HttpServletResponse)response;
        HttpSession session=req.getSession(false);User user=session==null?null:(User)session.getAttribute("user");
        if(user==null||!user.isAdmin()){resp.sendError(HttpServletResponse.SC_FORBIDDEN,"Admin access required.");return;}
        chain.doFilter(request,response);
    }
}
