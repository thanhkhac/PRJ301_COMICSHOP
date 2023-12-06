<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng ký</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css" type="text/css"/>
        <style>
            body{
                display: flex;
                height: 100vh;
                background-color: #D46B4A;
            }
        </style>
    </head>
    <c:set var="error" value=""/>

    <body>
        <div class="col-md-4 bg-success mx-auto my-auto p-5 rounded-3 text-white" id="form-block">
            <c:set var="USER" value="${sessionScope.USER}"/>
            <c:if test = "${not empty USER}">
                <h3>Bạn đã đăng nhập với tài khoản ${USER.userName}</h3>
            </c:if>
            <c:if test = "${empty USER}">
                <h1>Đăng ký</h1>
                <form action="DispatchController" method="POST">
                    <div class="p-1">
                        <label>Họ và tên:</label>
                        <input class="form-control" type="text" name="txtFullName" placeholder="6 - 50 ký tự" value="">
                    </div>
                    <div class="p-1">
                        <label>Số điện thoại:</label>
                        <input class="form-control" type="text" name="txtPhoneNumber" placeholder="10 - 12 chữ số" value="">
                    </div>
                    <div class="p-1">
                        <label>Tên đăng nhập:</label>
                        <input class="form-control" type="text" name="txtUserName" placeholder="6 - 18 ký tự" value="">
                    </div>
                    <div class="p-1">
                        <label>Mật khẩu:</label>
                        <input class="form-control" type="text" placeholder="8 - 24 ký tự" name="txtPassWord" >
                    </div>
                    <div class="p-1">
                        <label>Xác nhận mật khẩu:</label>
                        <input class="form-control" type="text" placeholder="Nhập lại mật khẩu" name="txtConfirm" >
                    </div>
                    <div class="p-1">
                        <button class="btn btn-danger p-1" name="btAction" value="register">Đăng ký</button>      
                    </div>
                </form>
                <c:set var="ERROR" value="${requestScope.ERROR}"/>
                <c:if test = "${not empty ERROR}">
                    <c:if test = "${not empty ERROR.length}">
                        <h6 class="p-1 alert alert-danger m-0 mt-1">${ERROR.length}</h6>
                    </c:if>
                    <c:if test = "${not empty ERROR.confirmNotMatch}">
                        <h6 class="p-1 alert alert-danger m-0 mt-1">${ERROR.confirmNotMatch}</h6>
                    </c:if>
                    <c:if test = "${not empty ERROR.phoneNumberExisted}">
                        <h6 class="p-1 alert alert-danger m-0 mt-1">${ERROR.phoneNumberExisted}</h6>
                    </c:if>
                    <c:if test = "${not empty ERROR.userNameExisted}">
                        <h6 class="p-1 alert alert-danger m-0 mt-1">${ERROR.userNameExisted}</h6>
                    </c:if>
                </c:if>
            </c:if>
            <div class="text-center">
                <a href="login.jsp" class="text-decoration-none text-white">Bạn đã có tài khoản ?</a>
            </div>
        </div>
    </body>
</html>
