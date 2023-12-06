
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.book.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css">
        <link rel="stylesheet" href="assets/css/booklist.css"/>
        <link rel="stylesheet" href="assets/css/general.css"/>
        <style>
            .page-size{
                padding-bottom: 140px;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#searchInput").on("keyup", function () {
                    var searchText = $(this).val();
                    $.ajax({
                        type: "POST",
                        url: "BookSearchServlet1",
                        data: {
                            txtSearchValue: searchText
                        },
                        success: function (response) {
                            $("#searchResult").html(response);
                        }
                    });
                });

                $('#searchInput').on('input', function () {
                    var inputText = $(this).val();
                    $('#filterSearchValue').val(inputText);
                });

                $('#mySelect').change(function () {
                    sendForm();
                });
                $('#mySelect1').change(function () {
                    sendForm();
                });
                $('#mySelect2').change(function () {
                    sendForm();
                });


                function sendForm() {
                    var formData = $('#myForm').serialize();
                    $.ajax({
                        type: 'POST',
                        url: 'BookSearchServlet1',
                        data: formData,
                        success: function (response) {
                            $("#searchResult").html(response);
                        },
                        error: function (error) {
                            console.log(error);
                        }
                    });
                }

            });
        </script>
    </head>
    <body>
        <%@include file="includes/header.jsp" %>
        <div id="filter">
            <%@include file="includes/filter.jsp" %>
        </div>
        <div id="searchResult">
            <c:if test = "${not empty requestScope.bookList}">
                <%@include file="includes/booklist.jsp" %>
            </c:if>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>
