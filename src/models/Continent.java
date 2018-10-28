package models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import models.Territory;

/**
 * Continent Class is a model 
 * to store and present information about a continent of a map
 */
public class Continent {
	public int id;
	public String name;
	public int score;
	public HashMap<String,Territory> territories;

	/**
	 * constructor
	 * @param : int Id, String name
	 */
	public Continent(int id, String name, int score) {
		this.id = id;
		this.name = name.toLowerCase();
		this.score = score;
		this.territories= new HashMap<>(); 
	}
	/**
	 * constructor without Id
	 * @param name of continent
	 */
	public Continent( String name, int score) {
		this.id = 1;
		this.score = score;
		this.name = name.toLowerCase();
		this.territories= new HashMap<>(); 
	}
	
	/**
	 * Adds a new territory to the continent
	 * Note: territories are unique in the map
	 * @param new_territory
	 * @return boolean
	 */
	public boolean Add_Territory(Territory new_territory) {
		Objects.requireNonNull(new_territory);	
		new_territory.name = new_territory.name.toLowerCase();
		  if (territories == null || territories.isEmpty()) {
			  territories = new HashMap<>();
		  }
		  if (!territories.containsKey(new_territory.name)) {
			  territories.put(new_territory.name, new_territory);
			  return true;
		  }		  
		  return false;
	}
	
	/**
	 * Deletes a territory and its connection from the continent
	 * @param territory_name
	 * @return boolean
	 */
	public boolean Delete_Territory(String territory_name) {
		territory_name = territory_name.toLowerCase();
		  if (territories != null && !territories.isEmpty() && territories.containsKey(territory_name)) {
			  Territory territory = territories.get(territory_name);
			  if (territory != null &&  territory.Delete_Neighbours()) {			  
				  territories.remove(territory_name);		  
				  return true;
			  }
		  }
		  return false;
	}
	
	/**
	 * Delete all territories and their connections from the continent 
	 * @return boolean
	 */
	public boolean Delete_Territories() {
		boolean result = true;
		Set<String> territories_name = territories.keySet();
		String[] arr = territories_name.toArray(new String[territories_name.size()]);
		for (int i = 0; i < territories_name.size(); i ++) {
			result = result && Delete_Territory(arr[i]);
		}
		return result;
	} 
	
	/**
<<<<<<< HEAD
	 * finds player who owns all the territories in the continent
	 * 
	 * @return name of owner or "" if the continent have more than one owner or no owner
	 */
	public String Get_Owner() {
		HashSet<String> owners = new HashSet<String>();
		for(Territory territory : territories.values()) {
			if (!owners.contains(territory.owner_name))
				owners.add(territory.owner_name);
		}
		if (owners.size() == 1)
			return owners.toArray(new String[1])[0];
		else 
			return "";
	}
	
	/**
=======
>>>>>>> refs/remotes/origin/ColinTemp
	 * calculates score based on the number of territories in the continent
	 */
	public void Calculate_Score() {
		if (this.score == 0) {
			if (territories.size() == 0)
				this.score = 0;
			else if (territories.size() < 4) 
				this.score = 2;
			else
				this.score = territories.size() / 2;
		}
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
	 * Returns continent information as string
	 */
	@Override
	public String toString() {
		String territories_str = "";
		for(String key: territories.keySet()) {
			territories_str += "\t" + territories.get(key).toString();
		}
		return "Continent [id =" + id + ", name =" + name + ", score = " + score + ", territories: \n" + territories_str + " \n]\n\n";
	}
	
}