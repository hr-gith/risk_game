package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Observable;
import java.util.Scanner;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;
import views.Game_View;
import models.Map_Model;
import utilities.Config;

public class Game_Model extends Observable{

    public ArrayList<Player> player_list;
    public Map_Model map;
    public Player current_player;
    public State_Game current_state; 
	public String current_action;
	
    public boolean isFighting;//???????????    
    public boolean player_flag = true;//????????????????
    
    public Game_Model(Map_Model map) {
    	this.map = map;
    	current_action = "";
    	player_list = new ArrayList<Player>();
    }
    
    public Boolean Is_Game_Over() {    	
    	return (Get_Next_Player() == null);
    }
    
    private Player Get_Next_Player() {
    	if (player_list.isEmpty()) return null;
    	if (current_player == null) return player_list.get(0);//game is just started
    	
    	int cur_player_index = player_list.indexOf(current_player);
    	//check players after him/her in the list
    	for(int i = cur_player_index; i < player_list.size(); i++) {
    		if (player_list.get(i).Is_Alive())
    			return player_list.get(i);
    	}
    	//check players before him/her in the list
    	for(int i = 0; i < cur_player_index; i++) {
    		if (player_list.get(i).Is_Alive())
    			return player_list.get(i);
    	}
    	return null;
    }
    
    /**
     * @returns list of players who are active in the game
     */
    public ArrayList<Player> Get_Active_Players(){
    	ArrayList<Player> result = new ArrayList<Player>();
    	for (Player p: player_list) {
    		if (p.Is_Alive())
    			result.add(p);
    	}
    	return result;
    }
    
    /**
     * changes the current player to the next one based on the order of players in the list
     * if there is another active player
     * @return true if it is successfully changed
     */
    public boolean Change_Player() {
    	Player next = Get_Next_Player();
    	if (next != null) {
    		current_player.current_state = State_Player.WAITING;
    		current_player = next;
    		current_player.current_state = State_Player.PLAYING;
    		return true;
    	}
    	return false;
    }

    /**
     * 
     * @return number of all players in the game weather are active or dead
     */
    public int Number_Of_Players() {
    	return player_list.size();
    }

    /**
     * adds a new player to the game
     * checks duplication and maximum number of players
     * @param new_player
     * @return
     */
    public boolean Add_Player(String new_player) {
    	if (new_player != "" && this.Number_Of_Players() < Config.max_nb_players) {
    		for(Player p: player_list){
    			if (p.name.equalsIgnoreCase(new_player))
    				return false;			
    		}
    		player_list.add(new Player(new_player));
    		return true;
    		
    	}
    	return false;
    }

	/** 
	 * Controls the game logic for the game setup phase
	 * 
	 */    
    public boolean Setup() {
    	if (Number_Of_Players() < Config.min_nb_players || Number_Of_Players() > Config.max_nb_players ||
    			map == null || map.Is_Empty() || (map.Get_Territories().size()< Number_Of_Players())) 
    		return false;
    	
    	Update_Current_State(State_Game.STARTUP);
        
    	current_action = "Set order of players";
        player_list = Player_List_Randomize();
        current_player = player_list.get(0);
        setChanged();
        notifyObservers(this);
        
        current_action = "Assign Territories";
        Assign_Territories();
        setChanged();
        notifyObservers(this);
        
        current_action = "Calculate Reinforcement for each player ";
        Startup_Reinforcement();
        setChanged();
        notifyObservers(this);
       
        isFighting = true;
        return true;

//        for (Player p : player_list) {
//            if (p.owned_territories != null && p.owned_territories.size() > 0) {
//                Boolean want_to_replace = Boolean.TRUE;
//                while (want_to_replace) {
//                    want_to_replace = game_view.Display_Menu_Replace_army(p);
//                    String territory_name = game_controller.Get_Replace_To_Territory();
//                    Territory territory = map_generator.map.Get_Territory(territory_name);
//                    if (territory != null) {
//                        territory.nb_armies += game_controller.Get_Replace_Number_Of_Move_Armies();
//                    } else {
//                        System.out.println("the input territory is invalid");
//                    }
//                }
//
//            }
//        }


    }    
    
    public void Startup_Reinforcement() {
		int nb_initial_armies = Get_Number_StartUp_Reinforcements();
        current_state = State_Game.STARTUP;
        for(Player p: this.player_list) {
        	p.reinforcements = nb_initial_armies;
        	p.Assign_Min_Army_To_Territories();
        }		
	}
    
    /** 
	 * Calculates the number of resulting units to start the game for each player
	 */	
	public int Get_Number_StartUp_Reinforcements(){		
		int result = 0;

        switch (this.Number_Of_Players())
        {
            case 2:
                result = 40;
                break;
            case 3:
                result = 35;
                break;
            case 4:
                result = 30;
                break;
            case 5:
                result = 25;
                break;
            case 6:
                result = 20;
                break;
        }
        return result;
	}
	
	/** 
	 * Sets up the Player list with their name and corresponding player name
	 * @param ArrayList<Player> the list of players in the game
	 */
    
    public boolean Player_List_Setup(ArrayList<String> players_name_list) {
    	boolean error = false;
        for (String s : players_name_list) {
        	boolean result = this.Add_Player(s);
        	error = error && result;
        }
        return !error;
    }
    
	/** 
	 * Randomizes the player's turn order upon the setup of the game. 
	 * @param ArrayList<Player> the list of players in the game
	 * @return A randomized order of players. Type: (ArrayList<Player>) 
	 */
    private ArrayList<Player> Player_List_Randomize() {
        ArrayList<Player> shuffled_player_list = new ArrayList<Player>(player_list);
        Collections.shuffle(shuffled_player_list);
        return shuffled_player_list;
    }

	/** 
	 * Prints the order of the players in the game
	 * @param ArrayList<Player> the list of players in the game
	 */    
    public void Print_Player_List_In_Order() {
        for (int i = 0; i < player_list.size(); i++) {
            System.out.println(i+1 + ": " + player_list.get(i).name);
        }
    }
    
	/** 
	 * Assigns the game territories acrross the players playing the game during the setup
	 */
    private void Assign_Territories() {
    	HashMap<String,Territory> game_territories = map.Get_Territories();

//	List keys = new ArrayList(game_territories.keySet());
//	Collections.shuffle(keys);
//
//	//add neutrals if needed 
//	
//	int index = 0; 
//	
//	
//	for (Object obj : keys) {
//	    // Access keys/values in a random order
//		
//		if(index<(Game_Model.number_of_players - (game_territories.size()%Game_Model.number_of_players))){
//			active_player_list.get((index%Game_Model.number_of_players)).Add_Territory(game_territories.get(obj));	
//			game_territories.get(obj).owner_id = index; 
//		}
//		
//		index++;
//		 
//	}

        int index = 0;
        Iterator it = game_territories.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry entry = (Map.Entry) it.next();
            Territory territory = (Territory) entry.getValue();

            if (index < (game_territories.size() - (game_territories.size() % Number_Of_Players()))) {
                player_list.get((index % Number_Of_Players())).Add_Territory(territory);
                territory.owner_name = player_list.get(index % Number_Of_Players()).name;
            }
            index++;
        }
    }   
	

	/** 
	 * Prints the current game phase of a player at the start of the phase
	 * @param Player The current game player
	 * @param String The current game state
	 */

    public static void Print_State_Once(Player player, String input) {
        if (player.current_state_game != player.old_state_game) {
            System.out.println(input);
        }
    }

	/** 
	 * Prints the current game phase of a player at the start of the phase
	 * @return A boolean value corresponding to whether more than one player are still playing the game.
	 */
    public Boolean isStillFighting() {
        return isFighting;
    }
    
    public void Update_Current_State(State_Game new_state) {
    	current_state = new_state;
    	setChanged();
        notifyObservers(this);
    }


}
