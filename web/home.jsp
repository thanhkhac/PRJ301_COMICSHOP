<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="model.book.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title> 
        <link rel="stylesheet" href="assets/Bootstrap_5/css/bootstrap.css">
        <link rel="stylesheet" href="assets/css/booklist.css"/>
        <link rel="stylesheet" href="assets/css/general.css"/>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <style>
            .mySlides {
                display:none;
            }
        </style>
    </head>

    <%
        ArrayList<BookDTO> bookList = new BookDAO().getTopBooksOrderedByDate();
        request.setAttribute("bookList", bookList);
        bookList = new BookDAO().getBestSellers();
        request.setAttribute("bookList1", bookList);
    %>
    <body>
        <div>
            <%@include file="includes/header.jsp" %>
        </div>
        <div class="overflow-hidden">
            <!--<img src="https://i.pinimg.com/originals/b5/b8/2c/b5b82ca931a478f50a94f06ef2f9d211.jpg" class="banner" style="width: 120%"/>-->
            <div>
                <div class=" w-100" >
                    <img class="mySlides" src="https://i.ibb.co/QNsn9t5/banner-1.png" style="width:100%">
                    <img class="mySlides" src="https://i.ibb.co/K0NHH0m/banner-2.png" style="width:100%">
                    <img class="mySlides" src="https://i.ibb.co/F8J9Qk3/banner-3.png" style="width:100%">
                    <img class="mySlides" src="https://i.ibb.co/HtsqZq3/banner-4.png" style="width:100%">
                    <!--<img class="mySlides" src="https://i.ibb.co/WF6rrf4/banner-5.png" style="width:100%">-->
                    <img class="mySlides" src="https://i.ibb.co/VQfyg0K/banner-6.png" style="width:100%">
                </div>
            </div>
        </div>  

        <h2 class="text-center text-danger mt-5 fw-bold">Mới nhất</h2><br>
        <div style="padding-bottom: 0px">
            <%@include file="includes/booklist.jsp" %>
        </div>

        <h2 class="text-center text-danger fw-bold">Best sellers</h2><br>
        <div style="padding-bottom:140px">
            <%@include file="includes/booklist_1.jsp" %>
        </div>

        <div>
            <%@include file="includes/footer.jsp" %>
        </div>
        <script>
            var myIndex = 0;
            carousel();

            function carousel() {
                var i;
                var x = document.getElementsByClassName("mySlides");
                for (i = 0; i < x.length; i++) {
                    x[i].style.display = "none";
                }
                myIndex++;
                if (myIndex > x.length) {
                    myIndex = 1;
                }
                x[myIndex - 1].style.display = "block";
                setTimeout(carousel, 1000); // Change image every 2 seconds
            }
        </script>
    </body>
</html>
