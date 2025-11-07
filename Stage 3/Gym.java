
public class Gym {
    private String gymName;
    private Facility facility;
    //private Schedule schedule;
    private MembershipManager membershipManager;
    private Membership membership;
    //private MemberManager memberManager;
    private StaffManager staffManager;
    private Staff staff;
    private CreditCardManager creditCardManager;
    
    public Gym(String gymName)
    {
        this.gymName = gymName;
        this.facility = new Facility(gymName + " Facility");
        this.staffManager = new StaffManager();
        this.membershipManager = new MembershipManager();
        this.creditCardManager = new CreditCardManager();
        
    }
    
    public void initializeDefaults()
    {
        facility.addArea("Shop");
        facility.addArea("Gym Floor");
        facility.addArea("Rest Area");
        
        Inventory shopInv = facility.accessInvent("Shop");
        shopInv.addItem(new Item(1, "Water Bottle", 20, 1.99));
        shopInv.addItem(new Item(2, "Towel", 10, 3.99));

        Inventory gymInv = facility.accessInvent("Gym Floor");
        gymInv.addItem(new Item(1, "Dumbbell Set", 10, 0));
        
        staffManager.addStaff(new Staff("Isiah", "zaya", "Password"));
        staffManager.addStaff(new Staff("Giovanni", "Gio", "password"));
        
    }
    
    public Member loginMember(String username, String password)
    {
        return null; // still need a membermanager
    }
    public Staff loginStaff(String username, String password)
    {
        if(password == null || password.isEmpty())
        {
            return staffManager.findStaffByUsername(username);
        }
        return staffManager.findStaff(username, password);
    }
    
    public void listMembership()
    {
        membershipManager.listMemberships();
    }
    
    public String getGymName()
    {
        return gymName;
    }
    public Facility getFacility()
    {
        return facility;
    }

    
    
}
