
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;
import java.util.Scanner;

public class StaffManager {
    private ArrayList<Staff> staffList;
    
    public StaffManager()
    {
        this.staffList = new ArrayList<>();
    }
    
    public void addStaff(Staff staff)
    {
        staffList.add(staff);
    }
    
    public void removeStaff(String name)
    {
        Staff staff = findStaff(name);
        if(staff != null)
        {
            staffList.remove(staff);
            System.out.println("Staff Member: '" + staff.getName() + "' removed successfully");
        }
        else
        {
            System.out.println("No Staff Member found with that Name");
        }
    }
    
    public Staff findStaff(String name)
    {
        for(Staff s : staffList)
        {
            if(s.getName().equalsIgnoreCase(name))
            {
                return s;
            }
        }
        return null;
    }
    
    public Staff findStaff(String username, String password)
    {
        for(Staff s : staffList)
        {
            if(s.getUsername().equalsIgnoreCase(username) && s.getPassword().equals(password));
            return s;
        }
        return null;
    }
    
    public Staff findStaffByUsername(String username)
    {
        for(Staff s : staffList)
        {
            if(s.getUsername().equalsIgnoreCase(username))
            {
                return s;
            }
        }
        return null;
    }
    
    public void listStaff()
    {
        if(staffList.isEmpty())
        {
            System.out.println("No staff found.");
            return;
        }
        for(Staff s : staffList)
        {
            System.out.println("Name: " + s.getName() + " | Username: " + s.getUsername());
        }
    }
    
    public ArrayList<Staff> getStaffList()
    {
        return staffList;
    }
}
