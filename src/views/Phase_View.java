package views;

import java.util.Observable;

import models.Game_Model;
import models.Player;
import models.State_Game;

public class Phase_View extends View {
	
	 public Player current_player;
	 public State_Game current_state;
	 public String message;
	 public String text;

	@Override
	public void update(Observable o, Object arg) {
		current_player = ((Game_Model) o).current_player;
		current_state = ((Game_Model) o).current_state;
		message = ((Game_Model) o).message;
		
	

	}

	@Override
	public void Update_Display(String text) {
		// TODO Auto-generated method stub
		
	}

}
