package model.author;


public class AuthorDTO {
    int authorID;
    String name;
    String thumbnail;
    String desc;

    public AuthorDTO(int authorID, String name, String thumbnail, String desc) {
        this.authorID = authorID;
        this.name = name;
        this.thumbnail = thumbnail;
        this.desc = desc;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" + "authorID=" + authorID + ", name=" + name + ", thumbnail=" + thumbnail + ", desc=" + desc + '}';
    }
    
    
}
