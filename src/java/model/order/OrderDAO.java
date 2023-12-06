package model.order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.DBContext;

public class OrderDAO extends DBContext {

    public ArrayList<OrderDTO> getOrders() {
        ArrayList<OrderDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT \n" +
                "	orderID,\n" +
                "	userName,\n" +
                "	recipientName,\n" +
                "	orderDate,\n" +
                "	phoneNumber,\n" +
                "	shipAddress,\n" +
                "	[status]\n" +
                "FROM Orders " +
                "ORDER BY orderDate ASC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String recipientName = rs.getNString("recipientName");
                String orderDate = rs.getString("orderDate");
                String phoneNumber = rs.getString("phoneNumber");
                String shipAddress = rs.getNString("shipAddress");
                int status = rs.getInt("status");
                al.add(new OrderDTO(orderID, userName, recipientName, orderDate, phoneNumber, shipAddress, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public ArrayList<OrderDTO> getWaitingOrders() {
        ArrayList<OrderDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT \n" +
                "	orderID,\n" +
                "	userName,\n" +
                "	recipientName,\n" +
                "	orderDate,\n" +
                "	phoneNumber,\n" +
                "	shipAddress,\n" +
                "	[status]\n" +
                "FROM Orders " +
                "WHERE " +
                "	[status] = 0\n" +
                "ORDER BY orderDate ASC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String recipientName = rs.getNString("recipientName");
                String orderDate = rs.getString("orderDate");
                String phoneNumber = rs.getString("phoneNumber");
                String shipAddress = rs.getNString("shipAddress");
                int status = rs.getInt("status");
                al.add(new OrderDTO(orderID, userName, recipientName, orderDate, phoneNumber, shipAddress, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public OrderDTO getOrder(String xUserName, int xOrderID) {
        String query = "" +
                "SELECT \n" +
                "	orderID,\n" +
                "	userName,\n" +
                "	recipientName,\n" +
                "	orderDate,\n" +
                "	phoneNumber,\n" +
                "	shipAddress,\n" +
                "	[status]\n" +
                "FROM Orders " +
                "WHERE " +
                "       userName = ? " +
                "       AND orderID = ? " +
                "ORDER BY orderDate ASC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, xUserName);
            ps.setInt(2, xOrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String recipientName = rs.getNString("recipientName");
                String orderDate = rs.getString("orderDate");
                String phoneNumber = rs.getString("phoneNumber");
                String shipAddress = rs.getNString("shipAddress");
                int status = rs.getInt("status");
                return new OrderDTO(orderID, userName, recipientName, orderDate, phoneNumber, shipAddress, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderDTO getOrder(int xOrderID) {
        String query = "" +
                "SELECT \n" +
                "	orderID,\n" +
                "	userName,\n" +
                "	recipientName,\n" +
                "	orderDate,\n" +
                "	phoneNumber,\n" +
                "	shipAddress,\n" +
                "	[status]\n" +
                "FROM Orders " +
                "WHERE " +
                "       orderID = ? " +
                "ORDER BY orderDate ASC";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xOrderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String recipientName = rs.getNString("recipientName");
                String orderDate = rs.getString("orderDate");
                String phoneNumber = rs.getString("phoneNumber");
                String shipAddress = rs.getNString("shipAddress");
                int status = rs.getInt("status");
                return new OrderDTO(orderID, userName, recipientName, orderDate, phoneNumber, shipAddress, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<OrderDTO> getOrdersByUserName(String xUserName) {
        ArrayList<OrderDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT \n" +
                "	orderID,\n" +
                "	userName,\n" +
                "	recipientName,\n" +
                "	orderDate,\n" +
                "	phoneNumber,\n" +
                "	shipAddress,\n" +
                "	[status]\n" +
                "FROM Orders " +
                "WHERE userName = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, xUserName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                String userName = rs.getString("userName");
                String recipientName = rs.getNString("recipientName");
                String orderDate = rs.getString("orderDate");
                String phoneNumber = rs.getString("phoneNumber");
                String shipAddress = rs.getNString("shipAddress");
                int status = rs.getInt("status");
                al.add(new OrderDTO(orderID, userName, recipientName, orderDate, phoneNumber, shipAddress, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public int insertOrder(String userName, String recipientName, String phoneNumber, String shipAddress) {
        String query = "" +
                "INSERT INTO Orders (userName, recipientName, orderDate, phoneNumber, shipAddress) VALUES\n" +
                "(? , ?, GETDATE(), ?, ?)";
        //    1   2             3  4  5  
        try {
            PreparedStatement ps = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setNString(2, recipientName);
            ps.setString(3, phoneNumber);
            ps.setNString(4, shipAddress);
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
        return 0;
    }

    public boolean delete(int orderID) {
        String query = "" +
                "DELETE FROM Orders\n" +
                "WHERE orderID = ? ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, orderID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean reject(int orderID) {
        String query = "" +
                "UPDATE Orders\n" +
                "SET [status] = 2\n" +
                "WHERE orderID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, orderID);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean Approve(int orderID) {
        String query = "" +
                "UPDATE Orders\n" +
                "SET [status] = 1\n" +
                "WHERE orderID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, orderID);
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
        OrderDAO dao = new OrderDAO();
        System.out.println("======================================GET ALL ORDERS");
        ArrayList<OrderDTO> al = dao.getOrders();
        for (OrderDTO orderDTO : al) {
            System.out.println(orderDTO);
        }
        System.out.println("======================================GET ALL ORDERS of USER");
        al = dao.getOrdersByUserName("duong123");
        for (OrderDTO orderDTO : al) {
            System.out.println(orderDTO);
        }
        System.out.println("======================================INSERT ORDER AND RETURN KEY");
        int insertID = dao.insertOrder("robeo123", "Rô béo", "03822938346", "Hà Nội");
        System.out.println("INSERTED KEY : " + insertID);
        al = dao.getOrders();
        for (OrderDTO orderDTO : al) {
            System.out.println(orderDTO);
        }
        System.out.println("Xác nhận: " + dao.Approve(insertID));
        boolean isDeleted = dao.delete(insertID);
        if (isDeleted) {
            System.out.println("XÓA THÀNH CÔNG");
        } else {
            System.out.println("XÓA THẤT BẠI");
        }
        System.out.println("======================================GET ORDER BY USERNAME AND ORDER ID");
        System.out.println(dao.getOrder("duong123", 2));
        System.out.println("======================================GET ORDER BY ORDER ID");
        System.out.println(dao.getOrder(2));
    }
}
