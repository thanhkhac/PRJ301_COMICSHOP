<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.genre.*"%>
<%@page import="model.author.*"%>
<%@page import="model.book.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm sản phẩm</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css">
        <style>
            body{
                background-color: #D46B4A;
            }
            .genre{
                display: inline-block;
                background-color: #B53D03;
                border-radius: 20px;
                padding: 5px 10px;
                margin: 2px 0px;
            }
        </style>
    </head>
    <%
        String xBookID = request.getParameter("bookID");
        int bookID = Integer.parseInt(xBookID);
        BookDTO bookDTO = new BookDAO().getBook(bookID);
        ArrayList<GenreDTO> genreList = new GenreDAO().getGenres();
        ArrayList<AuthorDTO> authorList  = new AuthorDAO().getAuthors();
        ArrayList<GenreDTO> bookGenres = new BookDAO().getGenres(bookID);
        request.setAttribute("bookDTO", bookDTO);
        request.setAttribute("bookGenres", bookGenres);
        request.setAttribute("genreList", genreList);
        request.setAttribute("authorList", authorList);
    %>
    <c:if test = "${empty sessionScope.USER}">
        <c:redirect url="login.jsp"/>
    </c:if>
    <c:if test = "${not empty sessionScope.USER && !sessionScope.USER.role}">
        <c:redirect url="includes/notification.jsp?mess=Vui long dang nhap bang tai khoan quan tri vien"/>
    </c:if>
    <c:if test = "${not empty sessionScope.USER && sessionScope.USER.role}">
        <body>
            <form action="UpdateBookServlet" method="POST">
                <div class=" d-flex flex-column justify-content-center text-white pt-5 pb-5">
                    <div class="bg-success m-auto rounded" style="width: 600px">
                        <div class="p-4 text-warning">
                            <a href="home.jsp" class="text-decoration-underline text-warning">Trang chủ</a> /
                            <a href="manageProduct.jsp" class="text-decoration-underline text-warning">Quản lý sản phẩm</a>
                        </div>
                        <h1 class="text-white text-center">Cập nhật thông tin sách</h1>
                        <c:set var="bookDTO" value="${requestScope.bookDTO}"/>
                        <div class="input p-4">
                            <div>
                                <h5>Tiêu đề:</h5>
                                <input class="form-control" type="text" name="title" value="${bookDTO.title}">
                            </div>
                            <div class="pt-1">
                                <h5>Hình ảnh (url): </h5>
                                <input class="form-control" type="text" name="thumbnail" value="${bookDTO.thumbnail}">
                            </div>
                            <div class="pt-1">
                                <h5>Giá bán: </h5>
                                <input class="form-control" type="text" name="price" value="${bookDTO.price}">
                            </div>
                            <div class="pt-1">
                                <h5>Số lượng: </h5>
                                <input class="form-control" type="number" name="quantity" value="${bookDTO.quantity}">
                            </div>
                            <div class="pt-1">
                                <h5>Ngày xuất bản: </h5>
                                <input class="form-control" type="date" name="publishedDate" value="${bookDTO.publishedDate}">
                            </div>
                            <div class="pt-1">
                                <h5>Tác giả</h5>
                                <select class="form-select" name="authorID">
                                    <c:forEach var="dto" items="${requestScope.authorList}" varStatus="counter">
                                        <c:if test = "${dto.authorID == bookDTO.authorID}">
                                            <option value="${dto.authorID}" selected="">${dto.name}</option>
                                        </c:if>
                                        <c:if test = "${not (dto.authorID == bookDTO.authorID)}">
                                            <option value="${dto.authorID}">${dto.name}</option>
                                        </c:if>

                                    </c:forEach>
                                </select>
                            </div>
                            <div class="pt-1">
                                <h5>Thể loại: </h5>         
                                <c:forEach var="dto" items="${requestScope.genreList}" varStatus="counter">
                                    <c:set var="check" value="${false}"/>
                                    <c:forEach var="dto2" items="${requestScope.bookGenres}" varStatus="counter">
                                        <c:if test = "${dto.genreID == dto2.genreID}">
                                            <c:set var="check" value="${true}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test = "${check}">
                                        <span class="genre fw-bold">
                                            <input type="checkbox" name="genreID" value="${dto.genreID}" checked> ${dto.name}
                                        </span>
                                    </c:if>
                                    <c:if test = "${not check}">
                                        <span class="genre fw-bold">
                                            <input type="checkbox" name="genreID" value="${dto.genreID}"> ${dto.name}
                                        </span>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div>
                                <div class="form-group">
                                    <label for="comment">Mô tả:</label>
                                    <textarea class="form-control" rows="5" name="desc" placeholder="500 ký tự"">${bookDTO.desc}</textarea>
                                </div>
                            </div>
                            <div class="pt-2 text-center">
                                <input type="hidden" name="bookID" value="<%= bookID %>">
                                <button class="btn btn-danger">Cập nhật</button>
                            </div>
                            <c:if test = "${not empty requestScope.errorList}">
                                <c:forEach var="dto" items="${requestScope.errorList}" varStatus="counter">
                                    <h6 class="p-1 alert alert-danger m-0 mt-1">${dto}</h6>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>

        </body>
    </c:if>
</html>
