public class Authentication{
    /*Başta databaseden veri çektim ama devamında employee sınıfındaki veriler gerekeceğinden employee üzerinden devam */
    public Database employeeInfo;

    public Authentication(){//constructor
        this.employeeInfo= new Database(); //creating new object
        this.employeeInfo.connectDatabase();//connect to the database
    }

    public boolean authenticateUsername(String username){
        Employee employee=employeeInfo.getEmployeeUsername(username);//information got from database class goes to employee class object employee
        if (username.equals(employee.getUsername())){//username typed by user vs employee object username
            return true;
        }
        else{
           System.out.println("Invalid username, Try again");
            return false; 
        }
    }
    public boolean authenticatePassword(String username, String password){
        Employee employee=employeeInfo.getEmployeeUsername(username);
        if ((employee.getPassword()).equals(password)){//
            System.out.println("Welcome "+username);
            return true;
        }
        else{
            System.out.println("Invalid password");
            return false;
        }
    }

    public String role(String username){
        Employee employee=employeeInfo.getEmployeeUsername(username);
        System.out.println("You have "+employee.getRole()+" access");
        return employee.getRole();
    }
}