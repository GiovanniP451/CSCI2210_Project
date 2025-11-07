/*
Author: Giovanni Pernudi & edited by Isiah John
*/
public class Item {
	
    private int id;
    private String name;
    private int quantity;
    private double price;
	
    public Item(int id, String name, int quantity, double price) {
	this.id = id;
	this.name = name;
	this.quantity = quantity;
        this.price = price;
    }
	
    public String getName() {
	return name;
    }
	
    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    //What I added
    public int getId() {
        return id;
    }
    
    public int getQuantity()
    {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString()
    {
        return String.format("ID: %d | %s (x%d) - $%.2f each", id, name, quantity, price);
    }
}