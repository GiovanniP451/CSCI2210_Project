/*

@Author Isiah John
*/
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
    
    public boolean createMembership(Membership membership)
    {
        if(membership == null || findMembership(membership.getType()) != null)
        {
            return false;
        }
        return memberships.add(membership);
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
    
    public boolean updateMembership(String type, String newType, Double newCost)
    {
        Membership m = findMembership(type);
        if(m == null)
        {
            return false;
        }
        if(newType != null && !newType.isBlank())
        {
            Membership exist = findMembership(newType);
            if(exist != null && exist != m)
            {
                return false;
            }
            m.setType(newType);
        }
        if(newCost != null)
        {
            if(newCost < 0)
            {
                return false;
            }
            m.setCost(newCost);
        }
        return true;
    }
    
    public boolean removeMembership(String type)
    {
        Membership m = findMembership(type);
        if(m == null)
        {
            return false;
        }
        return memberships.remove(m);
    }
    
    public boolean listMembership()
    {
        if(memberships.isEmpty())
        {
            return false;
        }
        else
        {
            for(Membership m : memberships)
            {
                System.out.println(m);
            }
        }
        return true;
    }
    
    public ArrayList<Membership> getMemberships()
    {
        return memberships;
    }
}
