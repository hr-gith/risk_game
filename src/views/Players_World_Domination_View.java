package views;

import java.awt.Color;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;

import models.Game_Model;
import models.Map_Model;

public class Players_World_Domination_View extends View {

	public String total_number_of_armies_Of_player;
	public String continent_owner;

	JFrame jFrame;
	JPanel panel;
	JLabel lable;
	public Players_World_Domination_View() {
		jFrame = new JFrame("Players_World_Domination");

		JPanel panel = new JPanel();
		JLabel Lable=new JLabel();
	}

	@Override
	public void update(Observable obs, Object arg1) {
		total_number_of_armies_Of_player = ((Game_Model) obs).Armies_Of_Player();
		continent_owner=((Game_Model) obs).Continent_Owner();

	}

	@Override
	public void Update_Display(String text) {
		// TODO Auto-generated method stub

	}

	public void Draw_Players_World_Domination_View_Window(String total_number_of_armies_Of_player) {
		this.total_number_of_armies_Of_player = total_number_of_armies_Of_player;
		 

		panel.setBounds(40, 80, 200, 200);
		panel.setBackground(Color.gray);
		jFrame.add(panel);
		jFrame.setSize(400,400);    
		jFrame.setLayout(null);    
		jFrame.setVisible(true);  
		 

	}

	public void Close() {
		jFrame.setVisible(false);
	}

}
