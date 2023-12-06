package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.book.BookDAO;
import model.book.BookDTO;
import model.order.OrderDAO;
import model.order.OrderDTO;
import model.orderdetail.OrderDetailDAO;
import model.orderdetail.OrderDetailDTO;
import model.user.UserDTO;

@WebServlet(name = "RejectOrderServlet", urlPatterns = {"/RejectOrderServlet"})
public class RejectOrderServlet extends HttpServlet {

    OrderDAO orderDAO = new OrderDAO();
    BookDAO bookDAO = new BookDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDTO USER = (UserDTO) request.getSession().getAttribute("USER");
        String url = request.getHeader("Referer");
        if (USER == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        if (USER != null && !USER.isRole()) {
            response.sendRedirect("/includes/notification.jsp?mess=Vui long dang nhap voi tai khoan quan tri vien");
            return;
        }
        try {
            String xOrderID = request.getParameter("orderID");
            int orderID = Integer.parseInt(xOrderID);
            OrderDTO orderDTO = orderDAO.getOrder(orderID);
            if (orderDTO.getStatus() == 1) {
                //TODO: cập nhật lại số lượng
                ArrayList<OrderDetailDTO> orderDetaiList = new OrderDetailDAO().getOrderDetailsByOrderID(orderID);
                for (OrderDetailDTO orderDetailDTO : orderDetaiList) {
                    BookDTO bookDTO = bookDAO.getBook(orderDetailDTO.getBookID());
                    int updateQuantity = bookDTO.getQuantity() + orderDetailDTO.getQuantity();
                    bookDAO.updateQuantity(bookDTO.getBookID(), updateQuantity);
                }
                orderDAO.reject(orderID);
            } else {
                orderDAO.reject(orderID);
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
