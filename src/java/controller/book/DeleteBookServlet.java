package controller.book;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.book.BookDAO;
import model.user.UserDTO;

@WebServlet(name = "DeleteBookServlet", urlPatterns = {"/DeleteBookServlet"})
public class DeleteBookServlet extends HttpServlet {

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
            String xBookID = request.getParameter("bookID");
            int bookID = Integer.parseInt(xBookID);
            new BookDAO().delete(bookID);
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
