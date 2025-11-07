
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;

public class Facility {
    private String facilityName;
    private ArrayList<Area> areas;
    
    public Facility(String facilityName)
    {
        this.facilityName = facilityName;
        this.areas = new ArrayList<>();
    }
    
    public void addArea(String name)
    {
        areas.add(new Area(name));
        System.out.println("Added area: " + name);
    }
    
    public void removeArea(String name)
    {
        Area a = findArea(name);
        if(a != null)
        {
            areas.remove(a);
            System.out.println("Area Removed");
        }
        else
        {
            System.out.println("Area not found");
        }
    }
    
    public Area findArea(String name)
    {
        for(Area a : areas)
        {
            if(a.getName().equalsIgnoreCase(name))
            {
                return a;
            }
        }
        return null;
    }
    
    public String getFacilityName()
    {
        return facilityName;
    }
    
    public void listAreas()
    {
        System.out.println("==== Facility Areas for " + facilityName + " ====");
        if(areas.isEmpty())
        {
            System.out.println("No Areas");
        }
        for(Area a : areas)
        {
            System.out.println("- " + a.getName());
        }
    }
    
    public Inventory accessInvent(String areaName)
    {
        Area a = findArea(areaName);
        if(a == null)
        {
            System.out.println("Area not found!");
            return null;
        }
        return a.getInventory();
    }
}
