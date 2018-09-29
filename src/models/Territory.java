package models;

import java.util.HashMap;
import java.util.Objects;

public class Territory {
	public int id;
	public String name;
	public int pos_x;
	public int pos_y;
	public String continent_name;
	public int owner_id;
	public int nb_armies;
    public HashMap<String, Territory> adj;

	//constructors
	public Territory(int id, String name, int pos_x, int pos_y, String continent_name) {
		this.id = id;
		this.name = name.toLowerCase();
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.continent_name = continent_name;
		this.owner_id = 0;
		this.nb_armies = 0;
		this.adj = new HashMap<>();
	}
	
	//methods	
	public boolean Add_Neighbour(Territory neighbour) {
		  Objects.requireNonNull(neighbour);	
		  if (adj == null || adj.isEmpty()) {
			  adj = new HashMap<>();
		  }
		  if (!adj.containsKey(neighbour.name.toLowerCase())) {
			  adj.put(neighbour.name.toLowerCase(), neighbour);
			  return true;
		  }		  
		  return false;
	}
	
	public boolean Delete_Neighbour(Territory neighbour) {
		  Objects.requireNonNull(neighbour);	
		  if (adj != null && !adj.isEmpty() && adj.containsKey(neighbour.name.toLowerCase())) {
			  adj.remove(neighbour.name.toLowerCase());		  
			  return true;
		  }
		  return false;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().isInstance(Territory.class)) {
			Territory other = (Territory) obj;
			return other.name.equals(this.name.toLowerCase());
		}
		return false;
	}

	@Override
	public String toString() {
		String adj_str = "";
		for (String key : this.adj.keySet()) 
			adj_str += this.adj.get(key).name + " , ";
		
		return "[name=" + name +
				", pos = (" + pos_x + " , " + pos_y + 
				"), owner_id=" + owner_id +
				", continent =" + continent_name +
				", nb_armies=" + nb_armies +
				", adj= (" + adj_str + ") ]\n";
	}
	
}
