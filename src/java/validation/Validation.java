package validation;

public class Validation {

    public static boolean isLengthInRange(String str, int min, int max) {
        try {
            if (str.length() >= min && str.length() <= max) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isPhoneNumber(String strN) {
        try {
            if (strN.matches("^\\d{10,12}$")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    public static int parseInteger(String number){
        try {
            int n = Integer.parseInt(number);
            return n;
        } catch (NumberFormatException e) {
        }
        return 1;
    }
    public static void main(String[] args) {
        Validation valid = new Validation();
        //
        System.out.println(valid.isPhoneNumber("0382293846"));
    }
}
