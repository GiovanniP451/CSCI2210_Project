
/**
 *
 * @author Isiah
 */
public class Login {
    private MemberManager memberManager;
    private StaffManager staffManager;
    
    public Login(MemberManager memberManager, StaffManager staffManager)
    {
        this.memberManager = memberManager;
        this.staffManager = staffManager;
    }
    
    public Member loginMember(String username, String password)
    {
        return memberManager.findMember(username, password);
    }
    
    public Staff loginStaff(String username, String password)
    {
        return staffManager.findStaff(username, password);
    }
}
