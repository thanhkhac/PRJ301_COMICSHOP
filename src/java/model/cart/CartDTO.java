package model.cart;


public class CartDTO {
    String userName;
    int bookID;
    int quantity;

    public CartDTO(String userName, int bookID, int quantity) {
        this.userName = userName;
        this.bookID = bookID;
        this.quantity = quantity;
    }

    public String getUserName() {
        return userName;
    }

    public int getBookID() {
        return bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    

    @Override
    public String toString() {
        return "CartDTO{" + "userName=" + userName + ", bookID=" + bookID + ", quantity=" + quantity + '}';
    }
    
    
    
}
