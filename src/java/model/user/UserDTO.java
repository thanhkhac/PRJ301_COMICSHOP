package model.user;
public class UserDTO {
    String userName; 
    String passWord;
    String fullName; 
    String phoneNumber;
    boolean role; 

    public UserDTO(String userName, String passWord, String fullName, String phoneNumber, boolean admin) {
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "UserDTO{" + "userName=" + userName + ", passWord=" + passWord + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", admin=" + role + '}';
    }
    
    
}
