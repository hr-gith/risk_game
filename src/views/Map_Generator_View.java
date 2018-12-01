package views;

import java.util.Scanner;

import models.Continent;
import models.Map_Model;
import models.Territory;

/**
 * class related to the map generator view
 */
public class Map_Generator_View {

	Scanner scanner;
	public Map_Model map = new Map_Model();
	boolean valid_result = false;

	/**
	 * Display main menu for map generator
	 */
	public int Display_Menu() {
		scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Map Generator");
		System.out.println("\n==================================");
		System.out.println("\n 1. Import Map From File");
		System.out.println("\n 2. Design a New Map");
		System.out.println("\n 3. Edit The Map");
		System.out.println("\n 4. Save The Map");
		System.out.println("\n 5. Display The Map");
		System.out.println("\n 6. Back to The Main Menu");
		System.out.println("\n\n Please Enter Your Choice(1-6): ");
		return scanner.nextInt();
	}

	/**
	 * Display menu for design map by user input
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
		System.out.println("\n 8.Validate Map");
		System.out.println("\n 9.Exit");
		System.out.println("\n\n Please Enter Your Choice(1 to 9): ");
		int result = Integer.valueOf(scanner.nextLine());

		return result;
	}
	
	/**
	 * method for getting map name
	 * @return string
	 */
	public String Display_Get_Map_Name() {
		System.out.println("\n Enter the map's name:  ");
		scanner = new Scanner(System.in);
		return scanner.nextLine().trim();
	}

	/**
	 * method for display map designer method
	 * @param game_view
	 * @param map_view
	 */
	public void Display_Map_Designer(Game_View game_view, Map_View map_view) {
		scanner = new Scanner(System.in);
		int choice;
		game_view.Draw_Window();
		game_view.Add_Panel(map_view.jPanel, 1);
		map_view.Draw_Map(map);

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
			case 8:// Validate Map
				if (!map.Is_Valid()) {
					System.out.println("\n==================================");
					System.out.println("\n\t Error!The map is not valid.");
				} else {
					System.out.println("\n==================================");
					System.out.println("\n\t The map is valid.");
				}
				break;
			case 9:// Exit!
				break;
			default:
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 9)");
				break;
			}
			
			game_view.Draw_Window();
			game_view.Add_Panel(map_view.jPanel, 1);
			map_view.Draw_Map(map);
			game_view.Redraw();

		} while (choice != 9);

	}

	/**
	 * Add continent to the user design map
	 */
	public void Add_Continent_Map_Menu() {
		scanner = new Scanner(System.in);
		String continent_name;
		Continent continent;
		boolean result = false;
		String add_more = "n";

		do {
			System.out.println("\n Enter continent name: ");
			continent_name = scanner.nextLine();
			Continent getting_continent = map.Get_Continent(continent_name);

			if (getting_continent == null) {
				continent = new Continent(continent_name, 0);
				result = map.Add_Continent(continent);
				if (result) {
					System.out.println("\n\t 'Continent is added successfully'");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to add another continent(y,n): ");
					add_more = scanner.nextLine().toLowerCase();
				} else {
					System.out.println("\n\t 'Continent existed!'");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to add another continent(y,n): ");
					add_more = scanner.nextLine().toLowerCase();
				}
			}

		} while (add_more.equals("y"));

	}

	/**
	 * Add territory to the user design map
	 */
	public void Add_Territory_Map_Menu() {
		scanner = new Scanner(System.in);
		Territory territory;
		String territory_name;
		String continent_name;
		boolean result = false;
		String add_more = "n";
		int x_position;
		int y_position;
		do {
			System.out.println("\n Enter related continent: ");
			continent_name = scanner.nextLine();
			Continent getting_continent = map.Get_Continent(continent_name);

			if (getting_continent != null) {
				System.out.println("\n Enter territory name: ");
				territory_name = scanner.nextLine();
				System.out.println("\n Enter territory X position: ");
				x_position = Integer.valueOf(scanner.nextLine());
				System.out.println("\n Enter territory Y position: ");
				y_position = Integer.valueOf(scanner.nextLine());

				territory = new Territory(territory_name, x_position, y_position, continent_name);
				result = getting_continent.Add_Territory(territory);
				if (result) {
					System.out.println("\n\t 'Territory is added successfully'");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to add another territory(y,n)? ");
					add_more = scanner.nextLine().toLowerCase();
				} else {

					System.out.println("\n\t The Territory exists!");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to try again (y,n)? ");
					add_more = scanner.nextLine().toLowerCase();
				}
			} else {
				System.out.println("\n\t 'The continent does not exist!'");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to try again (y,n)? ");
				add_more = scanner.nextLine().toLowerCase();
			}

		} while (add_more.equals("y"));
	}

	/**
	 * Add connection between territories on the user design map
	 */
	public void Add_Neighbour_Map_Menu() {
		scanner = new Scanner(System.in);
		String add_more_neighbour = "n";
		String territory_neihbour;
		String territory_name;
		boolean result = false;
		String add_more = "n";
		do {
			System.out.println("\n Enter territory name: ");
			territory_name = scanner.nextLine();
			Territory getting_territory = map.Get_Territory(territory_name);

			if (getting_territory != null) {
				do {
					System.out.println("\n Enter the neighbour name: ");
					territory_neihbour = scanner.nextLine();
					Territory getting_territory_neighbour = map.Get_Territory(territory_neihbour);
					if (getting_territory_neighbour != null) {
						result = getting_territory.Add_Neighbour(getting_territory_neighbour);
						if (result) {
							result = getting_territory_neighbour.Add_Neighbour(getting_territory);
							if (result) {
								System.out.println("\n\t 'Neighbour is added successfully'");
								System.out.println("\n==================================");
								System.out.println("\n Do you want to add another neighbour to the territory (y,n): ");
								add_more_neighbour = scanner.nextLine().toLowerCase();
							} else {
								System.out.println("\n\t 'Could not add connection properly!'");
							}
						} else {
							System.out.println("\n\t 'Neighbour does not found!'");
							System.out.println("\n==================================");
							System.out.println("\n Do you want to add another neighbour to the territory (y,n): ");
							add_more_neighbour = scanner.nextLine().toLowerCase();
						}
					}
				} while (add_more_neighbour.equals("y"));
			} else {
				System.out.println("\n\t 'Territory does not found!'");
			}
			System.out.println("\n==================================");
			System.out.println("\n Do you want to add neighbours to another territory (y,n): ");
			add_more = scanner.nextLine().toLowerCase();
		} while (add_more.equals("y"));
	}

	/**
	 * Delete the specify continent from the user design map
	 */
	public void Delete_Continent_Map_Menu() {
		scanner = new Scanner(System.in);
		String delete_more;
		String continent_name;
		do {
			System.out.println("\n\t Deleting the continent!");
			System.out.println("\n==================================");
			System.out.println("\n Enter the continent name: ");
			continent_name = scanner.nextLine();
			Continent getting_continent = map.Get_Continent(continent_name);
			if (getting_continent == null) {
				System.out.println("\n\t Continent does not exist at all!");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to delete another continent (y,n): ");
				delete_more = scanner.nextLine().toLowerCase();
			} else {
				map.Delete_Continent(continent_name);
				System.out.println("\n\t Continent was successfully deleted!");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to delete another continent (y,n): ");
				delete_more = scanner.nextLine().toLowerCase();
			}

		} while (delete_more.equals("y"));
	}

	/**
	 * Delete the specify territory from the user design map
	 */
	public void Delete_Territory_Map_Menu() {
		scanner = new Scanner(System.in);
		String territory_name;
		String delete_more = "n";
		boolean result = false;

		do {
			System.out.println("\n Enter territory name: ");
			territory_name = scanner.nextLine();
			Territory getting_territory = map.Get_Territory(territory_name);
			if (getting_territory != null) {
				Continent getting_continent = map.Get_Continent(getting_territory.continent_name);
				result = getting_continent.Delete_Territory(territory_name);
				if (result) {
					System.out.println("\n\t Territory and its connection(s) was successfully deleted!");
					System.out.println("\n==================================");
					System.out.println("\n Do you want to delete another territory (y,n): ");
					delete_more = scanner.nextLine().toLowerCase();
				}

			} else {
				System.out.println("\n\t 'Territory does not found!'");
				System.out.println("\n==================================");
				System.out.println("\n Do you want to select another territory (y,n): ");
				delete_more = scanner.nextLine().toLowerCase();
			}

		} while (delete_more.equals("y"));
	}

	/**
	 * Delete the specify connection from the user design map
	 */
	public void Delete_Neighbour_Map_Menu() {
		scanner = new Scanner(System.in);
		String territory_name;
		String neighbour_name;
		String delete_neighbour;
		String delete_more = "n";
		boolean result = false;

		do {
			System.out.println("\n Enter territory name: ");
			territory_name = scanner.nextLine();
			Territory getting_territory = map.Get_Territory(territory_name);

			System.out.println("\n Do you want delete neighbour(s) (one , all): ");
			delete_neighbour = scanner.nextLine().toLowerCase();

			if (delete_neighbour.equals("one")) {
				System.out.println("\n Enter neighbour name: ");
				neighbour_name = scanner.nextLine();
				// delete from gettingTerritory
				result = getting_territory.Delete_Neighbour(neighbour_name);
				if (result) {
					// delete from gettingNeighbour
					Territory getting_neighbour = map.Get_Territory(neighbour_name);
					result = getting_neighbour.Delete_Neighbour(territory_name);
					if (result) {
						System.out.println("\n\t Neighbour is successfully deleted!");
						System.out.println("\n==================================");
					} else {
						System.out.println("\n\t Neighbour does not exist!");
						System.out.println("\n==================================");
					}
				} else {
					System.out.println("\n\t Neighbour does not exist!");
					System.out.println("\n==================================");
				}

			} else if (delete_neighbour.equals("all")) {
				result = getting_territory.Delete_Neighbours();
				if (result) {
					System.out.println("\n\t All neighbours are successfully deleted!");
					System.out.println("\n==================================");
				}
			}
			System.out.println("\n Do you want to delete another neighbour (y,n): ");
			delete_more = scanner.nextLine().toLowerCase();
		} while (delete_more.equals("y"));
	}

	/**
	 * Display any massage
	 * @param message
	 */

	public void Display_Message(String message) {
		System.out.println("\n" + message);
	}

	/**
	 * Display map
	 * @param map
	 */

	public void Display_Map(Map_Model map) {
		if (map == null || map.Is_Empty())
			return;
		System.out.println(map.toString());
		this.map = map;
	}

}