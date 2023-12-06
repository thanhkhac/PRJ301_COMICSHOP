<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container bg-3 booklist" >
            <div class="row">
                <c:forEach var="dto" items="${requestScope.bookList1}" varStatus="counter">
                    <div class="col-md-3 col-6 p-1 book" id="${dto.bookID}">
                        <a class="book_title d-block w-100" href="bookDetails.jsp?bookID=${dto.bookID}">
                            <div class="border thumbnail" >
                                <img src="${dto.thumbnail}" class="img-responsive" alt="Image">
                            </div>
                        </a>
                        <div class="bg-light border p-3">
                            <a class="book_title" href="bookDetails.jsp?bookID=${dto.bookID}">${dto.title}</a>
                            <p>Giá bán: <span class="text-danger">${dto.price}đ</span></p>
                            <!--                            <form action="SaleController" method="POST">
                                                            <button class="button-cart text-white rounded-1 p-2 mb-2" name="btAction" value="addCart">
                                                                Thêm vào giỏ
                                                            </button>
                                                            <input type="hidden" name="bookID" value="${dto.bookID}">
                                                            <input type="hidden" name="txtUrl" value="${pageContext.request.requestURI}#${dto.bookID}">
                                                        </form>-->
                            <c:url value="SaleController" var="addCartUrl">
                                <c:param name = "btAction" value = "addCart"/>
                                <c:param name = "bookID" value = "${dto.bookID}"/>
                                <c:param name = "txtUrl" value = "${pageContext.request.requestURI}#${dto.bookID}"/>
                            </c:url>
                            <c:if test = "${dto.quantity != 0}">
                                <a class="button-cart btn text-white rounded-1 p-2 mb-2 " href="${addCartUrl}">
                                    Thêm vào giỏ
                                </a>
                            </c:if>
                            
                            <c:if test = "${dto.quantity == 0}">
                                <a class="button-cart btn text-white rounded-1 p-2 mb-2">
                                    Hết hàng
                                </a>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
