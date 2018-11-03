package views;

import java.awt.Graphics;
import java.util.Observable;
import javax.swing.JFrame;
import models.Game_Model;
import models.Player;
import models.State_Game;

public class Phase_View extends View {
	
	 public Player current_player;
	 public State_Game current_state;
	 public String update_state;
	 String current_player_name="";
	 String current_state_name="";
	 public String text;
	 
	 JFrame jFrame;
	 
	 public Phase_View(){
		 jFrame = new JFrame();	
	 }
	 
	 public void Draw_Phase_View () {
		 
			jFrame.add(this);
			jFrame.setSize(800, 800);
			jFrame.setVisible(true);
		}
	 
	 

	@Override
	public void update(Observable obs, Object arg) {
		current_player = ((Game_Model) obs).current_player;
		current_player_name = current_player.name;
		
		current_state = ((Game_Model) obs).current_state;
		current_state_name = current_state.name();
		
		update_state = ((Game_Model) obs).message;
		
		
		
	}

	@Override
	public void Update_Display(String text) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		
	}

}
