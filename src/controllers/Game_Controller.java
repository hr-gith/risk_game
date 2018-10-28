package controllers;

import models.Territory;

import java.util.ArrayList;

/**
 * @author e_narang in this class we get number of players,name of players, move
 *         armies and place armies.
 * @version 1.0
 */
public class Game_Controller {
	/*
	 * private static final int Number_Army_For_Two_Players = 40; private static
	 * final int Number_Army_For_Three_Players = 35; private static final int
	 * Number_Army_For_Four_Players = 30; private static final int
	 * Number_Army_For_Five_Players = 25; private static final int
	 * Number_Army_For_Six_Players = 20;
	 */

	private static int number_of_players;
	private static ArrayList<String> name_of_players;
	private static String from_Territory;
	private static String to_Territory;
	private static int number_of_move_armies;
	private static String reinforcement_to_Territory;
	private static int reinforcement_number_of_move_armies;
	private static String replace_to_Territory;
	private static int replace_number_of_move_armies;

	/**
	 * @param name_of_players
	 *            this is List of players name this method gets name of players
	 */
	public void Set_Name_Players(ArrayList<String> name_of_players) {
		this.name_of_players = name_of_players;
	}

	/**
	 * this is a get list of players name
	 *
	 * @return this method return name of players
	 */

	public ArrayList<String> Get_Name_Players() {

		return name_of_players;
	}

	/**
	 * according to the number of players it assigned number of armies
	 *
	 * @param number_of_players
	 *            it initialized number of armies
	 */

	public void Set_Number_Players(int number_of_players) {
		/*
		 * switch (number_of_players) { case (2): initial_number_of_armies =
		 * Number_Army_For_Two_Players; break; case (3): initial_number_of_armies =
		 * Number_Army_For_Three_Players; break; case (4): initial_number_of_armies =
		 * Number_Army_For_Four_Players; break; case (5): initial_number_of_armies =
		 * Number_Army_For_Five_Players; break; case (6): initial_number_of_armies =
		 * Number_Army_For_Six_Players; }
		 */
		this.number_of_players = number_of_players;

	}

	/**
	 * this method gets number of player for move army state
	 *
	 * @return number of player
	 */

	public int Get_Num_Players() {
		return number_of_players;
	}

	/**
	 * this method gets the name of territory for moving armies from this territory
	 *
	 * @return from territory
	 */

	public String Get_From_Territory() {
		return from_Territory;
	}

	/**
	 * this method sets the name of territory to show that armies move from this
	 * territory
	 *
	 * @param from_country
	 *            String the name of country
	 */
	public void Set_From_Territory(String from_country) {
		this.from_Territory = from_country;
	}

	/**
	 * this method gets the name of territory to show that armies move to this
	 * territory
	 *
	 * @return name of territory
	 */
	public String Get_To_Territory() {
		return to_Territory;
	}

	/**
	 * this method sets the name of territory to show that armies move to this
	 * territory
	 *
	 * @param to_country
	 *            name of country
	 */
	public void Set_To_Territory(String to_country) {
		this.to_Territory = to_country;
	}

	/**
	 * get number of armies that player wants to move them between territories
	 *
	 * @return number of armies that player wants to move
	 */

	public int Get_Number_of_move_armies() {
		return number_of_move_armies;
	}

	/**
	 * set number of armies that player wants to move them between territories
	 *
	 * @param number_of_move_armies
	 *            integer
	 */
	public void Set_Number_of_move_armies(int number_of_move_armies) {
		this.number_of_move_armies = number_of_move_armies;
	}

	/**
	 *
	 * @return number of place army
	 */
	public int Get_Reinforcement_Number_of_move_armies() {
		return reinforcement_number_of_move_armies;
	}
	/**
	 *
	 * @param reinforcement_number_of_move_armies
	 */

	public void Set_Reinforcement_Number_of_move_armies(int reinforcement_number_of_move_armies) {
		this.reinforcement_number_of_move_armies = reinforcement_number_of_move_armies;
	}
/**
 * get name of territory which player want to put the army on it
 * @return name of territory
 */
	public String Get_Reinforcement_To_Territory() {
		return reinforcement_to_Territory;
	}
/**
 * set name of territory which player want to put the army on it
 * @param reinforcement_to_Territory
 */
	public void Set_Reinforcement_To_Territory(String reinforcement_to_Territory) {
		this.reinforcement_to_Territory = reinforcement_to_Territory;
	}

	public String Get_Replace_To_Territory() {
		return replace_to_Territory;
	}

	public void Set_Replace_To_Territory(String replace_to_Territory) {
		this.replace_to_Territory = replace_to_Territory;
	}

	/**
	 * get number of armies in place army
	 * @return number of armies to replace in territory
	 */

	public int Get_Replace_Number_Of_Move_Armies() {
		return replace_number_of_move_armies;
	}

	/**
	 * set number of armies in place army
	 * @param replace_number_of_move_armies
	 */

	public void Set_Replace_Number_Of_Move_Armies(int replace_number_of_move_armies) {
		this.replace_number_of_move_armies = replace_number_of_move_armies;
	}
}
