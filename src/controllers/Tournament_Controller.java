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
import models.State_Player_Strategy;
import views.Tournament_View;

public class Tournament_Controller {
	private Set<Map_Model> maps;
	public ArrayList<AbstractMap.SimpleEntry<String,State_Player_Strategy>> player_list;
	public int nb_game;
	public int max_nb_turn;
	public Game_Model game;
	public Tournament_View tournament_view;
	public Map_Helper map_helper;

	public Tournament_Controller() {
		tournament_view = new Tournament_View(this);
		maps = new HashSet<Map_Model>();
		player_list = new ArrayList<AbstractMap.SimpleEntry<String,State_Player_Strategy>>();
		map_helper = new Map_Helper();
	}
	
	public boolean Add_Map(String map_name) {
		if (maps.size() < 5) {
			String path = ".\\src\\" + map_name +".map";
			Map_Model new_map = map_helper.Import_Map(path);
			//TODO: Check if does not add to the previous map because of singleton map
			if (new_map == null)
				return false;
			maps.add(new_map);
			return true;		
		}
		return false;		
	}
	
	public boolean Add_Player(String name, State_Player_Strategy strategy) {
		if (player_list.size() < 6) {
			player_list.add(new AbstractMap.SimpleEntry<String,State_Player_Strategy>(name, strategy));
			return true;		
		}
		return false;		
	}
	
	public void Setup() {
		this.tournament_view.Setup_Tournament();
	}
	
	public boolean Start() throws InterruptedException {
		if (maps.size() >= 1 && maps.size() <= 5 && 
				player_list.size() >= 2 &&  player_list.size() <= 4 &&
				nb_game >= 1 && nb_game <= 5 &&
				max_nb_turn >= 10 && max_nb_turn <= 50)
		{
			for(Map_Model map : maps) {
				for (int i = 0; i < this.nb_game; i++) {
					game = new Game_Model(map);
					game.Setup(player_list);
					System.out.println("End of game "+ (i+1)+ " for "+ map.name +" map");
					Thread.sleep(1000);
				}
			}			
			return true;
		}
		else
			return false;
	}
	

}
