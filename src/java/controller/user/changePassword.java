package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.user.UserDAO;
import model.user.UserDTO;
import validation.Validation;

@WebServlet(name = "changePassword", urlPatterns = {"/changePassword"})
public class changePassword extends HttpServlet {

    UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String referer = request.getHeader("Referer");
        String url = null;
        try {
            URI uri = new URI(referer);
            url = uri.getPath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(changePassword.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> errorList = new ArrayList<>();
        try {
            UserDTO userdto = (UserDTO) request.getSession().getAttribute("USER");
            if (userdto == null) {
                throw new Exception();
            }
            String oldPassWord = request.getParameter("oldPassWord").trim();
            String newPassWord = request.getParameter("newPassWord").trim();
            String confirmPassWord = request.getParameter("confirmPassWord").trim();
            boolean isValid = true;
            if (!userdto.getPassWord().equals(oldPassWord)) {
                errorList.add("Mật khẩu không đúng");
                isValid = false;
            }
            if (!Validation.isLengthInRange(newPassWord, 8, 24)) {
                isValid = false;
                errorList.add("Mật khẩu không hợp lệ");
            }
            if (!newPassWord.equals(confirmPassWord)) {
                errorList.add("Mật khẩu không khớp");
                isValid = false;
            }
            if (isValid) {
                userDAO.updatePassWord(userdto.getUserName(), newPassWord);
                request.getSession().invalidate();
                response.sendRedirect("login.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("errorList", errorList);
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
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

    public static void main(String[] args) {
        String url = "/http:/localhost:9999/Assignment_Retake_VIEW/changePassword.jsp";
        url = url.replaceAll("/", "//");
        System.out.println(url);
    }
}
