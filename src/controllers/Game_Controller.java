package controllers;

import models.Game_Model;
import models.Map_Model;
import models.State_Player_Strategy;
import views.Card_View;
import views.Console_View;
import views.Game_View;
import views.Map_View;
import views.Phase_View;
import views.Players_World_Domination_View;

import java.util.AbstractMap;
import java.util.ArrayList;

/**
 * game controller class for connecting between game model class and view
 * classes
 */
public class Game_Controller {

	public Game_Model game;
	public Game_View game_view;
	public Console_View console_view;
	public Map_View map_view;
	public Phase_View phase_view;
	public Players_World_Domination_View players_world_domination_view;
	public Card_View card_view;

	public Game_Controller(Game_Model game) {
		this.game = game;
		Set_Views();
	}

	/**
	 * constructor for initiate the game and adding observers to the game
	 */
	public Game_Controller(Map_Model map) {
		game = Game_Model.Get_Game();
		game.map = map;
		Set_Views();
	}

	/**
	 * Method for connect observer and observables
	 */
	public void Set_Views() {
		console_view = new Console_View(this);
		game.addObserver(console_view);

		if (map_view == null) {
			game_view = new Game_View(true);
		}

		game.addObserver(game_view);

		map_view = new Map_View();
		game.addObserver(map_view);

		phase_view = new Phase_View();
		game.addObserver(phase_view);

		card_view = new Card_View();
		game.addObserver(card_view);

		players_world_domination_view = new Players_World_Domination_View();
		game.addObserver(players_world_domination_view);

	}

	/**
	 * method for starting game
	 */
	public void Start() {
		ArrayList<AbstractMap.SimpleEntry<String, State_Player_Strategy>> players_name = console_view
				.Display_Menu_Players();
		this.Start(players_name);
	}

	public void Start(ArrayList<AbstractMap.SimpleEntry<String, State_Player_Strategy>> players_name) {
		game.Setup(players_name);
	}

	/**
	 * method for calling reinforcement from game
	 */
	public void Reinforcement() {
		game.Reinforce();
	}

	/**
	 * method for calling fortification from game
	 */
	public void Fortification() {
		game.Fortify();
	}

	/**
	 * method for calling attack from game
	 */
	public void Attack() {
		game.Attack();
	}

	/**
	 * this method calling Post_Attack from Game
	 */
	public void Post_Attack() {
		game.Post_Attack();
	}

	/**
	 * method for moving to the next phase
	 */
	public void Move_To_Next_Phase() {
		game.Move_To_Next_Phase();
	}

	/**
	 * this method for calling ReDraw method for view classes
	 */
	public void RedrawViews() {

		map_view.Redraw();
		phase_view.Redraw();
		card_view.Redraw();
		players_world_domination_view.Redraw();

	}

}
