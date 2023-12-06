package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import model.cart.CartDAO;
import model.cart.CartDTO;
import model.user.UserDTO;

@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        if (USER == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String username = USER.getUserName();
        String[] arr = request.getParameterValues("chkBuy");
        String url = request.getHeader("Referer");
        if (arr == null) {
            response.sendRedirect(url);
            return;
        }
        CartDAO cartDAO = new CartDAO();
        //Lấy ra id của các cuốn sách đã được tick trong giỏ hàng
        ArrayList<Integer> bookIdOrderList = new ArrayList<>();
        try {
            for (int i = 0; i < arr.length; i++) {
                bookIdOrderList.add(Integer.parseInt(arr[i]));
            }
            ArrayList<CartDTO> checkOutList = cartDAO.getCartsByArrayList(username, bookIdOrderList);
            request.getSession().setAttribute("checkOutList", checkOutList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
