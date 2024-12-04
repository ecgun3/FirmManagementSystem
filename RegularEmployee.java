import java.sql.Date;
import java.util.Scanner;

public class RegularEmployee extends Employee {

    Scanner scan = new Scanner(System.in,"UTF-8");

    // Constructor

    public RegularEmployee(){
        super();
    }

    public RegularEmployee(int employeeID, String username, String password, String role,
                           String name, String surname, String phoneNo,
                           Date dateOfBirth, Date dateOfStart, String email) {
        //Calling superclass's constructor
        super(employeeID, username, password, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    //Menu
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