<%-- 
    Document   : notification
    Created on : Oct 3, 2023, 10:28:10 AM
    Author     : thanh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../assets/Bootstrap_5/css/bootstrap.css"/>
        <style>
            body{
                height: 100vh;
                background-color: #f5c2c7;
            }
        </style>
    </head>
    <body class="d-flex">
        <%
        String mess = (String)request.getParameter("mess");
        %>
        <div class="notification-block col-md-6 p-5  bg-success text-white my-auto mx-auto rounded-2 text-center">  
            <h5><%= mess %></h5>
            <h6><a class="link-warning" href="${pageContext.request.contextPath}">Trang chủ</a></h6>
        </div>
    </body>
</html>
