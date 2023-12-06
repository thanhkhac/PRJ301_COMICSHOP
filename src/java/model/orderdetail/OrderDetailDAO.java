package model.orderdetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.DBContext;

public class OrderDetailDAO extends DBContext {

    public boolean insert(int orderID, int bookID, int quantity) {
        String query = "" +
                "INSERT INTO OrderDetails (orderID, bookID, quantity) VALUES " +
                "(?, ?, ?);";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, orderID);
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

    public ArrayList<OrderDetailDTO> getOrderDetailsByUserName(String xUserName, int xOrderID) {
        ArrayList<OrderDetailDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT \n" +
                "	OD.orderID, \n" +
                "	OD.bookID, \n" +
                "	OD.quantity\n" +
                "FROM \n" +
                "	Orders O \n" +
                "	JOIN OrderDetails OD ON O.orderID = OD.orderID\n" +
                "WHERE \n" +
                "	userName = ? \n" +
                "	AND OD.orderID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, xUserName);
            ps.setInt(2, xOrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                al.add(new OrderDetailDTO(orderID, bookID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public ArrayList<OrderDetailDTO> getOrderDetailsByOrderID(int xOrderID) {
        ArrayList<OrderDetailDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT \n" +
                "	OD.orderID, \n" +
                "	OD.bookID, \n" +
                "	OD.quantity\n" +
                "FROM \n" +
                "	Orders O \n" +
                "	JOIN OrderDetails OD ON O.orderID = OD.orderID\n" +
                "WHERE \n" +
                "	OD.orderID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xOrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                al.add(new OrderDetailDTO(orderID, bookID, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public static void main(String[] args) {
        System.out.println("===============================GET ORDER BY USERNAME AND ORDERID");
        ArrayList<OrderDetailDTO> al = new OrderDetailDAO().getOrderDetailsByUserName("duong123", 1);
        for (OrderDetailDTO orderDetailDTO : al) {
            System.out.println(orderDetailDTO);
        }
        System.out.println("===============================GET ORDER BY ORDERID");
        al = new OrderDetailDAO().getOrderDetailsByOrderID(1);
        for (OrderDetailDTO orderDetailDTO : al) {
            System.out.println(orderDetailDTO);
        }
    }
}
