package views;

import java.awt.Color;
import java.util.Observable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.Game_Model;
import models.Player;
import models.State_Game;

/**
 * phase view class is implemented observer and extends JPrame from abstract
 * view class for updating following fields 1. current player 2. current state
 * 3. update state
 */
public class Phase_View extends View {

	public Player current_player;
	public State_Game current_state;
	public String update_state = "";
	String current_player_name = "";
	String current_state_name = "";
	public String text = "";

	JPanel jPanel;
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;
	JLabel jLabel4;

	/**
	 * constructor that create new object for JFrame, JPanel and JLabel
	 */

	public Phase_View() {
		jPanel = new JPanel();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
	}

	/**
	 * Draw method for showing updated fields in the JFrame window
	 */

	public void Draw_Phase_View_Window() {

		jPanel.add(this);
		jPanel.setVisible(true);
		jPanel.setLayout(null);

		jPanel.setBackground(Color.pink);

		jLabel1.setText(current_player_name);
		jLabel1.setBounds(0, 0, 200, 50);
		jPanel.add(jLabel1);

		jLabel2.setText(current_state_name);
		jLabel2.setBounds(0, 20, 200, 50);
		jPanel.add(jLabel2);

		jLabel3.setText(update_state);
		jLabel3.setBounds(0, 40, 200, 50);
		jPanel.add(jLabel3);

		jLabel4.setText(text);
		jLabel4.setBounds(0, 60, 200, 50);
		jPanel.add(jLabel4);
		
	}

	/**
	 * close the JPanel
	 */

	public void Close() {
		jPanel.setVisible(false);
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
		this.text = current_player.behavior.am.message;
		//current_player.behavior.am.message = "";
		Draw_Phase_View_Window();

	}

	/**
	 * ReDraw JPanel
	 */
	public void Redraw() {
		jPanel.repaint();
	}

}
