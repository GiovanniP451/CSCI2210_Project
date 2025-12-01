/*

@Author Isiah John
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class MembershipManager {
    private ArrayList<Membership> memberships;
    
    public MembershipManager()
    {
        memberships = new ArrayList<>();
    }
    
    public boolean loadMembership(String membershipFile)
    {
        memberships.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(membershipFile)))
        {
            String row;
            while((row = br.readLine()) != null)
            {
                row = row.trim();
                if(row.isEmpty())
                {
                    continue;
                }
                
                String[] data = row.split(",");
                if(data.length<2)
                {
                    continue;
                }
                String type = data[0].trim();
                double price = Double.parseDouble(data[1].trim());
                
                memberships.add(new Membership(type,price));
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean saveMembershipFile(String membershipFile, ArrayList<String> tableData)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(membershipFile)))
        {
            for(String row : tableData)
            {
                bw.write(row);
                bw.newLine();
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
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
