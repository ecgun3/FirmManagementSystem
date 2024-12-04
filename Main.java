import java.util.Scanner;
import java.lang.Thread;

public class Main {

    public static final String ANSI_PURPLE = "\u001B[35m";//ANSI codes for colors used for coloring the ASCII
    public static final String ANSI_CYAN = "\u001B[36m"; //Returns to the default color property
    public static final String ANSI_GREEN = "\033[32m"; //Green ANSI CODE
    public static Scanner scanner = new Scanner(System.in,"UTF-8"); 


    public static void clearConsole(){      //Cleaning Console

		System.out.println("\033[H\033[2J");
	
	}

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

