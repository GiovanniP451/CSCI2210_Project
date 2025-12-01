
/**
 *
 * @author Isiah John
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class MemberManager {
    private ArrayList<Member> memberList;
    //private MembershipManager membershipManager;
    
    public MemberManager()
    {
        this.memberList = new ArrayList<>();
    }
    
    public boolean loadMembersFromFile(String MemberFile, MembershipManager membershipManager)
    {
        memberList.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(MemberFile)))
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
                
                if(data.length < 3)
                {
                    continue;
                }
                String name = data[0].trim();
                String username = data[1].trim();
                String password = data[2].trim();
                
                Member m = new Member(name,username,password);
                
                if(data.length>=4)
                {
                    String membershipType = data[3].trim();
                    if(!membershipType.equalsIgnoreCase("None"))
                    {
                        Membership found = membershipManager.findMembership(membershipType);
                        if(found != null)
                        {
                            m.setMembership(found);
                        }
                    }    
                }
                memberList.add(m);
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean saveMemberFile(String MemberFile, ArrayList<String> tableData)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(MemberFile)))
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
    
    public void listMembers()
    {
        for(Member m : memberList)
        {
            System.out.println(m);
        }
    }
}
