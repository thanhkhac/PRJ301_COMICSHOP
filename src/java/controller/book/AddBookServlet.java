package controller.book;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.book.BookDAO;

@WebServlet(name = "AddBookServlet", urlPatterns = {"/AddBookServlet"})
public class AddBookServlet extends HttpServlet {

    BookDAO bookDAO = new BookDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<String> errorList = new ArrayList<>();
        try {
            String title = request.getParameter("title").trim();
            String thumbnail = request.getParameter("thumbnail").trim();
            String r_Price = request.getParameter("price").trim();
            String r_Quantity = request.getParameter("quantity").trim();
            String publishedDate = request.getParameter("publishedDate").trim();
            String r_AuthorID = request.getParameter("authorID").trim();
            String desc = request.getParameter("desc");
            String[] r_genreID = request.getParameterValues("genreID");
            boolean isValid = true;
            if (title.isEmpty() || thumbnail.isEmpty() || publishedDate.isEmpty() || r_AuthorID.isEmpty() || r_Price.isEmpty()) {
                errorList.add("Vui lòng điền đầy đủ thông tin");
                isValid = false;
            }
            if (desc.length() > 500) {
                errorList.add("Mô tả vượt quá số lượng kí tự cho phép");
            }
            double price = Double.parseDouble(r_Price);
            int quantity = Integer.parseInt(r_Quantity);
            int authorID = Integer.parseInt(r_AuthorID);
            ArrayList<Integer> genreID = new ArrayList<>();
            if (r_genreID != null) {
                for (int i = 0; i < r_genreID.length; i++) {
                    genreID.add(Integer.parseInt(r_genreID[i]));
                }
            }
            if (isValid) {
                int insertedKey = bookDAO.insert(title, desc, thumbnail, price, publishedDate, authorID, quantity);
                for (Integer integer : genreID) {
                    bookDAO.insertGenre(insertedKey, integer);
                }
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorList", errorList);
            request.getRequestDispatcher("addBook.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("manageProduct.jsp");
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
