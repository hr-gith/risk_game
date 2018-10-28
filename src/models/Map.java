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

public class Map {
	public String image;
	public boolean wrap;
	public String scroll;
	public String author;
	public boolean warn;
	public HashMap<String,Continent> continents;
    
    //constructors
    private Map() {
    	continents = new HashMap<>();
    	this.image = "image";
		this.wrap = true;
		this.scroll = "scroll";
		this.author = "author";
		this.warn = false;
    }
    
	private Map(String image, boolean wrap, String scroll, String author, boolean warn,HashMap<String,Continent> continents) {
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
		private static final Map THE_UNIQUE_MAP= new Map();
	}
	/**
	 * 
	 * @return unique map instance
	 */
	public static Map Get_Map() {
		return MapUniqueInstanceHolder.THE_UNIQUE_MAP;
	}
	    
	/**
	 * Checks if map has no continents
	 * @return
	 */
	public boolean Is_Empty() {
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
		HashMap<String, Boolean> visited_territories = DFS(territories);
		 for (String territory : visited_territories.keySet())
			 if (!visited_territories.get(territory))
				 return false;
		
		//Are all continents connected graphs?
		for (Continent con : continents.values()) {
			territories = new HashSet<Territory>( con.territories.values());
			visited_territories = DFS(territories);
			for (String territory : visited_territories.keySet())
				 if (!visited_territories.get(territory))
					 return false;
		}
		
		//If two territories have same positions or the same names? => has been checked while importing a map in IO_Map_Helper	
		return true;
}
	
	/**
	 * part of Depth first search on a graph
	 * goes through neighbors of a territory if that neighbor belongs to list of visitors(customized graph: continents)
	 * @param territory
	 * @param visited
	 */
	private void DFS_Graph(Territory territory, HashMap<String, Boolean> visited) {
		visited.replace(territory.name, true);
		for (Territory neighbour : territory.adj.values()) {
			if (visited.containsKey(neighbour.name) && !visited.get(neighbour.name)) {
				DFS_Graph (neighbour, visited);
			}
		}
	}
	
	
	/**
	 * Apply Depth First Search (DFS) on a set of territory 
	 * @param continent 
	 * @return list of visited territories 
	 */
	private HashMap<String, Boolean> DFS(HashSet<Territory> territories) {
		HashMap<String, Boolean> visited = new HashMap<>();
		Optional<Territory> first =  territories.stream().findFirst();
		if(first.isPresent()){
		    Territory root = first.get();			    
		    //set visited to false for all territories
		    for (Territory territory: territories ) {
				visited.put(territory.name, false);
			}
			DFS_Graph(root, visited);
		} 		
		return visited;
	}
	
	/**
	 * Checks if any path exists between to territory in a specific list of territories
	 * @param territories which is list of territories we look for a path
	 * @param from_name which is the first territory
	 * @param to_name which is the second territory
	 * @return
	 */
	public Boolean Exist_Path(HashSet<Territory> territories, String from_name, String to_name) {
		Territory root = null;
		Territory to = null;
		for (Territory territory: territories ) {
			if (territory.name.equals(from_name.toLowerCase()))
				root = territory;
			if (territory.name.equals(to_name.toLowerCase()))
				to = territory;
		}
		if (root == null || to == null) return false;
		
		HashMap<String, Boolean> visited = new HashMap<>();
	    //set visited to false for all territories
	    for (Territory territory: territories ) {
			visited.put(territory.name, false);
		}
		DFS_Graph(root, visited);
		if (visited.get(to.name))
			return true;
		return false;
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