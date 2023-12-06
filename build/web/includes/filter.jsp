<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.genre.*"%>
<%@page import="model.book.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../assets/Bootstrap_5/css/bootstrap.css"/>
    </head>
    <%
        GenreDAO genDAO = new GenreDAO();
        BookDAO bookDAO = new BookDAO();
        ArrayList<GenreDTO> genreList = genDAO.getGenres();
        ArrayList<String> yearList = bookDAO.getPublishedYears();
        request.setAttribute("genreList", genreList);
        request.setAttribute("yearList", yearList);
    %>

    <body>
        <div class="container mt-5 mb-5 bg-white p-3 rounded-3">
            <form action="BookSearchServlet" id="myForm">
                <div class="filter row justify-content-between">
                    <div class="col-md-2 col-6">
                        <h5>Thể loại:</h5>
                        <select name="genre" class="form-select" id="mySelect">
                            <option value="">Tất cả</option>
                            <c:set var="genreList" value="${requestScope.genreList}"/>
                            <c:forEach var="dto" items="${genreList}" varStatus="counter">
                                <c:if test = "${not empty param.genre && dto.genreID eq param.genre}">
                                    <option value="${dto.genreID}" selected>${dto.name}</option>
                                </c:if>
                                <c:if test = "${empty param.genre || dto.genreID ne param.genre}">
                                    <option value="${dto.genreID}">${dto.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 col-6">
                        <h5>Năm:</h5>
                        <select name="year" class="form-select" id="mySelect1">
                            <option value="">Tất cả</option>    
                            <c:set var="yearList" value="${requestScope.yearList}"/>
                            <c:forEach var="dto" items="${yearList}" varStatus="counter">
                                <c:if test = "${not empty param.year && dto eq param.year}">
                                    <option value="${dto}" selected>${dto}</option>
                                </c:if>
                                <c:if test = "${empty param.year || dto ne param.year}">
                                    <option value="${dto}">${dto}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 col-6 form-group ">
                        <div class="row">
                            <div class="col-6">
                                <h5>Giá từ:</h5>
                                <c:if test = "${not empty param.startPrice}">
                                    <input id="startPrice" class="form-control" type="number" name="startPrice" value="${param.startPrice}">
                                </c:if>
                                <c:if test = "${empty param.startPrice}">
                                    <input id="startPrice" class="form-control" type="number" name="startPrice">
                                </c:if>
                            </div>
                            <div class="col-6">
                                <h5>đến:</h5>
                                <c:if test = "${not empty param.endPrice}">
                                    <input id="endPrice" class="form-control" type="number" name="endPrice" value="${param.endPrice}">
                                </c:if>
                                <c:if test = "${empty param.endPrice}">
                                    <input id="endPrice" class="form-control" type="number" name="endPrice">
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-2 col-6">
                        <h5>Sắp xếp theo:</h5>
                        <select id="mySelect2" class="form-control" name="sort">
                            <c:if test="${not empty param.sort && 'newest' eq param.sort}">
                                <option value="newest" selected>Mới nhất</option>
                            </c:if>
                            <c:if test="${empty param.sort || 'newest' ne param.sort}">
                                <option value="newest">Mới nhất</option>
                            </c:if>
                            <c:if test="${not empty param.sort && 'oldest' eq param.sort}">
                                <option value="oldest" selected>Cũ nhất</option>
                            </c:if>
                            <c:if test="${empty param.sort || 'oldest' ne param.sort}">
                                <option value="oldest">Cũ nhất</option>
                            </c:if>
                            <c:if test="${not empty param.sort && 'name-asc' eq param.sort}">
                                <option value="name-asc" selected>Tên (A-Z)</option>
                            </c:if>
                            <c:if test="${empty param.sort || 'name-asc' ne param.sort}">
                                <option value="name-asc">Tên (A-Z)</option>
                            </c:if>
                            <c:if test="${not empty param.sort && 'name-desc' eq param.sort}">
                                <option value="name-desc" selected>Tên (Z-A)</option>
                            </c:if>
                            <c:if test="${empty param.sort || 'name-desc' ne param.sort}">
                                <option value="name-desc">Tên (Z-A)</option>
                            </c:if>
                            <c:if test="${not empty param.sort && 'price-asc' eq param.sort}">
                                <option value="price-asc" selected>Giá (Thấp đến cao)</option>
                            </c:if>
                            <c:if test="${empty param.sort || 'price-asc' ne param.sort}">
                                <option value="price-asc">Giá (Thấp đến cao)</option>
                            </c:if>
                            <c:if test="${not empty param.sort && 'price-desc' eq param.sort}">
                                <option value="price-desc" selected>Giá (Cao đến thấp)</option>
                            </c:if>
                            <c:if test="${empty param.sort || 'price-desc' ne param.sort}">
                                <option value="price-desc">Giá (Cao đến thấp)</option>
                            </c:if>
                        </select>
                    </div>
                    <div class="col-md-2 col-6">
                        <h5>&nbsp;</h5>
                        <button class="form-control btn btn-success">
                            Lọc
                        </button>
                    </div>
                </div>
                <input type="hidden" id="filterSearchValue" name="txtSearchValue" value="${param.txtSearchValue}">
            </form>
        </div>
    </body>
</html>
