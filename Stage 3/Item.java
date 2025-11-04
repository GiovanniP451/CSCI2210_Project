//Author: Giovanni Pernudi 

public class Item {
	
	private int id;
	private String name;
	private int quantity;
	
	public Item(int id, String name, int quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
