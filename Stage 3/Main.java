
/**
 *
 * @author Isiah 
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Gym gym = new Gym("Forge Gym");
        
        System.out.println("Welcome to " + gym.getGymName());
        boolean running = true;
        while(running)
        {
            System.out.println("\n==== Login Menu ====");
            System.out.println("1. Member Login");
            System.out.println("2. Staff Login");
            System.out.println("3. Quit");
            System.out.println("Choice: ");
            int choice = input.nextInt();
            
            switch (choice)
            {
                case 1:
                {
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    Member member = gym.loginMember(user, pass);
                    if(member != null)
                    {
                        memberMenu(gym, member);
                    }
                    else
                    {
                        System.out.println("Invalid Member Credentials");
                    }
                    break;
                }
                case 2:
                {
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    Staff staff = gym.loginStaff(user, pass);
                    if(staff != null)
                    {
                        //staffMenu(gym, staff);
                    }
                    else
                    {
                        System.out.println("Invalid Member Credentials");
                    }
                    break;
                }
                case 3:
                {
                    
                    break;
                }
                default:
                {
                   
                    break; 
                }
            }
        }
    }
        
    public static void memberMenu(Gym gym, Member member)
    {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while(!exit)
        {
            System.out.println("\n==== Member Menu ( " + member.getName() + ") ====");
            System.out.println("1. View Membership");
            System.out.println("2. Purchase Membership");
            System.out.println("3. Browse Areas");
            System.out.println("4. View My Info");
            System.out.println("5. Logout"); //Goes back to Login Menu
            int choice = input.nextInt();
            
            switch(choice)
            {
                case 1: 
                {
                    gym.listMembership();
                    break;
                }
                case 2:
                {
                    gym.listMembership();
                    System.out.print("Enter Membership Type to Buy: ");
                    String type = input.nextLine();
                    
                }
            }
        }
    }
}
