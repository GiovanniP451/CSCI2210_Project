
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;

public class MemberManager {
    private ArrayList<Member> memberList = new ArrayList<>();
    
    public MemberManager()
    {
        this.memberList = new ArrayList<>();
    }
    
    public void addMember(Member m)
    {
        memberList.add(m);
    }
    
    public Member findMember(String name)
    {
        for(Member m : memberList)
        {
            if(m.getName().equalsIgnoreCase(name))
            {
                return m;
            }    
        }
        return null;
    }
    
    public Member findMember(String username, String password)
    {
        for(Member m : memberList)
        {
            if(m.getUsername().equalsIgnoreCase(username) && m.getPassword().equals(password))
            {
                return m;
            }
        }
        return null;
    }
    
    public Member findMemberByUsername(String username)
    {
        
    }
    
    public void removeMember(String username)
    {
        
    }
    
    public void listMembers()
    {
        
    }
}
