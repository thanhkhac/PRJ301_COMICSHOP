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
            <c:if test = "${not empty USER}">
                <h1>Đổi mật khẩu</h1>
                <form action="changePassword" method="POST">
                    <div class="p-1">
                        <label>Mật khẩu cũ:</label>
                        <input class="form-control" type="text" name="oldPassWord">
                    </div>
                    <div class="p-1">
                        <label>Mật khẩu mới:</label>
                        <input class="form-control" type="text" name="newPassWord">
                    </div>
                    <div class="p-1">
                        <label>Xác nhận mật khẩu</label>
                        <input class="form-control" type="text" name="confirmPassWord">
                    </div>
                    <div class="p-1">
                        <button class="btn btn-danger p-1" name="btAction" value="updateUser">Cập nhật</button>      
                    </div>
                </form>
                <a class="text-decoration-none" href="home.jsp"><h6 class="text-center text-warning text-decoration-underline">Trang chủ</h6></a>
                <c:if test = "${not empty requestScope.errorList}">
                    <c:forEach var="dto" items="${requestScope.errorList}" varStatus="counter">
                        <h6 class="p-1 alert alert-danger m-0 mt-1">${dto}</h6>
                    </c:forEach>
                </c:if>
            </c:if>
            <c:if test = "${empty USER}">
                <c:redirect url="login.jsp"/>
            </c:if>
        </div>
    </body>
</html>
