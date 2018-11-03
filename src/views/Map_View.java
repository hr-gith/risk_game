package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.Continent;
import models.Map_Model;
import models.Territory;

public class Map_View extends JPanel{

	private Map_Model map;
	/**
	 * draw map
	 */

	public void Draw_Map(Map_Model map) {
		this.map = map;
		JFrame jFrame = new JFrame();
		jFrame.add(this);
		jFrame.setSize(1000, 1000);
		jFrame.setVisible(true);
	}


	@Override
	public void paintComponent(Graphics g) {
		Color[] colors = { Color.blue, Color.green, Color.red, Color.pink, Color.yellow, Color.orange };
		HashMap<String, Color> player_color = new HashMap<>();
		int current_color = 0;
		for (Continent continent : map.continents.values()) {
			for (Territory territory : continent.territories.values()) {
				if (player_color.containsKey(territory.owner_name))
					g.setColor(player_color.get(territory.owner_name));
				else {
					player_color.put(territory.owner_name, colors[current_color]);
					g.setColor(colors[current_color]);
					current_color++;
				}
				g.drawOval(territory.pos_x - 30, territory.pos_y - 15, 60, 30);
				g.drawString(territory.name, territory.pos_x - 28, territory.pos_y + 2);
				g.drawString(Integer.toString(territory.nb_armies), territory.pos_x - 10, territory.pos_y + 12);
				// draw connections
				for (Territory neighbour : territory.adj.values()) {
					g.setColor(Color.BLACK);
					g.drawLine(territory.pos_x, territory.pos_y + 15, neighbour.pos_x, neighbour.pos_y + 15);
				}
			}

		}

	}
	@Override
	public void paint(Graphics g)
	{
	    super.paintComponent(g);
	    Graphics2D g2D = (Graphics2D) g;
	    AffineTransform affT = g2D.getTransform();
	    g2D.scale( 2, 2);
	    super.paint(g);
	    g2D.setTransform(affT);
	} 
}
