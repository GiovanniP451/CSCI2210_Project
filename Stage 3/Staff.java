
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
   
    public String setUsername(String username) {
        return this.username = username;
    }
    
    public String setPassword(String password) {
    	return this.password = password; 
    }
}
