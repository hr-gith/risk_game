package views;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import controllers.Tournament_Controller;
import models.Map_Model;
import models.Player;

public class Tournament_View {
	public Set<Map_Model> maps;
	public Set<Player> players;
	Scanner scanner;
	
	Tournament_Controller tournament_controler;
	
	public Tournament_View(Tournament_Controller controler) {
		this.tournament_controler = controler;
		maps = new HashSet<Map_Model>();
		players = new HashSet<Player>();
		
	}
	
	public int Display_Menu() {
		scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Tournament Mode");
		System.out.println("\n==================================");
		System.out.println("\n 1. EnterNumber of Maps");
		System.out.println("\n 2. Enter Number of Games at each Map");
		System.out.println("\n 3. Enter Your Player Strategies");
		System.out.println("\n 4. Enter Number of Turn at each Game");
		System.out.println("\n 5. Back to The Main Menu");
		System.out.println("\n\n Please Enter Your Choice(1-5): ");
		
		int result = Integer.valueOf(scanner.nextLine());
		return result;
	}

	
	public void Setup_Tournament() {
		int choice;
		
		do {
			choice = Display_Menu();
	
			switch (choice) {
			case 1:// Map Files between 1 to 5
				
				break;

			case 2: //number of games to be played on each map between 1 to 5
				
				break;

			case 3: // Player Strategies between 2 to 4
				
				break;

			case 4:// number of turn at each game 10 to 50
				break;

			case 5: // return to main menu
				break;
				
			case 6:// Exit!
				break;

			default:
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 5)");
				break;
			}
		} while (choice != 6);
		
		//TODO: get data for tournament
		
	}
	

}
