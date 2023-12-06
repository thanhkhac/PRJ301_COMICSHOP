package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.order.OrderDAO;
import model.order.OrderDTO;
import model.user.UserDTO;

@WebServlet(name = "DeleteOrderServlet", urlPatterns = {"/DeleteOrderServlet"})
public class DeleteOrderServlet extends HttpServlet {

    OrderDAO orderDAO = new OrderDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        String url = request.getHeader("Referer");
        if (request.getParameter("txtUrl") != null) {
            url = request.getParameter("txtUrl");
        }
        if (USER == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            String xOrderID = request.getParameter("orderID");
            int orderID = Integer.parseInt(xOrderID);
            OrderDTO orderDTO = orderDAO.getOrder(USER.getUserName(), orderID);
            if (orderDTO.getStatus() == 1) {
                throw new Exception();
            } else {
                orderDAO.delete(orderID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect(url);
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
