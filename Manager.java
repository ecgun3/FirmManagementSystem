import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Manager extends Employee{

    private Database database = new Database();

    private static Scanner scan = new Scanner(System.in,"UTF-8");

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

            System.out.print("\u001B[35m");
            System.out.println(title+"\n");
            System.out.print("\u001B[36m");

            System.out.println("----Manager Menu----");
            System.out.println("1. See Profile Informations");
            System.out.println("2. Display All Employees");
            System.out.println("3. Display Employees with the Role");
            System.out.println("4. Display Employee with the Username");
            System.out.println("5. Hire Employee");
            System.out.println("6. Fire Employee");
            System.out.println("7. Algorithms");
            System.out.println("8. Logout");
            


            int choice = getValidInt();
            Main.clearConsole();

            switch(choice) {
                case 1:
                    if(displayProfile()==1)
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
                    hireEmployee();
                    break;
                case 6:
                    fireEmployee();
                    break;
                case 7:
                    algorithms();
                    break;
                case 8:
                    System.out.println("Logging Out...");
                    Main.clearConsole();
                    return;
                default:
                    System.out.println("Invalid Choice. Please Try Again.");
                    continue;
            }

        }

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
        if(askForEmployeeInfoChange()==true)
            updateEmployee();

        database.disconnectDatabase();
        return employees;
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
            Main.clearConsole();
            ArrayList<Employee> employees = database.getEmployeesRole(role);
            displayEmployeeDetails(employees);
            if(askForEmployeeInfoChange()==true)
                updateEmployee();
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

            System.out.println("Enter the username for the search. If you wanna go back --> press x");
            String username = validString();

            if(username.equalsIgnoreCase("x")){
                Main.clearConsole();
                return;}

                Employee employee = database.getEmployeeUsername(username);
                ArrayList<Employee> employees = new ArrayList<>();

                if(employee != null){
                    employees.add(employee);
                    displayEmployeeDetails(employees);
                } else {
                    System.out.println("No employee found with " + username);
                }
            
        }catch (Exception e) {
            System.err.println("An error occurred while displaying employees by username.");
            e.printStackTrace();
        }
        returnToMenu();
        database.disconnectDatabase();
    }

    //yeni employee girişi exception lazım
    private void hireEmployee(){

        System.out.println("Enter new employee information: ");

        System.out.print("username: ");
        String username = checkPhoneAndUsername("username");
        if(username.equalsIgnoreCase("x")){
            Main.clearConsole();
            return;}

        String defaultPassword = "password123";

        System.out.print("Name: ");
        String name = nameSurnameCheck();
        if(name.equalsIgnoreCase("x")){
            Main.clearConsole();
            return;}

        System.out.print("Surname: ");
        String surname = nameSurnameCheck();
        if(name.equalsIgnoreCase("x")){
            Main.clearConsole();
            return;}

        String phoneNo = "(" + countryCode() + ") ";
        System.out.printf("Phone Number: %s",phoneNo);
        String temp = checkPhoneAndUsername("phone_no");
        if(temp.equalsIgnoreCase("x")){
            Main.clearConsole();
            return;}
        phoneNo = phoneNo + temp.substring(0,3) + " " + temp.substring(3, 6) +
                " " + temp.substring(6, 8) + " " + temp.substring(8, 10);

        //Valid email: ece.gunaydin@example.com
        //Invalid email: ece.gunaydin@com
        System.out.print("Email: ");
        while(true){
            String email = validString();
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
        System.out.println("Role: ");
        String role = null;
        while(role == null){
            role = selectRole();
            if (role == null)
                System.out.println("Invalid choice. Please try again.");
        }

        Employee hire;

        if (role.equalsIgnoreCase("Manager")) {
            hire = new Manager(0, username, defaultPassword, role, name, surname, phoneNo, date1, date2, email);
        }
        else {
            hire = new RegularEmployee(0, username, defaultPassword, role, name, surname, phoneNo, date1, date2, email);
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
            username = validString();
            //menajerin kendisini silememesi için
            boolean flag2=true;
            if (this.username.equals(username)) {
                System.out.println("You cannot delete your own account.");
                while(flag2){
                    System.out.println("If you want to delete another employee press 1, or back to main menu press 2");
                    int choice = getValidInt();
                    switch(choice){
                        case 1:
                            flag2=false;
                            continue;
                        case 2:
                            flag=false;
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
            System.out.println("employee not found: " + username);
        }

        database.disconnectDatabase();
        returnToMenu();
    }

    //employee güncelleme
    private void updateEmployee(){

        database.connectDatabase();

        boolean flag = true;
        while(flag) {


        try{

            System.out.println("Enter the username for the update operation: ");
            System.out.println("Username: ");
            String username = validString();

            Employee employeetoupdate = database.getEmployeeUsername(username);

            if(employeetoupdate != null){
                System.out.println("Enter the number of column to update: ");
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
                        value = checkPhoneAndUsername("username");
                        if(username.equals(this.username))
                            this.username=value;
                        break;
                    case 2:
                        column = "name";
                        System.out.printf("%nEnter the new value for " + column);
                        value = nameSurnameCheck();
                        if(username.equals(this.username))
                            this.name=value;
                        break;
                    case 3:
                        column = "surname";
                        System.out.printf("%nEnter the new value for " + column);
                        value = nameSurnameCheck();
                        if(username.equals(this.username))
                            this.surname=value;
                        break;
                    case 4:
                        column = "role";
                        System.out.printf("%nEnter the new value for " + column);
                        value = selectRole();
                        if(username.equals(this.username))
                            this.role=value;
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

            if(askForEmployeeInfoChange()==false)
                flag = false;
        }
        database.disconnectDatabase();
        returnToMenu();
    }

    private boolean askForEmployeeInfoChange() {

        System.out.println("\nDo you want to change an employee information?");
        System.out.println("1. YES");
        System.out.println("2. NO (Return to main menu)");

        while (true) {
            int userInput = getValidInt();

            if (userInput == 1) {
                Main.clearConsole();
                return true;
            } else if (userInput == 2) {
                Main.clearConsole();
                return false;
            } else {
                System.out.println("Please make a valid choice (1 for YES or 2 for NO).");
            }
        }
    }

    private void returnToMenu(){
        System.out.println("Press 'M' to return main menu");

        while (true) {
            String input = scan.nextLine();

            if (input.equalsIgnoreCase("M")) {
                Main.clearConsole();
                break;
            } else {
                System.out.println("Invalid input. Please press 'M' to return to the menu.");
                Main.clearConsole();
            }
        }
    }

    private String selectRole() {

        //display rolemenu
        System.out.println("1. Manager");
        System.out.println("2. Engineer");
        System.out.println("3. Intern");
        System.out.println("4. Technician");
        System.out.print("\nChoose a role from menu: ");

        int choice = getValidInt();
        Main.clearConsole();

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

    private String Date(){

        String date = "";
        String properDate = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(properDate);
        sdf.setLenient(false);

        boolean flag=true;
        while(flag){

            System.out.println("Please enter an appropriate date(YYYY-MM-DD): ");
            date = validString();
            try{

                sdf.parse(date);


            }
            catch(ParseException exception){
                continue;
            }
            catch(NumberFormatException e){
                System.out.println("Invalid format. Please try again.");
            }
            String yearString = date.substring(0, 4);
            int year = Integer.parseInt(yearString);
            if(year<1900 || year>2016)
                System.out.println("Year must be greater than 1900 or lower than 2016. Please try again.");
            else
                flag = false;

        }
        return date;
    }

    public String nameSurnameCheck() {
        String input;

        while (true)
        {
            input = validString();

            // Check for length and whether all characters are alphabetical
            if (isAlpha(input))
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
            if (!Character.isLetter(c) && (c != ' ') ) // Turkish character check
            {
                return false;
            }
        }
        return true;
    }


    ///////////////////////Algorithms eklendi///////////////////////

    private static void radixSort(int[] arr) {
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

    private static void shellSort(int[] arr)
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

    private static void heapSort(int[] arr)
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

    private static void insertionSort(int[] arr)
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

    private static int[] randomArray(int size)
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
    private static long executionTime(Runnable algorithmType) //Runnable is a interface it represents code blocks that can be runned
    {
        long timeStart = System.nanoTime(); //starting time
        algorithmType.run(); //Sorting algorithm is executed
        long timeEnd = System.nanoTime(); //ending time

        return timeEnd - timeStart; //calculating the time of the execution
    }

    private static boolean sortCheck(int[] sortedArr, int[] originalArr)
    {
        int[] javaSortArr = originalArr.clone();

        Arrays.sort(javaSortArr);

        return Arrays.equals(sortedArr, javaSortArr);
    }

    private void algorithms()
    {
        int size = 0;

        boolean flg = false;
        while(!flg)
        {
            System.out.printf("Enter size between 1,000 to 10,000: ");

            if(scan.hasNextInt())
            {
                size = scan.nextInt();

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
                scan.next(); //Skip invalid input
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
        else
            System.out.println("Shell wrong");

        if(sortCheck(heapArray, array))
            System.out.println("Heap true");
        else
            System.out.println("Heap wrong");
            
        if(sortCheck(insertionArray, array))
            System.out.println("Insertion true");
        else
            System.out.println("Insertion wrong");
        
        System.out.println();
            
        long[] times = new long[4];
        String[] algorithms = {"Radix Sort", "Shell Sort", "Heap Sort", "Insertion Sort"};

        times[0] = radixTime;
        times[1] = shellTime;
        times[2] = heapTime;
        times[3] = insertionTime;

        long[] sortedTimes = times.clone();
        Arrays.sort(sortedTimes);

        System.out.println("Execution Times:");
        for(long time : sortedTimes)
        {
            for(int i = 0; i < times.length; i++)
            {
                if(time == times[i])
                {
                    System.out.println(algorithms[i] + ": " + time + " ns");
                    break;
                }
            }

        }
    }
    
}