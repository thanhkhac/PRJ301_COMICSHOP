package controller.sale;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.cart.CartDAO;
import model.user.UserDTO;

@WebServlet(name = "DeleteCartServlet", urlPatterns = {"/DeleteCartServlet"})
public class DeleteCartServlet extends HttpServlet {

    String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        CartDAO cartDAO = new CartDAO();
        if (USER == null) {
            response.sendRedirect(LOGIN_PAGE);
        } else {
            String userName = USER.getUserName();
            String raw_bookID = request.getParameter("bookID");
            String url = request.getHeader("Referer");
            try {
                int bookID = Integer.parseInt(raw_bookID);
                cartDAO.delete(userName, bookID);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
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
