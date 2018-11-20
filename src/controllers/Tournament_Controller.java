package controllers;

import java.util.HashSet;
import java.util.Set;

import models.Game_Model;
import models.Map_Helper;
import models.Map_Model;
import models.Player;
import views.Tournament_View;

public class Tournament_Controller {
	private Set<Map_Model> maps;
	public Set<Player> players;
	public int nb_game;
	public int max_nb_turn;
	public Game_Model game;
	public Tournament_View tournament_view;
	public Map_Helper map_helper;

	public Tournament_Controller() {
		tournament_view = new Tournament_View(this);
		maps = new HashSet<Map_Model>();
		players = new HashSet<Player>();
	}
	
	public boolean Add_Map(String map_path) {
		if (maps.size() < 5) {
			Map_Model new_map = map_helper.Import_Map(map_path);
			//TODO: Check if does not add to the previous map because of singlton map
			if (new_map == null)
				return false;
			maps.add(new_map);
			return true;		
		}
		return false;		
	}
	
	public boolean Start() {
		if (maps.size() >= 1 && maps.size() <= 5 && 
				players.size() >= 2 &&  players.size() <= 4 &&
				nb_game >= 1 && nb_game <= 5 &&
				max_nb_turn >= 10 && max_nb_turn <= 50)
		{
			for(Map_Model map : maps) {
				game = new Game_Model(map);
				//game.Setup(players);
				//game.Start();
			}			
			return true;
		}
		else
			return false;
	}
	

}
