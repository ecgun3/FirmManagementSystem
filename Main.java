import java.util.Scanner;
public class Main {
    public static final String ANSI_PURPLE = "\u001B[35m";//ANSI codes for colors used for coloring the ASCII
    public static final String ANSI_CYAN = "\u001B[36m"; //Returns to the default color property
    public static final String clearr = "\033[H\033[2J";//clear the console
    public static void clearConsole(){      //Cleaning Console

		System.out.println("\033[H\033[2J");
	
	}

    public static void main(String[] args) {
        displayLoginScreen();// Display the login screen
        login();//Login the system
    }

    public static void displayLoginScreen() {
        System.out.println(ANSI_PURPLE);
        System.out.println("____    __    ____  _______  __        ______   ______   .___  ___.  _______    \n" + //
                        "\\   \\  /  \\  /   / |   ____||  |      /      | /  __  \\  |   \\/   | |   ____|   \n" + //
                        " \\   \\/    \\/   /  |  |__   |  |     |  ,----'|  |  |  | |  \\  /  | |  |__      \n" + //
                        "  \\            /   |   __|  |  |     |  |     |  |  |  | |  |\\/|  | |   __|     \n" + //
                        "   \\    /\\    /    |  |____ |  `----.|  `----.|  `--'  | |  |  |  | |  |____    \n" + //
                        "    \\__/  \\__/     |_______||_______| \\______| \\______/  |__|  |__| |_______|   \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                \n" + //
                        "                                                                                ");
        System.out.println("Access the System: Login Required");
        System.out.println(ANSI_CYAN);
    }

    public static void login (){
        Scanner input = new Scanner(System.in); 
        //An instance is created when calling a non-static class
        Authentication auth= new Authentication();
        boolean isLoggedin = false;
        String username;
        while(!isLoggedin){
            System.out.println("Enter username: ");
            username= input.nextLine();
            if(auth.authenticateUsername(username)){//if the username is valid
                boolean isValid= false;
                while(!isValid){
                    System.out.println("Enter password or choose other user by type escape character ");
                    String password= input.nextLine();
                    
                        if(password.equals("\u001B")){
                            break;
                        }
                        clearConsole();
                        isValid=auth.authenticatePassword(username,password);

                        if(isValid){
                            System.out.println("You are directed to the "+ auth.role(username)+ " menu");
                        }
                        isLoggedin=true;
                        } 
                
            }
            
        }
        input.close();
    }   
}

