/*

@Author Isiah John
*/
import java.util.ArrayList;

public class MembershipManager {
    private ArrayList<Membership> memberships;
    
    public MembershipManager()
    {
        memberships = new ArrayList<>();
    }
    
    public void createMembership(String type, double cost)
    {
        if(findMembership(type) != null)
        {
            System.out.println("Membership Type: " + type + " already exists.");
            return;
        }
        Membership m = new Membership();
        m.setType(type);
        m.setCost(cost);
        memberships.add(m);
    }
    
    public Membership findMembership(String type)
    {
        for(Membership m : memberships)
        {
            if(m.getType().equals(type))
                return m;
        }
        return null;
    }
    
    public void updateMembership(String type, String newType, double newCost)
    {
        Membership m = findMembership(type);
        if(m != null)
        {
            if(newType != null) 
                m.setType(newType);
            if(newCost >= 0)
                m.setCost(newCost);
        }
        else
        {
            System.out.println("Membership type not found: " + type);
        }
    }
    
    public boolean removeMembership(String type)
    {
        Membership m = findMembership(type);
        if(m != null)
        {
            memberships.remove(m);
            return true;
        }
        return false;
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
