import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Manager extends Employee{

    private Database database = new Database();

    private static Scanner scan = new Scanner(System.in);

    public Manager(){
        super();
    }

    public Manager(int employeeID, String username, String password, String role, 
                        String name, String surname, String phoneNo, 
                        Date dateOfBirth, Date dateOfStart, String email){
        // Parent class constructor çağırıldı
        super(employeeID, username, password, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }
    
    public void managerMenu()
    {
        while (true){
        String title = this.name + " " + this.surname;
        System.out.println(title);
        System.out.println("----Manager Menu----");
        System.out.println("1. See Profile Informations");
        System.out.println("2. Display All Employees");
        System.out.println("3. Display Employees with the Role");
        System.out.println("4. Display Employee with the Username");
        System.out.println("5. Update Employee Non-profile Fields");
        System.out.println("6. Hire Employee");
        System.out.println("7. Fire Employee");
        System.out.println("8. Algorithms");
        System.out.println("9. Logout");

        int choice = getValidInt();

        switch(choice) {
            case 1:
                displayProfile(); // buraya koşul filan eklencek
                updateProfile();
                break;
            case 2: 
                displayAllEmployees();
                break;
            case 3: 
                displayEmployeesRole();
                break;
            case 4: 
                displayEmployeeUsername();
                break;
            case 5: 
                updateEmployee();
                break;
            case 6: 
                hireEmployee();
                break;
            case 7: 
                fireEmployee();
                break;
            case 8: 
                // algorithms();
                break;
            case 9: 
                System.out.println("Logging Out...");
                return;
            default: 
                System.out.println("Invalid Choice. Please Try Again.");
                continue;
        }

        }
    }

    //updateProfile doğru değil
    @Override
    public void updateProfile() {
        database.connectDatabase();

        System.out.println("Enter the column to update(password, phoneNo, and e-mail)");//bunu switch choice yapıcaz
        String column = scan.nextLine();

        System.out.println("Enter the new value for " + column);
        String value = scan.nextLine();

        database.updateEmployee(this, column, value);
        System.out.println("Profile updated successfully!");
    }

    //display için fonksiyon
    private void displayEmployeeDetails(ArrayList<Employee> employees) {
        System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-20s\n", 
                "ID", "Username", "Role", "Name", "Surname", "Phone", "Email", "Birth Date", "Start Date");
    
        for (Employee employee : employees) {
            System.out.printf("%-10d %-15s %-15s %-15s %-15s %-15s %-20s %-20s %-15s\n", 
                    employee.getEmployeeID(),
                    employee.getUsername(),
                    employee.getRole(),
                    employee.getName(),
                    employee.getSurname(),
                    employee.getPhoneNo(),
                    employee.getEmail(),
                    employee.getDateOfBirth(),
                    employee.getDateOfStart());
        }
    }

    //tüm çalışanları display eder
    private ArrayList<Employee> displayAllEmployees(){


        database.connectDatabase();

            ArrayList<Employee> employees = database.getEmployees();
            displayEmployeeDetails(employees);
            askForEmployeeInfoChange();

        database.disconnectDatabase();
        return employees;
    }  

    public String selectRole() {
    
        //display rolemenu
        System.out.println("Select a role to display employees:");
        System.out.println("1. Manager");
        System.out.println("2. Engineer");
        System.out.println("3. Intern");
        System.out.println("4. Technician");
        System.out.print("Choose a role from menu: ");
    
        int choice = getValidInt();

        switch (choice) {
            case 1:
                return "Manager";
            case 2:
                return "Engineer";
            case 3:
                return "Intern";
            case 4:
                return "Technician";
            default:
                return null; // Geçersiz seçim
        }

    }

    //role göre seçim yaparak display
    private void displayEmployeesRole(){

        database.connectDatabase();
        String role = null;
        try {
            while(role == null){
            role = selectRole();
                if (role == null) 
                    System.out.println("Invalid choice. Please try again.");
            }
            ArrayList<Employee> employees = database.getEmployeesRole(role);
            displayEmployeeDetails(employees);
            askForEmployeeInfoChange();
            }
        catch (Exception e) {
            System.err.println("An error occurred while displaying employees by role.");
            e.printStackTrace();    
        }
    
        database.disconnectDatabase();
    }

    //username'e göre display burda şifreyi gizlememiz lazım
    private void displayEmployeeUsername(){


        database.connectDatabase();

        try{

            System.out.println("Enter the username for the search");
            String username = scan.nextLine();
            Employee employee = database.getEmployeeUsername(username);
            ArrayList<Employee> employees = new ArrayList<>();

            if(employee != null){
                employees.add(employee);
                displayEmployeeDetails(employees); 
            } else {
                System.out.println("No employee found with :" + username);
            }
        }catch (Exception e) {
            System.err.println("An error occurred while displaying employees by username.");
            e.printStackTrace();    
        }
        database.disconnectDatabase();
        returnToMenu();
    }

    private String usernameCheck(){

        database.connectDatabase();
        ArrayList<Employee> employees=database.getEmployees();
        boolean flag=true;
        String username = "";
        while(flag){
            boolean success=true;
            username = scan.nextLine();
            for(Employee employee : employees){
                if(employee.getUsername().equals(username)){
                    System.out.println("This username already taken. Please enter another username!");
                    System.out.print("Username: ");
                    success=false;
                }                
            }
            if(success==true){
                flag=false;
            }

        }
        return username;
    } 

    //yeni employee girişi exception lazım
    private void hireEmployee(){

        System.out.println("Enter new employee information:");

        System.out.print("username: ");
        String username = usernameCheck();

        String defaultPassword = "password123";

        System.out.print("Name: ");
        String name = scan.nextLine();

        System.out.print("Surname: ");
        String surname = scan.nextLine();

        String phoneNo = phoneCheck();

        //Valid email: ece.gunaydin@example.com
        //Invalid email: ece.gunaydin@com
        System.out.print("Email: ");
        while(true){
            String email = scan.nextLine();
            if(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
                break;
            else
                System.out.println("Please enter a proper email!: ");
        }
        
        System.out.print("Date of Birth - ");
        String birthdate = Date();
        Date date1 = Date.valueOf(birthdate);

        System.out.print("Start Date - ");
        String startdate = Date();
        Date date2 = Date.valueOf(startdate);

            //rol ekleme kısmı
        System.out.print("Role:");
        String role = null;
        while(role == null){
            role = selectRole();
                if (role == null) 
                    System.out.println("Invalid choice. Please try again.");
            }



        Employee hire;

        if (role.equalsIgnoreCase("Manager")) {
            hire = new Manager(0, username, defaultPassword, name, surname, phoneNo, email, date1, date2, role);
        }
        else {
            hire = new RegularEmployee(0, username, defaultPassword, name, surname, phoneNo, email, date1, date2, role);
        }


        database.connectDatabase();
        database.insertEmployee(hire);
        database.disconnectDatabase();
        returnToMenu();
    }

    //employee silme
    private void fireEmployee(){

        database.connectDatabase();
        

            String username="";
            boolean flag=true;
            while(flag){
                System.out.printf("Enter the username for the delete operation: ");
                username = scan.nextLine();
                //menajerin kendisini silememesi için
                if (this.username == username) {
                    System.out.println("You cannot delete your own account.");
                    while(true){
                        System.out.println("If you want to delete another employee press 1, or back to main menu press 2");
                        int choice = getValidInt();
                        switch(choice){
                            case 1:
                                continue;
                            case 2:
                                break;
                            default:
                                System.out.println("Invalid choice!");
                        }
                    }
                }
                else
                    break;

            }

            Employee employeetodelete = database.getEmployeeUsername(username);

            if(employeetodelete != null){
                database.deleteEmployee(employeetodelete);
                System.out.println("Employee with Username: " + username +" Deleted.");
            }else {
                System.out.println("employee not found:" + username);
            }

        database.disconnectDatabase();
        returnToMenu();
    }

    private String Date(){

        String date = "";
        String properDate = "yyyy-mm-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(properDate);
        sdf.setLenient(false);

        boolean flag=true;
        while(flag){

            System.out.println("Please enter an appropriate date(YYYY-MM-DD): ");
            date = scan.nextLine();
            try{
                sdf.parse(date);
                flag = false;
            }
            catch(ParseException exception){
            }
            
        }
        return date;
    }

    //employee güncelleme
    private void updateEmployee(){

        database.connectDatabase();
        
        try{

            System.out.println("Enter the username for the update operation:");
            System.out.println("Username: ");
            String username = scan.nextLine();

            Employee employeetoupdate = database.getEmployeeUsername(username);

            if(employeetoupdate != null){
                System.out.println("Enter the number of column to update:");
                System.out.println("1. Username");
                System.out.println("2. Name");
                System.out.println("3. Surname");
                System.out.println("4. Role");
                System.out.println("5. Birthdate (YYYY-MM-DD)");
                System.out.println("6. Start Date (YYYY-MM-DD)");

                String column = null;
                int choice = getValidInt();

                String value="";

                switch (choice) {
                    case 1:
                        column = "username";
                        System.out.printf("%nEnter the new value for " + column);
                        value = usernameCheck();
                        break;
                    case 2:
                        column = "name";
                        System.out.printf("%nEnter the new value for " + column);
                        value = scan.nextLine();
                        break;
                    case 3:
                        column = "surname";
                        System.out.printf("%nEnter the new value for " + column);
                        value = scan.nextLine();
                        break;
                    case 4:
                        column = "role";
                        System.out.printf("%nEnter the new value for " + column);
                        value = selectRole();
                        break;
                    case 5:
                        column = "date_of_birth";
                        value = Date();
                        break;
                    case 6:
                        column = "date_of_start";
                        value = Date();
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 6.");
                        return;
                }
                database.updateEmployee(employeetoupdate, column, value);
            }else
                System.out.println("Employee not found:" + username);

        }catch(Exception e) {
            System.err.println("An error occurred while updating employee");
            e.printStackTrace();    
        }
        database.disconnectDatabase();
        returnToMenu();
    }

    public void returnToMenu(){
        System.out.println("Press 'M' to return main menu");
    
        while (true) {
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("M")){
                break;
            }
            else
                System.out.println("Invalid input. Please press 'M' to return to the menu.");
        }
    }

    public void askForEmployeeInfoChange() {
    
    System.out.println("Do you want to change an employee information?");
    System.out.println("1. YES");
    System.out.println("2. NO (Return to main menu)");

    while (true) {
        int userInput = getValidInt();

            if (userInput == 1) {
                updateEmployee();
                return;
            } else if (userInput == 2) {
                return;
            } else {
                System.out.println("Please make a valid choice (1 for YES or 2 for NO).");
            }
        } 
    }


    public static int getValidInt() {

        int validInput = 0;

        while (true) {
            System.out.print("Please enter a valid integer:");
            if (scan.hasNextInt()) { 
                validInput = scan.nextInt();
                scan.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scan.nextLine();
            }
        }

        return validInput;
    }


    /*ALGORITHMS gelecek



     
    
    */
}


