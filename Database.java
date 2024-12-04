import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database operations for managing employees, such as connecting to the database,
 * executing queries, inserting, updating, and deleting employee records.
 */
public class Database{

    private final String url = "jdbc:mysql://localhost:3306/employee_database";
    private final String username = "root";
    private final String password = "12345678";

    private Connection connection;

    /**
     * Establishes a connection to the database.
     * 
     * @see #disconnectDatabase() for closing the connection.
     */
    public void connectDatabase(){

        try{
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    /**
     * Executes a query that returns a result set (e.g., SELECT).
     * 
     * @param query The SQL query to be executed.
     * @return A ResultSet containing the results of the query.
     * @throws SQLException If an SQL error occurs.
     */
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

    /**
     * Executes an update query (e.g., INSERT, UPDATE, DELETE).
     * 
     * @param query The SQL update query to be executed.
     * @return The number of rows affected by the query.
     * @throws SQLException If an SQL error occurs.
     */
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

    /**
     * Retrieves a array list of all employees from the database.
     * 
     * @return A list of Employee objects representing all employees in the database.
     * @see Employee for the Employee class details.
     */
    public ArrayList<Employee> getEmployees(){

        ArrayList<Employee> employees = new ArrayList<Employee>();
        String query = "SELECT * FROM employee";

        try{

            ResultSet rs = executeQuery(query);

            while(rs != null && rs.next()){

                Employee employee;
                String role = rs.getString("role");
                
                if(role.equalsIgnoreCase("manager"))
                    employee = new Manager();
                else
                    employee = new RegularEmployee();

                employee.setEmployeeID(rs.getInt("employee_ID"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));
                employee.setName(rs.getString("name"));
                employee.setSurname(rs.getString("surname"));
                employee.setPhoneNo(rs.getString("phone_no"));
                employee.setEmail(rs.getString("email"));
                employee.setDateOfBirth(rs.getDate("date_of_birth"));
                employee.setDateOfStart(rs.getDate("date_of_start"));
                employee.setRole(rs.getString("role"));
                employees.add(employee);
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return employees;
    }

    /**
     * Retrieves a array list of employees with a specific role from the database.
     * 
     * @param role The role of employees to retrieve (e.g., "manager").
     * @return A list of Employee objects with the specified role.
     * @see Employee for the Employee class details.
     */
    public ArrayList<Employee> getEmployeesRole(String role){

        ArrayList<Employee> employees = new ArrayList<Employee>();
        String query = "SELECT * FROM employee";

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
                    employee.setEmployeeID(rs.getInt("employee_ID"));
                    employee.setUsername(rs.getString("username"));
                    employee.setPassword(rs.getString("password"));
                    employee.setName(rs.getString("name"));
                    employee.setSurname(rs.getString("surname"));
                    employee.setPhoneNo(rs.getString("phone_no"));
                    employee.setEmail(rs.getString("email"));
                    employee.setDateOfBirth(rs.getDate("date_of_birth"));
                    employee.setDateOfStart(rs.getDate("date_of_start"));
                    employees.add(employee);
                }

            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return employees;
    }

    /**
     * Retrieves an employee by their username from the database.
     * 
     * @param username The username of the employee to retrieve.
     * @return An Employee object representing the employee, or null if not found.
     * @see Employee for the Employee class details.
     */
    public Employee getEmployeeUsername(String username){

        username = "'" + username + "'";
        String query = "SELECT * FROM employee WHERE username = " + username;
        Employee employee;

        try{

            ResultSet rs = executeQuery(query);

            if(rs.next()){

                if(rs.getString("role").equalsIgnoreCase("manager"))
                    employee = new Manager();
                else
                    employee = new RegularEmployee();                    
                    
                    employee.setRole(rs.getString("role"));
                    employee.setEmployeeID(rs.getInt("employee_ID"));
                    employee.setUsername(rs.getString("username"));
                    employee.setPassword(rs.getString("password"));
                    employee.setName(rs.getString("name"));
                    employee.setSurname(rs.getString("surname"));
                    employee.setPhoneNo(rs.getString("phone_no"));
                    employee.setEmail(rs.getString("email"));
                    employee.setDateOfBirth(rs.getDate("date_of_birth"));
                    employee.setDateOfStart(rs.getDate("date_of_start"));
                    
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

    /**
     * Inserts a new employee into the database.
     * 
     * @param employee The Employee object representing the new employee to insert.
     * @see Employee for the Employee class details.
     */
    public void insertEmployee(Employee employee){

        String query = "INSERT INTO employee (username, password, name, surname, phone_no, email, date_of_birth" + 
        ", date_of_start, role) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ?)";

        try(PreparedStatement pStatement = connection.prepareStatement(query)){
            
            pStatement.setString(1, employee.getUsername());
            pStatement.setString(2,employee.getPassword());
            pStatement.setString(3,employee.getName());
            pStatement.setString(4,employee.getSurname());
            pStatement.setString(5,employee.getPhoneNo());
            pStatement.setString(6,employee.getEmail());
            pStatement.setDate(7,employee.getDateOfBirth());
            pStatement.setDate(8,employee.getDateOfStart());
            pStatement.setString(9,employee.getRole());

            if (pStatement.executeUpdate() > 0)
                System.out.println("Employee inserted successfully!");
            else
                System.out.println("Insert failed!");
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Updates an employee's information in the database.
     * 
     * @param employee The Employee object representing the employee to update.
     * @param column The column to update (e.g., "username", "phone_no").
     * @param value The new value to set for the column.
     * @see Employee for the Employee class details.
     */
    public void updateEmployee(Employee employee, String column, String value){

        int ID = employee.getEmployeeID();
        String query = "UPDATE employee SET " + column + " = ? WHERE employee_ID = ? ";
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

    /**
     * Checks if the value for a specific column is unique in the employee table.
     * 
     * @param column The column to check (e.g., "username").
     * @param value The value to check for uniqueness.
     * @return True if the value is unique, false otherwise.
     */
    public boolean uniqueness(String column, String value){

        String query = "SELECT EXISTS (SELECT 1 FROM employee WHERE " + column +  " = ? )";
        try(PreparedStatement pStatement = connection.prepareStatement(query);){

            pStatement.setString(1, value);

            try(ResultSet rs = pStatement.executeQuery()){

            if (rs.next())
                return !rs.getBoolean(1);
            }

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an employee from the database.
     * 
     * @param employee The Employee object representing the employee to delete.
     * @see Employee for the Employee class details.
     */
    public void deleteEmployee(Employee employee){

        int ID = employee.getEmployeeID();
        String query = "DELETE FROM employee WHERE employee_ID = ?";
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

    /**
     * Closes the connection to the database.
     * 
     * @see #connectDatabase() for establishing the connection.
     */
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
// SELECT * FROM employee_database.employee
// DELETE FROM employee WHERE employee_ID>1
// INSERT INTO employee_database.employee (username,password,name,surname,phone_no,email,date_of_birth,date_of_start,role) VALUES ('Murat123','1234','Murat','Yılmaz','05543364895','mrt@gmail.com','2001.12.98','2002.06.23','manager')

// ALTER TABLE employee AUTO_INCREMENT = 1;
