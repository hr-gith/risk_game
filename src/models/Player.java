package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * Player Class is a model 
 * Controls the actions of the player and retains the state of a given player throughout the game
 */
public class Player {
	
	public Integer player_id; 
	
	public String player_name; 
	State_GamePhase state_game_phase; 
	State_GamePhase old_state_game_phase; 
	
	public HashMap<String,Territory> owned_territories;

	public Integer reinforcements;
	
	/** 
	 * Constructor for the Player object. The player object controls all the actions a player may make in the game. 
	 * @param Integer The player's game id 
	 * @param String The player's name 
	 */
	
	public Player(Integer player_id, String player_name){
		this.player_id = player_id; 
		this.player_name = player_name;  
		this.state_game_phase = State_GamePhase.SETUP; 
		this.old_state_game_phase = State_GamePhase.SETUP; 
	}
	
	/** 
	 * An overloaded constructor object that automatically assigns a player name corresponding to "Player" + player_id
	 * @param Integer The player's game id  
	 */
	public Player(Integer player_id){
		this(player_id, "Player " + player_id);  
	}

	/** 
	 * Calculates the number of resulting reinforcements based on the number of owned territories 
	 */
	
	public void Number_Territory_Reinforcements(){
		
		 this.reinforcements = 3 + this.owned_territories.size() / 3; 
		
		
	}
	
	/** 
	 * Calculates the number of resulting units to start the game for each player
	 */
	
	public void Number_StartUp_Reinforcements(){
		
		// hadi input start up reinforcement logic
		this.reinforcements = this.owned_territories.size(); 
		
	}
	
	/** 
	 * Adds an army to a territory during the setup and reinforcement phase
	 * @param String the name of the territory to add the army
	 * @return A boolean corresponding to if the army placement is valid
	 */
	
	public Boolean Add_Army(String territory_key){
		
		if(owned_territories.containsKey(territory_key) && this.reinforcements > 0){
		
			this.owned_territories.get(territory_key).nb_armies++; 
			
			this.reinforcements--; 
			
			return true;
		} 
	
		else{
			return false; 
		}
	}
	
	
	/** 
	 * Moves a number of armies between territories during the fortification phase
	 * @param Territory The starting territory of the armies being moved 
	 * @param Territory The finishing territory of the armies being moved 
	 * @param Integer The number of units being moved between territories
	 * @return A boolean corresponding to if the army movement is valid
	 */
	
	public Boolean Move_Army(Territory from, Territory to, Integer number_of_units){
		//is there a connection between the two points? 
		// do I leave at least one unit in the territory  
		
		if(this.ownsBothTerritories(from, to) && this.areConnectedTerritories(from.name, to.name) && this.hasEnoughTroopsForMove(from, number_of_units )){
			
			to.nb_armies = to.nb_armies + number_of_units; 
			from.nb_armies = from.nb_armies - number_of_units; 
			
			return true; 
			
		} 
		else{
			return false; 
		}
	
		
	}
	
	
	/** 
	 * Adds a territory to the list of owned territories of a player
	 * @param Territory The territory being added to the player
	 * @return A boolean corresponding to if the territory was successfully added
	 */
	
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
	
	
	/** 
	 * Removes a territory to the list of owned territories of a player
	 * @param Territory The territory being removed from the player
	 * @return A boolean corresponding to if the territory was removed sucessfully
	 */
	
	public boolean Delete_Territory(String territory_name) {
		Objects.requireNonNull(territory_name);	
		
		  if (this.owned_territories != null && !this.owned_territories.isEmpty() && this.owned_territories.containsKey(territory_name.toLowerCase())) {
			  this.owned_territories.remove(territory_name.toLowerCase());		  
			  return true;
		  }
		  return false;
	}
	
	
	// Test Logic
	
	/** 
	 * A test to determine whether a player owns two territories
	 * @param Territory A territory
	 * @param Territory A different territory
	 * @return A boolean corresponding to if the player owns both territories
	 */
	
	private Boolean ownsBothTerritories(Territory t1, Territory t2){
		
		if(this.owned_territories.containsKey(t1) && this.owned_territories.containsKey(t2)){
			return true; 
		}
		else {
			return false; 
		}
		
	}
	
	/** 
	 * A test to determine whether two territories are connected. 
	 * @param String A name of a territory
	 * @param String A name of a different territory
	 * @return A boolean corresponding to if the owned territories are connected
	 */
	
	private Boolean areConnectedTerritories(String t1, String t2){
		
		// for Hadi to complete with Hamideh
		
		HashSet<Territory> territories = new HashSet<>();
		//Needs testing
			for ( Territory territory : owned_territories.values()) 
				territories.add(territory);
	
		return Game_Model.map_generator.map.Exist_Path(territories, t1 , t2);
		
		
	}
	
	/** 
	 * A test to determine whether two territories are connected. 
	 * @param Territory A territory from which troops are being moved
	 * @param Integer The number of troops to be moved 
	 * @return A boolean corresponding to if the territory has troops capable of moving
	 */
	private Boolean hasEnoughTroopsForMove(Territory t1, Integer troops ){
		
		return (t1.nb_armies > troops); 
		
	}
	
	
}
