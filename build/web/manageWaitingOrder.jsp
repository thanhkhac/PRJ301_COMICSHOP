<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.order.*"%>
<%@page import="model.user.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý đơn hàng</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css">
        <link rel="stylesheet" href="assets/css/general.css"/>
    </head>
    <body>
        <c:if test = "${empty sessionScope.USER}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:if test = "${not empty sessionScope.USER && !sessionScope.USER.role}">
            <c:redirect url="includes/notification.jsp?mess=Vui long dang nhap bang tai khoan quan tri vien"/>
        </c:if>
        <c:if test = "${not empty sessionScope.USER && sessionScope.USER.role}">
            <%
                OrderDAO orderDAO = new OrderDAO();
                ArrayList<OrderDTO> al = new OrderDAO().getWaitingOrders();
                request.setAttribute("orderList", al);
            %>
            <%@include file="includes/header.jsp" %>
            <table class="table table-hover table-bordered">
                <tr class="table-dark">
                    <th>Mã đơn hàng</th>
                    <th>Tên người nhận</th>
                    <th>Ngày đặt</th>
                    <th>Số điện thoại</th>
                    <th>Địa chỉ</th>
                    <th>Tình trạng</th>
                    <th>Chi tiết</th>
                </tr>
                <c:forEach var="dto" items="${requestScope.orderList}" varStatus="counter">
                    <tr>
                        <td>#${dto.orderID}</td>
                        <td>${dto.recipientName}</td>
                        <td>${dto.orderDate}</td>
                        <td>${dto.phoneNumber}</td>
                        <td>${dto.shipAddress}</td>
                        <td>
                            <c:if test = "${dto.status == 0}">
                                Đang chờ xét duyệt
                            </c:if>
                            <c:if test = "${dto.status == 1}">
                                Đã xác nhận
                            </c:if>
                            <c:if test = "${dto.status == 2}">
                                Từ chối
                            </c:if>
                        </td>
                        <td>
                            <a href="manageOrderDetails.jsp?orderID=${dto.orderID}">Chi tiết</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <%@include file="includes/footer.jsp" %>
        </c:if>
    </body>
</html>
