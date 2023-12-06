package model.author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DBContext;

public class AuthorDAO extends DBContext {

    //GET: Author
    public AuthorDTO getAuthor(int xAuthorID) {
        String query = "" +
                "SELECT authorID, [name], thumbnail, [desc] " +
                "FROM Authors " +
                "WHERE authorID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xAuthorID);
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

    //GET: Authors
    public ArrayList<AuthorDTO> getAuthors() {
        ArrayList<AuthorDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT authorID, [name], thumbnail, [desc] " +
                "FROM Authors ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String name = rs.getNString("name");
                String thumbnail = rs.getNString("thumbnail");
                String desc = rs.getNString("desc");
                al.add(new AuthorDTO(authorID, name, thumbnail, desc)) ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }

    public static void main(String[] args) {
        AuthorDAO authorDAO = new AuthorDAO();
        System.out.println("=================================GET AUTHOR: 1 - GEGE TAKUMI==========");
        System.out.println(authorDAO.getAuthor(1));
        System.out.println("=================================GET AUTHORs =========================");
        ArrayList<AuthorDTO> al = authorDAO.getAuthors();
        for (AuthorDTO authorDTO : al) {
            System.out.println(authorDTO);
        }
    }
}
