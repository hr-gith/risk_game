package models;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Territory Class is a model 
 * A data class maintaining the state of a given territory 
 */

public class Territory {
	public int id;
	public String name;
	public int pos_x;
	public int pos_y;
	public String continent_name;
	public String owner_name;
	public int nb_armies;
    public HashMap<String, Territory> adj;

	/**
	 * Constructor with Id
	 * @param id
	 * @param name
	 * @param pos_x
	 * @param pos_y
	 * @param continent_name
	 */
	public Territory(int id, String name, int pos_x, int pos_y, String continent_name) {
		this.id = id;
		this.name = name.toLowerCase();
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.continent_name = continent_name;
		this.owner_name = "";
		this.nb_armies = 0;
		this.adj = new HashMap<>();
	}
	/**
	 * constructor without id
	 * @param name of territory
	 * @param pos_x X Position
	 * @param pos_y Y Position
	 * @param continent_name
	 */
	public Territory(String name, int pos_x, int pos_y, String continent_name) {
		this.id = 1;
		this.name = name.toLowerCase();
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.continent_name = continent_name;
		this.owner_name = "";
		this.nb_armies = 0;
		this.adj = new HashMap<>();
	}
	
	
	/**
	 * Adds a connection from the territory to another territory
	 * @param neighbour
	 * @return boolean
	 */
	public boolean Add_Neighbour(Territory neighbour) {
		  Objects.requireNonNull(neighbour);	
		  if (adj == null || adj.isEmpty()) {
			  adj = new HashMap<>();
		  }
		  if (!neighbour.name.toLowerCase().equals(this.name) && !adj.containsKey(neighbour.name.toLowerCase())) {
			  adj.put(neighbour.name.toLowerCase(), neighbour);
			  return true;
		  }		  
		  return false;
	}
	
	/**
	 * Deletes a connection between the territory and its neighbor
	 * @param neighbour_name
	 * @return boolean
	 */
	public boolean Delete_Neighbour(String neighbour_name) {
		neighbour_name = neighbour_name.toLowerCase();
		  if (adj != null && !adj.isEmpty() && adj.containsKey(neighbour_name)) {
			  adj.remove(neighbour_name);		  
			  return true;
		  }
		  return false;
	}
	
	/**
	 * Deletes all the connections from its neighbours
	 * @return boolean
	 */
	public boolean Delete_Neighbours() {
		boolean result = true;		
		Set<String> neighbours_name = adj.keySet();
		for (String neighbour_name : neighbours_name) {			
			Territory neighbour = adj.get(neighbour_name);
			result = result && neighbour.Delete_Neighbour(this.name);
		}
		return result;		
	}
	
	/**
	 * Two territories are equal only if their names are the same
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (obj.getClass().isInstance(Territory.class)) {
			Territory other = (Territory) obj;
			if (other.name.equals(this.name.toLowerCase()))
				result = false;
			else if (other.pos_x == this.pos_x && other.pos_y == this.pos_y)
				return false;						
		}
		return result;
	}

	/**
	 * Returns territory information as string
	 */
	@Override
	public String toString() {
		String adj_str = "";
		for (String key : this.adj.keySet()) 
			adj_str += this.adj.get(key).name + " , ";
		
		return "[name=" + name +
				", pos = (" + pos_x + " , " + pos_y + 
				"), owner=" + owner_name +
				", continent =" + continent_name +
				", nb_armies=" + nb_armies +
				", adj= (" + adj_str + ") ]\n";
	}
	
}
