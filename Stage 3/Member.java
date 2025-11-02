public class Member {
   
	
    private String name;
    private String username;
    private String password;
    
    
    public Member(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
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
    
    
    
}
