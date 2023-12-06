package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import model.cart.CartDAO;
import model.cart.CartDTO;
import model.order.OrderDAO;
import model.orderdetail.OrderDetailDAO;
import model.orderdetail.OrderDetailDTO;
import model.user.UserDTO;
import validation.Validation;

@WebServlet(name = "InsertOrderServlet", urlPatterns = {"/InsertOrderServlet"})
public class InsertOrderServlet extends HttpServlet {
    
    OrderDAO orderDAO = new OrderDAO();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    CartDAO cartDAO = new CartDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getHeader("Referer");
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        if (USER == null || (USER != null && USER.isRole()) || request.getParameterValues("bookID") == null) {
            response.sendRedirect(url);
            return;
        }
        String recipientName = request.getParameter("recipientName");
        String phoneNumber = request.getParameter("phoneNumber");
        String shipAddress = request.getParameter("shipAddress");
        String[] bookId_Arr = request.getParameterValues("bookID");
        try {
            if (recipientName.trim().isEmpty() || phoneNumber.trim().isEmpty() || shipAddress.trim().isEmpty()) {
                throw new Exception();
            }
            if (!Validation.isPhoneNumber(phoneNumber)) {
                throw new Exception();
            }
        } catch (Exception e) {
            response.sendRedirect(url);
            return;
        }
        int orderID = orderDAO.insertOrder(USER.getUserName(), recipientName, phoneNumber, shipAddress);
        try {
            for (int i = 0; i < bookId_Arr.length; i++) {
                CartDTO cartDTO = cartDAO.getCart(USER.getUserName(), Integer.parseInt(bookId_Arr[i]));
                boolean isInserted = orderDetailDAO.insert(orderID, cartDTO.getBookID(), cartDTO.getQuantity());
                if (cartDTO.getQuantity() == 0) {
                    orderDAO.delete(orderID);
                    response.sendRedirect("CheckOutServlet");
                    return;
                }
            }
            for (int i = 0; i < bookId_Arr.length; i++) {
                cartDAO.delete(USER.getUserName(), Integer.parseInt(bookId_Arr[i]));
            }
        } catch (Exception e) {
            response.sendRedirect(url);
        }
        response.sendRedirect("includes/notification.jsp?mess=Order thanh cong");
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
