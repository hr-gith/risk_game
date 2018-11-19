package models;

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
	public State_PlayerStrategy player_strategy; 
	public Game_Model ref_game; 
	public HashMap<String,Territory> owned_territories;
	public Integer reinforcements;
	public Cards cards; 
	public boolean is_conquerer;
	public boolean deserve_card;
	private Attack_Model attack_plan; 
	
	
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
		this.cards = new Cards(); 
		this.is_conquerer = false;
		this.deserve_card = false;
		this.player_strategy =  State_PlayerStrategy.HUMAN;
	}
	
	/**
	 * Constructor for the Player object
	 * @param name name of the player
	 * @param game object of this Gmae_Model
	 */
	public Player(String name, Game_Model game){
		this(0, name, game);
	}
	
	/** 
	 * An overloaded constructor object that automatically assigns a player name corresponding to "Player" + player_id
	 * @param Integer The player's game id  
	 */
	public Player(Integer id, Game_Model game){
		this(id, "p " + id, game);  
	}
	
	/**
	 * this method checks if the player is still in the game or has been terminated
	 * @return true if alive, false if terminated
	 */
	public boolean Is_Alive() {
		if (current_state == State_Player.DEAD)
			return false;
		return true;
	}
	
	/**
	 * this method updates the sate of Player
	 * @param new_state
	 */
	public void Update_State(State_Player new_state) {
		current_state = new_state;
	}
	
	/**
	 * this method handles the player interaction to be used in reinforcement phase
	 * @param to_territory territory recieving the armies
	 * @param nb_armies number of armies
	 * @return true if inputs are valid
	 */
	public Message_Handler Reinforce(String to_territory, int nb_armies) {
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
	
	/**
	 * this method handles the player interaction to be used in attack phase
	 * @param attack_plan Attack_Model object
	 * @return
	 */
	public Message_Handler Attack (Attack_Model attack_plan) {
        Message_Handler response = new Message_Handler(true);
		if (attack_plan.Is_Valid_Attack()) {
			if (!attack_plan.all_out) {
				attack_plan.Decide_Battle();
				attack_plan.Apply_Result();
			}
			else {
				//all out
				while (attack_plan.from.nb_armies > 1 && attack_plan.from.owner_name != attack_plan.to.owner_name) {
				attack_plan.Set_Max_NB_Dices();
				attack_plan.Decide_Battle();
				attack_plan.Apply_Result();
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
        return  response;
    }
	
	/**
	 * this method handles the player interaction to be used in Fortification phase
	 * @param from_territory source territory of fortification
	 * @param to_territory	destination of fortification
	 * @param nb_armies number of armies to fortify
	 * @return true if valid, false otherwise
	 */
	public Message_Handler Fortify(String from_territory, String to_territory, int nb_armies) {
		is_conquerer = false;
		//Test if any units to fortify
        if (Has_Extra_Army_To_Move()) {
    		if (!Move_Army(from_territory, to_territory, nb_armies))
    			return new Message_Handler(false, "Error: The territories are not connected, are invalid.");                 
        } 
        else {
    		return new Message_Handler(false, "Error: you have not left ample units.");
        }
        return new Message_Handler(true);
	}	
	
    
    /**
     * Adds a random card to the player cards if at least got a new territory in attack phase
     */
    public void Add_Card() {
    	if (deserve_card) {
			cards.Add_A_Random_Card();
			deserve_card = false;
		}
    }
    
    /**
     * assign one army to each territory of a player at the startup phase
     * @return true if successful, false if not
      */
	public boolean Assign_Min_Army_To_Territories() {
		if (reinforcements >= owned_territories.size()) {
			for(Territory t: owned_territories.values())
	    		t.nb_armies = 1;
	        reinforcements -= owned_territories.size();
	        return true;
        }
		return false;
	}
	
	
	/** 
	 * Adds an army to a territory during the setup and reinforcement phase
	 * @param String the name of the territory to add the army
	 * @return A boolean corresponding to if the army placement is valid
	 */	
	public Boolean Add_Army_To_Territory(String territory_key, int nb_new_armies ){	
		territory_key = territory_key.trim().toLowerCase();
		if(owned_territories.containsKey(territory_key) && this.reinforcements >= nb_new_armies){		
			this.owned_territories.get(territory_key).nb_armies += nb_new_armies; 			
			this.reinforcements -= nb_new_armies; 			
			return true;
		} 	
		else{
			return false; 
		}
	}	
	
	/**
	 * this methods call Add_Army_To_Territory
	 * @param territory_key string of the territory 
	 * @return true if successful, false if not
	 */
	public Boolean Add_Army_To_Territory(String territory_key ){		
		return Add_Army_To_Territory(territory_key, 1);
	}	
	
	
    /** 
	 * Calculates the number of resulting reinforcements based on the number of owned territories 
	 */	
	public void Set_Number_Territory_Reinforcements(){		
		 this.reinforcements = 3 + this.owned_territories.size() / 3; 	
		 //continents
	}
	
	/** 
	 * Moves a number of armies between connected territories during the fortification phase
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
				from.nb_armies -= number_of_units;
				to.nb_armies += number_of_units;
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
	 * method for calculating total number of armies for the player
	 * @return Total_Number_of_Armies
	 */
	public int Total_Number_of_Armies() {
		int total_Number_Of_Armies = 0;
		for (Territory t : owned_territories.values())
			total_Number_Of_Armies += t.nb_armies;
		return total_Number_Of_Armies;
	}
	
	/** 
	 * Removes a territory to the list of owned territories of a player
	 * @param Territory The territory being removed from the player
	 * @return A boolean corresponding to if the territory was removed successfully
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
    
    
    public Message_Handler AI_Attack(State_Game current_state) {
    	
    	// calculate the attack model to pass to rest of function
    	 attack_plan = new Attack_Model(); 
    	
		switch(player_strategy){
		case AGGRESSIVE: 
			
			break; 
		case BENEVOLENT: 
			break; 
		case RANDOM:
			break; 
		case CHEATER: 
			break; 
	
		
		}
		
		 Message_Handler response = new Message_Handler(true);
			if (attack_plan.Is_Valid_Attack()) {
				if (!attack_plan.all_out) {
					attack_plan.Decide_Battle();
					attack_plan.Apply_Result();
				}
				else {
					//all out
					while (attack_plan.from.nb_armies > 1 && attack_plan.from.owner_name != attack_plan.to.owner_name) {
					attack_plan.Set_Max_NB_Dices();
					attack_plan.Decide_Battle();
					attack_plan.Apply_Result();
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
	        return  response;
    	
    }
    
    
    public void AI_Startup() {
    	
		switch(player_strategy){
		case AGGRESSIVE: 
			
			break; 
		case BENEVOLENT: 
			break; 
		case RANDOM:
			break; 
		case CHEATER: 
			break; 
	
		
		}
    	
    }
    
    public Message_Handler AI_Reinforce( State_Game current_state) {
    	
    	
    	// calculate the reinforcements depending on the strategy 
    	String to_territory = new String(); 
    	Integer nb_armies = new Integer(0); 
    	
		switch(player_strategy){
		case AGGRESSIVE: 
			
			break; 
		case BENEVOLENT: 
			break; 
		case RANDOM:
			break; 
		case CHEATER: 
			break; 
	
		
		}
    	
		if (this.Add_Army_To_Territory(to_territory, nb_armies)) {
			return new Message_Handler(true);
		}
		return new Message_Handler(true, "The territory does not belong to you or your reinforcement is not enough.");
		
    }
    
    
    public Message_Handler AI_Fortify(State_Game current_state) {
    	
    	String from_territory = new String(); 
    	String to_territory = new String(); 
    	Integer nb_armies = new Integer(0);
    	
		switch(player_strategy){
		case AGGRESSIVE: 
			
			break; 
		case BENEVOLENT: 
			break; 
		case RANDOM:
			break; 
		case CHEATER: 
			break; 
	
		
		}
		
		is_conquerer = false;
		//Test if any units to fortify
        if (Has_Extra_Army_To_Move()) {
    		if (!Move_Army(from_territory, to_territory, nb_armies))
    			return new Message_Handler(false, "Error: The territories are not connected, are invalid.");                 
        } 
        else {
    		return new Message_Handler(false, "Error: you have not left ample units.");
        }
        return new Message_Handler(true);
	}	
    	
    
    
    public Message_Handler AI_PostAttack(State_Game current_state) {
    	
    	Integer nb_armies = new Integer(0); // AI sets up the number of armies to move
    	
		switch(player_strategy){
		case AGGRESSIVE: 
			
			break; 
		case BENEVOLENT: 
			break; 
		case RANDOM:
			break; 
		case CHEATER: 
			break; 
	
	
		}
		
		if (this.Move_Army(this.attack_plan.from.name, this.attack_plan.to.name, nb_armies)) {
			return new Message_Handler(true);
		}
		
		
		return new Message_Handler(true, "The AI generated and invalid move");
			
    	
    }
    

	
}
