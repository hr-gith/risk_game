package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Map {
	public String image;
	public boolean wrap;
	public String scroll;
	public String author;
	public boolean warn;
	public HashMap<String,Continent> continents;
    
    //constructors
    public Map() {
    	continents = new HashMap<>();
    }
    
	public Map(String image, boolean wrap, String scroll, String author, boolean warn,HashMap<String,Continent> continents) {
		this.image = image;
		this.wrap = wrap;
		this.scroll = scroll;
		this.author = author;
		this.warn = warn;
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
	
	/**
	 * @return list of all territories existed on the map
	 */
	public HashSet<Territory> Get_Territories() {
		HashSet<Territory> territories = new HashSet<>();
		for (String key : continents.keySet()) {
			for ( Territory territory : continents.get(key).territories.values()) 
				territories.add(territory);
		}
		return territories;
	}
	
	/**
	 * checks if a map is valid based on RISK game rules
	 * @return true if it is valid otherwise returns false
	 */
	public boolean Is_Valid() {
		HashSet<Territory> map_territories = this.Get_Territories();
		//Is a connected graph?
		int territories_count = map_territories.size();
		if (territories_count != DFS().size())
			return false;
		
		//Are all continents connected graphs?
		for (Continent con : continents.values()) {
			territories_count = con.territories.size();
			if (territories_count != DFS(con).size())
				return false;
		}
		
		//If two territories have the same pos? => has been checked while importing a map in IO_Map_Helper	
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
	 * Apply Depth First Search (DFS) on a map starting from its first territory of the map
	 * @return list of visited territories 
	 */
	private HashMap<String, Boolean> DFS() {
		HashMap<String, Boolean> visited = new HashMap<>();
		HashSet<Territory> territories = this.Get_Territories();
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
	 * Apply Depth First Search (DFS) on a continent starting from its first territory 
	 * @param continent 
	 * @return list of visited territories 
	 */
	private HashMap<String, Boolean> DFS(Continent continent) {
		HashMap<String, Boolean> visited = new HashMap<>();
		HashSet<Territory> territories = (HashSet<Territory>) continent.territories.values();
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
