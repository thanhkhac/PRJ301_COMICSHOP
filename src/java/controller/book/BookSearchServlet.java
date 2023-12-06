package controller.book;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DBContext;
import model.book.*;

@WebServlet(name = "BookSearchServlet", urlPatterns = {"/BookSearchServlet"})
public class BookSearchServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<BookDTO> al = new ArrayList<>();
        DBContext dBContext = new DBContext();
        String query = "" +
                "SELECT DISTINCT\n" +
                "	BO.bookID,\n" +
                "	BO.title,\n" +
                "	COALESCE(BO.[desc], N'Không có') [desc],\n" +
                "	BO.thumbnail,\n" +
                "	BO.price,\n" +
                "	BO.publishedDate,\n" +
                "	BO.authorID,\n" +
                "	BO.quantity\n" +
                "FROM \n" +
                "	Books BO \n" +
                "	JOIN Authors AU ON BO.authorID = AU.authorID\n" +
                "	JOIN BelongTo BL ON BO.bookID = BL.bookID \n" +
                "	JOIN Genres GE ON BL.genreID = GE.genreID\n" +
                "WHERE " +
                "       1 = 1 \n";
        String searchValue = request.getParameter("txtSearchValue");
        String genre = request.getParameter("genre");
        String year = request.getParameter("year");
        String startPrice = request.getParameter("startPrice");
        String endPrice = request.getParameter("endPrice");
        String sort = request.getParameter("sort");
        if (searchValue != null && !searchValue.trim().isEmpty()) {
            query += " AND CONCAT (BO.title, ' ', BO.[desc], ' ', AU.[name]) like ? \n";
        }
        if (genre != null && !genre.isEmpty()) {
            query += " AND GE.genreID = ? \n";
        }
        if (year != null && !year.isEmpty()) {
            query += " AND YEAR(BO.publishedDate) = ? \n";
        }
        if (startPrice != null && endPrice != null && !startPrice.trim().isEmpty() && !endPrice.trim().isEmpty()) {
            query += " AND BO.price between ? and ? \n";
        }
        if (sort != null) {
            switch (sort) {
                default:
                    query += " ORDER BY publishedDate DESC ";
                    break;
                case "oldest":
                    query += " ORDER BY publishedDate ASC ";
                    break;
                case "name-asc":
                    query += " ORDER BY BO.title ASC ";
                    break;
                case "name-desc":
                    query += " ORDER BY BO.title DESC ";
                    break;
                case "price-asc":
                    query += " ORDER BY BO.price ASC ";
                    break;
                case "price-desc":
                    query += " ORDER BY BO.price DESC ";
                    break;
            }
        } else {
            query += " ORDER BY publishedDate DESC ";
        }
        try {
            PreparedStatement ps = dBContext.connect.prepareStatement(query);
            int index = 1;
            if (searchValue != null && !searchValue.trim().isEmpty()) {
                ps.setNString(index++, "%" + searchValue + "%");
            }
            if (genre != null && !genre.isEmpty()) {
                int genreID = Integer.parseInt(genre);
                ps.setInt(index++, genreID);
            }
            if (year != null && !year.isEmpty()) {
                ps.setString(index++, year);
            }
            if (startPrice != null && endPrice != null && !startPrice.trim().isEmpty() && !endPrice.trim().isEmpty()) {
                double xStartPrice = Double.parseDouble(startPrice);
                double xEndPrice = Double.parseDouble(endPrice);
                ps.setDouble(index++, xStartPrice);
                ps.setDouble(index++, xEndPrice);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookID = rs.getInt("bookID");
                String title = rs.getNString("title");
                String desc = rs.getNString("desc");
                String thumbnail = rs.getNString("thumbnail");
                double price = rs.getDouble("price");
                String publishedDate = rs.getString("publishedDate");
                int authorID = rs.getInt("authorID");
                int quantity = rs.getInt("quantity");
                al.add(new BookDTO(bookID, title, desc, thumbnail, price, publishedDate, authorID, quantity));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        if (al.size() > 1) {
            request.setAttribute("bookList", al);
        }
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("bookSearch.jsp").forward(request, response);
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
