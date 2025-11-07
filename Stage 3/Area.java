
/**
 *
 * @author Isiah John
 */
public class Area {
    private String name;
    private Inventory inventory;
    
    public Area(String name)
    {
        this.name = name;
        this.inventory = new Inventory();
    }
    
    public String getName()
    {
        return name;
    }
    
    public Inventory getInventory()
    {
        return inventory;
    }
    
    public void showAreaDetails()
    {
        System.out.println("==== Area: " + name + " ====");
        inventory.showAllItems();
    }
    @Override
    public String toString()
    {
        return name;
    }
}
