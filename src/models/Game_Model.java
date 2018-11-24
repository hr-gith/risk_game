package models;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import models.Map_Model;
import utilities.Config;

/**
 * This is the main model class, it goes through phases of the game
 */
public class Game_Model extends Observable {

	public Map_Model map;
	public Attack_Model attack_plan;
	public Player_Collection players;
	public Player current_player;

	public State_Game current_state;
	public String message;

	public Game_Model(Map_Model map) {
		this.map = map;
		message = "";
		players = new Player_Collection();//ArrayList<Player>();
		current_state = State_Game.SETUP;
	}

	/**
	 * checking if game is over
	 * 
	 * @return boolean
	 */
	public Boolean Is_Game_Over() {
		return (players.Get_Next_Player(current_player) == null);
	}	

	/**
	 * Controls the game logic for the game setup phase
	 */
	public boolean Setup(ArrayList<AbstractMap.SimpleEntry<String,State_Player_Strategy>> player_list) {
		if (!players.Player_List_Setup(player_list, this)) return false;
		return this.Setup(players);		
	}
	
	/**
	 * Controls the game logic for the game setup phase
	 */
	public boolean Setup(Player_Collection player_list) {
		if (player_list == null) return false;
		if (players.Number_Of_Players() < Config.min_nb_players || players.Number_Of_Players() > Config.max_nb_players || map == null
				|| map.Is_Empty() || (map.Get_Territories().size() < players.Number_Of_Players()))
			return false;

		// Set order of players
		players.Player_List_Randomize();
		current_player = players.Get_Next_Player(current_player);
		Assign_Territories();

		// Calculate Reinforcement for each player
		Startup_Reinforcement();
		Update_State(State_Game.STARTUP, "");

		return true;
	}


	/**
	 * set the current player to the next player if there is another active player
	 * in the game otherwise will return false
	 * 
	 * @param next_player
	 * @return boolean
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
	 * checks if the player can not attack anymore
	 * if it is end of the attack gives a card to player in case of conquerer
	 */
	public void Can_Attack() {
		State_Game new_state = current_state;
		if (!current_player.Has_Extra_Army_To_Move()) {
			current_player.Add_Card();
			current_state = State_Game.FORTIFICATION;// the player can not fortify without army!!! => switch player
			Move_To_Next_Phase();
			return;
		} else
			new_state = State_Game.ATTACKING;

		Update_State(new_state, message);
	}	
	



	/**
	 * method for moving to the next phase
	 */
	public void Move_To_Next_Phase() {
		if (current_state == State_Game.ATTACKING) {
			current_player.Add_Card();
			Update_State(State_Game.FORTIFICATION, "");
		}
		else if (current_state == State_Game.FORTIFICATION) {
			Player next_player = players.Get_Next_Player(current_player);
			if (next_player != null) {
				current_player = next_player;
				current_player.Set_Number_Territory_Reinforcements();
				Update_State(State_Game.REINFORCEMENT, "");
			} else
				Update_State(State_Game.OVER, current_player.name + " is the winner");
		}
	}

	/**
	 * Controls the game logic and process flow once the setup is complete and the
	 * game begins
	 */
	public void Startup_Reinforcement() {
		int nb_initial_armies = Get_Number_StartUp_Reinforcements();
		for (Player p : players.player_list) {
			p.reinforcements = nb_initial_armies;
			p.Assign_Min_Army_To_Territories();
		}
	}

	/**
	 * Calculates the number of resulting units to start the game for each player
	 */
	public int Get_Number_StartUp_Reinforcements() {
		int result = 0;
		int nb_starting_territories = this.current_player.owned_territories.size();

		switch (players.Number_Of_Players()) {
		case 2:
			result = nb_starting_territories + 4; // 40;
			break;
		case 3:
			result = nb_starting_territories + 3; // 35;
			break;
		case 4:
			result = nb_starting_territories + 3; // 30;
			break;
		case 5:
			result = nb_starting_territories + 2; // 25;
		case 6:
			result = nb_starting_territories + 2; // 20;
			break;
		}
		return result;
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
				players.player_list.get((index % players.Number_Of_Players())).Add_Territory(territory);
				territory.owner_name = players.player_list.get(index % players.Number_Of_Players()).name;
			}
			index++;
		}
	}
	

	/**
	 * this method update states of the game
	 * 
	 * @param new_state
	 * @param new_message
	 */
	public void Update_State(State_Game new_state, String new_message) {
		message = new_message;
		current_state = new_state;
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * method for getting number of player armies
	 * 
	 * @return String the number of armies owned by each player
	 */
	public String Armies_Of_Players_To_String() {
		StringBuilder sb = new StringBuilder(256);
		for (Player p : players.player_list) {
			int sum = p.Total_Number_of_Armies();
			sb.append(p.name + "    " + " Armies: " + sum + "        ");
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	/**
	 * method for getting continent owner if all territories belong to one player
	 * 
	 * @return name of continent owner
	 */

	public String Continent_Owner_To_String() {
		return map.Continent_List_To_String();
	}

	/**
	 * method for calculating percentage of world that belong to each player
	 * 
	 * @return percentage of the map controlled by every player
	 */
	public String Percentage_Of_World_Owner_To_String() {
		float percentage = 0;
		String name = "";
		List<String> percentage_list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(256);
		float all_territories = (float) map.Number_Of_All_Territories();

		for (Player player : players.player_list) {
			float player_territories = (float) player.owned_territories.size();
			percentage = (100.0f * player_territories) / all_territories;
			String formattedString = String.format("%.02f", percentage);
			name = '\n' + player.name + ": " + "%" + formattedString + "     ";
			percentage_list.add(name);
		}
		for (int i = 0; i < percentage_list.size(); i++) {
			sb.append(percentage_list.get(i) + "\n");
			sb.append(System.getProperty("line.separator"));
		}

		return sb.toString();
	}

	

	public void Attack() {
		
		// from to defender 

	

//		this.attack_plan = new Attack_Model(current_player, defender, from, to, nb_dice, all_out);
		Message_Handler response = current_player.Attack();
		State_Game new_state = current_state;

		if (response.ok) {
			if (this.Is_Game_Over()) {
				new_state = State_Game.OVER;
			} else if (current_player.is_conquerer) {
				new_state = State_Game.POST_ATTACK;
				message = "You've conquered " + attack_plan.to.name + " territoy";
			} else {
				Can_Attack();
				return;
			}
		} else {
			message = "Error: please enter valid data";
		}

		Update_State(new_state, message);
	}
	
	
	
	

	public void Reinforce() {
		
		
		Message_Handler response = current_player.Reinforce();
		State_Game new_state = current_state;

		
		if (response.ok) {
			if (current_state == State_Game.STARTUP) {
				Player next_player = players.Get_Next_Player_For_Reinforcement(current_player);
				if (next_player != null) {
					Change_Player(next_player);
				} else {
					// End of StartUp => game is started
					Change_Player(players.First());// get the first player to play the game
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

	
	public void Fortify() {

		Message_Handler response = current_player.Fortify();
		State_Game new_state = current_state;

		if (response.ok) {
			// Move_To_Next_Phase();
		} else {
			message = "Error: please enter valid data";
		}
		Update_State(new_state, message);
	}
	
	
	
	public void Post_Attack() {
		
		Message_Handler response = current_player.PostAttack(); 
		current_player.is_conquerer = false;
		Can_Attack();
	}
	
}



//
//
///**
// * 
// * @param territory_name
// * @param nb_armies
// */
//public void Reinforce(String territory_name, int nb_armies) {
//	Message_Handler response = current_player.Reinforce(territory_name, nb_armies);
//	State_Game new_state = current_state;
//	if (response.ok) {
//		if (current_state == State_Game.STARTUP) {
//			Player next_player = players.Get_Next_Player_For_Reinforcement(current_player);
//			if (next_player != null) {
//				Change_Player(next_player);
//			} else {
//				// End of StartUp => game is started
//				Change_Player(players.First());// get the first player to play the game
//				new_state = State_Game.REINFORCEMENT;
//				current_player.Set_Number_Territory_Reinforcements();
//			}
//		} else if (current_state == State_Game.REINFORCEMENT) {
//			if (current_player.reinforcements == 0) {
//				// end of reinforcement
//				new_state = State_Game.ATTACKING;
//			}
//		}
//	}
//	Update_State(new_state, response.message);
//}
//
///**
// * the attacked player always plays with maximum number of dices
// * 
// * @param from_name
// * @param to_name
// * @param nb_dice
// * @param nb_armies
// */
//public void Attack(String from_name, String to_name, int nb_dice, boolean all_out) {
//	Territory from = this.map.Get_Territory(from_name);
//	Territory to = this.map.Get_Territory(to_name);
//	Player defender = players.Search_Player(to.owner_name);
//
//	this.attack_plan = new Attack_Model(current_player, defender, from, to, nb_dice, all_out);
//	Message_Handler response = current_player.Attack(this.attack_plan);
//	State_Game new_state = current_state;
//
//	if (response.ok) {
//		if (this.Is_Game_Over()) {
//			new_state = State_Game.OVER;
//		} else if (current_player.is_conquerer) {
//			new_state = State_Game.POST_ATTACK;
//			message = "You've conquered " + attack_plan.to.name + " territoy";
//		} else {
//			Can_Attack();
//			return;
//		}
//	} else {
//		message = "Error: please enter valid data";
//	}
//
//	Update_State(new_state, message);
//}
//
///**
// * Reposition units for fortification before finishing turn.
// * 
// * @param from_name
// * @param to_name
// * @param nb_armies
// */
//public void Fortify(String from_name, String to_name, int nb_of_armies) {
//
//	Message_Handler response = current_player.Fortify(from_name, to_name, nb_of_armies);
//	State_Game new_state = current_state;
//
//	if (response.ok) {
//		// Move_To_Next_Phase();
//	} else {
//		message = "Error: please enter valid data";
//	}
//	Update_State(new_state, message);
//}
//
//
///**
// * This method move armies from attacking territory to defeated territory
// * 
// * @param nb_armies
// *            number of armies to be moved, defined by attacking player
// */
//public void Post_Attack(int nb_armies) {
//	current_player.Move_Army(attack_plan.from.name, attack_plan.to.name, nb_armies);
//	current_player.is_conquerer = false;
//	Can_Attack();
//}
