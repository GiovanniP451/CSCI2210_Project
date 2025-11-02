//author: Giovanni Pernudi 
import java.util.ArrayList;


public class Inventory {
    private ArrayList<Item> Items;

    //Initializes inventory list
    public Inventory(){
    Items = new ArrayList<>();
}
    //add product to inventory
    public void addItem(Item Items){
        Items.add(Item);
    }

    //removes product
    public void removeItem(Items Items){
        Items.remove(Item);
    }


    //show inventory list if anything is in it
    public void showAllItems(){
        if (Items.isEmpty()){
            System.out.println("No Items in inventory.");
        }else{
            for(Item product:Items){
                System.out.println(Item);
            }
        }
    }
}
