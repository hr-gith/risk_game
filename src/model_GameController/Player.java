package model_GameController;

public class Player {
	
	public Integer player_id; 
	
	public String player_name; 
	State_GamePhase state_game_phase; 
	
	
	
	
	public Player(Integer player_id, String player_name){
		this.player_id = player_id; 
		this.player_name = player_name;  
		this.state_game_phase = State_GamePhase.STARTUP; 
	}
	
	public Player(Integer player_id){
		this(player_id, "Player " + player_id);  
	}

	
	
	
	
	public Boolean reinforceArmy(){
		
		
		
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
	
	
	
	
}
