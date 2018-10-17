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
 * @author Leila
 *
 */
public class Map_Generator_View extends JPanel {
	Scanner scanner;
	public Map map = Map.Get_Map();

	@SuppressWarnings("resource")
	/**
	 * Display main menu
	 * 
	 * @return
	 */

	public int Display_Menu() {
		scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Map Generator");
		System.out.println("\n==================================");
		System.out.println("\n 1. Import From File");
		System.out.println("\n 2. Design By MySelf");
		System.out.println("\n 3. Exit ");
		System.out.println("\n\n Please Enter Your Choice(1-3): ");
		return scanner.nextInt();
	}

	/**
	 * Display design main menu
	 * 
	 * @return choice
	 */

	public int Display_Menu_Design_Map() {
		scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Create Your Own Map");
		System.out.println("\n==================================");
		System.out.println("\n 1.Add Continent");
		System.out.println("\n 2.Add Territory");
		System.out.println("\n 3.Add Neighbour");
		System.out.println("\n 4.Delete Continent");
		System.out.println("\n 5.Delete Territory");
		System.out.println("\n 6.Delete Neighbour");
		System.out.println("\n 7.Display Map");
		System.out.println("\n 8.Exit");
		System.out.println("\n\n Please Enter Your Choice(1 to 7): ");
		int result = Integer.valueOf(scanner.nextLine());

		return result;
	}

	/**
	 * designing map by user
	 * 
	 * @return user design map
	 */
	public void Display_Map_Designer() {
		scanner = new Scanner(System.in);
		int choice;

		do {
			choice = Display_Menu_Design_Map();

			switch (choice) {
			case 1:// Add Continent to the map
				Add_Continent_Map_Menu();
				break;

			case 2:// Add Territory to the map
				Add_Territory_Map_Menu();
				break;

			case 3:// Add Connection to the map
				Add_Neighbour_Map_Menu();
				break;

			case 4:// Delete Continent from the map
				Delete_Continent_Map_Menu();
				break;

			case 5:// Delete Territory from the map
				Delete_Territory_Map_Menu();
				break;

			case 6:// Delete Connection from the map
				Delete_Neighbour_Map_Menu();
				break;
			case 7: // Display Map
				Display_Map(this.map);
				break;
			case 8:// Exit!
				break;

			default:
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 7)");
				break;
			}
			
		} while (choice != 8);

	}

	/**
	 * Add continent to the user design map
	 */
	public void Add_Continent_Map_Menu() {
		scanner = new Scanner(System.in);
		String continentName;
		Continent continent;
		boolean result = false;
		String addMore = "n";

		do {
			System.out.println("\n Enter continent name: ");
			continentName = scanner.nextLine();
			Continent gettingContinent = map.Get_Continent(continentName);

			if (gettingContinent == null) {
				continent = new Continent(continentName);
				result = map.Add_Continent(continent);
				if (result) {
					System.out.println("\n\t 'Continent is added successfully'");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to add another continent(y,n): ");
					addMore = scanner.nextLine().toLowerCase();
				} else {
					System.out.println("\n\t 'Continent existed!'");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to add another continent(y,n): ");
					addMore = scanner.nextLine().toLowerCase();
				}
			}

		} while (addMore.equals("y"));

	}

	/**
	 * Add territory to the user design map
	 */
	public void Add_Territory_Map_Menu() {
		scanner = new Scanner(System.in);
		Territory territory;
		String territoryName;
		String continentName;
		boolean result = false;
		String addMore = "n";
		int xPosition;
		int yPosition;
		do {
			System.out.println("\n Enter related continent: ");
			continentName = scanner.nextLine();
			Continent gettingContinent = map.Get_Continent(continentName);	

			if (gettingContinent != null) {
				System.out.println("\n Enter territory name: ");
				territoryName = scanner.nextLine();
				System.out.println("\n Enter territory X position: ");
				xPosition =Integer.valueOf(scanner.nextLine()) ;
				System.out.println("\n Enter territory Y position: ");
				yPosition = Integer.valueOf(scanner.nextLine()) ;
				
				territory = new Territory(territoryName, xPosition, yPosition, continentName);
				result = gettingContinent.Add_Territory(territory);
				if (result) {
					System.out.println("\n\t 'Territory is added successfully'");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to add another territory(y,n)? ");
					addMore = scanner.nextLine().toLowerCase();
				} else {
					
					System.out.println("\n\t The Territory exists!");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to try again (y,n)? ");
					addMore = scanner.nextLine().toLowerCase();	
				}
			}
			else {
				System.out.println("\n\t 'The continent does not exist!'");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to try again (y,n)? ");
				addMore = scanner.nextLine().toLowerCase();
			}
			
		} while (addMore.equals("y"));
	}

	/**
	 * Add connection between territories on the user design map
	 */
	public void Add_Neighbour_Map_Menu() {
		scanner = new Scanner(System.in);
		String addMoreNeighbour = "n";
		String territoryNeihbour;
		String territoryName;
		boolean result = false;
		String addMore = "n";
		do {
			System.out.println("\n Enter territory name: ");
			territoryName = scanner.nextLine();
			Territory gettingTerritory = map.Get_Territory(territoryName);

			if (gettingTerritory != null) {
				do {
					System.out.println("\n Enter the neighbour name: ");
					territoryNeihbour = scanner.nextLine();
					Territory gettingTerritoryNeighbour = map.Get_Territory(territoryNeihbour);
					if (gettingTerritoryNeighbour != null) {
						result = gettingTerritory.Add_Neighbour(gettingTerritoryNeighbour);
						if (result) {
							result = gettingTerritoryNeighbour.Add_Neighbour(gettingTerritory);
							if (result) {
								System.out.println("\n\t 'Neighbour is added successfully'");
								System.out.println("\n==================================");
								System.out.println("\n Do you want to add another neighbour to the territory (y,n): ");
								addMoreNeighbour = scanner.nextLine().toLowerCase();
							}
							else {
								System.out.println("\n\t 'Could not add connection properly!'");
							}
						} else {
							System.out.println("\n\t 'Neighbour does not found!'");
							System.out.println("\n==================================");
							System.out.println("\n Do you want to add another neighbour to the territory (y,n): ");
							addMoreNeighbour = scanner.nextLine().toLowerCase();
						}
					}
				} while (addMoreNeighbour.equals("y"));
			} else {
				System.out.println("\n\t 'Territory does not found!'");				
			}
			System.out.println("\n==================================");
			System.out.println("\n Do you want to add neighbours to another territory (y,n): ");
			addMore = scanner.nextLine().toLowerCase();
		} while (addMore.equals("y"));
	}

	/**
	 * Delete the specify continent from the user design map
	 */
	public void Delete_Continent_Map_Menu() {
		scanner = new Scanner(System.in);
		String deleteMore;
		String continentName;
		do {
			System.out.println("\n\t Deleting the continent!");
			System.out.println("\n==================================");
			System.out.println("\n Enter the continent name: ");
			continentName = scanner.nextLine();
			Continent gettingContinent = map.Get_Continent(continentName);
			if (gettingContinent == null) {
				System.out.println("\n\t Continent does not exist at all!");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to delete another continent (y,n): ");
				deleteMore = scanner.nextLine().toLowerCase();
			} else {
				map.Delete_Continent(continentName);
				System.out.println("\n\t Continent was successfully deleted!");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to delete another continent (y,n): ");
				deleteMore = scanner.nextLine().toLowerCase();
			}

		} while (deleteMore.equals("y"));
	}

	/**
	 * Delete the specify territory from the user design map
	 */
	public void Delete_Territory_Map_Menu() {
		scanner = new Scanner(System.in);
		String territoryName;
		String deleteMore ="n";
		boolean result = false;

		do {
			System.out.println("\n Enter territory name: ");
			territoryName = scanner.nextLine();
			Territory gettingTerritory = map.Get_Territory(territoryName);
			if (gettingTerritory != null) {
				Continent gettingContinent = map.Get_Continent(gettingTerritory.continent_name);
				result = gettingContinent.Delete_Territory(territoryName);
				if (result) {
					System.out.println("\n\t Territory and its connection(s) was successfully deleted!");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to delete another territory (y,n): ");
					deleteMore = scanner.nextLine().toLowerCase();
				}
				
			}
			else {
				System.out.println("\n\t 'Territory does not found!'");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to select another territory (y,n): ");
				deleteMore = scanner.nextLine().toLowerCase();
			}			

		} while (deleteMore.equals("y"));
	}

	/**
	 * Delete the specify connection from the user design map
	 */
	public void Delete_Neighbour_Map_Menu() {
		scanner = new Scanner(System.in);
		String territoryName;
		String neighbour_name;
		String deleteNeighbour;
		String deleteMore = "n";
		boolean result = false;

		do {
			System.out.println("\n Enter territory name: ");
			territoryName = scanner.nextLine();
			Territory gettingTerritory = map.Get_Territory(territoryName);

			System.out.println("\n Do you want delete neighbour(s) (one , all): ");
			deleteNeighbour = scanner.nextLine().toLowerCase();

			if (deleteNeighbour.equals("one")) {
				System.out.println("\n Enter neighbour name: ");
				neighbour_name = scanner.nextLine();
				//delete from gettingTerritory
				result = gettingTerritory.Delete_Neighbour(neighbour_name);
				if (result) {
					//delete from gettingNeighbour
					Territory getting_neighbour = map.Get_Territory(neighbour_name);
					result = getting_neighbour.Delete_Neighbour(territoryName);
					if (result) {
						System.out.println("\n\t Neighbour is successfully deleted!");
						System.out.println("\n==================================");
					}
					else {
						System.out.println("\n\t Neighbour does not exist!");
						System.out.println("\n==================================");
					}
				}
				else {
					System.out.println("\n\t Neighbour does not exist!");
					System.out.println("\n==================================");
				}

			} else if(deleteNeighbour.equals("all")) {
				result = gettingTerritory.Delete_Neighbours();
				if (result) {
					System.out.println("\n\t All neighbours are successfully deleted!");
					System.out.println("\n==================================");
				}
			}
			System.out.println("\n Do you want to delete another neighbour (y,n): ");
			deleteMore = scanner.nextLine().toLowerCase();
		} while (deleteMore.equals("y"));
	}

	/**
	 * 
	 * @param message
	 */

	public void Display_Message(String message) {
		System.out.println("\n" + message);
	}

	/**
	 * 
	 * @param map
	 */

	public void Display_Map(Map map) {
		if (map == null || map.Is_Empty())
			return;
		System.out.println(map.toString());
		this.map = map;
		Draw_Map();
	}

	/**
	 * 
	 */

	public void Draw_Map() {
		JFrame jFrame = new JFrame();
		jFrame.add(this);
		jFrame.setSize(1000, 1000);
		jFrame.setVisible(true);
	}

	/**
	 * 
	 */

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
