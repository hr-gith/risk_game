package views;

import java.awt.Color;
import java.util.Observable;
import javax.swing.*;
import models.Game_Model;
import models.Player;

/**
 * player world domination view class is implemented observer and  extends JPrame from abstract view class for updating following fields
 * 1.total number of armies Of player
 * 2.continent owner
 * 3.percentage of world owner
 */
public class Card_View extends View {

	private Player current_player; 
	

	JPanel jPanel;
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
	JLabel jLabel4;
/**
 *constructor that create new object for JFrame, JPanel and JLabel 
 */
	public Card_View() {
		jPanel = new JPanel();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel(); 
	}
	
/**
 * update method for observer
 */
	@Override
	public void update(Observable obs, Object arg1) {
		this.current_player = ((Game_Model) obs).current_player;
		Draw_Card_View();
	}

	
/**
 * Draw method for showing updated fields in the JFrame window
 */
	public void Draw_Card_View() {
		jPanel.add(this);
//		jPanel.setSize(800, 800);
//		jFrame.setBackground(Color.blue);
		jPanel.setVisible(true);
		jPanel.setLayout(null);

//		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

		
//		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
		jPanel.setBackground(Color.green);

	
	    jLabel1.setText("Current Player: " + current_player.name);
	    jLabel1.setBounds(0, 0, 200, 50);
	    jPanel.add(jLabel1);
	    
		jLabel2.setText("Infantry Cards: " + current_player.cards.infantry.toString());
		jLabel2.setBounds(0, 20, 200, 50);
		jPanel.add(jLabel2);

		jLabel3.setText("Cavalry Cards: " + current_player.cards.cavalry.toString());
		jLabel3.setBounds(0, 40, 200, 50);
		jPanel.add(jLabel3);
		
		jLabel4.setText("Artillery Cards: " + current_player.cards.artillery.toString());
		jLabel4.setBounds(0, 60, 200, 50);
		jPanel.add(jLabel4);

	}
	
	public void Redraw(){
		jPanel.repaint();
	}

	public void Close() {
		jPanel.setVisible(false);
	}
	@Override
	public void Update_Display(String text) {
		// TODO Auto-generated method stub

	}

}
