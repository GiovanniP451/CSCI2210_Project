
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;

public class Facility {
    private String facilityName;
    private ArrayList<Area> areas;
    
    public Facility()
    {
        this.facilityName = facilityName;
        this.areas = new ArrayList<>();
    }
    
    public void addArea(String name)
    {
        areas.add(new Area(name));
    }
    
    public boolean removeArea(String name)
    {
        Area a = findArea(name);
        if(a != null)
        {
            areas.remove(a);
            return true;
        }
        return false;
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
    
    public ArrayList<Area> getAreas()
    {
        return areas;
    }
    
    public ArrayList<String> getAreaNames()
    {
        ArrayList<String> names = new ArrayList<>();
        for(Area a : areas)
        {
            names.add(a.getName());
        }
        return names;
    }
    
    public Inventory accessInvent(String areaName)
    {
        Area a = findArea(areaName);
        if(a == null)
        {
            return null;
        }
        return a.getInventory();
    }
}
