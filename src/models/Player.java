package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;


public class Player {
	
	public Integer player_id; 
	
	public String player_name; 
	State_GamePhase state_game_phase; 
	State_GamePhase old_state_game_phase; 
	
	public HashMap<String,Territory> owned_territories;

	public Integer reinforcements;
	
	
	public Player(Integer player_id, String player_name){
		this.player_id = player_id; 
		this.player_name = player_name;  
		this.state_game_phase = State_GamePhase.SETUP; 
		this.old_state_game_phase = State_GamePhase.SETUP; 
	}
	
	public Player(Integer player_id){
		this(player_id, "Player " + player_id);  
	}

	
	
	public void Number_Territory_Reinforcements(){
		
		 this.reinforcements = this.owned_territories.size() / 3; 
		
		
	}
	
	public void Number_StartUp_Reinforcements(){
		
		// hadi input start up reinforcement logic
		
		
	}
	
	
	
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
	
	
	public boolean Delete_Territory(String territory_name) {
		Objects.requireNonNull(territory_name);	
		
		  if (this.owned_territories != null && !this.owned_territories.isEmpty() && this.owned_territories.containsKey(territory_name.toLowerCase())) {
			  this.owned_territories.remove(territory_name.toLowerCase());		  
			  return true;
		  }
		  return false;
	}
	
	
	// Test Logic
	
	private Boolean ownsBothTerritories(Territory t1, Territory t2){
		
		if(this.owned_territories.containsKey(t1) && this.owned_territories.containsKey(t2)){
			return true; 
		}
		else {
			return false; 
		}
		
	}
	
	private Boolean areConnectedTerritories(String t1, String t2){
		
		// for Hadi to complete with Hamideh
		
		HashSet<Territory> territories = new HashSet<>();
		//Needs testing
			for ( Territory territory : owned_territories.values()) 
				territories.add(territory);
	
		return Game_Model.map_generator.map.Exist_Path(territories, t1 , t2);
		
		
	}
	
	private Boolean hasEnoughTroopsForMove(Territory t1, Integer troops ){
		
		return (t1.nb_armies > troops); 
		
	}
	
	
}
