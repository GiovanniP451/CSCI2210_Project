
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
    
    public ArrayList<Member> getMembers()
    {
        return memberList;
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
        for(Member m : memberList)
        {
            if(m.getUsername().equalsIgnoreCase(username))
            {
                return m;
            }
        }
        return null;
    }
    
    public void removeMember(String name)
    {
        Member member = findMember(name);
        if(member != null)
        {
            memberList.remove(member);
            System.out.println("Member: '" + member.getName() + "' removed successfully");
        }
        else
        {
            System.out.println("No Member found with that Name");
        }
    }
    
    public void listMembers()
    {
        if(memberList.isEmpty())
        {
            System.out.println("No members found.");
            return;
        }
        else
        {
            for(Member m : memberList)
            {
                System.out.println("Name: " + m.getName() + " | Username: " + m.getUsername());
            }
        }
    }
}
