public class Authentication{
    /*Başta databaseden veri çektim ama devamında employee sınıfındaki veriler gerekeceğinden employee üzerinden devam */
    private Database database;
    private Employee employee;

    public Database getdatabase() {
        return database;
    }

    public void setdatabase(Database database) {
        this.database = database;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Authentication(){//constructor
        this.database = new Database(); //creating new object
        this.database.connectDatabase();//connect to the database
        this.employee = null;
    }

    public boolean authenticateUsername(String username){

        if (database.getEmployeeUsername(username)!=null){//username typed by user vs employee object username
            return true;
        }
        else{
           System.out.println("Invalid username, Try again");
            return false; 
        }
    }

    public boolean authenticatePassword(String username, String password){

        employee=database.getEmployeeUsername(username);//information got from database class goes to employee class object employee
        if ((employee.getPassword()).equals(password)){//
            System.out.println("Welcome " + username);
            if(employee.getPassword().equals("password123")){
                System.out.println("You must update your password before proceeding");
                employee.setPasswordFirstTime();
            }
            return true;
        }
        else{
            System.out.println("Invalid password");
            return false;
        }
    }

    public void disconnect(){
        database.disconnectDatabase();
    }

}