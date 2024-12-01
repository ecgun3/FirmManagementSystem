public class RegularEmployee extends Employee {

     // Constructor
     public RegularEmployee(int id, String name, String surname, String email, String phoneNumber, String role) {
         //reaching the parent clsas
          super(id, name, surname, email, phoneNumber, role);
     }
 
     @Override
     public void displayProfile() {
         System.out.println("Profile Information:");
         System.out.println("Name: " + getName() + " " + getSurname());
         System.out.println("Email: " + getEmail());
         System.out.println("Phone Number: " + getPhoneNumber());
     }
 
     @Override
     public void updateProfile(String newEmail, String newPhoneNumber) {
         setEmail(newEmail);
         setPhoneNumber(newPhoneNumber);
         System.out.println("Profile updated successfully!");
     }
 }
 