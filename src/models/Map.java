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
	/**
	 * Checks if map has no continents
	 * @return
	 */
	public boolean IsEmpty() {
		  return this.continents.isEmpty();
	}	
	
	/**
	 * Adds a continent to the map
	 * @param new_Continent
	 * @return
	 */
	public boolean Add_Continent(Continent new_continent) {
		Objects.requireNonNull(new_continent);	
		new_continent.name = new_continent.name.toLowerCase();
		  if (continents == null || continents.isEmpty()) {
			  continents = new HashMap<>();
		  }
		  if (!continents.containsKey(new_continent.name)) {
			  continents.put(new_continent.name, new_continent);
			  return true;
		  }		  
		  return false;
	}
	
	/**
	 * Delete a continent and its territories and all their connections from the map
	 * @param continent_name
	 * @return
	 */
	public boolean Delete_Continent(String continent_name) {
		continent_name = continent_name.toLowerCase();
		  if (continents != null && !continents.isEmpty() && continents.containsKey(continent_name)) {
			  Continent continent = continents.get(continent_name);
			  if (continent.Delete_Territories()) {
				  continents.remove(continent_name);		  
				  return true;
			  }
		  }
		  return false;
	}
	
	/**
	 * Search a territory name in the map and returns the territory
	 * @param territory_name
	 * @return Territory
	 */
	public Territory Get_Territory (String territory_name) {
		territory_name = territory_name.toLowerCase();
		for (String key : continents.keySet()) {
			if (continents.get(key).territories.containsKey(territory_name)) 
				return continents.get(key).territories.get(territory_name);		    
		}
		return null;
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
