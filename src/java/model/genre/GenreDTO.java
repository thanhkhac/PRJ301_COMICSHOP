package model.genre;


public class GenreDTO {
    int genreID;
    String name;

    public GenreDTO(int genreID, String name) {
        this.genreID = genreID;
        this.name = name;
    }

    public int getGenreID() {
        return genreID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GenreDTO{" + "genreID=" + genreID + ", name=" + name + '}';
    }
    
    
}
