package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.UserDAO;
import model.user.UserDTO;

@WebServlet(name = "AdminUpdateServlet", urlPatterns = {"/AdminUpdateServlet"})
public class AdminUpdateServlet extends HttpServlet {

    UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getHeader("Referer");
        UserDTO userdto = (UserDTO) request.getSession().getAttribute("USER");
        if (userdto == null || (userdto != null && !userdto.isRole())) {
            response.sendRedirect("home.jsp");
            return;
        }
        try {
            String updateID = request.getParameter("updateID");
            String chkRole = request.getParameter("chkRole" + updateID);
            if (!userdto.getUserName().equals(updateID)) {
                if (chkRole != null) {
                    userDAO.updateRoleAdmin(updateID);
                }else{
                    userDAO.updateRoleUser(updateID);
                }
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
