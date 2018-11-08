package models;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;
import views.Game_View;
import models.Map_Model;
import utilities.Config;

public class Game_Model extends Observable {

	public ArrayList<Player> player_list;
	public Map_Model map;
	public Player current_player;
	public Attack_Model attack_plan;

	public State_Game current_state;
	public String message;

	public boolean isFighting;// ???????????
	public boolean player_flag = true;// ????????????????

	public Game_Model(Map_Model map) {
		this.map = map;
		message = "";
		player_list = new ArrayList<Player>();
		current_state = State_Game.SETUP;
	}

	public Boolean Is_Game_Over() {
		return (Get_Next_Player() == null);
	}

	private Player Get_Next_Player() {
		if (player_list.isEmpty())
			return null;
		if (current_player == null)
			return player_list.get(0);// game is just started

		int cur_player_index = player_list.indexOf(current_player);
		// check players after him/her in the list
		for (int i = cur_player_index+1; i < player_list.size(); i++) {
			if (player_list.get(i).Is_Alive())
				return player_list.get(i);
		}
		// check players before him/her in the list
		for (int i = 0; i < cur_player_index; i++) {
			if (player_list.get(i).Is_Alive())
				return player_list.get(i);
		}
		return null;
	}

	private Player Get_Next_Player_For_Reinforcement() {
		if (player_list.isEmpty())
			return null;
		if (current_player == null)
			return player_list.get(0);// game is just started

		int cur_player_index = player_list.indexOf(current_player);
		// check players after the current player in the list
		for (int i = cur_player_index + 1; i < player_list.size(); i++) {
			if (player_list.get(i).Is_Alive() && player_list.get(i).reinforcements > 0)
				return player_list.get(i);
		}
		// check players before the current player in the list
		for (int i = 0; i < cur_player_index; i++) {
			if (player_list.get(i).Is_Alive() && player_list.get(i).reinforcements > 0)
				return player_list.get(i);
		}
		if (current_player.reinforcements > 0) return current_player;
		return null;
	}

	/**
	 * @returns list of players who are active in the game
	 */
	public ArrayList<Player> Get_Active_Players() {
		ArrayList<Player> result = new ArrayList<Player>();
		for (Player p : player_list) {
			if (p.Is_Alive())
				result.add(p);
		}
		return result;
	}
	
	/**
	 * 
	 * @return number of all players in the game weather are active or dead
	 */
	public int Number_Of_Players() {
		return player_list.size();
	}

	/**
	 * adds a new player to the game checks duplication and maximum number of
	 * players
	 * 
	 * @param new_player
	 * @return
	 */
	public boolean Add_Player(String new_player) {
		if (new_player != "" && this.Number_Of_Players() < Config.max_nb_players) {
			for (Player p : player_list) {
				if (p.name.equalsIgnoreCase(new_player))
					return false;
			}
			player_list.add(new Player(new_player, this));
			return true;

		}
		return false;
	}

	public Player Search_Player(String name) {
		for (Player p : player_list) {
			if (p.name.equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

	/**
	 * 
	 * @return String the number of armies owned by each player
	 */
	public String Armies_Of_Player() {
		StringBuilder sb = new StringBuilder(64);
		for (Player p : player_list) {
			int sum = p.Total_Number_of_Armies_Of_Players();
			sb.append(" Name Of Player: " + p.name + "    " +"Sum of Armies: "+ sum+"    ") ;
			sb.append(System.getProperty("line.separator"));
		}
		
		return sb.toString();

	}

	/**
	 * 
	 * @return name of continent owner
	 */

	public String Continent_Owner() {
		return map.Continent_List();
	}

	/**
	 * 
	 * @return percentage of the map controlled by every player
	 */
	public String Percentage_of_world_Owner() {
		float percentage = 0;
		String name = "";
		List<String> percentage_list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(64);
		float all_territories =(float) map.Number_Of_All_Territories();

		for (Player player : player_list) {
			float player_territories = (float)player.owned_territories.size();
			percentage =(100.0f * player_territories) / all_territories;
			String formattedString = String.format("%.02f", percentage);
			name = '\n' + player.name + ": " + "%" + formattedString;
			percentage_list.add(name);
		}
		for (int i = 0; i < percentage_list.size(); i++) {
	    	sb.append(percentage_list.get(i)+"    ");
	    	sb.append(System.getProperty("line.separator"));
		} 
		
		
		return sb.toString();
	}

	/**
	 * Controls the game logic for the game setup phase
	 * 
	 */
	public boolean Setup() {
		if (Number_Of_Players() < Config.min_nb_players || Number_Of_Players() > Config.max_nb_players || map == null
				|| map.Is_Empty() || (map.Get_Territories().size() < Number_Of_Players()))
			return false;

		// Set order of players
		player_list = Player_List_Randomize();
		current_player = player_list.get(0);

		Assign_Territories();

		// Calculate Reinforcement for each player
		Startup_Reinforcement();
		Update_State(State_Game.STARTUP, "");

		isFighting = true;
		return true;

		// for (Player p : player_list) {
		// if (p.owned_territories != null && p.owned_territories.size() > 0) {
		// Boolean want_to_replace = Boolean.TRUE;
		// while (want_to_replace) {
		// want_to_replace = game_view.Display_Menu_Replace_army(p);
		// String territory_name = game_controller.Get_Replace_To_Territory();
		// Territory territory = map_generator.map.Get_Territory(territory_name);
		// if (territory != null) {
		// territory.nb_armies += game_controller.Get_Replace_Number_Of_Move_Armies();
		// } else {
		// System.out.println("the input territory is invalid");
		// }
		// }
		//
		// }
		// }

	}

	/**
	 * set the current player to the next player if there is another active player
	 * in the game otherwise will return false
	 * 
	 * @param next_player
	 * @return
	 */
	public boolean Change_Player(Player next_player) {
		if (next_player != null && next_player.current_state != State_Player.DEAD) {
			current_player.Update_State(State_Player.WAITING);
			current_player = next_player;
			current_player.Update_State(State_Player.PLAYING);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param territory_name
	 * @param nb_armies
	 */
	public void Reinforcement(String territory_name, int nb_armies) {
		Message_Handler response = current_player.Reinforcement(territory_name, nb_armies);
		State_Game new_state = current_state;
		if (response.ok) {
			if (current_state == State_Game.STARTUP) {
				Player next_player = this.Get_Next_Player_For_Reinforcement();
				if (next_player != null) {
					Change_Player(next_player);
				} else {
					// End of StartUp => game is started
					Change_Player(player_list.get(0));// get the first player to play the game
					new_state = State_Game.REINFORCEMENT;
					current_player.Set_Number_Territory_Reinforcements();
				}
			} else if (current_state == State_Game.REINFORCEMENT) {
				if (current_player.reinforcements == 0) {
					// end of reinforcement
					new_state = State_Game.ATTACKING;
				}
			}
		}
		Update_State(new_state, response.message);
	}

	/**
	 * the attacked player always plays with maximum number of dices
	 * 
	 * @param from_name
	 * @param to_name
	 * @param nb_dice
	 * @param nb_armies
	 */
	public void Attack(String from_name, String to_name, int nb_dice, boolean all_out) {
		Territory from = this.map.Get_Territory(from_name);
		Territory to = this.map.Get_Territory(to_name);
		Player defender = this.Search_Player(to.owner_name);

		this.attack_plan = new Attack_Model(current_player, defender, from, to, nb_dice, all_out);
		Message_Handler response = current_player.Attack(this.attack_plan);
		State_Game new_state = current_state;
		
		if (response.ok) {
			if (this.Get_Next_Player() == null) {
				new_state = State_Game.OVER;
			}else if (current_player.is_conquerer) {
				new_state = State_Game.POST_ATTACK;
				message = "You've conquered "+ attack_plan.to.name +" territoy";
			}else if (!current_player.Has_Extra_Army_To_Move()) {
				current_state = State_Game.FORTIFICATION;//the player can not fortify without army!!! => switch player
				Move_To_Next_Phase();
				return;
			}
		} else {
			message = "Error: please enter valid data";
		}

		Update_State(new_state, message);
	}
	
	public void Post_Attack(int nb_armies) {
		current_player.Move_Army(attack_plan.from.name, attack_plan.to.name, nb_armies);
		State_Game new_state = current_state;
		if (!current_player.Has_Extra_Army_To_Move()) {
			current_state = State_Game.FORTIFICATION;//the player can not fortify without army!!! => switch player
			Move_To_Next_Phase();
			return;
		}
		else
			new_state = State_Game.ATTACKING;
		
		Update_State(new_state, message);
	}

	/**
     * Reposition units for fortification before finishing turn. 
     * @param from_name
     * @param to_name
     * @param nb_armies
     */
    public void Fortify (String from_name, String to_name, int nb_of_armies ) {   

		Message_Handler response = current_player.Fortify(from_name, to_name, nb_of_armies);
		State_Game new_state = current_state;

		if (response.ok) {
			//if (!current_player.Has_Extra_Army_To_Move()) {
				Move_To_Next_Phase();
			//}
		} else {
			message = "Error: please enter valid data";
		}

		Update_State(new_state, message);
	}
    

	public void Move_To_Next_Phase() {
		if (current_state == State_Game.ATTACKING)
			Update_State(State_Game.FORTIFICATION, "");
		else if (current_state == State_Game.FORTIFICATION) {
			Player next_player = this.Get_Next_Player();
			if (next_player != null) {
				current_player = next_player;
				current_player.Set_Number_Territory_Reinforcements(); 
				Update_State(State_Game.REINFORCEMENT, "");
			}
			else
				Update_State(State_Game.OVER, current_player.name + " is the winner");
		}
	}


	/**
	 * Controls the game logic and process flow once the setup is complete and the
	 * game begins
	 */
	public void Startup_Reinforcement() {
		int nb_initial_armies = Get_Number_StartUp_Reinforcements();
		for (Player p : this.player_list) {
			p.reinforcements = nb_initial_armies;
			p.Assign_Min_Army_To_Territories();
		}
	}

	/**
	 * Calculates the number of resulting units to start the game for each player
	 */
	public int Get_Number_StartUp_Reinforcements() {
		int result = 0;
		int nb_starting_territories= this.current_player.owned_territories.size();

		switch (this.Number_Of_Players()) {
		case 2:
            result = nb_starting_territories +4; //40;
            break;
        case 3:
            result = nb_starting_territories +3; //35;
            break;
        case 4:
            result = nb_starting_territories +3; // 30;
            break;
        case 5:
            result = nb_starting_territories +2; //25;
        case 6:
            result = nb_starting_territories +2; //20;
            break;
		}
		return result;
	}

	/**
	 * Sets up the Player list with their name and corresponding player name
	 * 
	 * @param ArrayList<Player>
	 *            the list of players in the game
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
	 * 
	 * @param ArrayList<Player>
	 *            the list of players in the game
	 * @return A randomized order of players. Type: (ArrayList<Player>)
	 */
	private ArrayList<Player> Player_List_Randomize() {
		ArrayList<Player> shuffled_player_list = new ArrayList<Player>(player_list);
		Collections.shuffle(shuffled_player_list);
		return shuffled_player_list;
	}

	/**
	 * Prints the order of the players in the game
	 * 
	 * @param ArrayList<Player>
	 *            the list of players in the game
	 */
	public void Print_Player_List_In_Order() {
		for (int i = 0; i < player_list.size(); i++) {
			System.out.println(i + 1 + ": " + player_list.get(i).name);
		}
	}

	/**
	 * Assigns the game territories acrross the players playing the game during the
	 * setup
	 */
	private void Assign_Territories() {
		HashMap<String, Territory> game_territories = map.Get_Territories();


		int index = 0;
		Iterator it = game_territories.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry entry = (Map.Entry) it.next();
			Territory territory = (Territory) entry.getValue();

			if (index < (game_territories.size())) {// - (game_territories.size() % Number_Of_Players())
				player_list.get((index % Number_Of_Players())).Add_Territory(territory);
				territory.owner_name = player_list.get(index % Number_Of_Players()).name;
			}
			index++;
		}
	}

	/**
	 * Prints the current game phase of a player at the start of the phase
	 * 
	 * @return A boolean value corresponding to whether more than one player are
	 *         still playing the game.
	 */
	public Boolean isStillFighting() {
		return isFighting;
	}

	public void Update_State(State_Game new_state, String new_message) {
		message = new_message;
		current_state = new_state;
		setChanged();
		notifyObservers(this);
	}

}
