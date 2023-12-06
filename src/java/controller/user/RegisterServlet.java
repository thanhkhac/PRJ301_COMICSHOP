package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import validation.Validation;
import model.user.*;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserInsertError error = new UserInsertError();
        UserDAO userDAO = new UserDAO();

        String fullName = request.getParameter("txtFullName").trim();
        String phoneNumber = request.getParameter("txtPhoneNumber").trim();
        String userName = request.getParameter("txtUserName").trim();
        String passWord = request.getParameter("txtPassWord").trim();
        String confirm = request.getParameter("txtConfirm").trim();

        boolean isError = false;
        if (!Validation.isLengthInRange(fullName, 6, 50) ||
                !Validation.isLengthInRange(phoneNumber, 10, 12) ||
                !Validation.isLengthInRange(userName, 6, 18) ||
                !Validation.isLengthInRange(passWord, 8, 24) ||
                !Validation.isPhoneNumber(phoneNumber)) {
            error.setLength("Thông tin không hợp lệ");
            isError = true;
        }
        if (userDAO.getUserByPhoneNumber(phoneNumber) != null) {
            error.setPhoneNumberExisted("Số điện thoại đã tồn tại");
            isError = true;
        }
        if (userDAO.getUserByUserName(userName) != null) {
            error.setUserNameExisted("Tên đăng nhập đã tồn tại");
            isError = true;
        }
        if (!passWord.equals(confirm)) {
            error.setConfirmNotMatch("Mật khẩu không khớp");
            isError = true;
        }

        if (isError) {
            request.setAttribute("ERROR", error);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!userDAO.insert(userName, passWord, fullName, phoneNumber)) {
            error.setUserNameExisted("Tài khoản đã tồn tại");
            request.setAttribute("ERROR", error);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
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
