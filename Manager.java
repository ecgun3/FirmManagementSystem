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
            /* max 45 karakter kontrolü */
            while (true) 
            {
                System.out.print("Bir metin girin (maksimum 45 karakter): ");
                username = scan.nextLine();

                if (username.length() <= 45) 
                {
                    break; // Exit the loop if the user entered valid input
                } 
                else 
                {
                    System.out.println("Error: The text you entered is more than 45 characters. Please enter another username.");
                }
            }
            /* max 45 karakter kontrolü */

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
        String name = scan.nextLine();//////////////////////////////////////////45 karakter

        System.out.print("Surname: ");
        String surname = scan.nextLine();//////////////////////////////////////////45 karakter

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
            String username = scan.nextLine();//////////////////////////////////////////45 karakter usernameCheck çağır

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
                        value = scan.nextLine();//////////////////////// 45 karakter, sadece alfabetik, ilk harf büyük
                        break;
                    case 3:
                        column = "surname";
                        System.out.printf("%nEnter the new value for " + column);
                        value = scan.nextLine();//////////////////////// 45 karakter, sadece alfabetik, ilk harf büyük
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

    // Checks if characters are alphabetic, length of the input and converts the input as required format 
    public static String nameSurnameCheck(String nameOrSurname) {
        Scanner scan = new Scanner(System.in);
        String input;
    
        while (true) 
        {
            System.out.print("Enter " + nameOrSurname + " (Alphabetical, max 45 characters): ");
            input = scan.nextLine().trim();
    
            // Check for length and whether all characters are alphabetical
            if (input.length() <= 45 && isAlpha(input)) 
            {
                // Convert all characters to lowercase
                char[] chars = input.toCharArray();
                for (int i = 0; i < chars.length; i++) 
                {
                    chars[i] = Character.toLowerCase(chars[i]);
                }
                // Capitalize the first character
                chars[0] = Character.toUpperCase(chars[0]);
                input = new String(chars);
    
                break;
            } 
            else 
            {
                System.out.println("Invalid input. Please enter only alphabetical characters (max 45 characters).");
            }
        }
    
        return input;
    }
    
    // Method to check if a string contains only alphabetic characters
    public static boolean isAlpha(String str) 
    {
        for (char c : str.toCharArray()) 
        {
            if (!Character.isLetter(c)) // Turkish character check
            {
                return false;
            }
        }
        return true;
    }

    public static String ValidCheck() // Checks if input has more than 45 character
    {
        Scanner scan = new Scanner(System.in);
        String username;

        while (true) 
        {
            System.out.print("Enter a username (maximum 45 characters): ");
            username = scan.nextLine();

            if (username.length() <= 45) 
            {
                break; // Exit the loop if the user entered valid input
            } 
            else 
            {
                System.out.println("Error: The text you entered is more than 45 characters. Please enter another username.");
            }
        }

        return username;
    }


    ///////////////////////Algorithms eklendi///////////////////////
    
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length == 0) 
        {
            return; // If size is 0 don't do anything
        }
    
        // Put negative and pozitive values into different arrays
        int negativeCount = 0;
        for (int value : arr) 
        {
            if (value < 0) 
            {
                negativeCount++;
            }
        }
    
        int[] negatives = new int[negativeCount]; // Negative values as an array
        int[] positives = new int[arr.length - negativeCount]; // Positive values as an array
    
        int negIndex = 0, posIndex = 0;
        for (int value : arr) 
        {
            if (value < 0) 
            {
                negatives[negIndex++] = -value; // Turn negative numbers to positive
            } 
            
            else 
            {
                positives[posIndex++] = value; // If positive put it into positive array
            }
        }
    
        // Sort negative and positive arrays
        if (negatives.length > 0) 
        {
            radixSortPositive(negatives);
        }

        if (positives.length > 0) 
        {
            radixSortPositive(positives);
        }
    
        // Reverse the negative array convert the values to negative and place them in the array
        for (int i = 0; i < negatives.length; i++) 
        {
            arr[i] = -negatives[negatives.length - 1 - i];
        }
    
        // Add  the positive array
        for (int i = 0; i < positives.length; i++) 
        {
            arr[negatives.length + i] = positives[i];
        }
    }
    
    private static void radixSortPositive(int[] arr) 
    {
        int max = maxValue(arr);
    
        int exp = 1;
        while (max / exp > 0) 
        {
            sortCount(arr, exp);
            exp *= 10;
        }
    }
    
    private static int maxValue(int[] arr) 
    {
        int max = arr[0];
        for (int value : arr) 
        {
            if (value > max) 
            {
                max = value;
            }
        }
        return max;
    }
    
    private static void sortCount(int[] arr, int exp) 
    {
        int n = arr.length;
        int[] sorted = new int[n];
        int[] count = new int[10]; // Counts numbers from 0 to 9
    
        // Count the digits
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
    
        // Kümülatif toplama
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
    
        // Sort numbers
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            sorted[count[digit] - 1] = arr[i];
            count[digit]--;
        }
    
        // Copy sorted array to original array
        System.arraycopy(sorted, 0, arr, 0, n);
    }
        
    public static void shellSort(int[] arr)
    {
        int n = arr.length;

        for(int gap = n / 2; gap > 0; gap /= 2)
        {
            for(int i = gap; i < n; i++)
            {
                int key = arr[i];
                int j = i;

                while(j >= gap && arr[j - gap] > key)
                {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = key;
            }
        }
    }

    public static void heapSort(int[] arr)
    {
        int n = arr.length;

        //turns array into Max-Heap
        for(int i = n / 2 - 1; i >= 0; i--)
        {
            heapify(arr, n, i);
        }

        for(int i = n - 1; i >= 0; i--)
        {
            //move largest element to end of the array
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0); //heapify remaining part
        }

    }

    private static void heapify(int[] arr, int n, int i)
    {
        int large = i; //parent
        int left = 2 * i + 1; //left child
        int right = 2 * i + 2; //right child

        //if left child is bigger than parent
        if(left < n && arr[left] > arr[large])
        {
            large = left;
        }

        //if right child is bigger than parent
        if(right < n && arr[right] > arr[large])
        {
            large = right;
        }

        //if largest isn't parent
        if(large != i)
        {
            int temp = arr[i];
            arr[i] = arr[large];
            arr[large] = temp;

            heapify(arr, n, large);
        }
    }

    public static void insertionSort(int[] arr)
    {
        int n = arr.length;
        for(int i = 1; i < n; i++)
        {
            int current = arr[i];
            int j = i - 1;

            while(j >= 0 && arr[j] > current)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current;
        }
    }

    public static int[] randomArray(int size)
    {
        Random rNum = new Random();
        int arr[] = new int[size];
        for(int i = 0; i < size; i++)
        {
            arr[i] = rNum.nextInt(20001) - 10000; // -10000 <= arr[i] < 10001 (-10000+20001)
        }
        return arr;
    }

    //Without usage of Runnable interface executionTime of each sort type should be a different method
    public static long executionTime(Runnable algorithmType) //Runnable is a interface it represents code blocks that can be runned
    {
        long timeStart = System.nanoTime(); //starting time
        algorithmType.run(); //Sorting algorithm is executed
        long timeEnd = System.nanoTime(); //ending time

        return timeEnd - timeStart; //calculating the time of the execution
    }

    public static boolean sortCheck(int[] sortedArr, int[] originalArr)
    {
        int[] javaSortArr = originalArr.clone();

        Arrays.sort(javaSortArr);

        return Arrays.equals(sortedArr, javaSortArr);
    }

    public static void algorithms()
    {
        int size = 0;
        Scanner input = new Scanner(System.in);

        boolean flg = false;
        while(!flg)
        {   
            System.out.printf("Enter size between 1,000 to 10,000: ");

            if(input.hasNextInt())
            {
                size = input.nextInt();
                
                if (size >= 1000 && size <= 10000) 
                {
                    if(size == 0)
				    {
					    System.out.println("Size is zero.");
				    }

                    flg = true; 
                }

                else 
                {
                    System.out.println("Size should be in the [1,000 - 10,000] range.");
			    }
            }

            else
		    {
			    System.out.println("Invalid input. Please enter an integer.");
			    input.next(); //Skip invalid input
		    }       
        }
    
        int[] array = randomArray(size);
    
        int[] radixArray = array.clone();
        int[] shellArray = array.clone();
        int[] heapArray = array.clone();
        int[] insertionArray = array.clone();

        long radixTime = executionTime(() -> radixSort(radixArray));
        long shellTime = executionTime(() -> shellSort(shellArray));
        long heapTime = executionTime(() -> heapSort(heapArray));
        long insertionTime = executionTime(() -> insertionSort(insertionArray));

        System.out.println();

        if(sortCheck(radixArray, array) && sortCheck(shellArray, array) && sortCheck(heapArray, array) && sortCheck(insertionArray, array))
        {
            System.out.println("Algorithms produced correct results.");
        }

        else
        {
            System.out.println("Some algorithms produced incorrect results.");
        }

        System.out.println();

        if(sortCheck(radixArray, array))
            System.out.println("Radix true");
        else
        System.out.println("Radix wrong");
        
        if(sortCheck(shellArray, array))
            System.out.println("Shell true");

        if(sortCheck(heapArray, array))
            System.out.println("Heap true");
            
        if(sortCheck(insertionArray, array))
            System.out.println("Insertion true");
        
        System.out.println();
            
        System.out.println("Execution Times:");
        System.out.println("Radix Sort: " + radixTime + " ns");
        System.out.println("Shell Sort: " + shellTime + " ns");
        System.out.println("Heap Sort: " + heapTime + " ns");
        System.out.println("Insertion Sort: " + insertionTime + " ns");

        //return menu
    }
}