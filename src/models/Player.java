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
	
	public Integer id; 	
	public String name; 
	public State_Player current_state;
	public State_Game current_state_game; 
	public State_Game old_state_game; 
	public boolean alive;	
	public HashMap<String,Territory> owned_territories;
	public Integer reinforcements;
	
	
	/** 
	 * Constructor for the Player object. The player object controls all the actions a player may make in the game. 
	 * @param Integer The player's game id 
	 * @param String The player's name 
	 */
	
	public Player(Integer id, String name){
		this.id = id; 
		this.name = name;  
		this.current_state = State_Player.WAITING;
		this.current_state_game = State_Game.SETUP; 
		this.old_state_game = State_Game.SETUP; 
	}
	
	public Player(String name){
		this.id = 0; 
		this.name = name;  
		this.current_state = State_Player.WAITING;
		this.current_state_game = State_Game.SETUP; 
		this.old_state_game = State_Game.SETUP; 
	}
	
	/** 
	 * An overloaded constructor object that automatically assigns a player name corresponding to "Player" + player_id
	 * @param Integer The player's game id  
	 */
	public Player(Integer id){
		this(id, "Player " + id);  
	}
	
	public boolean Startup_Reinforcement() {
		Set_Number_StartUp_Reinforcements();
        current_state_game = State_Game.STARTUP;
        alive = true;
		if (reinforcements > 0) {
        	Assign_Min_Army_To_Territories();
        	return true;
		}
		else
			return false;
	}
	
	public boolean Reinforcement() {
		current_state = State_Player.PLAYING;
        old_state_game = State_Game.REINFORCEMENT;
    	if (old_state_game != State_Game.STARTUP)  
            Set_Number_Territory_Reinforcements();          
    	else
    		return false;
        return true;
	}
  
	public boolean Assign_Min_Army_To_Territories() {
		if (reinforcements >= owned_territories.size()) {
			for(Territory t: owned_territories.values())
	    		t.nb_armies = 1;
	        reinforcements -= owned_territories.size();
	        return true;
        }
		return false;
	}
	
	public boolean Assign_Army_To_Territories(HashMap<String, Integer> territories_armies) {
        if (this.reinforcements > 0) {
        	for (String territory_name : territories_armies.keySet()) {
        		 if(!this.Add_Army_To_Territory(territory_name, territories_armies.get(territory_name)))   		
        			return false;  		
        	}
        }
        else
        	return false;
		
        current_state_game = State_Game.ATTACKING;
        return true;

        //check if card set can be used
        //if so ask if user wants to play cards

        //take input
        //y-> add armies to reinforcement list, decrement cards, increase cards played

        //if reinforcement list is empty
        //n-> prompt user to place troops
        //take input and update troop positions
        // update map
        //y -> notify user they are finished fortifying and are now ready to attack
        // transition to attack phase

    }
	
	/** 
	 * Adds an army to a territory during the setup and reinforcement phase
	 * @param String the name of the territory to add the army
	 * @return A boolean corresponding to if the army placement is valid
	 */	
	public Boolean Add_Army_To_Territory(String territory_key, int nb_new_armies ){		
		if(owned_territories.containsKey(territory_key) && this.reinforcements >= nb_new_armies){		
			this.owned_territories.get(territory_key).nb_armies += nb_new_armies; 			
			this.reinforcements -= nb_new_armies; 			
			return true;
		} 	
		else{
			return false; 
		}
	}	
	public Boolean Add_Army_To_Territory(String territory_key ){		
		return Add_Army_To_Territory(territory_key, 1);
	}	
	
	
    public void Attack() {
        old_state_game = State_Game.ATTACKING;
        //check if user satisfies any territories to attack from
        //y-> update map with potential attackers
        //prompt attack move
        // call attack method
        // update army counts and army locations on map
        // if defeated Player
        //increment cards
        //move to reinforcement phase

        //or  end attack phase
        // increment cards if conquered
        //set phase to fortification


        //n-> notify user they are finished attacking and are now ready to fortify
        // increment cards if conquered
        //set phase to fortification

        current_state_game = State_Game.FORTIFICATION;

    }
    
    public boolean Fortification(String from, String to, int nb_armies) {
        old_state_game = State_Game.FORTIFICATION;
        // game_view.Display_Menu_Fortification(current_player);        

        //Test if any units to fortify
        if (Has_Units_To_Move()) {
    		if (!Move_Army(from, to, nb_armies))
    			return false;                 
        } 
        else {
        	return false;
        }

    	//current_player_order = (current_player_order + 1) % Game_Model.number_of_players;
        //player_flag = true;
    	current_state = State_Player.WAITING;  
        //current_player_order = (current_player_order + 1) % Game_Model.number_of_players;
        //player_flag = true;
    	current_state_game = State_Game.REINFORCEMENT;//for the next player//??????????????????????
    	return true;
    }
    
    /** 
	 * Checks whether a player has at least any units that are capable of moving territories
	 * @param Player The current game player
	 * @return A boolean value representing whether the player is capable of moving any units
	 */
    
    public Boolean Has_Units_To_Move() {
        for (String key : owned_territories.keySet()) {
            if (owned_territories.get(key).nb_armies > 1)
                return true;
        }
        return false;
    }
	

	/** 
	 * Calculates the number of resulting reinforcements based on the number of owned territories 
	 */	
	public void Set_Number_Territory_Reinforcements(){		
		 this.reinforcements = 3 + this.owned_territories.size() / 3; 	
	}
	
	/** 
	 * Calculates the number of resulting units to start the game for each player
	 */	
	public void Set_Number_StartUp_Reinforcements(){		
		// hadi input start up reinforcement logic
		this.reinforcements = this.owned_territories.size() * 2; 		
	}
	
	
	
	/** 
	 * Moves a number of armies between territories during the fortification phase
	 * @param Territory The starting territory of the armies being moved 
	 * @param Territory The finishing territory of the armies being moved 
	 * @param Integer The number of units being moved between territories
	 * @return A boolean corresponding to if the army movement is valid
	 */
	
	public Boolean Move_Army(String from, String to, int number_of_units){
		Territory from_territory = this.owned_territories.get(from);
		Territory to_territory = this.owned_territories.get(to);
		if (from_territory != null && to_territory != null && !from_territory.equals(to_territory) && !from_territory.owner_name.equals(to_territory.owner_name)) {
			//TODO: check if there is a path 
			
			if (Has_Enough_Army_To_Move(from_territory, number_of_units )) {
				this.Add_Army_To_Territory(to, number_of_units);
				this.Add_Army_To_Territory(from, (number_of_units * -1));
				return true;
			}			
		}			
		return false; 
			
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
	
	
	/** 
	 * A test to determine whether two territories are connected. 
	 * @param String A name of a territory
	 * @param String A name of a different territory
	 * @return A boolean corresponding to if the owned territories are connected
	 */
	
	private Boolean areConnectedTerritories(String t1, String t2){
		
		// for Hadi to complete with Hamideh
		/*
		HashSet<Territory> territories = new HashSet<>();
		//Needs testing
			for ( Territory territory : owned_territories.values()) 
				territories.add(territory);
	
		return Game_Model.map_generator.map.Exist_Path(territories, t1 , t2);
		*/
		return true;
		
	}
	
	/** 
	 * A test to determine whether two territories are connected. 
	 * @param Territory A territory from which troops are being moved
	 * @param Integer The number of troops to be moved 
	 * @return A boolean corresponding to if the territory has troops capable of moving
	 */
	private Boolean Has_Enough_Army_To_Move(Territory t1, Integer number_of_armies ){		
		return (t1.nb_armies > number_of_armies); 		
	}
	
	
}
