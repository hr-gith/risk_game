package models;

import java.util.HashMap;
import java.util.Objects;

/**
 * Continent Class is a model 
 * to store and present information about a continent of a map
 */
public class Continent {
	public int id;
	public String name;
	public HashMap<String,Territory> territories;

	/**
	 * constructor
	 * @param : int Id, String name
	 */
	public Continent(int id, String name) {
		this.id = id;
		this.name = name.toLowerCase();
		this.territories= new HashMap(); 
	}
	
	public boolean Add_Territory(Territory new_territory) {
		Objects.requireNonNull(new_territory);	
		  if (territories == null || territories.isEmpty()) {
			  territories = new HashMap<>();
		  }
		  if (!territories.containsKey(new_territory.name.toLowerCase())) {
			  territories.put(new_territory.name.toLowerCase(), new_territory);
			  return true;
		  }		  
		  return false;
	}
	
	public boolean Delete_Territory(String territory_name) {
		  if (territories != null && !territories.isEmpty() && territories.containsKey(territory_name.toLowerCase())) {
			  territories.remove(territory_name.toLowerCase());		  
			  return true;
		  }
		  return false;
	}

	/**
	 * Two continents are equal only if their names are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().isInstance(Continent.class)) {
			Continent other = (Continent) obj;
			return other.name.equals(this.name.toLowerCase());
		}
		return false;
	}

	/**
	 * Convert a continent information to a string
	 */
	@Override
	public String toString() {
		String territories_str = "";
		for(String key: territories.keySet()) {
			territories_str += "\t" + territories.get(key).toString();
		}
		return "Continent [id=" + id + ", name=" + name + ", territories: \n" + territories_str + " \n]\n\n";
	}
	
}
