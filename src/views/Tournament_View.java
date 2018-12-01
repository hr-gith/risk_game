package views;

import java.util.Scanner;
import controllers.Tournament_Controller;
import models.State_Player_Strategy;

/**
 * this class is responsible for setup tournament and gathering information and
 * pass to the controller
 */
public class Tournament_View {
	Scanner scanner;

	Tournament_Controller tournament_controler;

	/**
	 * constructor for creating tournament controller object
	 * 
	 * @param controler
	 */
	public Tournament_View(Tournament_Controller controler) {
		this.tournament_controler = controler;
	}

	/**
	 * this method is responsible for getting information and check correctness and
	 * pass to the tournament controller
	 */
	public void Setup_Tournament() {

		String new_input = "n";
		String new_strategy_input = "n";
		boolean add_player_result = false;

		System.out.println("\n==================================");
		System.out.println("\n\t Tournament Mode");

		// Entering Map
		System.out.println("\n==================================");
		System.out.println("\n\t  MAP ");
		System.out.println("\n==================================");

		do {
			scanner = new Scanner(System.in);
			System.out.println("\n Enter Number of Map: ");
			int map_number = Integer.valueOf(scanner.nextLine());
			String map_name = "";

			if (map_number < 1 || map_number > 5) {
				System.out.println("\n\t Please enter between 1 to 5 ");
				System.out.println("\n\t Try Again");
				new_input = "y";

			} else {
				for (int i = 0; i < map_number; i++) {
					System.out.println("\nMap" + (i + 1) + ": Enter the Map Name: ");
					map_name = scanner.nextLine();
					tournament_controler.Add_Map(map_name);
					new_input = "n";
				}
			}

		} while (new_input.equals("y"));

		// number of games to be played on each map between 1 to 5
		System.out.println("\n==================================");
		System.out.println("\n\t GAME");
		System.out.println("\n==================================");

		do {
			scanner = new Scanner(System.in);

			System.out.println("\n Enter Number of Game on each Map: ");
			int game_number = Integer.valueOf(scanner.nextLine());

			if (game_number < 1 || game_number > 5) {
				System.out.println("\n\t Please enter between 1 to 5 ");
				System.out.println("\n\t Try Again");
				new_input = "y";

			} else {
				tournament_controler.nb_game = game_number;
				new_input = "n";
			}

		} while (new_input.equals("y"));

		// Players Strategies
		System.out.println("\n==================================");
		System.out.println("\n\t PLAYER ");
		System.out.println("\n==================================");

		do {
			scanner = new Scanner(System.in);
			System.out.println("\n Please Enter Number of Players: ");

			int player_number = Integer.valueOf(scanner.nextLine());

			if (player_number < 2 || player_number > 4) {

				System.out.println("\n\t Please enter between 2 to 4 ");
				System.out.println("\n\t Try Again");
				new_input = "y";

			} else {

				for (int i = 0; i < player_number; i++) {

					do {
						System.out.println("\n Enter Name of Player: ");
						String name = scanner.nextLine().toLowerCase();

						System.out.println("\n Enter player Strategy: "
								+ "player's Strategies should be (AGGRESSIVE, BENEVOLENT, RANDOM, CHEATER):");
						String strategy = scanner.nextLine().trim().toUpperCase();

						if (!(strategy.equals("AGGRESSIVE") || strategy.equals("BENEVOLENT")
								|| strategy.equals("RANDOM") || strategy.equals("CHEATER"))) {

							System.out.println("\n\t Wrong Input Strategy !! ");
							System.out.println("\n\t Try Again");
							new_strategy_input = "y";

						} else {

							add_player_result = tournament_controler.Add_Player(name,
									State_Player_Strategy.valueOf(strategy));

							if (add_player_result != true) {

								System.out.println("\n\t Error: Failed to add the player! ");
								System.out.println("\n\t Try Again ");
								new_strategy_input = "y";

							} else {
								System.out.println("\n==================================");
								System.out.println("\n\t Player is added successfully ");
								System.out.println("\n==================================");
								new_strategy_input = "n";
							}
						}

					} while (new_strategy_input.equals("y"));
				}
			}

		} while (new_input.equals("y"));

		// Enter Turn at each Game
		System.out.println("\n==================================");
		System.out.println("\n\t PLAY");
		System.out.println("\n==================================");

		do {
			scanner = new Scanner(System.in);
			System.out.println("\n Please enter Nember of The Turn at Each Game: ");
			int turn_number = Integer.valueOf(scanner.nextLine());

			if (turn_number < 10 && turn_number > 50) {
				System.out.println("\n\t Please enter between 10 to 50 ");
				System.out.println("\n\t Try Again:");
				new_input = "y";

			} else {
				tournament_controler.max_nb_turn = turn_number;
				new_input = "n";
			}

		} while (new_input.equals("y"));

		// Starting Tournament
		System.out.println("\n==================================");
		System.out.println("\n\t Starting Tournament");
		System.out.println("\n==================================");

		try {
			if (!this.tournament_controler.Start())
				System.out.println("\n\t Error: Tournament can not be started...");
			System.out.println("\n==================================");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
