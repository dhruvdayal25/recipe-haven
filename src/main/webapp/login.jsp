<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html lang="en"><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Login | Recipe Haven</title><link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"></head><body>
<%@ include file="partials-nav.jspf" %><main class="container"><section class="form-card"><span class="eyebrow">Welcome back</span><h1>Log in</h1>
<% if(request.getAttribute("error")!=null){ %><div class="alert"><%= request.getAttribute("error") %></div><% } %>
<% if("1".equals(request.getParameter("registered"))){ %><div class="success">Account created. Please log in.</div><% } %>
<form method="post" action="${pageContext.request.contextPath}/login"><label>Email</label><input type="email" name="email" required maxlength="190" autocomplete="email"><label>Password</label><input type="password" name="password" required autocomplete="current-password"><p><button type="submit">Log in</button></p></form><p class="muted">New here? <a href="${pageContext.request.contextPath}/register">Create an account</a></p></section></main></body></html>
