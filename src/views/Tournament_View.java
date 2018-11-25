package views;


import java.util.Scanner;
import controllers.Tournament_Controller;
import models.State_Player_Strategy;


public class Tournament_View {
	Scanner scanner;

	Tournament_Controller tournament_controler;

	public Tournament_View(Tournament_Controller controler) {
		this.tournament_controler = controler;
	}

	public int Display_Tournament_Menu() {
		scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Tournament Mode");
		System.out.println("\n==================================");
		System.out.println("\n 1. For Entering Maps");
		System.out.println("\n 2. For Entering Number of Games at each Map");
		System.out.println("\n 3. For Entering Your Players Strategies");
		System.out.println("\n 4. For Entering Number of Turn at each Game");
		System.out.println("\n 5. Start tournament ");
		System.out.println("\n 6. Back to main menu ");
		System.out.println("\n\n Please Enter Your Choice(1-6): ");

		int result = Integer.valueOf(scanner.nextLine());
		return result;
	}

	public void Setup_Tournament() {
		
		int choice;

		do {
			choice = Display_Tournament_Menu();

			switch (choice) {
			
			case 1:// Map Files between 1 to 5
				scanner = new Scanner(System.in);
				System.out.println("\n Enter Number of Map: ");
				int map_number = Integer.valueOf(scanner.nextLine());
				String map_name = "";

				if (map_number < 1 && map_number > 5) {
					// msg to select between 1 to 5
					return;
				}

				for (int i = 0; i < map_number; i++) {
					System.out.println("\nMap"+(i+1)+": Enter the Name: ");
					map_name = scanner.nextLine();
					tournament_controler.Add_Map(map_name);
				}

				break;

			case 2: // number of games to be played on each map between 1 to 5
				scanner = new Scanner(System.in);
				System.out.println("\n Enter Number of Game on each Map: ");
				int game_number = Integer.valueOf(scanner.nextLine());
				if (game_number < 1 && game_number > 5) {
					// msg to select between 1 to 5
					return;
				}
				tournament_controler.nb_game = game_number;
				break;

			case 3: // Player Strategies between 2 to 4
				scanner = new Scanner(System.in);
				System.out.println("\n Enter Number of Players: ");
				int strategies_number = Integer.valueOf(scanner.nextLine());
				if (strategies_number < 2 && strategies_number > 4) {
					// msg to select between 1 to 5
					return;
				}
				for (int i = 0; i < strategies_number; i++) {
					System.out.println("\n Enter player name: ");
					String name = scanner.nextLine().toLowerCase();
					System.out.println("\n Enter the player's Strategy(AGGRESSIVE, BENEVOLENT, RANDOM, CHEATER): ");					
					String strategy = scanner.nextLine().trim().toUpperCase();
					
					//if(State_Player_Strategy.values().equals(strategy)) {
						tournament_controler.Add_Player(name, State_Player_Strategy.valueOf(strategy));//check if return false						
					//}else {
						// msg for entering correct strategy
					//}
				}

				break;

			case 4:// number of turn at each game 10 to 50
				scanner = new Scanner(System.in);
				System.out.println("\n Enter Number of Turn at each Game: ");
				int turn_number = Integer.valueOf(scanner.nextLine());
				if (turn_number < 10 && turn_number > 50) {
					// msg to select between 10 to 50
					return;
				}
				tournament_controler.max_nb_turn = turn_number;
				break;
				
			case 5://Start tournament
				try {
					if (!this.tournament_controler.Start())
						System.out.println("Error: Tournament can not be started...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 6://Back to main menu
				break;

			default:
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 6)");	
				break;
			}
		} while (choice != 6);
	}

}
