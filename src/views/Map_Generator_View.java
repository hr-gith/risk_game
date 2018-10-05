package views;

import java.util.Scanner;

import models.Continent;
import models.Map;
import models.Territory;

import java.awt.Graphics;
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
		if (map.IsEmpty()) return;
		System.out.println(map.toString());
		this.map = map;
		Draw_Map();
	}
	
	public void Draw_Map() {
		JFrame jFrame = new JFrame();
        jFrame.add(this);
        jFrame.setSize(800, 800);
        jFrame.setVisible(true);
	}
	
	@Override
    public void paintComponent(Graphics g) {        
        //g.drawOval(5, 5, 25, 25);
		for(Continent continent: map.continents.values()) {
			for(Territory territory : continent.territories.values()) {
				g.drawRect(territory.pos_x, territory.pos_y, 60, 20);
				for(Territory neighbour : territory.adj.values()) {
					//g.drawLine(territory.pos_x, territory.pos_y, neighbour.pos_x, neighbour.pos_y);
					g.drawString(territory.name, territory.pos_x+2, territory.pos_y+15);
				}
			}
			
		}
    }
	
}





