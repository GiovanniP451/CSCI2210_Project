/*

@Author Isiah John
*/
import java.util.Scanner;
import java.util.ArrayList;

public class MembershipManager {
    private ArrayList<Membership> memberships;
    
    public MembershipManager()
    {
        memberships = new ArrayList<>();
        defaultMemberships();
    }
    
    public void defaultMemberships()
    {
        memberships.add(new Membership("Basic", 19.99));
        memberships.add(new Membership("Premium", 39.99));
    }
    
    public void createMembership(Membership membership)
    {
        memberships.add(membership);
        System.out.println("Added Membership: " + membership.getType());
    }
    
    public Membership findMembership(String type)
    {
        for(Membership m : memberships)
        {
            if(m.getType().equalsIgnoreCase(type))
                return m;
        }
        return null;
    }
    
    public void updateMembership(String type)
    {
        Membership m = findMembership(type);
        if(m == null)
        {
            System.out.println("Membership: " + type + " was not found");
            return;
        }
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Editing Membership: " + m.getType());
        System.out.println("1. Change cost");
        System.out.println("2. Change name");
        System.out.println("3. Change Both");
        System.out.println("Choice: ");
        int choice = input.nextInt();
        input.nextLine();
        
        switch(choice)
        {
            case 1:
                System.out.println("Enter new cost: ");
                double newCost = input.nextDouble();
                m.setCost(newCost);
                System.out.println("Cost Updated");
                break;
            case 2:
                System.out.println("Enter new name: ");
                String newName = input.nextLine();
                m.setType(newName);
                System.out.println("Name Changed");
                break;
            case 3:
                System.out.println("Enter new name: ");
                String bName = input.nextLine();
                System.out.println("Enter new cost: ");
                double bCost = input.nextDouble();
                m.setType(bName);
                m.setCost(bCost);
                System.out.println("Name and Cost was Updated");
                break;
            default:
                System.out.println("Invalid Choice.");
        }
    }
    
    public void removeMembership(String type)
    {
        Membership m = findMembership(type);
        if(m != null)
        {
            memberships.remove(m);
            System.out.println("Membership Removed");
        }
        else
        {
            System.out.println("Membership: " + type + " not found");
        }
    }
    
    public void listMemberships()
    {
        System.out.println("Available Membership:");
        for (Membership m : memberships)
        {
            System.out.println("Type: " + m.getType() + " | Cost: $" + m.getCost());
        }
    }
    
    public ArrayList<Membership> getMemberships()
    {
        return memberships;
    }
}
