import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database{

    private final String url = "jdbc:mysql://localhost:3306/employee_database";
    private final String username = "root";
    private final String password = "1234";

    private Connection connection;gg

    public void connectDatabase(){

        try{
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query){

        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(String query){

        try{
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            return -1;
        }
    }

    public ArrayList<Employee> getEmployees(){

        ArrayList<Employee> employees = new ArrayList<Employee>();
        String query = "SELECT * FROM employees";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Employee employee;
                String role = rs.getString("role");
                
                if(role.equalsIgnoreCase("manager"))
                    employee = new Manager();
                else
                    employee = new RegularEmployee();

                employee.setID(rs.getInt("employee_ID"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setPhone(rs.getString("phone_no"));
                employee.setEmail(rs.getString("email"));
                employee.setBirth(rs.getDate("date_of_birth"));
                employee.setStart(rs.getDate("date_of_start"));
                employee.setRole(rs.getString("role"));
                employees.add(employee);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return employees;
    }

    public ArrayList<Employee> getEmployeesRole(String role){

        ArrayList<Employee> employees = new ArrayList<Employee>();
        String query = "SELECT * FROM employees";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Employee employee;
                if(role.equalsIgnoreCase("manager"))
                    employee = new Manager();
                else
                    employee = new RegularEmployee();

                if(role.equalsIgnoreCase(rs.getString("role"))){
                    employee.setRole(rs.getString("role"));
                    employee.setID(rs.getInt("employee_ID"));
                    employee.setUsername(rs.getString("username"));
                    employee.setPassword(rs.getString("password"));
                    employee.setName(rs.getString("name"));
                    employee.setSurname(rs.getString("surname"));
                    employee.setPhone(rs.getString("phone_no"));
                    employee.setEmail(rs.getString("email"));
                    employee.setBirth(rs.getDate("date_of_birth"));
                    employee.setStart(rs.getDate("date_of_start"));
                    employees.add(employee);
                }

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeUsername(String username){

        String query = "SELECT * FROM employees WHERE username = ?";

        try(PreparedStatement pStatement = connection.prepareStatement(query)){

            pStatement.setString(1, username);
            ResultSet rs = pStatement.executeQuery(query);

            if(rs.next()){

                Employee employee;
                if(rs.getString("role").equalsIgnoreCase("manager"))
                    employee = new Manager();
                else
                    employee = new RegularEmployee();                    
                    
                    employee.setRole(rs.getString("role"));
                    employee.setID(rs.getInt("employee_ID"));
                    employee.setUsername(rs.getString("username"));
                    employee.setPassword(rs.getString("password"));
                    employee.setName(rs.getString("name"));
                    employee.setSurname(rs.getString("surname"));
                    employee.setPhone(rs.getString("phone_no"));
                    employee.setEmail(rs.getString("email"));
                    employee.setBirth(rs.getDate("date_of_birth"));
                    employee.setStart(rs.getDate("date_of_start"));

                return employee;
            }
            else 
                return null;

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }

    }

    public void insertEmployee(Employee employee){

        String query = "INSERT INTO employee_database.employee (username, password, name, surname, phone_no, email, date_of_birth" + 
        ", date_of_start, role) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ?)";

        // String query = "INSERT INTO employee_database.employee (employee_ID, username, password, name, surname, phone_no, email, date_of_birth" + 
        //     ", date_of_start, role)VALUES ('" +  employee.getID() + "', '" + employee.getUsername() + "', '" + 
        //     employee.getPassword() + "', '" + employee.getName() + "', '" + employee.getSurname() + "', '" + employee.getPhone() + "', '" +
        //     employee.getEmail() + "', '" + employee.getBirth() + "', '" + employee.getStart() + "', '" + employee.getRole() + "') ";

        try(PreparedStatement pStatement = connection.prepareStatement(query)){
            
            pStatement.setString(1, employee.getUsername());
            pStatement.setString(2,employee.getPassword());
            pStatement.setString(3,employee.getName());
            pStatement.setString(4,employee.getSurname());
            pStatement.setString(5,employee.getPhone());
            pStatement.setString(6,employee.getEmail());
            pStatement.setDate(7,employee.getBirth());
            pStatement.setDate(8,employee.getStart());
            pStatement.setString(9,employee.getRole());

            if (pStatement.executeUpdate(query) > 0)
                System.out.println("Employee inserted successfully!");
            else
                System.out.println("Insert failed!");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee, String column, String value){

        int ID = employee.getID();
        String query = "UPDATE employee_database.employee SET " + column + " = ? WHERE employee_ID = ? ";
        try(PreparedStatement pStatement = connection.prepareStatement(query);){

            pStatement.setString(1, value);
            pStatement.setInt(2, ID);

            if (pStatement.executeUpdate() > 0)
                System.out.println("Employee updated successfully!");
            else
                System.out.println("Update failed.");

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void deleteEmployee(Employee employee){

        int ID = employee.getID();
        String query = "DELETE FROM employee_database.employee WHERE employee_ID = ?";
        try(PreparedStatement pStatement = connection.prepareStatement(query)){
            pStatement.setInt(1, ID);
            if (pStatement.executeUpdate() > 0)
                System.out.println("Employee deleted successfully!");
            else
                System.out.println("Delete failed!");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public void disconnectDatabase(){

        try{
            if(connection!=null && !connection.isClosed())
                connection.close();
            else
                System.out.println("Disconnection error!");
        }
        catch(SQLException sqlException){
        }
    }

    
}