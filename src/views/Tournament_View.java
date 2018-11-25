package views;

import java.util.Scanner;
import controllers.Tournament_Controller;
import models.State_Player_Strategy;

/**
 * this class is responsible for setup tournament and gathering information and pass to the controller
 *
 */
public class Tournament_View {
	Scanner scanner;

	Tournament_Controller tournament_controler;

	/**
	 * constructor for creating tournament controller object
	 * @param controler
	 */
	public Tournament_View(Tournament_Controller controler) {
		this.tournament_controler = controler;
	}

	/**
	 * Display menu for gathering information for tournament
	 * @return integer result based on choice and pass it to the setup tournament function
	 */
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
		System.out.println("\n 6. Exit ");
		System.out.println("\n\n Please Enter Your Choice(1-6): ");

		int result = Integer.valueOf(scanner.nextLine());
		return result;
	}

	/**
	 * this method is responsible for getting information and check correctness and pass to the tournament controller
	 */
	public void Setup_Tournament() {

		int choice;
		String new_input = "n";
		String new_strategy_input = "n";
		boolean add_player_result = false;
		

		do {
			choice = Display_Tournament_Menu();

			switch (choice) {

			case 1:// Map Files between 1 to 5

				do {
					scanner = new Scanner(System.in);
					System.out.println("\n Enter Number of Map: ");
					int map_number = Integer.valueOf(scanner.nextLine());
					String map_name = "";

					if (map_number < 1 || map_number > 5) {
						System.out.println("\n==================================");
						System.out.println("\n\t Please enter between 1 to 5 ");
						System.out.println("\n==================================");
						System.out.println("\n\t Do you want to enter again(y,n):");
						new_input = scanner.nextLine().toLowerCase();

					} else {
						for (int i = 0; i < map_number; i++) {
							System.out.println("\nMap" + (i + 1) + ": Enter the Name: ");
							map_name = scanner.nextLine();
							tournament_controler.Add_Map(map_name);
							new_input="n";
						}
					}

				} while (new_input.equals("y"));

				break;

			case 2: // number of games to be played on each map between 1 to 5
				do {
					scanner = new Scanner(System.in);
					System.out.println("\n Enter Number of Game on each Map: ");
					int game_number = Integer.valueOf(scanner.nextLine());

					if (game_number < 1 || game_number > 5) {
						System.out.println("\n==================================");
						System.out.println("\n\t Please enter between 1 to 5 ");
						System.out.println("\n==================================");
						System.out.println("\n\t Do you want to try again(y,n):");
						new_input = scanner.nextLine().toLowerCase();

					} else {
						tournament_controler.nb_game = game_number;
						new_input="n";
					}

				} while (new_input.equals("y"));

				break;

			case 3: // Player Strategies between 2 to 4
				do {
					scanner = new Scanner(System.in);
					System.out.println("\n Enter Number of Players: ");
					int strategies_number = Integer.valueOf(scanner.nextLine());

					if (strategies_number < 2 || strategies_number > 4) {
						System.out.println("\n==================================");
						System.out.println("\n\t Please enter between 2 to 4 ");
						System.out.println("\n==================================");
						System.out.println("\n\t Do you want to try again(y,n):");
						new_input = scanner.nextLine().toLowerCase();

					} else {

						
							for (int i = 0; i < strategies_number; i++) {
								do {
									System.out.println("\n Enter player name: ");
									String name = scanner.nextLine().toLowerCase();

									System.out.println(
											"\n Enter the player's Strategy(AGGRESSIVE, BENEVOLENT, RANDOM, CHEATER): ");
									String strategy = scanner.nextLine().trim().toUpperCase();									
									
									/*if (!State_Player_Strategy.values().equals(strategy)) {
										System.out.println("\n==================================");
										System.out.println("\n\t Please enter correct strategy ");
										System.out.println("\n==================================");
										System.out.println("\n\t Do you want to try again(y,n):");
										new_strategy_input = scanner.nextLine().toLowerCase();
									}else {*/
										add_player_result = tournament_controler.Add_Player(name,State_Player_Strategy.valueOf(strategy));
										
										if (add_player_result != true) {
											
											System.out.println("\n==================================");
											System.out.println("\n\tError: Failed to add the player! ");
											System.out.println("\n==================================");
											System.out.println("\n\t Do you want to try again(y,n):");
											new_strategy_input = scanner.nextLine().toLowerCase();
											
										}else {
											System.out.println("\n==================================");
											System.out.println("\n\t Player is added successfully ");
											new_strategy_input ="n";
										}
									//}
									
								}while(new_strategy_input.equals("y"));	
							}
					}
					
				} while (new_input.equals("y"));

				break;

			case 4:// number of turn at each game 10 to 50
				do {
					scanner = new Scanner(System.in);
					System.out.println("\n Enter Number of Turn at each Game: ");
					int turn_number = Integer.valueOf(scanner.nextLine());

					if (turn_number < 10 && turn_number > 50) {
						System.out.println("\n==================================");
						System.out.println("\n\t Please enter between 10 to 50 ");
						System.out.println("\n==================================");
						System.out.println("\n\t Do you want to try again(y,n):");
						new_input = scanner.nextLine().toLowerCase();

					} else {
						tournament_controler.max_nb_turn = turn_number;
						new_input ="n";
					}

				} while (new_input.equals("y"));

				break;

			case 5:// Start tournament
				try {
					if (!this.tournament_controler.Start())
						System.out.println("Error: Tournament can not be started...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 6:// Back to main menu

				break;

			default:
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 6)");
				break;
			}
		} while (choice != 6);
	}

}
