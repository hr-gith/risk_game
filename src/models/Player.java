package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Player {
	
	public Integer player_id; 
	
	public String player_name; 
	State_GamePhase state_game_phase; 
	
	public HashMap<String,Territory> owned_territories;
	
	
	
	public Player(Integer player_id, String player_name){
		this.player_id = player_id; 
		this.player_name = player_name;  
		this.state_game_phase = State_GamePhase.STARTUP; 
	}
	
	public Player(Integer player_id){
		this(player_id, "Player " + player_id);  
	}

	/**
	 * This method calculates number of initial armies for each player to be used in the STARTUP phase
	 * @return return number of initial armies as an integer
	 */
	public static int Calc_Initial_Armies() {
		int initial_armies=15;
		//TODO the logic based on number of players and territories
		// logic should be mentioned in Documentations
		return initial_armies;
	} 
	
	public Boolean Add_Army(){
		
		
		
		return true; 
	}
	
	
	public Boolean attackTerritory(Territory attacking, Territory defending){
		//is the territory adjacent
		//is there more than one army in the territory 
		
		
		
		
		return true; 
	}
	
	public Boolean fortifyTerritory(Territory from, Territory to, Integer number_of_units){
		//is there a connection between the two points? 
		// do I leave at least one unit in the territory 
		
		return true; 
	}
	
	public boolean Add_Territory(Territory new_territory) {
		Objects.requireNonNull(new_territory);	
		  if (this.owned_territories == null || this.owned_territories.isEmpty()) {
			  this.owned_territories = new HashMap<>();
		  }
		  if (!this.owned_territories.containsKey(new_territory.name.toLowerCase())) {
			  this.owned_territories.put(new_territory.name.toLowerCase(), new_territory);
			  			  
			  return true; 
		  }		  
		  return false;
	}
	
	
}
