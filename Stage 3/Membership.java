/*

@Author Isiah John && edited by Giovanni Pernudi 
*/
public class Membership {
    private String type;
    private double cost;
    private String benefits;
    
    public Membership(String type, double cost, String benefits)
    {
        this.type = type;
        this.cost = cost;
        this.benefits = benefits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public void createMembership(String type, double cost) {
    	this.type = type;
    	this.cost = cost;
    	}


    public Membership updateMembership(String type, double cost) {
    	this.type = type;
    	this.cost = cost;
    	return this;
    	}


    public void cancelMembership() {
    	this.type = null;
    	this.cost = 0.0;
    	this.benefits = null;
    	}
}
