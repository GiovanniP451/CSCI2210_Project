//Author: Giovanni Pernudi & edited by Isiah John


public class Member {
   
	
    private String name;
    private String username;
    private String password;
    private Membership membership;
    
    
    public Member(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.membership = null;
    }
    
    //explicit parameters the name and age and assigns them to the instance variables
    public String getName() {
        return name;
    }
    
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    //receives as explicit parameter the name of the member and assigns it to the instance variable.
    public void setName(String name) {
        this.name = name;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
    	this.password = password; 
    }
    
    public Membership getMembership()
    {
        return membership;
    }
    
    public void setMembership(Membership membership)
    {
        this.membership = membership;
    }
    
    @Override
    public String toString()
    {
        return "Member Name: " + name + " | Username: " + username;
    }
    
}
