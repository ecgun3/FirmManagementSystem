public abstract class Employee {
     
     private int id;
     private String name;
     private String surname;
     private String email;
     private String phoneNumber;
     private String role;
 
     // Constructor
     public Employee(int id, String name, String surname, String email, String phoneNumber, String role) {
         this.id = id;
         this.name = name;
         this.surname = surname;
         this.email = email;
         this.phoneNumber = phoneNumber;
         this.role = role;
     }
 
     // Getters and Setters
     public int getId() { return id; }
     public void setId(int id) { this.id = id; }
 
     public String getName() { return name; }
     public void setName(String name) { this.name = name; }
 
     public String getSurname() { return surname; }
     public void setSurname(String surname) { this.surname = surname; }
 
     public String getEmail() { return email; }
     public void setEmail(String email) { this.email = email; }
 
     public String getPhoneNumber() { return phoneNumber; }
     public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
 
     public String getRole() { return role; }
     public void setRole(String role) { this.role = role; }
 
     // Abstract Methods
     public abstract void displayProfile();
     public abstract void updateProfile(String newEmail, String newPhoneNumber);
 }
 