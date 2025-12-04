
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 *
 * @author Isiah John
 */
public class Area {
    private String name;
    private Inventory inventory;
    private String inventoryFile;
    private boolean hasInventory;
    private boolean hasClasses;
    private ArrayList<Class> classes;
    
    public Area(String name, boolean hasInventory, boolean hasClasses)
    {
        this.name = name;
        this.hasInventory = hasInventory;
        this.hasClasses = hasClasses;
        
        if(hasInventory)
        {
            this.inventory = new Inventory();
            this.inventoryFile = generateFile(name);
        }
        else
        {
            this.inventory = null;
            this.inventoryFile = null;
        }
        
        if(hasClasses)
        {
            classes = new ArrayList<>();
        }
        else
        {
            classes = null;
        }
    }
    
    public String generateFile(String aName)
    {
        return aName + "_inventory.txt";
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        
        if(hasInventory)
        {
            inventoryFile = generateFile(name);
        }
    }

    public void setHasInventory(boolean hasInventory) {
        this.hasInventory = hasInventory;
    }

    public void setHasClasses(boolean hasClasses) {
        this.hasClasses = hasClasses;
    }
    
    public boolean hasInventory()
    {
        return hasInventory;
    }
    
    public boolean hasClasses()
    {
        return hasClasses;
    }    
    
    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setInventoryFile(String inventoryFile) {
        this.inventoryFile = inventoryFile;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }
    
    public String getInventoryFile()
    {
        return inventoryFile;
    }
    
    public ArrayList<Class> getClasses()
    {
        return classes;
    }    
    
    @Override
    public String toString()
    {
        return name;
    }
}
