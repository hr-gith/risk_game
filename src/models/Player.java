package models;

import java.util.ArrayList;
import java.util.Collections;
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
	public Game_Model ref_game; 
	public HashMap<String,Territory> owned_territories;
	public Integer reinforcements;
	
	
	/** 
	 * Constructor for the Player object. The player object controls all the actions a player may make in the game. 
	 * @param Integer The player's game id 
	 * @param String The player's name 
	 */
	
	public Player(Integer id, String name, Game_Model game){
		this.id = id; 
		this.name = name;  
		this.current_state = State_Player.WAITING;
		this.ref_game = game;
	}
	
	public Player(String name, Game_Model game){
		this(0, name, game);
	}
	
	/** 
	 * An overloaded constructor object that automatically assigns a player name corresponding to "Player" + player_id
	 * @param Integer The player's game id  
	 */
	public Player(Integer id, Game_Model game){
		this(id, "Player " + id, game);  
	}
	
	public boolean Is_Alive() {
		if (current_state == State_Player.DEAD)
			return false;
		return true;
	}
	
	public void Update_State(State_Player new_state) {
		current_state = new_state;
	}
	
	public Message_Handler Reinforcement(String to_territory, int nb_armies) {
		if (this.Add_Army_To_Territory(to_territory, nb_armies)) {
			return new Message_Handler(true);
		}
		return new Message_Handler(true, "The territory does not belong to you or your reinforcement is not enough.");
		/*current_state = State_Player.PLAYING;
        old_state_game = State_Game.REINFORCEMENT;
    	if (old_state_game != State_Game.STARTUP)  
            Set_Number_Territory_Reinforcements();          
    	else
    		return false;
        return true;*/
	}
	
	public Message_Handler Attack (Attack_Model attack_plan) {
        Message_Handler response = new Message_Handler(true);
		if (attack_plan.Is_Valid_Attack()) {
			if (!attack_plan.all_out) {
				attack_plan.Decide_Battle();
				attack_plan.Apply_Result();
				//TODO: move armies by winner
			}
			else {
				//TODO: all out
				// to be tested
				while (attack_plan.from.nb_armies > 1 && attack_plan.from.owner_name != attack_plan.to.owner_name) {
				attack_plan.Decide_Battle();
				attack_plan.Apply_Result();
				//TODO: if successful, movie all possible armies ?!
				}
			}
			/*if (!Has_Extra_Army_To_Move()) {    		
       			ref_game.Update_State(State_Game.FORTIFICATION, "You can not attack any more!");
       		}else {
       			//go to post attack???? if there is a conquerer????
       		}*/
		}
		else {
			response.ok = false;
			response.message = attack_plan.message;
		}
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

        return  response;
    }
	
	
    public boolean Fortification(String from, String to, int nb_armies) {
        //Test if any units to fortify
        if (Has_Extra_Army_To_Move()) {
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
    	//current_state_game = State_Game.REINFORCEMENT;//for the next player//??????????????????????
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
		
        //current_state_game = State_Game.ATTACKING; //?????????
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
	
	
    /** 
	 * Calculates the number of resulting reinforcements based on the number of owned territories 
	 */	
	public void Set_Number_Territory_Reinforcements(){		
		 this.reinforcements = 3 + this.owned_territories.size() / 3; 	
	}
	
	
	/** 
	 * Moves a number of armies between territories during the fortification phase
	 * @param Territory The starting territory of the armies being moved 
	 * @param Territory The finishing territory of the armies being moved 
	 * @param Integer The number of units being moved between territories
	 * @return A boolean corresponding to if the army movement is valid
	 */
	
	public Boolean Move_Army(String from_name, String to_name, int number_of_units){
		Territory from = this.owned_territories.get(from_name);
		Territory to = this.owned_territories.get(to_name);
		if (from != null && to != null &&
				!from_name.equalsIgnoreCase(to_name) && from.owner_name.equalsIgnoreCase(to.owner_name)) {
			
			if (Are_Connected_Territories (from_name, to_name)  && Has_Enough_Army_To_Move(from, number_of_units )) {
				this.Add_Army_To_Territory(to_name, number_of_units);
				this.Add_Army_To_Territory(from_name, (number_of_units * -1));
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
			  new_territory.owner_name = this.name;
			  			  
			  return true; 
		  }		  
		  return false;
	}
	
	/**
	 * 
	 * @return Total_Number_of_Armies_Of_Players
	 */
	
	public int Total_Number_of_Armies_Of_Players() {

		int total_Number_Of_Armies = 0;
		for (Territory t : owned_territories.values())
			total_Number_Of_Armies += t.nb_armies;
		return total_Number_Of_Armies;

	}
	
	
	/** 
	 * Removes a territory to the list of owned territories of a player
	 * @param Territory The territory being removed from the player
	 * @return A boolean corresponding to if the territory was removed sucessfully
	 */
	
	public boolean Delete_Territory(String territory_name) {
		Objects.requireNonNull(territory_name);	
		
		  if (this.owned_territories != null && !this.owned_territories.isEmpty() && this.owned_territories.containsKey(territory_name.toLowerCase())) {
			  Territory deleted = this.owned_territories.get(territory_name);
			  deleted.owner_name = "";
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
	
	private Boolean Are_Connected_Territories(String t1, String t2){
		
		if (Map_Helper.Exist_Path(new HashSet<Territory>( owned_territories.values()), t1, t2))
			return true;
		return false;
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
	
	/** 
	 * Checks whether a player has at least any units that are capable of moving territories
	 * @param Player The current game player
	 * @return A boolean value representing whether the player is capable of moving any units
	 */
    
    public Boolean Has_Extra_Army_To_Move() {
        for (String key : owned_territories.keySet()) {
            if (owned_territories.get(key).nb_armies > 1)
                return true;
        }
        return false;
        
    }
	
}
