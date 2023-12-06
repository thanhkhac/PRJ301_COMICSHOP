<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.user.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="../assets/Bootstrap_5/css/bootstrap.css">
        <link rel="icon" type="image/x-icon" href="https://drive.google.com/uc?export=view&id=1BQdfRGuJ0TLVUOrZnF1P4x1jPdPq9ACc">       
        <script src="assets/Bootstrap_5/js/bootstrap.bundle.min.js"></script>
        <style>
            #header1{
                background-color: #B53D03;
                min-height: 60px;
            }

            #logo img{
                width: 70px;
                height: 70px;
            }

            #login_logout a{
                text-decoration: none;
                color: white;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row" id="header1">
                <div class="col-md-1 text-center" id="logo">
                    <a href="${pageContext.request.contextPath}">
                        <img src="https://drive.google.com/uc?export=view&id=1EwSV2xfH0reSAUEngZBwsqtbJaV5ifbK"
                             alt="logo">
                    </a>
                </div>
                <!-- my-auto: căn giữa dọc -->
                <div class="col-md-8 my-auto" id="searchbar-form">
                    <form action="BookSearchServlet" method="GET">
                        <div class="input-group">
                            <!-- form-control: cho phần tử chiếm 100% witdh -->
                            <c:if test = "${empty param.txtSearchValue}">
                                <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm" name="txtSearchValue">
                            </c:if>
                            <c:if test = "${not empty param.txtSearchValue}">
                                <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm" name="txtSearchValue" value="${param.txtSearchValue}">
                            </c:if>
                            <!-- input-group-btn: đính kèm button vào input -->
                            <div class="input-group-btn">
                                <button class="btn btn-success" type="submit" value="bookSearch" name="btAction">Tìm kiếm</button>
                            </div>
                        </div>
                    </form>
                    <!-- ============================================================ -->
                </div>

                <div class="col-md-3 my-auto text-center" >
                    <c:set var="USER" value="${sessionScope.USER}"/>
                    <c:if test = "${empty USER}">
                        <div class="btn-group" id="login_logout">
                            <!-- =================User======================== -->
                            <a class="btn btn-link" href="login.jsp">Đăng nhập</a>
                            <a class="btn btn-link" href="register.jsp">Đăng ký</a>
                            <!-- ========================================= -->
                        </div>  
                    </c:if>

                    <c:if test = "${not empty USER}">
                        <div class="dropdown">
                            <button type="button" class="btn btn-success dropdown-toggle" data-bs-toggle="dropdown">
                                Xin chào ${USER.userName}
                            </button>
                            <ul class="dropdown-menu">

                                <c:if test = "${USER.role}">
                                    <li><a class="dropdown-item " href="manageUser.jsp">Quản lý người dùng</a></li>
                                    <li><a class="dropdown-item " href="manageWaitingOrder.jsp">Đang chờ xét duyệt</a></li>
                                    <li><a class="dropdown-item " href="manageOrder.jsp">Tất cả đơn hàng</a></li>
                                    <li><a class="dropdown-item " href="manageProduct.jsp">Quản lý sản phẩm</a></li>
                                    </c:if>    
                                    <c:if test = "${not USER.role}">
                                    <li><a class="dropdown-item " href="cart.jsp">Giỏ hàng</a></li>
                                    <li><a class="dropdown-item " href="order.jsp">Đơn hàng</a></li>
                                    </c:if>
                                <li><a class="dropdown-item " href="changePassword.jsp">Thay đổi mật khẩu</a></li>
                                <li><a class="dropdown-item " href="DispatchController?btAction=logout">Đăng xuất</a></li>
                            </ul>
                        </div>   
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
