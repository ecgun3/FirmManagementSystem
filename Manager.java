import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends Employee{
    
    //constructor
    public Manager(int id, String username, String password, String name, String surname, 
    String phoneNumber, String email, String dateOfBirth, String dateOfStart, String role) {
super(id, username, password, name, surname, phoneNumber, email, dateOfBirth, dateOfStart, role);
}


    public void displayMenu()
    {
        while (true){
        System.out.println("----Manager Menu----");
        System.out.println("1. Update Own Profile");
        System.out.println("2. Display All Employees");
        System.out.println("3. Display Employees with the Role");
        System.out.println("4. Display Employees with the Username");
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
                updateProfile(getEmail(), getPhoneNo());
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
                algorithms();
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
    public void updateProfile(String newEmail, String newPhoneNumber) {
        setEmail(newEmail);
        setPhoneNo(newPhoneNumber);
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
    private void displayAllEmployees(){

        Database database = new Database();
        database.connectDatabase();

        try{
            ArrayList<Employee> employees = database.getEmployees();
            displayEmployeeDetails(employees);  
        }
        
        catch(Exception e) {
                System.err.println("Something went Wrong.");
                e.printStackTrace();
                }
        database.disconnectDatabase();
    }  

    //role göre seçim yaparak display
    private void displayEmployeesRole(){
        Database database = new Database();
        database.connectDatabase();

        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Select a role to display employees:");
            System.out.println("1. Manager");
            System.out.println("2. Engineer");
            System.out.println("3. Intern");
            System.out.println("4. Technician");
            System.out.println("Choose a role from menu.");
            
            int choice = scan.nextInt(); 
            scan.nextLine();
            String role = "";
            
            switch (choice) {
                case 1:
                    role = "Manager";
                    break;
                case 2:
                    role = "Engineer";
                    break;
                case 3:
                    role = "Intern";
                    break;
                case 4:
                    role = "Technician";
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return;
            }

            ArrayList<Employee> employees = database.getEmployeesRole(role);
            displayEmployeeDetails(employees);
            }
        catch (Exception e) {
            System.err.println("An error occurred while displaying employees by role.");
            e.printStackTrace();    
        }
    database.disconnectDatabase();
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
    }

    //yeni employee girişi exception lazım
    private void hireEmployee(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter new employee information:");

        System.out.print("Employee ID: ");
        int employeeId = scan.nextInt();
        scan.nextLine();

        System.out.print("Username: ");
        String username = scan.nextLine();

        System.out.print("Name: ");
        String name = scan.nextLine();

        System.out.print("Surname: ");
        String surname = scan.nextLine();

        System.out.print("Phone Number: ");
        String phoneNo = scan.nextLine();

        System.out.print("Email: ");
        String email = scan.nextLine();
        
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String birthdate = scan.nextLine();

        System.out.print("Start Date (YYYY-MM-DD): ");
        String startdate = scan.nextLine();

        System.out.print("Role");
        String role = scan.nextLine();

        Employee hire;

        if (role.equalsIgnoreCase("Manager")) {
            hire = new Manager(employeeId, username, "defaultPassword", name, surname, phoneNo, email, birthdate, startdate, role);
        } else {
            hire = new RegularEmployee(employeeId, username, "defaultPassword", name, surname, phoneNo, email, birthdate, startdate, role);
        }

        Database database = new Database();
        database.connectDatabase();
        database.insertEmployee(hire);
        database.disconnectDatabase();
    }

    //employee silme
    private void fireEmployee(){
        Database database = new Database();
        database.connectDatabase();
        
        try{
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the id for the delete operation");
            int employeeID = scan.nextInt();
            scan.nextLine();
            
            int managerId =;
                //menajerin kendisini silememesi için
            if (employeeID == managerId) {
                System.out.println("You cannot delete your own account.");
                return;
            }

            Employee employeetodelete = database.getEmployees().stream().filter(e -> e.getId() == employeeID).findFirst().orElse(null); // DÜZELTMEK LAZIM

            if(employeetodelete != null){
                database.deleteEmployee(employeetodelete);
                System.out.println("Employee with ID: " + employeeID +" Deleted.");
            }else {
            System.out.println("employee not found:" + employeeID);
            }
        }catch(Exception e) {
        System.err.println("An error occurred while deleting employee");
        e.printStackTrace();    
        }
    database.disconnectDatabase();
    }

    //employee güncelleme
    private void updateEmployee(){
        Database database = new Database();
        database.connectDatabase();
        
        try{
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the id for the update operation");
            int employeeID = scan.nextInt();
            scan.nextLine();

            Employee employeetoupdate = database.getEmployees().stream().filter(e -> e.getEmployeeID() == employeeID).findFirst().orElse(null); //süpheli

            if(employeetoupdate != null){
                System.out.println("Enter the column to update(id,username,name,surname,role,birthdate,startdate)");
                String column = scan.nextLine();

                System.out.println("Enter the new value for " + column);
                String value = scan.nextLine();

                database.updateEmployee(employeetoupdate, column, value);
            }else {
                System.out.println("employee not found:" + employeeID);
            }
        }catch(Exception e) {
        System.err.println("An error occurred while updating employee");
        e.printStackTrace();    
        }
    database.disconnectDatabase();
    }

    @Override
    public void displayProfile() {
       //dummy
    }

    /*ALGORITHMS sekmesi gelecek



     
    
    */
}



