
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;

public class StaffManager {
    private ArrayList<Staff> staffList;
    
    public StaffManager()
    {
        this.staffList = new ArrayList<>();
    }
    
    public boolean addStaff(Staff staff)
    {
        if(staff == null || findStaffByUsername(staff.getUsername()) != null)
        {
            return false;
        }
        return staffList.add(staff);
    }
    
    public boolean removeStaff(String name)
    {
        Staff staff = findStaff(name);
        if(staff == null)
        {
            return false;
        }
        return staffList.remove(staff);
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
    
    public boolean updateStaff(String username, String newName, String newUsername, String newPassword)
    {
        Staff staff = findStaffByUsername(username);
        if(staff == null)
        {
            return false;
        }
        if(newName != null && !newName.isBlank())
        {
            staff.setName(newName);
        }
        if(newUsername != null && !newUsername.isBlank() && !newUsername.equalsIgnoreCase(username))
        {
            if(findStaffByUsername(newUsername) != null)
            {
                return false;
            }
            staff.setUsername(newUsername);
        }
        if(newPassword != null && !newPassword.isBlank())
        {
            staff.setPassword(newPassword);
        }
        return true;
    }
    
    public void listStaff()
    {
        for(Staff s : staffList)
        {
            System.out.println(s);
        }
    }
    
    public ArrayList<Staff> getStaffList()
    {
        return staffList;
    }
}
