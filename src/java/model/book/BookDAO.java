package model.book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.DBContext;
import model.author.AuthorDTO;

import model.genre.GenreDTO;
import model.orderdetail.OrderDetailDTO;

public class BookDAO extends DBContext {

    //GET_1 Get Book
    public BookDTO getBook(int xBookID) {
        String query = "" +
                "SELECT " +
                "       [bookID]," +
                "	[title]," +
                "	COALESCE([desc], N'Không có') as [desc],\n" +
                "	[thumbnail]," +
                "	[price]," +
                "	[publishedDate]," +
                "	[authorID]," +
                "       [quantity]" +
                "FROM Books " +
                "WHERE bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xBookID);
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
                return new BookDTO(bookID, title, desc, thumbnail, price, publishedDate, authorID, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //GET_2 Get Books
    public ArrayList<BookDTO> getBooks() {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT " +
                "       [bookID]," +
                "	[title]," +
                "	[desc]," +
                "	[thumbnail]," +
                "	[price]," +
                "	[publishedDate]," +
                "	[authorID], " +
                "       [quantity]" +
                "FROM Books ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //GET_3 Get Books Order By Date; Newest
    public ArrayList<BookDTO> getBooksOrderedByDate() {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT " +
                "       [bookID]," +
                "	[title]," +
                "	[desc]," +
                "	[thumbnail]," +
                "	[price]," +
                "	[publishedDate]," +
                "	[authorID]," +
                "       [quantity]" +
                "FROM Books " +
                "ORDER BY [publishedDate] DESC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //GET_4 Get all year
    public ArrayList<String> getPublishedYears() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String query = "" +
                "SELECT DISTINCT YEAR(publishedDate) as [year] " +
                "FROM Books " +
                "ORDER BY [year]";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arrayList.add(rs.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<BookDTO> getTopBooksOrderedByDate() {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT  TOP 8" +
                "       [bookID]," +
                "	[title]," +
                "	[desc]," +
                "	[thumbnail]," +
                "	[price]," +
                "	[publishedDate]," +
                "	[authorID]," +
                "       [quantity]" +
                "FROM Books " +
                "ORDER BY [publishedDate] DESC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //3. Insert Book
    public Integer insert(String title, String desc, String thumbnail, double price, String publishedDate, int authorID, int quantity) {
        String query = "" +
                //                              1     2       3           4         5       6               
                "INSERT INTO Books ([title],[desc],[thumbnail],[price],[publishedDate],[authorID],[quantity]) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        //        1  2  3  4  5  6  7
        try {
            PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNString(1, title);
            if (desc.trim().isEmpty()) {
                ps.setNString(2, null);
            } else {
                ps.setNString(2, desc);
            }
            ps.setNString(3, thumbnail);
            ps.setDouble(4, price);
            ps.setString(5, publishedDate);
            ps.setInt(6, authorID);
            ps.setInt(7, quantity);
            int isExcute = ps.executeUpdate();
            if (isExcute > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    int key = rs.getInt(1);
                    return key;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //4. Delete Book
    public boolean delete(int bookID) {
        String query = "" +
                "DELETE FROM Books " +
                "WHERE bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, bookID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //6. Search book: (Dựa trên title và description)
    public ArrayList<BookDTO> search(String xSearchValue, int page) {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT\n" +
                "	BO.bookID,\n" +
                "	BO.title,\n" +
                "	COALESCE(BO.[desc], N'Không có') as [desc],\n" +
                "	BO.thumbnail,\n" +
                "	BO.price,\n" +
                "	BO.publishedDate,\n" +
                "	BO.authorID,\n" +
                "	BO.quantity\n" +
                "FROM \n" +
                "	Books BO JOIN Authors AU ON BO.authorID = AU.authorID\n" +
                "WHERE\n" +
                "	CONCAT (BO.title, ' ', BO.[desc], ' ', AU.[name]) like ?";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setNString(1, "%" + xSearchValue + "%");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //5. Get Genres;
    public ArrayList<GenreDTO> getGenres(int bookID) {
        ArrayList<GenreDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT GE.[genreID], GE.[name] " +
                "FROM " +
                "	BelongTo BL  " +
                "	JOIN Books BO ON BL.bookID = BO.bookID " +
                "	JOIN Genres GE ON BL.genreID = GE.genreID " +
                "WHERE " +
                "	BL.bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int genreID = rs.getInt("genreID");
                String name = rs.getNString("name");
                al.add(new GenreDTO(genreID, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //6. Get Author
    public AuthorDTO getAuthor(int bookID) {
        String query = "" +
                "SELECT " +
                "	AU.authorID, " +
                "	[name]," +
                "	AU.thumbnail," +
                "	AU.[desc] " +
                "FROM " +
                "	Books BO " +
                "	JOIN  Authors AU ON BO.authorID = AU.authorID " +
                "WHERE BO.bookID = 1";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String name = rs.getNString("name");
                String thumbnail = rs.getNString("thumbnail");
                String desc = rs.getNString("desc");
                return new AuthorDTO(authorID, name, thumbnail, desc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //7 Get Book of a genre
    public ArrayList<BookDTO> getBooksOfGenre(int xGenreID) {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT  " +
                "	BO.bookID," +
                "	BO.title," +
                "	BO.[desc]," +
                "	BO.thumbnail," +
                "	BO.price," +
                "	BO.publishedDate," +
                "	BO.authorID, " +
                "       BO.quantity " +
                "FROM " +
                "	BelongTo BL  " +
                "	JOIN Books BO ON BL.bookID = BO.bookID " +
                "	JOIN Genres GE ON BL.genreID = GE.genreID " +
                "WHERE  " +
                "	GE.genreID = ?";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xGenreID);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //8 Get Book of a Author
    public ArrayList<BookDTO> getBooksOfAuthor(int xAuthorID) {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT  " +
                "	BO.bookID, " +
                "	BO.title, " +
                "	BO.[desc], " +
                "	BO.thumbnail, " +
                "	BO.price, " +
                "	BO.publishedDate, " +
                "	BO.authorID, " +
                "       BO.quantity " +
                "FROM  " +
                "	Books BO " +
                "	JOIN  Authors AU ON BO.authorID = AU.authorID " +
                "WHERE AU.authorID = ?";

        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xAuthorID);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //3. Insert Book
    public int getInsertedID(String title, String desc, String thumbnail, double price, String publishedDate, int authorID, int quantity) {
        String query = "" +
                //                              1     2       3           4         5       6               
                "INSERT INTO Books ([title],[desc],[thumbnail],[price],[publishedDate],[authorID],[quantity]) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        //        1  2  3  4  5  6  7
        try {
            PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setNString(1, title);
            ps.setNString(2, desc);
            ps.setNString(3, thumbnail);
            ps.setDouble(4, price);
            ps.setString(5, publishedDate);
            ps.setInt(6, authorID);
            ps.setInt(7, quantity);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    System.out.println("Inserted ID is: " + id);
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateQuantity(int bookID, int quantity) {
        String query = "" +
                "UPDATE Books\n" +
                "SET quantity = ? \n" +
                "WHERE bookID = ? ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setInt(2, bookID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(int bookID, String title, String desc, String thumbnail, double price, String publishedDate, int authorID, int quantity) {
        String query = "" +
                "UPDATE Books\n" +
                "SET \n" +
                "	title = ? ,\n" + // 1
                "	[desc] = ? ,\n" + // 2
                "	thumbnail = ? ,\n" + //3
                "	price = ? ,\n" + //4
                "	publishedDate = ? ,\n" + //5
                "	authorID = ? ,\n" + // 6
                "	quantity = ? " + //7
                "WHERE " +
                "       bookID = ?"; // 8
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setNString(1, title);
            if (desc.isEmpty()) {
                ps.setNString(2, null);
            } else {
                ps.setNString(2, desc);
            }

            ps.setNString(3, thumbnail);
            ps.setDouble(4, price);
            ps.setString(5, publishedDate);
            ps.setInt(6, authorID);
            ps.setInt(7, quantity);
            ps.setInt(8, bookID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertGenre(int bookID, int genreID) {
        String query = "INSERT INTO BelongTo(bookID, genreID) VALUES (?, ?);";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, bookID);
            ps.setInt(2, genreID);
            int rs = ps.executeUpdate();
            while (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteGenre(int bookID) {
        String query = "" +
                "DELETE FROM BelongTo\n" +
                "WHERE bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, bookID);
            int rs = ps.executeUpdate();
            while (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<BookDTO> getBestSellers() {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT TOP 4\n" +
                "	BO.bookID, \n" +
                "	BO.title,\n" +
                "	COALESCE(BO.[desc], N'Không có') [desc],\n" +
                "	BO.thumbnail,\n" +
                "	BO.price,\n" +
                "	BO.publishedDate,\n" +
                "	BO.authorID,\n" +
                "	BO.quantity,\n" +
                "	SUM(ODD.quantity) AS totalQuantity\n" +
                "FROM\n" +
                "	OrderDetails ODD JOIN Books BO ON ODD.bookID = BO.bookID\n" +
                "GROUP BY BO.bookID, BO.title, BO.[desc], BO.thumbnail, BO.price, BO.publishedDate, BO.authorID, BO.quantity\n" +
                "ORDER BY totalQuantity DESC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public ArrayList<BookDTO> getBooksSameAuthor(int xBookID, int xAuthorID ) {
        ArrayList<BookDTO> al = new ArrayList<>();
        String query = "SELECT TOP 4\n" +
                "	BO.bookID,\n" +
                "	BO.title,\n" +
                "	BO.[desc],\n" +
                "	BO.thumbnail,\n" +
                "	BO.price,\n" +
                "	BO.publishedDate,\n" +
                "	BO.authorID,\n" +
                "	BO.quantity\n" +
                "FROM \n" +
                "	Books BO JOIN Authors AU ON BO.authorID = AU.authorID\n" +
                "WHERE \n" +
                "	AU.authorID = ? AND BO.bookID != ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xAuthorID);
            ps.setInt(2, xBookID);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        System.out.println("================================INSERT BOOKS =============================");
        if (bookDAO.insert("Nắng mùa đông", "Mô tả về nắng mùa đông", "Không có", 100, "2023-09-23", 1, 10) != null) {
            System.out.println("INSERT THÀNH CÔNG");
        } else {
            System.out.println("INSERT THẤT BẠI");
        }
        System.out.println("================================GET INSERTED BOOKID");
        int insertedKey = bookDAO.getInsertedID("Nắng mùa đông", "Mô tả về nắng mùa đông", "Không có", 100, "2023-09-23", 1, 10);
        System.out.println(insertedKey);
        System.out.println("================================GET BOOK : 1================================");
        System.out.println(bookDAO.getBook(1));
        System.out.println("================================UPDATE BOOKS");
        if (bookDAO.update(insertedKey, "Cơm rang dưa bò", "Quá hay luông", "để tạm đây vậy", 120_000, "1996-04-03", 2, 999)) {

        }
        System.out.println("================================GET BOOKS================================");
        ArrayList<BookDTO> al = bookDAO.getBooks();
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }

        System.out.println("================================DELETE BOOKS =============================");
        al = bookDAO.search("Nắng mùa đông", 1);
        for (BookDTO bookDTO : al) {
            if (bookDAO.delete(bookDTO.getBookID())) {
                System.out.println("XÓA THÀNH CÔNG (Nắng mùa đông)");
            } else {
                System.out.println("XÓA THẤT BẠI (Nắng mùa đông)");
            }
        }
        System.out.println("================================Search BOOKS (title)=============================");
        al = bookDAO.search("Yuji", 1);
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }
        System.out.println("================================GET GENRES OF BOOK (1)=============================");
        ArrayList<GenreDTO> genreList = bookDAO.getGenres(1);
        for (GenreDTO genreDTO : genreList) {
            System.out.println(genreDTO);
        }
        System.out.println("================================GET AUTHOR OF BOOK (1)");
        System.out.println(bookDAO.getAuthor(1));
        System.out.println("================================GET BOOK OF A GENRE (1 - Hành động)");
        al = bookDAO.getBooksOfGenre(1);
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }
        System.out.println("================================GET BOOK OF A AUTHOR (1 - GEGE TAKUMI)");
        al = bookDAO.getBooksOfAuthor(1);
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }
        System.out.println("================================GET BOOK ORDERED BY DATE  ");
        al = bookDAO.getBooksOrderedByDate();
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }
        System.out.println("================================GET PUBLISHED YEARS");
        for (String year : bookDAO.getPublishedYears()) {
            System.out.println(year);
        }
        bookDAO.updateQuantity(1, 100);
        System.out.println(bookDAO.getBook(1));
//        System.out.println("================================INSERT GENRE ");
//        if (bookDAO.insertGenre(1, 8)) {
//            System.out.println("Insert Thành công");
//        } else {
//            System.out.println("Insert lỗi");
//        }
        if (bookDAO.delete(1)) {
            System.out.println("Xóa Thành công");
        } else {
            System.out.println("Xóa lỗi");
        }
        System.out.println("================================BEST SELLER");
        al = bookDAO.getBestSellers();
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }
        System.out.println("================================BOOKS SAME AUTHORS");
        al = bookDAO.getBooksSameAuthor(1, 2);
        for (BookDTO bookDTO : al) {
            System.out.println(bookDTO);
        }
    }
}
