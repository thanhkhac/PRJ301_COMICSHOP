package model.order;

public class OrderDTO {

    int orderID;
    String userName;
    String recipientName;
    String orderDate;
    String phoneNumber;
    String shipAddress;
    int status;

    public OrderDTO(int orderID, String userName, String recipientName, String orderDate, String phoneNumber, String shipAddress, int status) {
        this.orderID = orderID;
        this.userName = userName;
        this.recipientName = recipientName;
        this.orderDate = orderDate;
        this.phoneNumber = phoneNumber;
        this.shipAddress = shipAddress;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" + "orderID=" + orderID + ", userName=" + userName + ", recipientName=" + recipientName + ", orderDate=" + orderDate + ", phoneNumber=" + phoneNumber + ", shipAddress=" + shipAddress + ", status=" + status + '}';
    }


    
    
}
