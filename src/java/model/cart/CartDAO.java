package model.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DBContext;

public class CartDAO extends DBContext {

    //GET CARTS OF A CUSTOMER
    public ArrayList<CartDTO> getCarts(String xUserName) {
        ArrayList<CartDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT userName, bookID, quantity " +
                "FROM Carts " +
                "WHERE userName = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, xUserName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                al.add(new CartDTO(userName, bookID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //GET CART OF A CUSTOMER
    public CartDTO getCart(String xUserName, int xbookID) {
        String query = "" +
                "SELECT userName, bookID, quantity " +
                "FROM Carts " +
                "WHERE " +
                "   userName = ? AND" +
                "   bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, xUserName);
            ps.setInt(2, xbookID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                return (new CartDTO(userName, bookID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //GET CART By ArrayList
    public  ArrayList<CartDTO> getCartsByArrayList(String xUserName, ArrayList<Integer> xbookIDs) {
        ArrayList<CartDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT userName, bookID, quantity " +
                "FROM Carts " +
                "WHERE " +
                "   userName = ? AND " +
                "   bookID IN ";
        String elements = xbookIDs.toString().replaceAll("\\[", "(");
        elements = elements.replaceAll("\\]", ")");
        query += elements;
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, xUserName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                al.add(new CartDTO(userName, bookID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //INSERT CART
    public boolean insert(String userName, int bookID, int quantity) {
        String query = "" +
                "INSERT INTO Carts([userName], bookID, quantity) VALUES " +
                "(?, ?, ?);";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, userName);
            ps.setInt(2, bookID);
            ps.setInt(3, quantity);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //UPDATE QUANTITY
    public boolean updateQuantity(String userName, int book, int quantity) {
        String query = "" +
                "UPDATE Carts " +
                "SET quantity = ? " +
                "WHERE " +
                "	userName = ? AND " +
                "	bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setString(2, userName);
            ps.setInt(3, book);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //DELETE CART
    public boolean delete(String userName, int book) {
        String query = "" +
                "DELETE FROM Carts " +
                "WHERE " +
                "   userName = ? AND" +
                "   bookID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, userName);
            ps.setInt(2, book);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        CartDAO dao = new CartDAO();
        System.out.println("===============================GET CART (trinh0403)");
        ArrayList<CartDTO> al = dao.getCarts("trinh0403");
        for (CartDTO cartDTO : al) {
            System.out.println(cartDTO);
        }
        System.out.println("===============================INSERT CART(trinh0403, 2, 5 quyển)");
        if (dao.insert("trinh0403", 2, 5)) {
            System.out.println("INSERT THÀNH CÔNG");
            System.out.println("Bản ghi hiện tại là: " + dao.getCart("trinh0403", 2));
        } else {
            System.out.println("INSERT LỖI");
        }
        System.out.println("===============================UPDATE CART(trinh0403, 2, 10 quyển)");
        if (dao.updateQuantity("trinh0403", 2, 10)) {
            System.out.println("UPDATE THÀNH CÔNG");
            System.out.println("Bản ghi hiện tại là: " + dao.getCart("trinh0403", 2));
        } else {
            System.out.println("UPDATE LỖI");
        }
        System.out.println("===============================DELETE CART (Trinh0403, 2)");
        if (dao.delete("trinh0403", 2)) {
            System.out.println("XÓA THÀNH CÔNG");
            System.out.println("Bản ghi hiện tại là: " + dao.getCart("trinh0403", 2));
        } else {
            System.out.println("Xóa lỗi");
        }
    }

}
