
/**
 *
 * @author Isiah John
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Facility {
    private String facilityName;
    private ArrayList<Area> areas;
    
    public Facility()
    {
        this.facilityName = "Gym";
        this.areas = new ArrayList<>();
    }
    
    public boolean loadFacilityFile(String file)
    {
        areas.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String row;
            while((row = br.readLine()) != null)
            {
                String[] data = row.split(",");
                if(data.length < 3)
                {
                    continue;
                }
                String fName = data[0].trim();
                boolean hasInv = Boolean.parseBoolean(data[1].trim());
                boolean hasClass = Boolean.parseBoolean(data[2].trim());
                
                areas.add(new Area(fName,hasInv,hasClass));
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean saveFacilityFile(String file, ArrayList<String> tableData)
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
    
    public boolean loadClasses(String fileName)
    {
        for(Area area : areas)
        {
            if(area.hasClasses() && area.getClasses() != null)
            {
                area.getClasses().clear();
            }    
        }
        
        File f = new File(fileName);
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
                if(data.length != 5)
                {
                    continue;
                }
                
                String name = data[0];
                String startTime = data[1];
                String endTime = data[2];
                int maxCap = Integer.parseInt(data[3]);
                String areaName = data[4];
                
                Area area = findArea(areaName);
                if(area != null && area.hasClasses())
                {
                    Class c = new Class(name,startTime,endTime,maxCap);
                    area.getClasses().add(c);
                }
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }  
    }
    
    public boolean saveClasses(String fileName, ArrayList<String> tableData)
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
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
            e.printStackTrace();
            return false;
        }
    }
    
    public void addArea(String name, boolean hasInventory, boolean hasClasses)
    {
        Area a = new Area(name,hasInventory,hasClasses);
        areas.add(a);
        if(hasInventory)
        {
            try
            {
                File f = new File(a.getInventoryFile());
                if(!f.exists())
                {
                    f.createNewFile();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public boolean updateArea(String oldName, String newName, boolean newHasInventory, boolean newHasClasses)
    {
        Area a = findArea(oldName);
        if(a == null)
        {
            return false;
        }
        
        if(!a.getName().equals(newName))
        {
            a.setName(newName);
        }
        // If inventory is True (false to true)
        if(!a.hasInventory() && newHasInventory)
        {
            a.setHasInventory(true);
            a.setInventory(new Inventory());
            a.setInventoryFile(newName + "_inventory.txt");
            try
            {
                File f = new File(a.getInventoryFile());
                if(!f.exists())
                {
                    f.createNewFile();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        // If inventory is false (true to false)
        else if(a.hasInventory() && !newHasInventory)
        {
            a.setHasInventory(false);
            a.setInventory(null);
            a.setInventoryFile(null);
        }
        
        //If classes is True (false to True)
        if(!a.hasClasses() && newHasClasses)
        {
            a.setHasClasses(true);
            a.setClasses(new ArrayList<>());
        }
        
        //if classes is false(true to false)
        else if(a.hasClasses() && !newHasClasses)
        {
            a.setHasClasses(false);
            a.setClasses(null);
        }
        
        return true;
    }
    
    public boolean removeArea(String name)
    {
        Area a = findArea(name);
        if(a != null)
        {
            if(a.hasInventory() && a.getInventoryFile() != null)
            {
                File f = new File(a.getInventoryFile());
                if(f.exists())
                {
                    f.delete();
                }
            }
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
