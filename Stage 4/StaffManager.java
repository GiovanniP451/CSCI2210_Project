
/**
 *
 * @author Isiah John
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class StaffManager {
    private ArrayList<Staff> staffList;
    
    public StaffManager()
    {
        this.staffList = new ArrayList<>();
    }
    
    public boolean loadStaffFromFile(String StaffFile)
    {
        staffList.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(StaffFile)))
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
                if(data.length != 3)
                {
                    continue;
                }
                String name = data[0].trim();
                String username = data[1].trim();
                String password = data[2].trim();
                addStaff(new Staff(name, username, password));
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean saveStaffFile(String StaffFile, ArrayList<String> data)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(StaffFile)))
        {
            for(String row : data)
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
            if(s.getUsername().equalsIgnoreCase(username) && s.getPassword().equals(password))
            {
                return s;
            }
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
