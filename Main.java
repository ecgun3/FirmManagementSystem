import java.util.Scanner;
import java.lang.Thread;
public class Main {

    public static final String ANSI_PURPLE = "\u001B[35m";//ANSI codes for colors used for coloring the ASCII
    public static final String ANSI_CYAN = "\u001B[36m"; //Returns to the default color property
    public static Scanner input = new Scanner(System.in); 


    public static void clearConsole(){      //Cleaning Console

		System.out.println("\033[H\033[2J");
	
	}

    public static void main(String[] args) {
        while(true){
            clearConsole();
            displayWelcome();
            displayLoginScreen();// Display the login screen
            login();//Login the system
        }
    }
    public static void displayWelcome(){
        System.out.println(ANSI_PURPLE);
        String message=("____    __    ____  _______  __        ______   ______   .___  ___.  _______    \n" + //
                        "\\   \\  /  \\  /   / |   ____||  |      /      | /  __  \\  |   \\/   | |   ____|   \n" + //
                        " \\   \\/    \\/   /  |  |__   |  |     |  ,----'|  |  |  | |  \\  /  | |  |__      \n" + //
                        "  \\            /   |   __|  |  |     |  |     |  |  |  | |  |\\/|  | |   __|     \n" + //
                        "   \\    /\\    /    |  |____ |  `----.|  `----.|  `--'  | |  |  |  | |  |____    \n" + //
                        "    \\__/  \\__/     |_______||_______| \\______| \\______/  |__|  |__| |_______|   \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                ");
        String[] lines=message.split("\n");//'/n' karakteri sınırlayıcı görev görüyor

        for (String line:lines){//her line için sağ karaktere geçmesi için loop
            System.out.println(" "+line);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void displayLoginScreen() {

        System.out.println("Access the System: Login Required");
        System.out.println(ANSI_CYAN);
        
    }

    public static void login (){

        //An instance is created when calling a non-static class
        Authentication auth= new Authentication();
        boolean isLoggedin = false;
        String username;

        while(!isLoggedin){

            System.out.print("Enter username: ");
            username = input.nextLine();
            if(auth.authenticateUsername(username)){//if the username is valid

                boolean isValid = false;

                while(!isValid){

                    System.out.print("Enter password or login to other user by type escape character: ");
                    String password= input.nextLine();
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
                        System.out.println("You are directed to the " + role + " menu");

                        try {//upper string will appear for two seconds after that the console will be cleared
                            Thread.sleep(2000);
                            clearConsole();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        auth.disconnect();

                        if(role.equalsIgnoreCase("manager"))
                            ((Manager) employee).managerMenu();
                        else
                            ((RegularEmployee) employee).RegularMenu();
                    }
                    isLoggedin=true;

                } 
                
            }
            
        }
        // input.close();
    }   
}

