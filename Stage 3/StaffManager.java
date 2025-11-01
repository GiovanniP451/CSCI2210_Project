
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
    
    public void editStaff(String name)
    {
        Staff staff = findStaff(name);
        if(staff == null)
        {
            System.out.println("No Staff member was found");
            return;
        }
        
        Scanner input = new Scanner(System.in);
        System.out.println("Editing Staff Member: '" + staff.getName() + "'");
        System.out.println("1. Change Name");
        System.out.println("2. Change Username");
        System.out.println("3. Change Password");
        System.out.println("4. Change All");
        System.out.print("Choice: ");
        int choice = input.nextInt();
        
        switch(choice)
        {
            case 1: 
            {
                System.out.print("Enter New Name: ");
                String newName = input.nextLine();
                staff.setName(newName);
                System.out.println("Name Updated Successfully");
                break;
            }
            case 2:
            {
               System.out.print("Enter New Username: ");
               String newUsername = input.nextLine();
               staff.setUsername(newUsername);
               System.out.println("Username Updated Successfully");
               break;
            }
            case 3:
            {
                System.out.print("Enter New Password: ");
                String newPassword = input.nextLine();
                staff.setPassword(newPassword);
                System.out.println("Password updated successfully");
            }
            case 4:
            {
                System.out.print("Enter New Name: ");
                staff.setName(input.nextLine());
                System.out.print("Enter New Username: ");
                staff.setUsername(input.nextLine());
                System.out.print("Enter New Password: ");
                staff.setPassword(input.nextLine());
                System.out.print("All Details Have Been Updated!");
                
            }
            default:
            {
                System.out.println("Invalid Choice");
            }
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
}
