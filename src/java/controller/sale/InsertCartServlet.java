package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import model.cart.*;
import model.user.UserDTO;
import validation.Validation;

@WebServlet(name = "InsertCartServlet", urlPatterns = {"/InsertCartServlet"})
public class InsertCartServlet extends HttpServlet {

    String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        CartDAO cartDAO = new CartDAO();
        //Nếu USER null - Người dùng chưa đăng nhập => Sendirect về trang đăng nhập
        if (USER == null) {
            response.sendRedirect(LOGIN_PAGE);
        } else if (USER.isRole()) {
            response.sendRedirect(request.getContextPath() + "/includes/notification.jsp?mess=Vui long dang nhap bang tai khoan nguoi dung");
        } else {
            String raw_bookID = request.getParameter("bookID");
            String userName = USER.getUserName();
//            String param_quantity = "quantity" + raw_bookID;
            String param_quantity = "quantity";
            String raw_quantity = request.getParameter(param_quantity + raw_bookID);
//            String url = request.getParameter("txtUrl");
            String url = request.getHeader("Referer");
            try {
                if (raw_quantity != null && raw_quantity.trim().isEmpty()) {
                    raw_quantity = null;
                }
                int bookID = Integer.parseInt(raw_bookID);
                CartDTO carDTO = cartDAO.getCart(userName, bookID);
                if (carDTO != null) {
                    if (raw_quantity != null) {
                        int quantity = Integer.parseInt(raw_quantity);
                        if (quantity <= 0) {
                            quantity = 1;
                        }
                        cartDAO.updateQuantity(userName, bookID, quantity);
                    } else {
                        int quantity = carDTO.getQuantity() + 1;
                        cartDAO.updateQuantity(userName, bookID, quantity);
                    }
                } else {
                    if (raw_quantity != null) {
                        int quantity = Integer.parseInt(raw_quantity);
                        if (quantity <= 0) {
                            quantity = 1;
                        }
                        cartDAO.insert(userName, bookID, quantity);
                    } else {
                        int quantity = 1;
                        cartDAO.insert(userName, bookID, quantity);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } finally {
                //Quay trở lại trang gửi request
                response.sendRedirect(url);
            }
        }
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
