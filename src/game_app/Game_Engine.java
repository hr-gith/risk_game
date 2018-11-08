package game_app;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;

/**
 * class for calling to start Map generator and Game
 */
public class Game_Engine {

	public static void main(String[] args) {
		Map_Generator_Controller map_generator = new Map_Generator_Controller();

		if (map_generator.start()) {
			Game_Controller game = new Game_Controller(map_generator.map);
			game.map_view = map_generator.map_view;
			game.Start();
		}

		else {
			// TODO:ERROR
		}

	}

}
