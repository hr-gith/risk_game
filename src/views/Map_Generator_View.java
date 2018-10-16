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

/**
 * 
 * @author l_noroo
 *
 */
public class Map_Generator_View extends JPanel {
	Scanner scanner = new Scanner(System.in);
	Map_Generator_View map_Generator_View;

	private Map map;

	@SuppressWarnings("resource")

	/**
	 * 
	 * @return
	 */
	public int Display_Menu() {
		System.out.println("\n\t Map Generator");
		System.out.println("\n===========================");
		System.out.println("\n 1. Import From File");
		System.out.println("\n 2. Design By MySelf");
		System.out.println("\n\n Please Enter Your Choice(1,2): ");
		return scanner.nextInt();
	}

	/**
	 * 
	 * @return
	 */

	public int Display_Menu_Design_Map() {
		System.out.println("\n\t Create Your Own Map");
		System.out.println("\n===========================");
		System.out.println("\n 1.Add Continent");
		System.out.println("\n 2.Add Territory");
		System.out.println("\n 3.Add Connection");
		System.out.println("\n 4.Delete Continent");
		System.out.println("\n 5.Delete Territory");
		System.out.println("\n 6.Delete Continent");
		System.out.println("\n 7.Exit");
		System.out.println("\n\n Please Enter Your Choice(1 to 7): ");

		return scanner.nextInt();
	}

	/**
	 * 
	 * @return
	 */
	public Map Display_Map_Designer() {
		int choice;
		boolean result = false;
		String addMore = null;
		String addMoreNeighbour = null;
		String userRespond = null;
		String continentName;
		String territoryName;
		String territoryNeihbour;
		int xPosition;
		int yPosition;

		map_Generator_View = new Map_Generator_View();
		choice = map_Generator_View.Display_Menu_Design_Map();
		Map map = new Map();
		Continent continent = null;
		Territory territory = null;

		switch (choice) {
		case 1:// Add Continent
			do {
				System.out.println("\n enter continent name: ");
				continentName = scanner.nextLine();
				Continent gettingContinent = map.Get_Continent(continentName);

				if (gettingContinent == null) {
					continent = new Continent(continentName);
					result = map.Add_Continent(continent);
					if (result) {
						System.out.println("\n\t Continent is added successfully");
						System.out.println("\n===========================");
						System.out.println("\n Do you want to add another continent(y,n): ");
						addMore = scanner.nextLine().toLowerCase();
					}
				} else {
					System.out.println("\n\t Continent existed!");
					System.out.println("\n===========================");
					System.out.println("\n Do you want to add another continent(y,n): ");
					addMore = scanner.nextLine().toLowerCase();
				}

			} while (addMore.equals("y"));

			break;

		case 2:// Add Territory
			do {
				System.out.println("\n enter territory name: ");
				territoryName = scanner.nextLine();
				System.out.println("\n enter territory X position: ");
				xPosition = scanner.nextInt();
				System.out.println("\n enter territory Y position: ");
				yPosition = scanner.nextInt();
				System.out.println("\n enter crresponding continent: ");
				continentName = scanner.nextLine();
				
				Continent gettingContinent = map.Get_Continent(continentName);

				if (gettingContinent != null) {
					territory = new Territory(territoryName, xPosition, yPosition, continentName);
					result = gettingContinent.Add_Territory(territory);
					if (result) {
						System.out.println("\n\t territory is added successfully");
						System.out.println("\n===========================");
						System.out.println("\n Do you want to add another territory(y,n): ");
						addMore = scanner.nextLine().toLowerCase();
					}

				} else {
					System.out.println("\n\t the continent does not exist!");
					System.out.println("\n===========================");
					System.out.println("\n Do you want to add territory (y,n): ");
					addMore = scanner.nextLine().toLowerCase();
				
				}
			} while (addMore.equals("y"));

			break;

		case 3:// Add Connection
			do {
				System.out.println("\n\t enter territory name: ");
				territoryName = scanner.nextLine();
				Territory gettingTerritory = map.Get_Territory(territoryName);

				if (gettingTerritory != null) {
					do {
						System.out.println("\n enter the neighbour name: ");
						territoryNeihbour = scanner.nextLine();
						Territory gettingTerritoryNeighbour = map.Get_Territory(territoryNeihbour);
						if (gettingTerritoryNeighbour != null) {
							result = gettingTerritory.Add_Neighbour(gettingTerritoryNeighbour);
							if (result) {
								System.out.println("\n\t connection is added successfully");
								System.out.println("\n===========================");
								System.out.println("\n Do you want to add another connection to territory (y,n): ");
								addMoreNeighbour = scanner.nextLine().toLowerCase();
							}

						} else {
							System.out.println("\n\t Neighbour is not found!");
							System.out.println("\n===========================");
							System.out.println("\n Do you want to add another connection to territory (y,n): ");
							addMoreNeighbour = scanner.nextLine().toLowerCase();
						}
					} while (addMoreNeighbour.equals("y"));
				} else {
					System.out.println("\n\t Territory is not found!");
					System.out.println("\n===========================");
					System.out.println("\n Do you want to select another territory (y,n): ");
					addMore = scanner.nextLine().toLowerCase();
				}

			} while (addMore.equals("y"));
			break;

		case 4:// Delete Continent

			break;

		case 5:// Delete Territory

			break;

		case 6:// Delete Connection

			break;

		case 7:// Exit
			break;

		default:
			break;
		}

		// TODO: get edges

		return map;
	}

	public void Display_Message(String message) {
		System.out.println("\n" + message);
	}

	public void Display_Map(Map map) {
		if (map == null || map.IsEmpty())
			return;
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
	/*
	 * @Override public void paint(Graphics g) { super.paintComponent(g); Graphics2D
	 * g2D = (Graphics2D) g; AffineTransform affT = g2D.getTransform(); g2D.scale(
	 * 2, 2); super.paint(g); g2D.setTransform(affT); }
	 */

}
