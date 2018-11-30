package views;

import java.awt.Color;
import java.util.Observable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	JLabel lb_player_name;
	JLabel lb_current_state;
	JLabel lb_state_message;
	JTextArea text_area;

	/**
	 * constructor that create new object for JFrame, JPanel and JLabel
	 */

	public Phase_View() {
		jPanel = new JPanel();
		lb_player_name = new JLabel();
		lb_current_state = new JLabel();
		lb_state_message = new JLabel();
		text_area = new JTextArea();
	}

	/**
	 * Draw method for showing updated fields in the JFrame window
	 */

	public void Draw_Phase_View_Window() {

		jPanel.add(this);
		jPanel.setVisible(true);
		jPanel.setLayout(null);

		jPanel.setBackground(Color.pink);

		lb_player_name.setText(current_player_name);
		lb_player_name.setBounds(0, 0, 200, 50);
		jPanel.add(lb_player_name);

		lb_current_state.setText(current_state_name);
		lb_current_state.setBounds(0, 20, 200, 50);
		jPanel.add(lb_current_state);

		lb_state_message.setText(update_state);
		lb_state_message.setBounds(0, 40, 200, 50);
		jPanel.add(lb_state_message);

		text_area.setText(text);
		text_area.setBounds(5, 90, 900, 120);
		text_area.setBackground(Color.pink);
		jPanel.add(text_area);
		
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
