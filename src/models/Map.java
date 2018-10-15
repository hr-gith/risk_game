package models;

import java.util.HashMap;
import java.util.Objects;

public class Map {
	public String name;
	public HashMap<String,Continent> continents;
    
    //constructors
    public Map() {
    	name = "Risk Game Map";
    	continents = new HashMap<>();
    }
    
	public Map(String name,HashMap<String,Continent> continents) {
		this.name = name;
		this.continents = continents;
	}
	    
	//methods
	public boolean IsEmpty() {
		  return this.continents.isEmpty();
	}	
	
	
	public boolean Add_Continent(Continent new_Continent) {
		Objects.requireNonNull(new_Continent);	
		  if (continents == null || continents.isEmpty()) {
			  continents = new HashMap<>();
		  }
		  if (!continents.containsKey(new_Continent.name)) {
			  continents.put(new_Continent.name, new_Continent);
			  return true;
		  }		  
		  return false;
	}
	
	public boolean Delete_Continent(String continent_name) {
		  if (continents != null && !continents.isEmpty() && continents.containsKey(continent_name.toLowerCase())) {
			  continents.remove(continent_name.toLowerCase());		  
			  return true;
		  }
		  return false;
	}
	public Territory Get_Territory (String territory_name) {
		for (String key : continents.keySet()) {
			if (continents.get(key).territories.containsKey(territory_name.toLowerCase())) 
				return continents.get(key).territories.get(territory_name.toLowerCase());		    
		}
		return null;
	}
	/**
	 * check if the user_enter continent name is not repetitive
	 * @param continent_name
	 * @return continent name or null value
	 */
	public Continent Get_Continent (String continent_name) {
		return continents.get(continent_name);
	}
	
	public boolean Is_Valid_Map() {
		boolean result = false;
		//TODO: is a connected graph?
		
		//TODO: All continents are connected graph?
		
		//TODO: if two territories have the same pos
		
		return result;
	}

	@Override
	public String toString() {
		String continent_str = "";
		for(String key: continents.keySet()) {
			continent_str += continents.get(key).toString();
		}
		return "Map_Model " + name 
				+ "\n====================================== "
				+ "\n" + continent_str ;
	}
	
	
}
