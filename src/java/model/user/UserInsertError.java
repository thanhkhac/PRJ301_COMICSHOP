package model.user;


public class UserInsertError {
    String length;
    String confirmNotMatch;
    String phoneNumberExisted;
    String userNameExisted;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getConfirmNotMatch() {
        return confirmNotMatch;
    }

    public void setConfirmNotMatch(String confirmNotMatch) {
        this.confirmNotMatch = confirmNotMatch;
    }

    public String getPhoneNumberExisted() {
        return phoneNumberExisted;
    }

    public void setPhoneNumberExisted(String phoneNumberExisted) {
        this.phoneNumberExisted = phoneNumberExisted;
    }

    public String getUserNameExisted() {
        return userNameExisted;
    }

    public void setUserNameExisted(String userNameExisted) {
        this.userNameExisted = userNameExisted;
    }
    
    
    
}
