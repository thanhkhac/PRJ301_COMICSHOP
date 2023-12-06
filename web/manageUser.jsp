<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="model.user.*"%>
<%
    ArrayList<UserDTO> userList = new UserDAO().getUsers();
    request.setAttribute("userList", userList);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý người dùng</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css"/>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div>
            <%@include file="includes/header.jsp" %>
        </div>
        <c:set var="USER" value="${sessionScope.USER}"/>
        <c:if test = "${USER.role}">
            <form>
                <table class="table table-hover table-bordered">
                    <tr class="table-dark">
                        <th>Tên tài khoản</th>
                        <th>Mật khẩu</th>
                        <th>Họ và tên</th>
                        <th>Số điện thoại</th>
                        <th>Quyền</th>
                        <th>Xóa</th>
                        <th>Cập nhật</th>
                    </tr>
                    <c:set var="userList" value="${requestScope.userList}"/>
                    <c:forEach var="dto" items="${userList}" varStatus="counter">
                        <tr>
                            <td>
                                <input class="form-control" type="text" name="userName" value="${dto.userName}" disabled="">
                                <input  type="hidden" name="userName" value="${dto.userName}">
                            </td>
                            <td><input disabled class="form-control" type="password" name="txtPassWord" value="${dto.passWord}"></td>
                            <td><input disabled class="form-control" type="text" name="txtFullName" value="${dto.fullName}"></td>
                            <td><input disabled class="form-control" type="text" name="txtPhoneNumber" value="${dto.phoneNumber}"></td>
                                <c:if test = "${dto.role}">
                                <td><input type="checkbox" name="chkRole${dto.userName}" checked> Admin</td>
                                </c:if>
                                <c:if test = "${not dto.role}">
                                <td><input type="checkbox" name="chkRole${dto.userName}"> Admin</td>
                                </c:if>
                            <td>
                                <button class="btn btn-danger form-control" formaction="AdminDeleteServlet" type="submit" name="deleteID" value="${dto.userName}">Xóa</button>
                            </td>
                            <td>
                                <button class="btn btn-success form-control" formaction="AdminUpdateServlet" type="submit" name="updateID" value="${dto.userName}">Cập nhật</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </c:if>

        <c:if test = "${not USER.role}">
            <div class="notification-block col-md-6 p-5  bg-success text-white my-auto mx-auto rounded-2 text-center">  
                <h1>Vui lòng đăng nhập với tài khoản quản trị viên</h1>
            </div>
        </c:if>
    </body>

</html>
