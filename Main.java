import java.util.Scanner;
import java.lang.Thread;

/**
 * A Firm Management System built with Java and MySQL, supporting role-based access for Managers and Regular Employees. 
 * Managers can manage employees, update profiles, and hire/fire users, while employees can update their profiles. 
 * The app also includes sorting algorithm performance analysis.
 * 
 * Main class for handling the login system and navigating between different menus based on user role.
 * This class provides the console interface for the user to log in and interact with the system.
 * @author Group1
 * @version 8
 * @since 2024-12-04
 */
public class Main {
    /**
     * ANSI codes for colors used for coloring the ASCII
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    
    /**
     * Returns to the default color property
     */
    public static final String ANSI_CYAN = "\u001B[36m";
    
    /**
     * Green ANSI CODE
     */
    public static final String ANSI_GREEN = "\033[32m";
    
    /**
     * Scanner instance used for reading input from the user.
     */
    public static Scanner scanner = new Scanner(System.in,"UTF-8"); 

    /**
     * Clears the console screen.
     */
    public static void clearConsole(){

		System.out.println("\033[H\033[2J");
	
	}

    /**
     * The entry point of the application. 
     * This method displays the login screen and navigates the user through the login process.
     * 
     * @param args Command-line arguments (not used).
     * @see #displayLoginScreen() for the login screen display logic.
     * @see #makeChoice() for the user's menu choice.
     * @see #login() for the login process.
     * @see #clearConsole() for clearing console
     */
    public static void main(String[] args) {
        while(true){
            clearConsole();
            displayLoginScreen();// Display the login screen
            int choice= makeChoice();

            switch(choice){
                case 1:
                login();//Login the system
                break;

                case 2:
                System.exit(0);
                break;
                
                default:
                System.out.println("Invalid choice. Please choose");
            }
        }
    }

    /**
     * Displays the login screen with ASCII art and options for the user to login or terminate the system.
     * This method uses animation to display a welcoming message with colorful ASCII art.
     * 
     * @see #clearConsole() for clearing the screen during animation.
     */
    public static void displayLoginScreen(){
        System.out.println(ANSI_PURPLE);

        String message=("             U _____ u  _        ____   U  ___ u  __  __  U _____ u    \n" + //
                        " __        __\\| ___\"|/ |\"|    U /\"___|   \\/\"_ \\/U|' \\/ '|u\\| ___\"|/    \n" + //
                        " \\\"\\      /\"/ |  _|\" U | | u  \\| | u     | | | |\\| |\\/| |/ |  _|\"      \n" + //
                        " /\\ \\ /\\ / /\\ | |___  \\| |/__  | |/__.-,_| |_| | | |  | |  | |___      \n" + //
                        "U  \\ V  V /  U|_____|  |_____|  \\____|\\_)-\\___/  |_|  |_|  |_____|     \n" + //
                        ".-,_\\ /\\ /_,-.<<   >>  //  \\\\  _// \\\\      \\\\   <<,-,,-.   <<   >>     \n" + //
                        " \\_)-'  '-(_/(__) (__)(_\")(\"_)(__)(__)    (__)   (./  \\.) (__) (__)    ");

        String flower1=("         wWWWw               wWWWw\n" + //
                        "   vVVVv (___) wWWWw         (___)  vVVVv\n" + //
                        "   (___)  ~Y~  (___)  vVVVv   ~Y~   (___)\n" + //
                        "    ~Y~   \\|    ~Y~   (___)    |/    ~Y~\n" + //
                        "    \\|   \\ |/   \\| /  \\~Y~/   \\|    \\ |/\n" + //
                        "   \\\\|// \\\\|// \\\\|/// \\\\|//  \\\\|// \\\\\\|///\n" + //
                        "   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        
        String flower2=("  wWWWw               wWWWw\n" +
                        "   (___) wWWWw         (___)  vVVVv\n" +
                        "    ~Y~  (___)  vVVVv   ~Y~   (___)    vVVVv\n" +
                        "    \\|    ~Y~   (___)    |/    ~Y~    (___)\n" +
                        "    \\ |/   \\| /  \\~Y~/   \\|    \\ |/  \\\\~Y~/\n" +
                        "   \\\\|// \\\\|// \\\\|/// \\\\|//  \\\\|// \\\\\\|///\n" +
                        "   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        String[] lines=message.split("\n");//'/n' karakteri sınırlayıcı görev görüyor

        for(int i=0;i<40;i++){//sağa kayacağı loop sayısı
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearConsole();
            for (String line:lines){//her line için sağ karaktere geçmesi için loop
                System.out.println(" ".repeat(i)+line);
        }
        }

        System.out.println(ANSI_GREEN);
        for(int i=0;i<4;i++){
            clearConsole();
            System.out.println(flower1);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
            clearConsole();
            System.out.println(flower2);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Access the System: Login the system by press '1'");
        System.out.println("Terminate the system by press '2'");
        System.out.println(ANSI_CYAN);
        
    }

    /**
     * Handles the login process by prompting the user for a username and password.
     * After successful login, directs the user to either manager or regular employee menu.
     * 
     * @see #clearConsole() for clearing the console.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
    public static void login (){

        //An instance is created when calling a non-static class
        Authentication auth= new Authentication();
        boolean isLoggedin = false;
        String username;

        while(!isLoggedin){
            clearConsole();
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            if(auth.authenticateUsername(username)){//if the username is valid

                boolean isValid = false;

                while(!isValid){

                    System.out.print("Enter password or login to other user by type escape character: ");
                    String password= scanner.nextLine();
                    System.out.println();
                    Employee employee;
                    
                    if(password.equals("\u001B")){
                        break;
                    }

                    clearConsole();
                    isValid=auth.authenticatePassword(username,password);

                    if(isValid){
                        employee = auth.getEmployee();
                        String role = employee.getRole();

                        auth.disconnect();

                        if(role.equalsIgnoreCase("manager")){
                            System.out.println("You are directed to the Manager Menu");

                            try {//upper string will appear for two seconds after that the console will be cleared
                                Thread.sleep(800);
                                clearConsole();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            ((Manager) employee).managerMenu();
                        }
                        else{
                            System.out.println("You are directed to the Regular Menu");

                            try {//upper string will appear for two seconds after that the console will be cleared
                                Thread.sleep(1200);
                                clearConsole();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            ((RegularEmployee) employee).RegularMenu();
                        }
                    }
                    isLoggedin=true;

                } 
                
            }
            
        }
        // input.close();
    }

    /**
     * Makes the user choose a valid option for the login or termination process.
     * 
     * @return The user's choice (1 for login, 2 for termination).
     * @see #login() for what happens after this choice is made.
     */
    public static int makeChoice(){
        int choice=0;

        while(choice != 1 && choice != 2) {

            while(!scanner.hasNextInt()){
                System.out.printf("%nInvalid input. Please enter an integer (1, 2).%n Make a choice: ");
                scanner.next(); // skip the invalid input 
            }

            choice = getValidInt();

            if(choice != 1 && choice != 2)
                System.out.printf("%nInvalid input. Please enter an integer (1, 2).%n Make a choice: ");
        }

        return choice;
    }

    /**
     * Gets a valid integer input from the user.
     * 
     * @return The valid integer input from the user.
     * @see #makeChoice() for using this method.
     */
    public static int getValidInt() {

        int validInput = 0;

        while (true) {
            if (scanner.hasNextInt()) { 
                validInput = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }

        return validInput;
    }
}
