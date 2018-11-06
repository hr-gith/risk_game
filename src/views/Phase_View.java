package views;

import java.awt.Color;
import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Game_Model;
import models.Player;
import models.State_Game;

/**
 * phase view class is implemented observer and  extends JPrame from abstract view class for updating following fields
 * 1. current player
 * 2. current state
 * 3. update state
 */
public class Phase_View extends View {

	public Player current_player;
	public State_Game current_state;
	public String update_state="";
	String current_player_name = "";
	String current_state_name = "";
	public String text="";

	JFrame jFrame;
	JPanel jpanel;  
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
 
	/**
	 *constructor that create new object for JFrame, JPanel and JLabel 
	 */	

	public Phase_View() {
		jFrame = new JFrame("Phase_View");
		jpanel=new JPanel();
		jLabel1=new JLabel();
		jLabel2=new JLabel();
		jLabel3=new JLabel();
	}
	
	/**
	 * Draw method for showing updated fields in the JFrame window
	 */
	
	public void Draw_Phase_View_Window() {

        jFrame.setSize(500, 500);
        jFrame.setBackground(Color.blue);
		jFrame.setVisible(true);
		jFrame.setLayout(null);
		
		jpanel.setBounds(45,40,400,400);    
	    jpanel.setBackground(Color.yellow);
	    
	    jFrame.add(jpanel);
	    
	   
	    jLabel1.setText(current_player_name);
	    jLabel1.setBounds(0, 0, 200, 50);
	    jpanel.add(jLabel1);
	    
	    jLabel2.setText(current_state_name);
	    jLabel2.setBounds(0, 20, 200, 50);
	    jpanel.add(jLabel2);
	    
	    jLabel3.setText(update_state);
	    jLabel3.setBounds(0, 40, 200, 50);
	    jpanel.add(jLabel3);
	   
	}

	public void Close() {
		jFrame.setVisible(false);
	}
	

	/**
	 * update method for observer
	 */
	@Override
	public void update(Observable obs, Object arg) {
		current_player = ((Game_Model) obs).current_player;
		this.current_player_name = current_player.name;

		current_state = ((Game_Model) obs).current_state;
		this.current_state_name = current_state.name();

		this.update_state = ((Game_Model) obs).message;
		
		Draw_Phase_View_Window();

	}

	@Override
	public void Update_Display(String text) {
		// TODO Auto-generated method stub

	}

	

}
