package views;

import java.awt.Color;
import java.util.Observable;
import javax.swing.*;
import models.Game_Model;

/**
 * player world domination view class is implemented observer and  extends JPrame from abstract view class for updating following fields
 * 1.total number of armies Of player
 * 2.continent owner
 * 3.percentage of world owner
 */
public class Players_World_Domination_View extends View {

	public String total_number_of_armies_Of_player;
	public String continent_owner;
	public String percentage_of_world_owner;

	JFrame jFrame;
	JPanel jpanel;
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
/**
 *constructor that create new object for JFrame, JPanel and JLabel 
 */
	public Players_World_Domination_View() {
		jFrame = new JFrame("Players_World_Domination");
		jpanel = new JPanel();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
	}
	
/**
 * update method for observer
 */
	@Override
	public void update(Observable obs, Object arg1) {
		this.total_number_of_armies_Of_player = ((Game_Model) obs).Armies_Of_Player();
		this.continent_owner = ((Game_Model) obs).Continent_Owner();
		this.percentage_of_world_owner = ((Game_Model) obs).Percentage_of_world_Owner();
		Draw_Players_World_Domination_View_Window();
	}

	
/**
 * Draw method for showing updated fields in the JFrame window
 */
	public void Draw_Players_World_Domination_View_Window() {
		jFrame.setSize(500, 500);
		jFrame.setBackground(Color.blue);
		jFrame.setVisible(true);
		jFrame.setLayout(null);

		jpanel.setBounds(45, 40, 400, 400);
		jpanel.setBackground(Color.yellow);

		jFrame.add(jpanel);
		
	    jLabel1.setText(percentage_of_world_owner);
	    jLabel1.setBounds(0, 0, 200, 50);
	    jpanel.add(jLabel1);

		jLabel2.setText(continent_owner);
		jLabel2.setBounds(0, 20, 200, 50);
		jpanel.add(jLabel2);

		jLabel3.setText(total_number_of_armies_Of_player);
		jLabel3.setBounds(0, 40, 200, 50);
		jpanel.add(jLabel3);

	}

	public void Close() {
		jFrame.setVisible(false);
	}
	@Override
	public void Update_Display(String text) {
		// TODO Auto-generated method stub

	}

}
