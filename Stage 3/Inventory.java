//author: Giovanni Pernudi && edited by Isiah John 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> Items;

    //Initializes inventory list
    public Inventory(){
    Items = new ArrayList<>();
    }
    
    public boolean loadFromFile(String file)
    {
        Items.clear();
        File f = new File(file);
        
        if(!f.exists())
        {
            return false;
        }
        
        try(BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            String row;
            while((row = br.readLine()) != null)
            {
                String[] data = row.split(",");
                if(data.length == 4)
                {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int qty = Integer.parseInt(data[2]);
                    double price = Double.parseDouble(data[3]);
                    
                    Items.add(new Item(id,name,qty,price));
                }
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean saveToFile(String file, ArrayList<String> tableData)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
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
