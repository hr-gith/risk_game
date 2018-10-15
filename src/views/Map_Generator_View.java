package views;

import java.util.Scanner;

import models.Continent;
import models.Map;
import models.Territory;

public class Map_Generator_View {
	Scanner scanner = new Scanner(System.in);
	Map_Generator_View map_Generator_View;

	@SuppressWarnings("resource")

	public int Display_Menu() {
		System.out.println("\n\t Map Generator");
		System.out.println("\n===========================");
		System.out.println("\n 1. Import From File");
		System.out.println("\n 2. Design By MySelf");
		System.out.println("\n\n Please Enter Your Choice(1,2): ");
		return scanner.nextInt();
	}

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

	public Map Display_Map_Designer() {
		int choice;
		boolean result = false;
		String addMore = null;
		String userRespond = null;
		String continentName;
		String territoryName;
		int xPosition;
		int yPosition;

		map_Generator_View = new Map_Generator_View();
		choice = map_Generator_View.Display_Menu_Design_Map();
		Map map = new Map();
		Continent continent;
		Territory territory;

		switch (choice) {
		case 1:// Add Continent
			do {
				System.out.println("\n enter continent name: ");
				continentName = scanner.nextLine();

				if (map.Get_Continent(continentName) == null) {
					continent = new Continent(continentName);
					result = map.Add_Continent(continent);
				}
				if (result) {
					System.out.println("\n\t Continent is added successfully");
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

				if (map.Get_Continent(continentName) != null) {
					territory = new Territory(territoryName, xPosition, yPosition, continentName);
					// put to hash map
					if (result) {
						System.out.println("\n\t territory is added successfully");
						System.out.println("\n===========================");
						System.out.println("\n Do you want to add another territory(y,n): ");
						addMore = scanner.nextLine().toLowerCase();
					}

				} else if (map.Get_Continent(continentName) == null) {
					System.out.println("\n the continent does not exist!");
					break;
				}
			} while (addMore.equals("y"));

			break;
		case 3:// Add Connection
			do {
				System.out.println("\n ");

			} while (result);

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
		if (map.IsEmpty())
			return;
		System.out.println(map.toString());
	}

}
