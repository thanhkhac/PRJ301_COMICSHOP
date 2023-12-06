package model.genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DBContext;

public class GenreDAO extends DBContext {

    //1. Get Genre
    public GenreDTO getGenre(int xGenreID) {
        String query = "" +
                "SELECT genreID, name " +
                "FROM Genres " +
                "WHERE genreID = ?";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, xGenreID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int genreID = rs.getInt("genreID");
                String name = rs.getNString("name");
                return new GenreDTO(genreID, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<GenreDTO> getGenres() {
        ArrayList<GenreDTO> al = new ArrayList<>();
        String query = "" +
                "SELECT genreID, name " +
                "FROM Genres " +
                "ORDER BY name ";
        try {
            PreparedStatement ps = connect.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int genreID = rs.getInt("genreID");
                String name = rs.getNString("name");
                al.add(new GenreDTO(genreID, name)) ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
    }
    
    public static void main(String[] args) {
        GenreDAO dao = new GenreDAO();
        System.out.println("=================================GET Genre");
        System.out.println(dao.getGenre(1));
        System.out.println("=================================GET Genres");
        ArrayList<GenreDTO> al = dao.getGenres();
        for (GenreDTO genreDTO : al) {
            System.out.println(genreDTO);
        }
    }

}
