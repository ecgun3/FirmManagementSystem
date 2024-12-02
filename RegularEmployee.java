import java.sql.Date;

public class RegularEmployee extends Employee {

    // Constructor
    public RegularEmployee(int employeeID, String username, String password, String role, 
                           String name, String surname, String phoneNo, 
                           Date dateOfBirth, Date dateOfStart, String email) {
        // Parent class constructor çağırıldı
        super(employeeID, username, password, role, name, surname, phoneNo, dateOfBirth, dateOfStart, email);
    }

    @Override
    public void displayProfile() {
        System.out.println("Profile Information:");
        System.out.println("Name: " + getName() + " " + getSurname());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNo());
    }

    @Override
    public void updateProfile(String newEmail, String newPhoneNumber) {
        setEmail(newEmail);
        setPhoneNo(newPhoneNumber);
        System.out.println("Profile updated successfully!");
    }
}
