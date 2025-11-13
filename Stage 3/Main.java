
/**
 *
 * @author Isiah 
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static MemberManager memberManager = new MemberManager();
    private static StaffManager staffManager = new StaffManager();
    private static MembershipManager membershipManager = new MembershipManager();
    private static CreditCardManager creditCardManager = new CreditCardManager();
    private static Facility facility = new Facility();
    
    public static void main(String[] args) {
        
        initializeDefaultData();
        
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Iron Forge");
        
        byte choice;
        do
        {
            System.out.println("\n==== Login Menu ====");
            System.out.println("1. Member Login");
            System.out.println("2. Staff Login");
            System.out.println("3. Quit");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch (choice)
            {
                case 1:
                {
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    Member member = memberManager.findMember(user,pass);
                    if(member != null)
                    {
                        System.out.println("Welcome " + member.getName());
                        memberMenu(member, membershipManager, creditCardManager, facility);
                    }
                    else
                    {
                        System.out.println("Invalid Member Credentials\n");
                    }
                    break;
                }
                case 2:
                {
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    Staff staff = staffManager.findStaff(user,pass);
                    if(staff != null)
                    {
                        staffMenu(staff, membershipManager);
                    }
                    else
                    {
                        System.out.println("Invalid Staff Credentials\n");
                    }
                    break;
                }
                case 3:
                {
                    System.out.println("GoodBye!"); return;
                }
                default:
                {
                    System.out.println("Invalid Choice! Please Try Again!\n");
                    break; 
                }
            }
        } while(choice != 3);
    }
    
    public static void initializeDefaultData()
    {
        memberManager.addMember(new Member("Bob", "bob1", "pass1234"));
        
        
        staffManager.addStaff(new Staff("Isiah", "zaya", "pass1234"));
        staffManager.addStaff(new Staff("Giovanni", "gio", "pass1234"));
        
        facility.addArea("Shop");
        facility.addArea("Gym Floor");
        facility.addArea("Rest Area");
        
        facility.getAreas().get(0).getInventory().addItem(new Item(1, "Water Bottle", 20, 1.99));
        facility.getAreas().get(0).getInventory().addItem(new Item(2, "Towel", 10, 3.99));
        facility.getAreas().get(1).getInventory().addItem(new Item(1, "Dumbbells", 15, 0));
    }
        
    public static void memberMenu(Member member, MembershipManager membershipManager, CreditCardManager creditCardManager, Facility facility)
    {
        Scanner input = new Scanner(System.in);
        
        byte choice;
        do
        {
            System.out.println("\n==== Member Menu ( " + member.getName() + ") ====");
            System.out.println("1. Browse Areas");
            System.out.println("2. View My Info");
            System.out.println("3. Purchase Membership");
            System.out.println("4. Logout"); //Goes back to Login Menu
            System.out.print("Choice: ");
            choice = input.nextByte();
            
            switch(choice)
            {
                case 1: 
                {
                    System.out.println("\n==== Areas ====");
                    for(Area a : facility.getAreas())
                    {
                        if(a.getName().equalsIgnoreCase("Rest Area"))
                        {
                            System.out.println(" * Rest Area (Premium Only)");
                        }
                        else
                        {
                            System.out.println(" * " + a.getName());
                        }
                    }
                    break;
                }
                case 2:
                {
                    System.out.println("==== Member Info ====");
                    System.out.println("Name: " + member.getName());
                    System.out.println("Username: " + member.getUsername());
                    
                    if(member.getMembership() != null)
                    {
                        System.out.println("Current Membership: " + member.getMembership().getType());
                    }
                    else
                    {
                        System.out.println("Current Membership: (None Assigned)");
                    }
                    
                    CreditCard savedCard = creditCardManager.findCard(member);
                    if(savedCard != null)
                    {
                        System.out.println("Saved Credit Card: " + savedCard);
                    }
                    else
                    {
                        System.out.println("No Saved Credit Cards on File");
                    }
                    System.out.println("====================");
                    break;
                }
                case 3:
                {
                    break;
                }
                case 4:
                {
                    System.out.println("Returning Back to Login Menu"); return;
                }
            }
        } while(choice != 3);
    }
    
    public static void staffMenu(Staff staff, MembershipManager membershipManager)
    {
        
    }
}
