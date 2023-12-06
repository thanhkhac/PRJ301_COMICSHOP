<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Đăng nhập</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css" type="text/css"/>
        <style>
            body{
                display: flex;
                height: 100vh;
                background-color: #D46B4A;
            }
        </style>
    </head>

    <body>

        <div class="col-md-4 bg-success mx-auto my-auto p-5 rounded-3 text-white" id="form-block">
            <!-- Nhận request từ ControllerLogin -->
            <!-- Nếu người dùng chưa đăng nhập (USERNAME == null) hiển thị form đăng nhập-->
            <%
//                Enumeration<String> e =  (Enumeration<String>)request.getAttributeNames();
//                while (e.hasMoreElements()) {
//                    String nextElement = e.nextElement();
//                    System.out.println(nextElement);
//                }
            %>
            <c:set var="isFalse" value="${requestScope.isFalse}"/>
            <c:set var="USER" value="${sessionScope.USER}"/>
            <c:if test = "${empty USER}">
                <h1>Đăng nhập</h1>
                <form action="DispatchController" method="POST">
                    <div class="p-1">
                        <label>Tên đăng nhập:</label>
                        <input class="form-control" type="text" name="txtUserName">
                    </div>
                    <div class="p-1">
                        <label>Mật khẩu:</label>
                        <input class="form-control" type="text" name="txtPassWord">
                    </div>
                    <c:if test = "${not empty isFalse}">
                        <h6 class="p-1">Tài khoản hoặc mật khẩu không hợp lệ</h6>
                        <div class="d-none">
                            <audio controls autoplay>
                                <source src="assets/Bruh.mp3" type="audio/mpeg">
                            </audio>
                        </div>
                    </c:if>
                    <div class="p-1">
                        <button class="btn btn-danger p-1" name="btAction" value="login">Đăng nhập</button>      
                    </div>
                </form>
                <div class="text-center">
                    <a href="register.jsp" class="text-decoration-none text-white">Bạn chưa có tài khoản ?</a>
                </div>
            </c:if>
            <c:if test = "${not empty USER}">
                <h3>Bạn đã đăng nhập với tài khoản ${USER.userName}</h3>
            </c:if>
        </div>
    </body>
</html>
