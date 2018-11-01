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
		game_view = new Game_View(this);
		game.addObserver(game_view);
	}
	
	public void Start() {
		ArrayList<String> players_name = game_view.Display_Menu_Players();
        if (game.Player_List_Setup(players_name)) {
        	game.Setup();
        	/*if (game.Setup()) {
	        	this.Start_Up_Reinforcement();
	        	while(!game.Is_Game_Over()) {
	        		game.Play();
	        	}
	        	game_view.Display_Winner(game.current_player.name);
        	}
        	else {
        		//TODO: display error message
        	}*/
        }

	}
	
	public void Reinforcement(String territory_name, int nb_armies) {
		game.Reinforcement(territory_name, nb_armies);
	}	
	
	public void Fortification (String from, String to, int nb_armies) {
		game.current_player.Move_Army(from, to, nb_armies);
	}
	
	
}
