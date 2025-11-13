
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;

public class MemberManager {
    private ArrayList<Member> memberList;
    
    public MemberManager()
    {
        this.memberList = new ArrayList<>();
    }
    
    public ArrayList<Member> getMembers()
    {
        return memberList;
    }
    
    public boolean addMember(Member m)
    {
        if(m == null || findMemberByUsername(m.getUsername()) != null)
        {
            return false;
        }
        return memberList.add(m);
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
        for(Member m : memberList)
        {
            if(m.getUsername().equalsIgnoreCase(username))
            {
                return m;
            }
        }
        return null;
    }
    
    public boolean removeMember(String name)
    {
        Member member = findMember(name);
        if(member == null)
        {
            return false;
        }
        return memberList.remove(member);
    }
    
    public boolean updateMember(String username, String newName, String newPassword)
    {
        Member member = findMemberByUsername(username);
        if(member == null)
        {
            return false;
        }
        if(newName != null && !newName.isBlank())
        {
            member.setName(newName);
        }
        if(newPassword != null && !newPassword.isBlank())
        {
            member.setPassword(newPassword);
        }
        return true;
    }
}
