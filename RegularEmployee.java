import java.sql.Date;
import java.util.Scanner;
/**
 * Regular Employee class inherits properties from the Employee class.
 * This class provides functionalities like displaying and updating profiles, 
 * and a menu for regular employees.
 */
public class RegularEmployee extends Employee {

    Scanner scan = new Scanner(System.in,"UTF-8");

    /**
     * Default constructor for RegularEmployee.
     */
    public RegularEmployee(){ // Constructor
        super();
    }

    /**
     * Constructs a RegularEmployee with specific properties.
     * 
     * @param employeeID   the ID of the employee
     * @param username     the username of the employee
     * @param password     the password of the employee
     * @param role         the role of the employee
     * @param name         the name of the employee
     * @param surname      the surname of the employee
     * @param phoneNo      the phone number of the employee
     * @param dateOfBirth  the date of birth of the employee
     * @param dateOfStart  the start date of employment
     * @param email        the email adress of the employee
     */
    public RegularEmployee(int employeeID, String username, String password, String role, 
                           String name, String surname, String phoneNo, 
                           Date dateOfBirth, Date dateOfStart, String email) {
            //Calling superclass's constructor
            super(employeeID, username, password, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    /**
     * Displays the Normal Employee menu and manages user actions.
     * Options:
     * 1. Display Profile
     * 2. Update Profile
     * 3. Logout
     */
    public void RegularMenu() {
        int choice = 0;
        while (choice!=3){
            String title = this.name + " " + this.surname;
            System.out.println(title);
            System.out.println("\nRegular Employee Menu");
            System.out.println("=====================");
            System.out.println("1. Display Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Logout");

            choice = getValidInt();

            switch (choice) {
                case 1: 
                    if(displayProfile()==1)
                        updateProfile();
                    returnToMenu();
                    break;
                case 2 :
                    updateProfile();
                    returnToMenu();
                    break;
                case 3 : 
                    System.out.println("Logging out...");
                    //Exit the menu
                    break;
                default :
                    System.out.println("Invalid choice. Try again.");
                    continue;
            }
        }
    }

    /**
     * Returns the user to the main menu when the valid input is entered.
     * Repeatedly asks until the 'M' key is pressed.
     */
    public void returnToMenu(){
        System.out.println("Press 'M' to return main menu");
    
        while (true) {
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("M")){
                RegularMenu();
                break;
            }
            else
                System.out.println("Invalid input. Please press 'M' to return to the menu.");
        }
    }

}
