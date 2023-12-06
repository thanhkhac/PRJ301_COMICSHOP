package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DispatchController", urlPatterns = {"/DispatchController"})
public class DispatchController extends HttpServlet {

    String PAGE_HOME = "home.jsp";
    String USER_LOGIN = "LoginServlet";
    String USER_LOGOUT = "LogoutServlet";
    String USER_REGISTER = "RegisterServlet";
    String ADMIN_USER_DELETE = "AdminDeleteServlet";
    String ADMIN_USER_UPDATE = "AdminDeleteUpdate";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = PAGE_HOME;
        String btAction = request.getParameter("btAction");
        if(btAction != null)
        switch (btAction) {
            case "login":
                url = USER_LOGIN;
                break;
            case "logout":
                url = USER_LOGOUT;
                break;
            case "register":
                url = USER_REGISTER;
                break;
//            case "adminDelete":
//                url = ADMIN_USER_DELETE;
//                break;
//            case "adminUpdate":
//                url = ADMIN_USER_UPDATE;
//                break;
        }
        request.getRequestDispatcher(url).forward(request, response);
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
