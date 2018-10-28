package game_app;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;

public class Game_Engine {
		
	public static void main(String[] args) {
		Map_Generator_Controller map_generator = new Map_Generator_Controller();
		
		if (map_generator.start()) {
			Game_Controller game = new Game_Controller(map_generator.map);
			game.Start();
		}
		else {
			//TODO:ERROR
		}
		
	}
	
	
	

}
