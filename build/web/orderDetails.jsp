<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.orderdetail.*"%>
<%@page import="model.order.*"%>
<%@page import="model.book.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css"/>
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
            <div>
                <h1 class="text-center mt-3">Chi tiết đơn hàng #<%= request.getParameter("orderID") %></h1>
            </div>
            <div class="container mt-3" style="min-width: 1400px; background-color: #FFF; padding-bottom: 140px">
                <div>
                    <%  
                        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("USER");
                        String xOrderID = request.getParameter("orderID");
                        int orderID = Integer.parseInt(xOrderID);
                        ArrayList<OrderDetailDTO> al = new OrderDetailDAO().getOrderDetailsByUserName(userDTO.getUserName(), orderID);
                        int i = 0;
                        double price = 0;
                        for(OrderDetailDTO orderDDTO : al){
                            BookDTO bookDTO = new BookDAO().getBook(orderDDTO.getBookID());
                            price += (bookDTO.getPrice() * orderDDTO.getQuantity());
                    %>
                    <div>
                        <!--<form action="SaleController" method="post">-->
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
                                <h5> <span class="text-danger"><%= bookDTO.getQuantity() %></span> có sẵn</h5>
                            </div>
                            <div class="col-1 p-2 d-flex justify-content-center align-items-center">
                                <h5 class="text-danger"><%= bookDTO.getPrice() %></h5>
                            </div>
                            <div class="col-2 p-2 d-flex flex-column justify-content-center align-items-center">
                                <input class="form-control w-50" type="number" name="quantity<%= bookDTO.getBookID() %>" value= <%= orderDDTO.getQuantity()%> >
                            </div>
                        </div>
                    </div>
                    <%
                            }
                    %>
                </div>
                <div class="container " style="background-color: #FFF">
                    <h4 class="text-center">Tổng tiền: <span class="text-danger"><%= price %>đ</span>  </h4>
                    <div class="text-center">
                        <%
                            OrderDTO orderDTO = new OrderDAO().getOrder(orderID);
                            if(orderDTO.getStatus() == 0){
                        %>
                        <form action="DeleteOrderServlet" method="POST">
                            <button class="btn btn-danger">Hủy đơn hàng</button>
                            <input type="hidden" name="txtUrl" value="<%=request.getHeader("Referer")%>">
                            <input type="hidden" name="orderID" value="<%=orderID%>">
                        </form>
                        <%
                            } else if(orderDTO.getStatus() == 1){
                        %>
                        <h3>Đơn hàng đã xác nhận</h3>
                        <%
                            } else {
                        %>
                        <h3>Đơn hàng đã bị từ chối</h3>
                        <%
                            } 
                        %>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div>
        <%@include file="includes/footer.jsp" %>
    </div>

</body>
</html>
