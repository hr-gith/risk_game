package controllers;

import models.Game_Model;
import models.Map_Model;
import models.Player;
import models.State_Game;
import views.Game_View;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * @author e_narang in this class we get number of players,name of players, move
 *         armies and place armies.
 * @version 1.0
 */
public class Game_Controller {
	
	public Game_Model game;
	public Game_View game_view;
	
	public Game_Controller(Map_Model map) {
		game = new Game_Model(map);
		game_view = new Game_View();
	}
	
	public void Start() {
		ArrayList<String> players_name = game_view.Display_Menu_Players();
        if (game.Player_List_Setup(players_name)) {
        	if (game.Setup()) {
	        	this.Start_Up_Reinforcement();
	        	while(!game.Is_Game_Over()) {
	        		game.Play();
	        	}
	        	game_view.Display_Winner(game.current_player.name);
        	}
        	else {
        		//TODO: display error message
        	}
        }

	}
	
	public void Start_Up_Reinforcement() {
		int count = 0;
    	int nb_toatl_reinforcements = 0;
    	for (Player p: game.player_list) {
    		nb_toatl_reinforcements += p.reinforcements;
    	}
    	while (count < nb_toatl_reinforcements) {
    		for (Player p: game.player_list) {
        		//TODO: add loop in case of failure while adding army to the territory
    			AbstractMap.SimpleEntry<String, Integer> result =  game_view.Display_Menu_Reinforcements(p);    	
    	        if (p.Add_Army_To_Territory(result.getKey(), result.getValue()))
    	        	count += result.getValue();
    		}
    	}
    	for (Player p: game.player_list) {
    		p.current_state_game = State_Game.REINFORCEMENT;    	
    	}
    	game.Update_Current_State(State_Game.REINFORCEMENT);
	}
	
	
	public void Fortification (String from, String to, int nb_armies) {
		game.current_player.Move_Army(from, to, nb_armies);
	}	
}
