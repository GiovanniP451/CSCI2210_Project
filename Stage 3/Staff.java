
/**
 *
 * @author Isiah John
 */
public class Staff {
    private String name;
    private String username;
    private String password;
    
    public Staff(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
    	this.password = password; 
    }
    
    @Override
    public String toString()
    {
        return "Staff Name: " + name + " | Username: " + username;
    }
}
