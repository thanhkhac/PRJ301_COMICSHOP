package model.orderdetail;


public class OrderDetailDTO {
    int orderID;
    int bookID;
    int quantity;

    public OrderDetailDTO(int orderID, int bookID, int quantity) {
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
    }

    public OrderDetailDTO(int bookID, int quantity) {
        this.bookID = bookID;
        this.quantity = quantity;
    }
    
    

    public int getOrderID() {
        return orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" + "orderID=" + orderID + ", bookID=" + bookID + ", quantity=" + quantity + '}';
    }
    
    
}
