package controllers;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import models.Game_Model;
import models.Map_Helper;
import models.Map_Model;
import models.Player;
import models.Player_Collection;
import models.State_Game;
import models.State_Player_Strategy;
import views.Card_View;
import views.Console_View;
import views.Game_View;
import views.Map_View;
import views.Phase_View;
import views.Players_World_Domination_View;
import views.Tournament_View;

/**
 * Tournament controller is a class for starting and controlling tournament
 * phase
 */
public class Tournament_Controller {
	private Set<Map_Model> maps;
	public ArrayList<AbstractMap.SimpleEntry<String, State_Player_Strategy>> player_list;
	public int nb_game;
	public int max_nb_turn;
	public Game_Model game;
	public Map_Helper map_helper;
	public Game_View game_view;
	public Tournament_View tournament_view;
	public Map_View map_view;
	public Phase_View phase_view;
	public Players_World_Domination_View players_world_domination_view;
	public Card_View card_view;

	/**
	 * constructor of tournament controller initial game and map and player list and
	 * map and tournament view
	 */
	public Tournament_Controller() {
		game = Game_Model.Get_Game();
		maps = new HashSet<Map_Model>();
		player_list = new ArrayList<AbstractMap.SimpleEntry<String, State_Player_Strategy>>();
		map_helper = new Map_Helper();
		// set views
		tournament_view = new Tournament_View(this);
	}

	/**
	 * method for getting maps from tournament view and add
	 * @param map_name taking map name as a input parameters
	 * @return boolean
	 */
	public boolean Add_Map(String map_name) {
		if (maps.size() < 5) {
			String path = ".\\src\\" + map_name + ".map";
			Map_Model new_map = map_helper.Import_Map(path);
			// TODO: Check if does not add to the previous map because of singleton map
			if (new_map == null)
				return false;
			maps.add(new_map);
			return true;
		}
		return false;
	}

	/**
	 * method for adding player depend on user input from tournament view
	 * @param name setting name for each player
	 * @param strategy setting strategy for each player
	 * @return boolean
	 */
	public boolean Add_Player(String name, State_Player_Strategy strategy) {
		if (player_list.size() < 6) {
			player_list.add(new AbstractMap.SimpleEntry<String, State_Player_Strategy>(name, strategy));
			return true;
		}
		return false;
	}

	/**
	 * method for calling setup tournament method from tournament view
	 */
	public void Setup() {
		this.tournament_view.Setup_Tournament();
	}

	/**
	 * method for starting tournament
	 * @return boolean
	 * @throws InterruptedException
	 */
	public boolean Start() throws InterruptedException {
		if (maps.size() >= 1 && maps.size() <= 5 && player_list.size() >= 2 && player_list.size() <= 4 && nb_game >= 1
				&& nb_game <= 5 && max_nb_turn >= 10 && max_nb_turn <= 50) {
			String res_str = "\n\n*********************************************************************";
			res_str += "\n\t";
			for (int i = 0; i < this.nb_game; i++)
				res_str += ("  Game" + (i + 1) + " ");

			for (Map_Model map : maps) {
				res_str += ("\n" + map.name + "\t");
				for (int i = 0; i < this.nb_game; i++) {
					Game_Controller game_ctrl = new Game_Controller(map);
					game_ctrl.game.max_nb_turns = this.max_nb_turn;
					game_ctrl.Start(player_list);
					if (game_ctrl.game.current_state == State_Game.DRAW)
						res_str += ("  Draw  ");
					else
						res_str += ("  " + game_ctrl.game.current_player.name + "  ");
					System.out.println("End of game " + (i + 1) + " for " + map.name + " map");
					Thread.sleep(500);
				}
			}
			res_str += "\n*********************************************************************";
			System.out.println(res_str);

			return true;
		} else
			return false;
	}

}
