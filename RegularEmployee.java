import java.sql.Date;
import java.util.Scanner;

public class RegularEmployee extends Employee {

    // Constructor
    public RegularEmployee(int employeeID, String username, String password, String role, 
                           String name, String surname, String phoneNo, 
                           Date dateOfBirth, Date dateOfStart, String email) {
            //Calling superclass's constructor
            super(employeeID, username, password, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    @Override
    public void displayProfile() {
        System.out.println("Profile Information:");
        System.out.println("Name: " + getName() + " " + getSurname());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNo());

        //Note that profil refers to password, phone_no, and e-mail only
    }

    @Override
    public void updateProfile(String newEmail, String newPhoneNumber) {
        if (newEmail != null) //boş ise bir şeyler yap
            setEmail(newEmail);
        if(newPhoneNumber != null)
            setPhoneNo(newPhoneNumber);
        System.out.println("Profile updated successfully!");

        //After the update, create a log record
    }

    //Menu
    public void displayMenu() {
        if(isDefaultPassword()) {
            //If the password is default, first update the password
            System.out.println("You must update your password before proceeding");
            System.out.println("1. Update Password");
        } else {
            System.out.println("\nRegular Employee Menu");
            System.out.println("1. Display Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Profile");
            System.out.println("4. Logout");
        }
    }

    //Update ProfileMenu
    public void updateProfileMenu(Scanner scanner) {
        System.out.println("Which field do you want to update");
        System.out.println("1. Email");
        System.out.println("2. Phone Number");
        System.out.println("3. Both");

        //BU seçeneklre dışında bir şey seçerse? 
        int choice = scanner.nextInt();
        scanner.nextLine(); //Clear buffer because we call nextInt before

        // Invalid choice
        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice! Please choose a valid option.");
            return; // End the method if the input is invalid
        }

        if(choice == 1 || choice == 3) {
            System.out.println("Enter new mail:");
            String newEmail = scanner.nextLine();
            setEmail(newEmail);
        }
        if(choice == 2 || choice == 3) {
            System.out.println("Enter new phone number");
            String newPhoneNumber = scanner.nextLine();
            setPhoneNo(newPhoneNumber);
        }
        System.out.println("Profile updated successfully!");
        logAction("Profile updated via menu");
    }

    public void runMenu(Scanner scanner) {
        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextInt();//Clear buffer

            switch (choice) {
                case 1 -> displayMenu();
                case 2 -> updateProfileMenu(scanner);
                case 3 -> {
                    System.out.println("Enter new password");
                    String newPassword = scanner.nextLine();
                    setPassword(newPassword);
                }
                case 4 -> {
                    System.out.println("Logging out...");
                    return; //Exit the menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
