import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Employee {

    //Information of Employees
    protected int employeeID;

    protected String username;
    protected String password;
    protected String role;
    protected String name;
    protected String surname;
    protected String phoneNo;
    protected String email;

    protected Date dateOfBirth;
    protected Date dateOfStart;

    private Scanner scan = new Scanner(System.in);

    // Constructor

    public Employee(){
        this.employeeID = 0;
        this.username = null;
        this.password = null;
        this.role = null;
        this.name = null;
        this.surname = null;
        this.phoneNo = null;
        this.dateOfBirth = null;
        this.dateOfStart = null;
        this.email = null;
    };

    public Employee(int employeeID, String username, String password, String role, 
                    String name, String surname, String phoneNo, 
                    Date dateOfBirth, Date dateOfStart, String email) {
            this.employeeID = employeeID;
            this.username = username;
            this.password = password;
            this.role = role;
            this.name = name;
            this.surname = surname;
            this.phoneNo = phoneNo;
            this.dateOfBirth = dateOfBirth;
            this.dateOfStart = dateOfStart;
            this.email = email;
    }

    // Getters and Setters

    //employeeID
    public int getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    //Username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    //Role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //Name
    public String getName() {
        return name;
    }

    //set etmeli mi
    public void setName(String name) {
        this.name = name;
    }

    //Surname
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    //PhoneNo
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    //DateOfBirth
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //DateOfStart
    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    //Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
            this.email = email;
    }

    // Abstract Methods (2 tane)
    protected int displayProfile() {

        System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-20s\n", 
            "ID", "Username", "Role", "Name", "Surname", "Phone", "Email", "Birth Date", "Start Date");
    
        System.out.printf("%-10d %-15s %-15s %-15s %-15s %-15s %-20s %-20s %-15s\n", 
                this.employeeID, this.username, this.role, this.name, this.surname, this.phoneNo, this.email, this.dateOfBirth, this.role);
    
        System.out.println("1. Change Profile Informations");
        System.out.println("2. Return Main Menu");
        int choice=0;
        while(choice != 1 && choice != 2) {
            while(!scan.hasNextInt()){
                System.out.printf("%nInvalid input. Please enter an integer (1, 2).%n Make a choice: ");
                scan.next(); // skip the invalid input 
            }
            choice = scan.nextInt();

            if(choice != 1 && choice != 2)
                System.out.printf("%nInvalid input. Please enter an integer (1, 2).%n Make a choice: ");

        }
        return choice;
    }

    public void updateProfile() {

        scan = new Scanner(System.in);
        Database database = new Database();
        database.connectDatabase();

        boolean flag=true; 
        String column = ""; 
        String value = ""; 

        while(flag){ 
            System.out.println("Enter the column to update(password, phoneNo, and e-mail)");
            System.out.println("Select a role to display employees:");
            System.out.println("1. Password");
            System.out.println("2. Phone Number");
            System.out.println("3. E-mail");
            System.out.print("Choose a column from menu: ");

            int choice = Manager.getValidInt();
                switch(choice) {
                    case 1: 
                        column = "password";
                        flag=false; 
                        System.out.println("Enter the new value for " + column);
                        value = setPasswordFirstTime();
                        break;
                    case 2:
                        column = "phoneNo";
                        flag=false;
                        System.out.println("Enter the new value for " + column);
                        value = phoneCheck();
                        this.phoneNo=value;
                        break;
                    case 3:
                        column = "email";
                        flag=false;
                        System.out.println("Enter the new value for " + column);
                        while(true){
                            value = scan.nextLine();

                            
                            if(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
                                break;
                            else
                                System.out.println("Please enter a proper email!: ");
                        }
                        this.email=value;
                        break;
                    default:
                        break;
                }
        }

        
        database.updateEmployee(this, column, value);
        System.out.println("Profile updated successfully!");
    }

    //Helper Methods (4 tane)

    //1.
    public String setPasswordFirstTime(){
        // Şifreyi kontrol et
        String newPassword = "";
        while(true){
            System.out.print("Password: ");
            newPassword = scan.nextLine();

            //Calculate password strength
            int score = evaluatePasswordStrenght(newPassword);
            String strength = getPasswordStrengthLevel(score);
            System.out.println("Password Strength: " + strength);

            //Be sure that password meets criteria:
            if(score >= 5 && !newPassword.contains(" ")) {
                this.password = newPassword;
                System.out.println("Password updated successfully!");
                break;
            } else {
                System.out.println("Please compy with password criteria");
                System.out.println("Password Criteria:\n-12 characters long\n-Include a mix of: \n\tUppercase\n\tLowercase\n\tNumbers\n\tSpecial Characters\n\tTurkish Characters\n-It must not contain spaces");
            }
        }
        return newPassword;
    }

    /*
    Password Strength Evaluation Criteria Strength Scoring:
        +2 points: 12+ characters
        +1 point: Uppercase letters
        +1 point: Lowercase letters +1 point:
        Numbers +1 point: Special characters
        +1 point: Turkish characters

    Strength Levels:
        0-2: Very Weak
        3-4: Weak
        5-6: Medium
        7-8: Strong
        9+: Very Strong

    Test Cases:
        • 123456 → Very Weak (0-1 points)
        • Password123 → Weak (3-4 points)
        • Türk!şÇalışan2024 → Very Strong (9+ points)
    */

    //2.
    public int evaluatePasswordStrenght(String password) {
        int score = 0;

        //1.Check for length (12 or more characters)
        if(password.length() >= 12)
            score += 2;

        //2.Check for uppercase letters
        if(password.matches(".*[A-Z].*"))
            score += 1;

        //3.Check for lowercase letters
        if(password.matches(".*[a-z].*"))
            score += 1;

        // 4. Check for numbers
        if (password.matches(".*[0-9].*")) {
            score += 1;
        }

        // 5. Check for special characters
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            score += 1;
        }

        // 6. Check for Turkish characters
        if (password.matches(".*[öÖçÇşŞıİğĞüÜ].*")) {
            score += 1;
        }

        return score;
    }

    public String getPasswordStrengthLevel(int score) {
        if(score <= 2)
            return "Very Weak";
        else if (score <= 4)
            return "Weak";
        else if (score <= 6)
            return "Medium";
        else if (score <= 8)
            return "Strong";
        else
            return "Very Strong";
    }


    //3.Başka çalışanınki ile aynı olmamalı bir de 10 haneli olmalı
    protected String phoneCheck(){

        Database database = new Database();
        database.connectDatabase(); 
        ArrayList<Employee> employees=database.getEmployees(); 
        String phoneNo = ""; 
        boolean success = true;
        while(success){ 
            success=true; //Bu kısım silinmeli!!
            System.out.print("Phone Number: ");
            phoneNo = scan.nextLine();
            for(Employee employee : employees){
                if(employee.getPhoneNo().equals(phoneNo)){
                    System.out.println("This phone number is belong to someone else. Please enter different phone number!");
                    break;
                }
                else if(!phoneNo.matches("\\d{10}")){ 
                    System.out.println("This phone number is not correct. Please enter 10 digit phone number!");   
                    break;
                }
                else
                    success = false;
            }
        }

        employees=null;

        database.disconnectDatabase();

        return phoneNo;
    }

    //Prints the action and the user to console
    //4.Bir çalışanın gerçekleştirdiği eylemi ve zamanı günlük olarak kaydeder.
    public void logAction(String action) {
        System.out.println("LOG: " + action + " by " + username + " at " + new java.util.Date());
    }

    //Output example:
    //LOG: Profile updated by ece_g at Mon Dec 02 14:32:47 IST 2024

    //The reason behind that method is saving the action on the diary
    //Trace the actions , time and the user 
}