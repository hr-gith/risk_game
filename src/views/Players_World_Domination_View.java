package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.Game_Model;

/**
 * player world domination view class is implemented observer and extends JPrame
 * from abstract view class for updating following fields 1.total number of
 * armies Of player 2.continent owner 3.percentage of world owner
 */
public class Players_World_Domination_View extends View {

	public String total_number_of_armies_Of_player;
	public String continent_owner;
	public String percentage_of_world_owner;

	JPanel jPanel;
	JLabel jLabel1;
	JLabel jLabel2;
	JLabel jLabel3;

	/**
	 * constructor that create new object for JFrame, JPanel and JLabel
	 */
	public Players_World_Domination_View() {
		jPanel = new JPanel();
		jLabel1 = new JLabel("<html>Case &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Item&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; CaseNum<br><font color=yellow>Party1<br>Party2</font></html>");
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
	}

	/**
	 * update method for observer
	 */
	@Override
	public void update(Observable obs, Object arg1) {
		this.total_number_of_armies_Of_player = ((Game_Model) obs).Armies_Of_Players_To_String();
		this.continent_owner = ((Game_Model) obs).Continent_Owner_To_String();
		this.percentage_of_world_owner = ((Game_Model) obs).Percentage_Of_World_Owner_To_String();
		Draw_Players_World_Domination_View_Window();
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//        setBounds(10,200,screenSize.width, screenSize.height);
	}

	/**
	 * ReDraw JPanel
	 */
	public void Redraw() {
		jPanel.repaint();
	}

	/**
	 * Draw method for showing updated fields in the JFrame window
	 */
	public void Draw_Players_World_Domination_View_Window() {
		jPanel.add(this);
		jPanel.setVisible(true);
		jPanel.setLayout(null);
		 EmptyBorder border1 = new EmptyBorder(5, 20, 15, 18);

		jPanel.setBackground(Color.yellow);

		jLabel1.setText(percentage_of_world_owner);
		jLabel1.setBounds(10, 10, 200, 50);
		jLabel1.setBackground(Color.GRAY);
		 jLabel1.setSize(600, 100);
		 jLabel1.setBorder(border1);
         Dimension d = jLabel1.getPreferredSize();
		  jLabel1.setOpaque(true);
		jPanel.add(jLabel1);

		jLabel2.setText(continent_owner);
		jLabel2.setBounds(10, 200, 200, 50);
		jLabel2.setBackground(Color.orange);
		 jLabel2.setSize(600, 100);
		 jLabel2.setBorder(border1);
         Dimension p = jLabel2.getPreferredSize();
		  jLabel2.setOpaque(true);
		jPanel.add(jLabel2);

		jLabel3.setText(total_number_of_armies_Of_player);
		jLabel3.setBounds(10, 400, 200, 50);
		jLabel3.setBackground(Color.cyan);
		 jLabel3.setSize(600, 100);
		 jLabel3.setBorder(border1);
         Dimension l = jLabel3.getPreferredSize();
		  jLabel3.setOpaque(true);
		jPanel.add(jLabel3);

	}

	/**
	 * close the JPanel
	 */
	public void Close() {
		jPanel.setVisible(false);
	}

}

