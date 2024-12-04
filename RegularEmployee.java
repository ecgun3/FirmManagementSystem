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

            System.out.print("\u001B[35m");
            System.out.println(title+"\n");
            System.out.print("\u001B[36m");

            System.out.println("\nRegular Employee Menu");
            System.out.println("=====================");
            System.out.println("1. Display and Update Profile");
            System.out.println("2. Logout");

            choice = getValidInt();

            switch (choice) {
                case 1:
                    if(displayProfile()==1)
                        updateProfile();
                    break;
                case 2 :
                    System.out.println("Logging out...");
                    //Exit the menu
                    break;
                default :
                    System.out.println("Invalid choice. Try again.");
                    continue;
            }
            break;
        }
    }


}