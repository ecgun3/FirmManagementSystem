import java.sql.Date;

//Çalışan türlerinin temel sınıfı --> Abstract
public abstract class Employee {

    //Information of Employees
    protected int employeeID;
    protected String username;
    protected String password;
    protected boolean isDefaultPassword; //Şifrenin varsayılan olup olmadığını kontrol eder
    protected String role;
    protected String name;
    protected String surname;
    protected String phoneNo;
    protected Date dateOfBirth;
    protected Date dateOfStart;
    protected String email;

    // Constructor
    public Employee(int employeeID, String username, String password, String role, 
                    String name, String surname, String phoneNo, 
                    Date dateOfBirth, Date dateOfStart, String email) {
            this.employeeID = employeeID;
            this.username = username;
            this.password = password;
            this.isDefaultPassword = password.equals("1234"); //Default password control
            this.role = role;
            this.name = name;
            this.surname = surname;
            this.phoneNo = phoneNo;
            this.dateOfBirth = dateOfBirth;
            this.dateOfStart = dateOfStart;
            this.email = email;
    }

    // Getters and Setters
    public int getEmployeeID() {
        return employeeID;
    }

    //Set etmeli mi
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    //en az bir değişik karakter ve en az bir sayı eklesin (boşluk olamaz)
    public void setPassword(String newPassword) {
        // Şifreyi kontrol et
        if (newPassword.length() >= 8 && 
            newPassword.matches(".*[A-Z].*") && // En az bir büyük harf
            newPassword.matches(".*[a-z].*") && // En az bir küçük harf
            newPassword.matches(".*[0-9].*") && // En az bir rakam
            newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*") && // En az bir özel karakter
            !newPassword.contains(" ")) { // Şifre boşluk içermemeli
            this.password = newPassword;
            this.isDefaultPassword = false;
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Password must be at least 8 characters long, include a mix of uppercase, lowercase, numbers, and special characters, and not contain spaces.");
        }
    }
    
    public boolean isDefaultPassword() {
        return isDefaultPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    //set etmeli mi
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    //set etmese de olur
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(isValidEmail(email)) {
            this.email = email;
        } else {
            System.out.println("Invalid email format!");
        }
    }

    // Abstract Methods
    public abstract void displayProfile();
    public abstract void updateProfile(String newEmail, String newPhoneNumber);

    //Helper Methods

    //Valid email: ece.gunaydin@example.com
    //Invalid email: ece.gunaydin@com
    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    //Prints the action and the user to console
    public void logAction(String action) {
        System.out.println("LOG: " + action + " by " + username + " at " + new java.util.Date());
    }

    //Output example:
    //LOG: Profile updated by ece_g at Mon Dec 02 14:32:47 IST 2024

    //The reason behind that method is saving the action on the diary
    //Trace the actions , time and the user 
}
