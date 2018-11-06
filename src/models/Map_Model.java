package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Map Class is a model 
 * Map is a data class that maintains the game map structure
 */

public class Map_Model {
	public String image;
	public boolean wrap;
	public String scroll;
	public String author;
	public boolean warn;
	public HashMap<String,Continent> continents;
    
    /**constructors
     * 
     */
    private Map_Model() {
    	continents = new HashMap<>();
    	this.image = "image";
		this.wrap = true;
		this.scroll = "scroll";
		this.author = "author";
		this.warn = false;
    }
    
	private Map_Model(String image, boolean wrap, String scroll, String author, boolean warn,HashMap<String,Continent> continents) {
		this.image = image;
		this.wrap = wrap;
		this.scroll = scroll;
		this.author = author;
		this.warn = warn;
		this.continents = continents;
	}
	
	/**
	 * Singleton Pattern
	 * @author Leila
	 *
	 */
	
	private static class MapUniqueInstanceHolder{
		private static final Map_Model THE_UNIQUE_MAP= new Map_Model();
	}
	/**
	 * 
	 * @return unique map instance
	 */
	public static Map_Model Get_Map() {
		return MapUniqueInstanceHolder.THE_UNIQUE_MAP;
	}
	    
	/**
	 * Checks if map has no continents
	 * @return
	 */
	public boolean Is_Empty() {
		  return this.continents.isEmpty();
	}	
	
	
	public String Continent_List() {
		StringBuilder sb = new StringBuilder(64);
		
		for ( Continent continent:continents.values() ){
			sb.append(continent.name+ " : " + continent.Get_Owner()+"    ");
			sb.append(System.getProperty("line.separator"));
		}
		
		return sb.toString();
		
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
	
	public int Number_Of_All_Territories() {
		int sum=0;
		for (Continent continent:continents.values()) {
			sum+=continent.Number_OF_Territory();
		}
		return sum;
		
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
	/**
	 * check if the user_enter continent name is not repetitive
	 * @param continent_name
	 * @return continent name or null value
	 */
	public Continent Get_Continent (String continent_name) {
		return continents.get(continent_name.toLowerCase());
	}
	
	/**
	 * @return list of all territories existed on the map
	 */
	public HashMap<String,Territory> Get_Territories() {
		HashMap<String, Territory> territories = new HashMap<>();
		for (String key : continents.keySet()) {
			for ( Territory territory : continents.get(key).territories.values()) 
				territories.put(territory.name, territory);
		}
		return territories;
	}
	
	/**
	 * assigns score to each continent
	 */
	public void Set_Continents_Score() {
		for (Continent continent: continents.values())
			continent.Calculate_Score();
	}
	
	/**
	 * checks if a map is valid based on RISK game rules
	 * @return true if it is valid otherwise returns false
	 */
	public boolean Is_Valid() {
		//Is a connected graph?
		HashSet<Territory> territories = new HashSet<Territory>(this.Get_Territories().values());
		//HashSet<Territory> territories = (HashSet<Territory>)this.Get_Territories().values();
		HashMap<String, Boolean> visited_territories = Map_Helper.DFS(territories);
		 for (String territory : visited_territories.keySet())
			 if (!visited_territories.get(territory))
				 return false;
		
		//Are all continents connected graphs?
		for (Continent con : continents.values()) {
			territories = new HashSet<Territory>( con.territories.values());
			visited_territories = Map_Helper.DFS(territories);
			for (String territory : visited_territories.keySet())
				 if (!visited_territories.get(territory))
					 return false;
		}
		
		//If two territories have same positions or the same names? => has been checked while importing a map in IO_Map_Helper	
		return true;
	}
	
	
	@Override
	public String toString() {
		String continent_str = "";
		for(String key: continents.keySet()) {
			continent_str += continents.get(key).toString();
		}
		return "Map created by: " + author 
				+ "\n====================================== "
				+ "\n" + continent_str ;
	}
	
	
}