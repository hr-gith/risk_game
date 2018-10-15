package views;

import java.util.HashMap;
import java.util.Scanner;

import models.Continent;
import models.Map;
import models.Territory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Map_Generator_View extends JPanel{
	
	private Map map;
	
	@SuppressWarnings("resource")
	public int Display_Menu() {	
		System.out.println("\n\t Map Generator");
		System.out.println("\n===========================");
		System.out.println("\n 1. Import From File");
		System.out.println("\n 2. Design By MySelf");
		System.out.println("\n\n Please Enter Your Choice(1,2): ");
		Scanner in = new Scanner(System.in);
		return in.nextInt();
	}
	
	public Map Display_Map_Designer() {
		Map map = new Map();
		Scanner in = new Scanner(System.in);
		
		System.out.println("\n\t Create Your Own Map");
		System.out.println("\n===========================");
		System.out.println("\n How many continents does the map have?");
		int continents_num = in.nextInt();	
		
		//TODO: get edges
		
		return map;
	}
	
	public void Display_Message(String message) {
		System.out.println("\n" + message);
	}
	
	public void Display_Map(Map map) {
		if (map == null || map.IsEmpty()) return;
		System.out.println(map.toString());
		this.map = map;
		Draw_Map();
	}
	
	public void Draw_Map() {
		JFrame jFrame = new JFrame();
        jFrame.add(this);
        jFrame.setSize(1000, 1000);
        jFrame.setVisible(true);
	}
	
	@Override
    public void paintComponent(Graphics g) {        
        Color[] colors = {Color.blue, Color.green, Color.red, Color.pink, Color.yellow, Color.orange};
        HashMap<String, Color> player_color = new HashMap<>(); 
        int current_color = 0;
		for(Continent continent: map.continents.values()) {
			for(Territory territory : continent.territories.values()) {
				if (player_color.containsKey(territory.owner_name) )
					g.setColor(player_color.get(territory.owner_name));				
				else {
					player_color.put(territory.owner_name, colors[current_color]);
					g.setColor(colors[current_color]);
					current_color++;
				}
				g.drawOval(territory.pos_x - 30, territory.pos_y - 15, 60, 30);
				g.drawString(territory.name, territory.pos_x - 28, territory.pos_y + 2);
				g.drawString(Integer.toString(territory.nb_armies), territory.pos_x - 10, territory.pos_y+12);
				//draw connections
				for(Territory neighbour : territory.adj.values()) {			
					g.setColor(Color.BLACK);
					g.drawLine(territory.pos_x, territory.pos_y + 15, neighbour.pos_x, neighbour.pos_y + 15);
				}
			}
			
		}
		
    }
	/*@Override
	public void paint(Graphics g)
	{
	    super.paintComponent(g);
	    Graphics2D g2D = (Graphics2D) g;
	    AffineTransform affT = g2D.getTransform();
	    g2D.scale( 2, 2);
	    super.paint(g);
	    g2D.setTransform(affT);
	} */
	
}





