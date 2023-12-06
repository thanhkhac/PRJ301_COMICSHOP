package controller.sale;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import model.cart.CartDTO;

@WebServlet(name = "DeleteCheckOutServlet", urlPatterns = {"/DeleteCheckOutServlet"})
public class DeleteCheckOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getHeader("Referer");
        String btnBookID = request.getParameter("btnBookID");
        ArrayList<CartDTO> checkOutList = (ArrayList<CartDTO>) request.getSession().getAttribute("checkOutList");
        if (checkOutList != null && btnBookID != null) {
            try {
                int bookID = Integer.parseInt(btnBookID);
                Iterator<CartDTO> itr = checkOutList.iterator();
                while (itr.hasNext()) {
                    if (itr.next().getBookID() == bookID) {
                        itr.remove();
                    }
                }
                if (checkOutList.size() >= 1) {
                    request.getSession().removeAttribute("checkOutList");
                    request.getSession().setAttribute("checkOutList", checkOutList);
                } else {
                    request.getSession().removeAttribute("checkOutList");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
