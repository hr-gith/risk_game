package views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import models.Game_Model;
import models.Map_Model;

/**
 * game view class implement observer and extends JPanel from abstract view
 * class
 *
 */
public class Game_View extends View {

	private Map_Model map;

	JFrame jFrame;
	JPanel mainPanel;

	Dimension screenSize;
	private int top_margin;
	private int side_margin;

	/**
	 * constructor to initiate JFrame and mainPanel
	 */
	public Game_View(boolean exit_on_close) {
		jFrame = new JFrame();
		
		if(exit_on_close){
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		mainPanel = new JPanel();
		mainPanel = (JPanel) jFrame.getContentPane();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

	}


	
	/**
	 * draw JFrame and set size and margin
	 */
	public void Draw_Window() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		side_margin = screenSize.width / 50;
		top_margin = screenSize.height / 50;

		jFrame.setSize(screenSize.width / 2, screenSize.height / 2);
		jFrame.setVisible(true);
	}

	/**
	 * adding panel to the JFrame
	 * @param panel for Map and Phase View and World domination
	 * @param position for switch cases from 1 to 4 for adding different panel to main panel
	 */
	public void Add_Panel(JPanel panel, int position) {

		switch (position) {
		case 1: // top left
//			panel.setSize(screenSize.width / 2, screenSize.height / 2);
			// panel.setBounds(side_margin, top_margin, 11*side_margin, 11*top_margin);
			// panel.setBounds(45, 40, 400, 400);
			// panel.setBackground(Color.yellow);
			mainPanel.add(panel);
			// jFrame.pack();
			jFrame.setVisible(true);

			break;

		case 2:

			panel.setBounds(13 * side_margin, top_margin, 11 * side_margin, 11 * top_margin);
			mainPanel.add(panel);
			// jFrame.pack();
			jFrame.setVisible(true);

			break;

		case 3:

			panel.setBounds(side_margin, 13 * top_margin, 11 * side_margin, 11 * top_margin);
			mainPanel.add(panel);
			// jFrame.pack();
			jFrame.setVisible(true);

			break;

		case 4:

			panel.setBounds(13 * side_margin, 13 * top_margin, 11 * side_margin, 11 * top_margin);
			jFrame.add(panel);

			break;

		}

	}

	/**
	 * redraw for JFrame
	 */
	public void Redraw() {
		jFrame.repaint();
	}

	/**
	 * closing JFrame
	 */
	public void Close() {
		jFrame.setVisible(false);
	}

	/**
	 * overriding update for observer
	 */
	@Override
	public void update(Observable obs, Object arg1) {
		this.map = ((Game_Model) obs).map;

	}
}
