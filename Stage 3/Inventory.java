//author: Giovanni Pernudi && edited by Isiah John 
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> Items;

    //Initializes inventory list
    public Inventory(){
    Items = new ArrayList<>();
}
    //add product to inventory
    public void addItem(Item item){
        Items.add(item);
    }

    //removes product
    public void removeItem(Item item){
        Items.remove(item);
    }
    
    //Get the list of items from the arraylist.
    public ArrayList<Item> getItems()
    {
        return Items;
    }
    
    public Item findItemByID(int id)
    {
        for (Item i : Items)
        {
            if(i.getId() == id)
            {
                return i;
            }
        }
        return null;
    }

    //show inventory list if anything is in it
    public void showAllItems(){
        if (Items.isEmpty()){
            System.out.println("No Items in inventory.");
        }else{
            for(Item product:Items){
                System.out.println(product);
            }
        }
    }
    
     /*
     * Method to be able to complete the transaction for purchasing an item. 
     * and getting it from inventory. 
     */
    public Item purchaseItem(int id, int quantityReq)
    {
        Item items = findItemByID(id);
        if(items == null)
        {
            System.out.println("Item not found.");
            return null;
        }
        if(quantityReq <= 0)
        {
            System.out.println("Invalid Quantity!");
            return null;
        }
        if(quantityReq > items.getQuantity())
        {
            System.out.println("Not enough in Stock!");
            return null;
        }
        items.setQuantity(items.getQuantity() - quantityReq);
        return new Item(items.getId(), items.getName(), quantityReq, items.getPrice());
    }
}
