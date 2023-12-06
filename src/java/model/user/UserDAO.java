package model.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DBContext;

public class UserDAO extends DBContext {

    //1. GET: all User
    public ArrayList<UserDTO> getUsers() {
        ArrayList<UserDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT " +
                "   [userName], " +
                "   [passWord], " +
                "   [fullName], " +
                "   [phoneNumber], " +
                "   [role] " +
                "FROM Users ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String passWord = rs.getString("passWord");
                String fullName = rs.getNString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                boolean admin = rs.getBoolean("role");
                al.add(new UserDTO(userName, passWord, fullName, phoneNumber, admin));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    //2. GET: User by userName
    public UserDTO getUserByUserName(String xUserName) {
        String query = "" +
                "SELECT " +
                "   [userName], " +
                "   [passWord], " +
                "   [fullName], " +
                "   [phoneNumber], " +
                "   [role] " +
                "FROM Users " +
                "WHERE [userName] = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            //Set tham số
            ps.setString(1, xUserName);
            //Thực thi
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String passWord = rs.getString("passWord");
                String fullName = rs.getNString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                boolean admin = rs.getBoolean("role");
                return new UserDTO(userName, passWord, fullName, phoneNumber, admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //3 GET: user by phone number
    public UserDTO getUserByPhoneNumber(String xPhoneNumber) {
        String query = "" +
                "SELECT " +
                "   [userName], " +
                "   [passWord], " +
                "   [fullName], " +
                "   [phoneNumber], " +
                "   [role] " +
                "FROM Users " +
                "WHERE [phoneNumber] = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            //Set tham số
            ps.setString(1, xPhoneNumber);
            //Thực thi
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String passWord = rs.getString("passWord");
                String fullName = rs.getNString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                boolean admin = rs.getBoolean("role");
                return new UserDTO(userName, passWord, fullName, phoneNumber, admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // INSERT: User
    public boolean insert(String userName, String passWord, String fullName, String phoneNumber) {
        String query = "" +
                "INSERT INTO Users ([userName], [password], [fullName], [role],[phoneNumber]) \n" +
                "VALUES (?, ?, ?, 0, ?);";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            //Set
            ps.setString(1, userName);
            ps.setString(2, passWord);
            ps.setNString(3, fullName);
            ps.setString(4, phoneNumber);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE: user
    public boolean delete(String userName) {
        String query = "" +
                "DELETE FROM Users " +
                "WHERE userName = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            //set
            ps.setString(1, userName);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //1. UPDATE: user infor - Full Name and Phone Number
    public boolean updateInfor(String userName, String fullName, String phoneNumber) {
        String query = "" +
                "UPDATE Users " +
                "SET fullName =  ?, phoneNumber = ? " +
                "WHERE userName = ? ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setNString(1, fullName);
            ps.setNString(2, phoneNumber);
            ps.setString(3, userName);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //2. UPDATE: Password
    public boolean updatePassWord(String userName, String passWord) {
        String query = "" +
                "UPDATE Users " +
                "SET passWord =  ? " +
                "WHERE userName = ? ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, passWord);
            ps.setString(2, userName);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check login
    public boolean isUser(String userName, String passWord) {
        String query = "" +
                "SELECT \n" +
                "   [userName], " +
                "   [passWord], " +
                "   [fullName], " +
                "   [phoneNumber], " +
                "   [role] " +
                "FROM Users " +
                "WHERE " +
                "   [userName] = ? AND " +
                "   [passWord] = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, passWord);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Search: Dựa trên username + fullname + phoneNumber
    public ArrayList<UserDTO> search(String xSearchValue) {
        ArrayList<UserDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT " +
                "   [userName], " +
                "   [passWord], " +
                "   [fullName], " +
                "   [phoneNumber], " +
                "   [role] " +
                "FROM Users ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            //Set tham số
            //Thực thi
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userName = rs.getString("userName");
                String passWord = rs.getString("passWord");
                String fullName = rs.getNString("fullName");
                String phoneNumber = rs.getString("phoneNumber");
                boolean admin = rs.getBoolean("role");
                if ((userName + " " + fullName + " " + phoneNumber).toLowerCase().contains(xSearchValue.toLowerCase())) {
                    al.add(new UserDTO(userName, passWord, fullName, phoneNumber, admin));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public boolean updateRoleAdmin(String userName) {
        String query = "" +
                "UPDATE Users\n" +
                "SET [role] = 1\n" +
                "WHERE userName = ? ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, userName);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateRoleUser(String userName) {
        String query = "" +
                "UPDATE Users\n" +
                "SET [role] = 0\n" +
                "WHERE userName = ? ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, userName);
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
        UserDAO userDAO = new UserDAO();

        System.out.println("----------------------INSERT USERS: lap123---------------------------------");
        if (userDAO.insert("lap123", "123456", "Nguyễn Thành Lập", "1234567890")) {
            System.out.println("INSERT THÀNH CÔNG");
        } else {
            System.out.println("INSERT THẤT BẠI");
        }
        System.out.println("---------------------------GET USER BY USER NAME---------------------------");
        System.out.println(userDAO.getUserByUserName("lap123"));
        System.out.println("---------------------------GET USER BY PHONE NUMBER 1234567890---------------------------");
        if (userDAO.getUserByPhoneNumber("1234567890") != null) {
            System.out.println("Số điện thoại đã tồn tại");
        } else {
            System.out.println("Số điện thoại chưa tồn tại");
        }
        System.out.println("---------------------------UPDATE USER INFOR (Nguyễn Lập Thành, 123456789012) ---------------------------");
        if (userDAO.updateInfor("lap123", "Nguyễn Lập Thành", "123456789012")) {
            System.out.println("Cập nhật thông tin thành công");
        } else {
            System.out.println("Cập nhật thông tin thất bại");
        }
        System.out.println("---------------------------UPDATE USER PASSWORD (matkhaulacomrangduabo) ---------------------------");
        if (userDAO.updatePassWord("lap123", "matkhaulacomrangduabo")) {
            System.out.println("Cập nhật mật khẩu thành công");
        } else {
            System.out.println("Cập nhật mật khẩu thất bại");
        }
        System.out.println("---------------------------GET USERS-------------------------------");
        ArrayList<UserDTO> al = userDAO.getUsers();
        for (UserDTO userDTO : al) {
            System.out.println(userDTO);
        }
        System.out.println("---------------------------DELETE USERS: lap123---------------------------------------");
        if (userDAO.delete("lap123")) {
            System.out.println("XÓA THÀNH CÔNG");
        } else {
            System.out.println("XÓA THẤT BẠI");
        }
        System.out.println("---------------------------CHECK LOGIN: thanhcqb2048, 123456");
        if (userDAO.isUser("thanhcqb2048", "123456")) {
            System.out.println("ĐĂNG NHẬP THÀNH CÔNG");
        } else {
            System.out.println("ĐĂNG NHẬP THẤT BẠI");
        }
        System.out.println("---------------------------SEARCH: username (04)");
        al = userDAO.search("04");
        for (UserDTO userDTO : al) {
            System.out.println(userDTO);
        }
        System.out.println("---------------------------SEARCH fullname (th)");
        al = userDAO.search("Th");
        for (UserDTO userDTO : al) {
            System.out.println(userDTO);
        }
        System.out.println("---------------------------SEARCH phoneNumber");
        al = userDAO.search("56");
        for (UserDTO userDTO : al) {
            System.out.println(userDTO);
        }
    }
}
