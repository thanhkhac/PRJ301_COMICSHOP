package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "SaleController", urlPatterns = {"/SaleController"})
public class SaleController extends HttpServlet {

    String PAGE_HOME = "home.jsp";
    String CART_INSERT = "InsertCartServlet";
    String CART_DELETE = "DeleteCartServlet";
    String CHECKOUT_SERVLET = "CheckOutServlet";
    String DELETE_CHECKOUT_SERVLET = "DeleteCheckOutServlet";
    String ORDER_SERVLET = "InsertOrderServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = PAGE_HOME;
        String btAction = request.getParameter("btAction");
        if (btAction != null) {
            switch (btAction) {
                case "addCart":
                    url = CART_INSERT;
                    break;
                case "deleteCart":
                    url = CART_DELETE;
                    break;
                case "checkOut":
                    url = CHECKOUT_SERVLET;
                    break;
                case "order":
                    url = ORDER_SERVLET;
                    break;
            }
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
// </editor-fold>

}
