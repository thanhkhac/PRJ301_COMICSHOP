<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.book.*"%>
<%@page import="model.genre.*"%>
<%@page import="model.author.*"%>
<!DOCTYPE html>
<%
    BookDAO bookDAO = new BookDAO();
    GenreDAO genreDAO = new GenreDAO();
    AuthorDAO authorDAO = new AuthorDAO();
    String xBookID = request.getParameter("bookID");
    if(xBookID == null){
%>
<c:redirect url="home.jsp"/>
<%
    } else {
        int bookID = Integer.parseInt(xBookID);
        BookDTO bookdto = bookDAO.getBook(bookID);
        AuthorDTO authordto = authorDAO.getAuthor(bookdto.getAuthorID());
        ArrayList<GenreDTO> genreList = bookDAO.getGenres(bookID);
        request.setAttribute("dto", bookdto);
        request.setAttribute("genreList", genreList);
        
        ArrayList<BookDTO> bookList1 = new BookDAO().getBooksSameAuthor(bookID, bookdto.getAuthorID());
        request.setAttribute("bookList1", bookList1);
%>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= bookdto.getTitle() %></title> 
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="assets/css/general.css"/>
        <link rel="stylesheet" href="assets/css/booklist.css"/>
        <style>

            #detail-block{
                background-color: white;
            }

            .detail{
                margin: 10px 0px;
            }

            .genre{
                display: inline-block;
                background-color: rgb(233, 202, 155);
                border-radius: 20px;
                padding: 5px 10px;
                margin: 2px 0px;
            }

            .genre a{
                text-decoration: none;
                color: black
            }

            .button-cart{
                background-color: #F11F3B !important;
                color: white !important;
            }

        </style>
    </head>

    <body>
        <%@include file="includes/header.jsp" %>
        <div class="container mt-5" style="padding-bottom: 180px">
            <div class="row">
                <div class="col-md-6" id="thumbnail">
                    <img class="w-100" src="<%= bookdto.getThumbnail() %>" >
                </div>
                <div class="col-md-6" id="detail-block" style="padding: 20px 30px;">
                    <div id="detail-content">
                        <div class="title">
                            <h3><%= bookdto.getTitle() %></h3>
                        </div>
                        <hr class="divider">
                        <h5>Giá bán: <span class="text-danger"><%= bookdto.getPrice() %></span></h5>
                        <hr class="divider">
                        <div class="detail">
                            <h6>Tác giả: <span class="text-danger"><%= authordto.getName() %></span></h6>
                        </div>
                        <div class="detail">
                            <h6>Ngày xuất bản: <span class="text-danger"><%= bookdto.getPublishedDate() %></span></h6>
                        </div>
                        <div class="detail">
                            <h6>Số lượng: <span class="text-danger"><%= bookdto.getQuantity() %> có sẵn</span></h6>
                        </div>
                        <div class="detail">
                            <h6>Thể loại:</h6>
                            <c:forEach var="dto" items="${requestScope.genreList}" varStatus="counter">
                                <span class="genre">
                                    <a href="BookSearchServlet?genre=${dto.genreID}">${dto.name}</a>
                                </span>
                            </c:forEach>
                        </div>
                        <div class="detail">
                            <form action="SaleController">
                                <div class="row">
                                    <div class="col-md-3">
                                        <input class="form-control" type="number" name="quantity${dto.bookID}" value=1>
                                    </div>
                                    <div class="col-md-4 ">
                                        <c:if test = "${requestScope.dto.quantity == 0}">
                                            <button type="button" class="button-cart btn text-white rounded-1 p-2 mb-2">
                                                Hết hàng
                                            </button>
                                        </c:if>
                                        <c:if test = "${requestScope.dto.quantity != 0}">
                                            <button class="button-cart btn text-white rounded-1 p-2 mb-2" name="btAction" value="addCart">
                                                Thêm vào giỏ
                                            </button>
                                        </c:if>
                                    </div>
                                    <input type="hidden" name="bookID" value="${requestScope.dto.bookID}">
                                    <input type="hidden" name="txtUrl" value="${pageContext.request.requestURI}#${dto.bookID}">
                                </div>
                            </form>
                        </div>
                        <div class="detail">
                            <div class="description bg-secondary text-white rounded-1 p-2">
                                <h6>Mô tả:</h6>
                                <%= bookdto.getDesc() %>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <h3 class="text-center text-danger pt-5">Cùng tác giả</h3><br>
            <div style="">
                <%@include file="includes/booklist_1.jsp" %>
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>
<%
    } 
%>


