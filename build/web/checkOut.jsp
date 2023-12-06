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
        <c:if test = "${not empty sessionScope.USER && sessionScope.USER.role}">
            <c:redirect url="/includes/notification.jsp?mess=Vui long dang nhap voi tai khoan nguoi dung"/>
        </c:if>
        <c:if test = "${empty sessionScope.checkOutList}">
            <c:redirect url="home.jsp"/>
        </c:if>
        <c:if test = "${not empty sessionScope.USER && not empty sessionScope.checkOutList}">
            <h1 class="text-center mt-5">Đặt hàng</h1>
            <form action="SaleController" method="POST">
                <div class="container mt-5" style="min-width: 1400px; background-color: #FFF">
                    <div>
                        <%  
                            ArrayList<CartDTO> checkedList = (ArrayList<CartDTO>) request.getSession().getAttribute("checkOutList");
                            int i = 0;
                            double price = 0;
                            for(CartDTO cartDTO : checkedList){
                                    BookDTO bookDTO = new BookDAO().getBook(cartDTO.getBookID());
                                    price += bookDTO.getPrice() * cartDTO.getQuantity();
                        %>
                        <div>
                            <div class="row border justify-content-between">
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center border">
                                    <h5><%= ++i %></h5>
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <img class="w-100 border" src="<%= bookDTO.getThumbnail() %>" alt="">
                                </div>
                                <div class="col-3 p-2 d-flex justify-content-center align-items-center">
                                    <a class="text-decoration-none text-dark" href="bookDetails.jsp?bookID=<%= bookDTO.getBookID() %>">
                                        <h5><%= bookDTO.getTitle() %></h5>
                                    </a>
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <h5 class="text-danger"><%= bookDTO.getPrice() %></h5>
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <h5> <span class="text-danger"><%= bookDTO.getQuantity() %></span> có sẵn</h5>
                                </div>
                                <div class="col-2 p-2 d-flex flex-column justify-content-center align-items-center">
                                    <input class="form-control w-50" type="number" name="quantity<%= bookDTO.getBookID() %>" value= <%= cartDTO.getQuantity()%> >
                                </div>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <button class="text-decoration-none btn btn-danger" type="submit" formaction="DeleteCheckOutServlet" name="btnBookID" value="<%= bookDTO.getBookID() %>">Xóa</button>
                                </div>
                                <%
                                    if(bookDTO.getQuantity() != 0){
                                %>
                                <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                    <button class="text-decoration-none btn btn-success" type="submit" formaction="UpdateCheckOutServlet" name="btnbookID" value="<%= bookDTO.getBookID() %>">Cập nhật</button>
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
                                <input type="hidden" name="bookID" value="<%= bookDTO.getBookID() %>" >
                            </div>
                        </div>
                        <%
                                }
                        %>
                        <div class=" pt-4 pb-4 d-flex justify-content-center">
                            <div class="bg-success my-auto p-4 rounded text-white" style="width: 500px">
                                <div>
                                    <h6>Họ và tên người nhận </h6>
                                    <input class="form-control" type="text" name="recipientName" value="${sessionScope.USER.fullName}">
                                </div>
                                <div>
                                    <h6>Số điện thoại: </h6>
                                    <input class="form-control" type="text" name="phoneNumber" value="${sessionScope.USER.phoneNumber}">
                                </div>
                                <div>
                                    <h6>Địa chỉ: </h6>
                                    <input class="form-control" type="text" name="shipAddress" placeholder="Số nhà, tên đường, phường/xã, quận/huyện, thành phố/tỉnh">
                                </div>
                            </div>
                        </div>
                        <div class="text-center pt-4 pb-4">
                            <h5>Tổng tiền: <span class="text-danger"><%= price %>đ</span></h5>
                            <button class="btn btn-danger mx-auto px-5 fw-bold" type="submit" name="btAction" value="order">
                                ĐẶT HÀNG
                            </button>
                        </div>
                    </div>
            </form>
        </div>
    </c:if>
</body>
</html>
