package models;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import utilities.Config;

/**
 *player collection class to implement all method related to the player
 */
public class Player_Collection implements java.io.Serializable {
	public ArrayList<Player> player_list;
	private Color[] colors = { Color.blue, Color.green, Color.red, Color.pink, Color.yellow, Color.orange };
	private int next_color_index;

	/**
	 * constructor to initialize player list
	 */
	public Player_Collection() {
		player_list = new ArrayList<Player>();
		next_color_index = 0;
	}

	/**
	 * method for choosing first player from the list
	 * @return player
	 */
	public Player First() {
		return player_list.get(0);
	}

	/**
	 * method for getting next player
	 * @return Player Object
	 */
	public Player Get_Next_Player(Player current_player) {
		if (player_list.isEmpty())
			return null;
		if (current_player == null)
			return player_list.get(0);// game is just started

		int cur_player_index = player_list.indexOf(current_player);
		// check players after him/her in the list
		for (int i = cur_player_index + 1; i < player_list.size(); i++) {
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

	/**
	 * method for getting next player for fortification
	 * @return player object
	 */
	public Player Get_Next_Player_For_Reinforcement(Player current_player) {
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
		if (current_player.reinforcements > 0)
			return current_player;
		return null;
	}

	/**
	 * method for getting active player
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
	 * method for getting number of player
	 * @return number of all players in the game weather are active or dead
	 */
	public int Number_Of_Players() {
		return player_list.size();
	}

	/**
	 * adds a new player to the game checks duplication and maximum number of
	 * players
	 * @param new_player
	 * @return boolean
	 */
	public boolean Add_Player(String new_player, State_Player_Strategy behaviour, Game_Model game) {
		if (new_player != "" && this.Number_Of_Players() < Config.max_nb_players) {
			for (Player p : player_list) {
				if (p.name.equalsIgnoreCase(new_player))
					return false;
			}
			player_list.add(new Player(new_player, colors[next_color_index], behaviour, game));
			next_color_index++;
			return true;
		}
		return false;
	}

	/**
	 * method for searching player
	 * @param name
	 * @return player object
	 */
	public Player Search_Player(String name) {
		for (Player p : player_list) {
			if (p.name.equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

	/**
	 * Sets up the Player list with their name and corresponding player name
	 * @param ArrayList<Player
	 *            the list of players in the game
	 */
	public boolean Player_List_Setup(ArrayList<AbstractMap.SimpleEntry<String, State_Player_Strategy>> players_list,
			Game_Model game) {
		boolean error = false;
		for (AbstractMap.SimpleEntry<String, State_Player_Strategy> p : players_list) {
			boolean result = this.Add_Player(p.getKey(), p.getValue(), game);

			error = error && result;
		}
		return !error;
	}

	/**
	 * Randomizes the player's turn order upon the setup of the game.
	 * @param ArrayList<Player>
	 *            the list of players in the game
	 * @return A randomized order of players. Type: (ArrayList<Player>)
	 */
	public void Player_List_Randomize() {
		ArrayList<Player> shuffled_player_list = new ArrayList<Player>(player_list);
		Collections.shuffle(shuffled_player_list);
		this.player_list = shuffled_player_list;
	}

	/**
	 * Prints the order of the players in the game
	 * @param ArrayList<Player>
	 *            the list of players in the game
	 */
	public void Print_Player_List_In_Order() {
		for (int i = 0; i < player_list.size(); i++) {
			System.out.println(i + 1 + ": " + player_list.get(i).name);
		}
	}
}
