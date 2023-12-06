package model;

import java.sql.Connection;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.DriverManager;

public class DBContext {

    public Connection connect;
    
    public DBContext() {
        String user = "sa";
        String password = "123456";
        String port = "1433";
        String databaseName = "COMICSHOP";
        String url = "jdbc:sqlserver://localhost:" + port + ";databaseName=" + databaseName;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(url, user, password);
            System.out.println("Connect Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws SQLException {
        DBContext context = new DBContext();
    }
}
