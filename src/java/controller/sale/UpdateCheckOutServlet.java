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
import model.cart.CartDTO;

@WebServlet(name = "UpdateCheckOutServlet", urlPatterns = {"/UpdateCheckOutServlet"})
public class UpdateCheckOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xBookID = request.getParameter("btnbookID");
        String xQuantity = request.getParameter("quantity" + xBookID);
        try {
            int bookID = Integer.parseInt(xBookID);
            int quantity = Integer.parseInt(xQuantity);
            if (quantity <= 1) {
                quantity = 1;
            }
            BookDTO bookDTO = new BookDAO().getBook(bookID);
            if (quantity > bookDTO.getQuantity()) {
                quantity = bookDTO.getQuantity();
            }
            ArrayList<CartDTO> checkOutList = (ArrayList<CartDTO>) request.getSession().getAttribute("checkOutList");
            for (CartDTO cartDTO : checkOutList) {
                if (cartDTO.getBookID() == bookID) {
                    cartDTO.setQuantity(quantity);
                }
            }
            request.setAttribute("checkOutList", checkOutList);
        } catch (NumberFormatException e) {
        }
        String url = request.getHeader("Referer");
        response.sendRedirect(url);
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
