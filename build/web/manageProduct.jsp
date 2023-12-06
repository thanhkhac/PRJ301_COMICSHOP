<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.book.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/general.css"/>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css">
    </head>
    <%
        ArrayList<BookDTO> bookList = new BookDAO().getBooks();
        request.setAttribute("bookList", bookList);
    %>
    <c:if test = "${empty sessionScope.USER}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <c:if test = "${not empty sessionScope.USER && !sessionScope.USER.role}">
        <c:redirect url="includes/notification.jsp?mess=Vui long dang nhap bang tai khoan quan tri vien"/>
    </c:if>
    <c:if test = "${not empty sessionScope.USER && sessionScope.USER.role}">
        <body>
            <%@include file="includes/header.jsp" %>
            <div class="container">
                <table class="table table-hover table-bordered">
                    <tr class="table-dark text-center">
                        <th>Mã</th>
                        <th>Ảnh</th>
                        <th>Tiêu đề</th>
                        <th>Giá</th>
                        <th>Xóa</th>
                        <th>Sửa</th>
                    </tr>
                    <c:forEach var="dto" items="${requestScope.bookList}" varStatus="counter">
                        <tr class="table-light text-center">
                            <td>#${dto.bookID}</td>
                            <td>
                                <img src="${dto.thumbnail}" alt="alt" width="70" height="70"/>
                            </td>
                            <td><a class="text-decoration-none text-dark" href="bookDetails.jsp?bookID=${dto.bookID}">${dto.title}</a></td>
                            <td>${dto.price}</td>
                            <td>
                                <a class="btn btn-danger" href="DeleteBookServlet?bookID=${dto.bookID}">Xóa</a>
                            </td>
                            <td> 
                                <a class="btn btn-success" href="updateBook.jsp?bookID=${dto.bookID}">Cập nhật</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="text-center">
                    <a href="addBook.jsp" class="btn btn-success"><h4>Thêm sản phẩm</h4></a>
                </div>
            </div>
        </body>
    </c:if>
    </html>
