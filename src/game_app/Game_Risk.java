package game_app;

import models.Game_Model;

public class Game_Risk {
	
	public static void main(String[] args) {
	
		Game_Model.Game_Setup(); 
		
		while(Game_Model.isStillFighting()){
		Game_Model.Game_Loop(); 
		}
		
	}
	
	
	

}
