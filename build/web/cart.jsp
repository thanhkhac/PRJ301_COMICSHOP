<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.cart.*"%>
<%@page import="model.user.*"%>
<%@page import="model.book.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng của bạn</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css"/>
        <link rel="stylesheet" href="assets/css/booklist.css"/>
        <link rel="stylesheet" href="assets/css/general.css"/>
    </head>
    <body>
        <div>
            <%@include file="includes/header.jsp" %>
        </div>
        <c:if test = "${empty sessionScope.USER}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:if test = "${not empty sessionScope.USER}">
            <h1 class="text-center m-5">Giỏ hàng</h1>
            <form action="SaleController" method="POST">
                <div class="container mt-5" style="min-width: 1400px; background-color: #FFF; padding-bottom: 140px">
                    <!--                    <div class="row border justify-content-between">
                                            <div class="col-1 p-1 d-flex justify-content-center align-items-center">
                                                <h4>STT</h4>
                                            </div>
                                            <div class="col-1 p-1 d-flex justify-content-center align-items-center">
                                            </div>
                                            <div class="col-3 p-1 d-flex justify-content-center align-items-center ">
                                                <h4>Sách</h4>
                                            </div>
                                            <div class="col-1 p-1 d-flex justify-content-center align-items-center">
                                                <h4>Giá</h4>
                                            </div>
                                            <div class="col-1 p-1 d-flex flex-column justify-content-center align-items-center">
                                                <h4>Số lượng</h4>
                                            </div>
                                            <div class="col-1 p-1 d-flex justify-content-center align-items-center">
                                            </div>
                                            <div class="col-1 p-1 d-flex justify-content-center align-items-center">
                                            </div>
                                        </div>-->
                    <div>
                        <%  
                            UserDTO userDTO = (UserDTO) request.getSession().getAttribute("USER");
                            if(userDTO != null) {
                                String username = userDTO.getUserName();
                                ArrayList<CartDTO> cartList = new CartDAO().getCarts(username);
                                int i = 0;
                                for(CartDTO cartDTO : cartList){
                                    BookDTO bookDTO = new BookDAO().getBook(cartDTO.getBookID());
                        %>
                        <div>
                            <!--<form action="SaleController" method="post">-->
                            <div class="row border justify-content-between">
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center border">
                                    <h5><%= ++i %></h5>
                                </div>
                                <%
                                   if(bookDTO.getQuantity() != 0){
                                %>
                                <div class="col-1 p-2 d-flex justify-content-center">
                                    <input class="w-25" type="checkbox" name="chkBuy" value="<%= bookDTO.getBookID() %>">
                                </div>
                                <%
                                    } else {
                                %>
                                <div class="col-1 p-2 d-flex justify-content-center">

                                </div>
                                <%
                                    }
                                %>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <img class="w-100 border" src="<%= bookDTO.getThumbnail() %>" alt="">
                                </div>
                                <div class="col-3 p-2 d-flex justify-content-center align-items-center">
                                    <a class="text-decoration-none text-dark" href="bookDetails.jsp?bookID=<%= bookDTO.getBookID() %>">
                                        <h5><%= bookDTO.getTitle() %></h5>
                                    </a>
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <h5> <span class="text-danger"><%= bookDTO.getQuantity() %></span> có sẵn</h5>
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <h5 class="text-danger"><%= bookDTO.getPrice() %></h5>
                                </div>
                                <div class="col-2 p-2 d-flex flex-column justify-content-center align-items-center">
                                    <input class="form-control w-50" type="number" name="quantity<%= bookDTO.getBookID() %>" value= <%= cartDTO.getQuantity()%> >
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <button class="text-decoration-none btn btn-danger" type="submit" formaction="DeleteCartServlet" name="bookID" value="<%= bookDTO.getBookID() %>">Xóa</button>
                                </div>
                                <%
                                    if(bookDTO.getQuantity() != 0){
                                %>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <button class="text-decoration-none btn btn-success" type="submit" formaction="InsertCartServlet" name="bookID" value="<%= bookDTO.getBookID() %>">Cập nhật</button>
                                </div>
                                <%
                                    } else {
                                %>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <button class="text-decoration-none btn btn-danger" type="button" >Hết hàng</button>
                                </div>
                                <%
                                    }
                                %>
                                <!--<input type="hidden" name="txtUrl" value="${pageContext.request.requestURI}">-->

                            </div>
                            <!--</form>-->
                        </div>
                        <%
                                }
                            }
                        %>
                        <div class="text-center pt-4 pb-4">
                            <button class="btn btn-danger mx-auto px-5 fw-bold" type="submit" name="btAction" value="checkOut">
                                ĐẶT HÀNG
                            </button>
                        </div>
                    </div>
            </form>
        </div>
    </c:if>
    <div>
        <%@include file="includes/footer.jsp" %>
    </div>
</body>
</html>
