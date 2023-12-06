package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.cart.CartDAO;
import model.cart.CartDTO;
import model.user.UserDTO;

@WebServlet(name = "ResetCheckOutServlet", urlPatterns = {"/ResetCheckOutServlet"})
public class ResetCheckOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        if (USER == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        CartDAO cartDAO = new CartDAO();
        ArrayList<CartDTO> checkOutList = (ArrayList<CartDTO>) request.getSession().getAttribute("checkOutList");
        for (CartDTO cartDTO : checkOutList) {
            cartDTO = cartDAO.getCart(USER.getUserName(), cartDTO.getBookID());
        }
        request.getSession().setAttribute("checkOutList", checkOutList);
        response.sendRedirect("checkOut.jsp");
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
