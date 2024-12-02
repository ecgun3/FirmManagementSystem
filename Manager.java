import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends Employee{

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
        System.out.println("1. Update Own Profile");
        System.out.println("2. Display All Employees");
        System.out.println("3. Display Employees with the Role");
        System.out.println("4. Display Employee with the Username");
        System.out.println("5. Update Employee Non-profile Fields");
        System.out.println("6. Hire Employee");
        System.out.println("7. Fire Employee");
        System.out.println("8. Algorithms");
        System.out.println("9. Logout");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1: 
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
                break;
            default: 
                System.out.println("Invalid Choice. Please Try Again.");
                continue;
        }

        if(choice == 9) break;
        }
    }

    //updateProfile doğru değil
    @Override
    public void updateProfile() {
        Database database = new Database();
        database.connectDatabase();

        Scanner scan = new Scanner(System.in);
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

        Database database = new Database();
        database.connectDatabase();

        // try{
            ArrayList<Employee> employees = database.getEmployees();
            displayEmployeeDetails(employees);
        // }
        
        // catch(Exception e) {
        //         System.err.println("Something went Wrong.");
        //         e.printStackTrace();
        // }
        database.disconnectDatabase();
        return employees;
    }  

    public String selectRole() {
        Scanner scan = new Scanner(System.in);
    
        //display rolemenu
        System.out.println("Select a role to display employees:");
        System.out.println("1. Manager");
        System.out.println("2. Engineer");
        System.out.println("3. Intern");
        System.out.println("4. Technician");
        System.out.print("Choose a role from menu: ");
    
        int choice = scan.nextInt();
        scan.nextLine();

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
        Database database = new Database();
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
            }
        catch (Exception e) {
            System.err.println("An error occurred while displaying employees by role.");
            e.printStackTrace();    
        }
    
    database.disconnectDatabase();
    returnToMenu();
    }

    //username'e göre display burda şifreyi gizlememiz lazım
    private void displayEmployeeUsername(){

        Database database = new Database();
        database.connectDatabase();

        try{
            Scanner scan = new Scanner(System.in);
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

    //yeni employee girişi exception lazım
    private void hireEmployee(){
        ArrayList<Employee> employees=displayAllEmployees();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter new employee information:");

        boolean success=true;
        String username = "";
        while(success){
            System.out.print("Username: ");
            username = scan.nextLine();
            for(Employee employee : employees){
                if(employee.getUsername().equals(username)){
                    System.out.println("This username already taken. Please enter another username!");
                    success=false;
                    break;
                }
            }
        }

        String defaultPassword = "password123";

        System.out.print("Name: ");
        String name = scan.nextLine();

        System.out.print("Surname: ");
        String surname = scan.nextLine();

        success=true;
        String phoneNo = "";
        while(success){
            System.out.print("Phone Number: ");
            phoneNo = scan.nextLine();
            for(Employee employee : employees){
                if(employee.getPhoneNo().equals(phoneNo)){
                    System.out.println("This phone number is belong to someone else. Please enter different phone number!");
                    success=false;
                    break;
                }
                else if(!phoneNo.matches("\\d{10}")){
                    System.out.println("This phone number is not correct. Please enter 10 digit phone number!");   
                    success=false;
                    break;
                }
            }
        }

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
        
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String birthdate = scan.nextLine();//date ekleme düzenlencek
        Date date1 = Date.valueOf(birthdate);

        System.out.print("Start Date (YYYY-MM-DD): ");
        String startdate = scan.nextLine();//date ekleme düzenlencek
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

        Database database = new Database();
        database.connectDatabase();
        database.insertEmployee(hire);
        database.disconnectDatabase();
        returnToMenu();
    }

    //employee silme
    private void fireEmployee(){
        Database database = new Database();
        database.connectDatabase();
        
        try{
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the username for the delete operation");
            String username = scan.nextLine();
            
            if (username == this.username) {
                System.out.println("You cannot delete your own account.");
                return;
            }

            Employee employeetodelete = database.getEmployeeUsername(username);

            if(employeetodelete != null){
                database.deleteEmployee(employeetodelete);
                System.out.println("Employee with Username: " + username +" Deleted.");
            }else {
            System.out.println("employee not found:" + username);
            }
        }catch(Exception e) {
        System.err.println("An error occurred while deleting employee");
        e.printStackTrace();    
        }
    database.disconnectDatabase();
    returnToMenu();
    }

    //employee güncelleme
    private void updateEmployee(){
        Database database = new Database();
        database.connectDatabase();
        
        try{
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the username for the update operation");
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
                int choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        column = "username";
                        break;
                    case 2:
                        column = "name";
                        break;
                    case 3:
                        column = "surname";
                        break;
                    case 4:
                        column = "role"; //selectrole fonksiyonu gelcek
                        break;
                    case 5:
                        column = "date1";  //degiscek
                        break;
                    case 6:
                        column = "date2"; //degiscek
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 6.");
                        return;
                }
                System.out.println("Enter the new value for " + column);
                String value = scan.nextLine();
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

    @Override
    public void displayProfile() {
       //dummy
    }

    public void returnToMenu(){
        System.out.println("Press 'M' to return main menu");
    
        Scanner scan = new Scanner(System.in);
        while (true) {
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("M"))
                managerMenu();
            else
                System.out.println("Invalid input. Please press 'M' to return to the menu.");
        }
    }

    /*ALGORITHMS gelecek



     
    
    */
}



