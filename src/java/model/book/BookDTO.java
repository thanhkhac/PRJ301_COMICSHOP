package model.book;

public class BookDTO {

    int bookID;
    String title;
    String desc;
    String thumbnail;
    double price;
    String publishedDate;
    int authorID;
    int quantity;

    public BookDTO(int bookID, String title, String desc, String thumbnail, double price, String publishedDate, int authorID) {
        this.bookID = bookID;
        this.title = title;
        this.desc = desc;
        this.thumbnail = thumbnail;
        this.price = price;
        this.publishedDate = publishedDate;
        this.authorID = authorID;
    }

    public BookDTO(int bookID, String title, String desc, String thumbnail, double price, String publishedDate, int authorID, int quantity) {
        this.bookID = bookID;
        this.title = title;
        this.desc = desc;
        this.thumbnail = thumbnail;
        this.price = price;
        this.publishedDate = publishedDate;
        this.authorID = authorID;
        this.quantity = quantity;
    }
    
    

    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public int getAuthorID() {
        return authorID;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" + "bookID=" + bookID + ", title=" + title + ", desc=" + desc + ", thumbnail=" + thumbnail + ", price=" + price + ", publishedDate=" + publishedDate + ", authorID=" + authorID + ", quantity=" + quantity + '}';
    }
    

    

}
