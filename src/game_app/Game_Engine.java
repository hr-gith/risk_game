package game_app;

import java.util.Scanner;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;
import controllers.Tournament_Controller;

/**
 * class for calling to start Map generator and Game
 */
public class Game_Engine {

	public static void main(String[] args) {
		Map_Generator_Controller map_generator = new Map_Generator_Controller();
		int choice = 0;
		boolean ready_state = false;
		do {
			choice = Display_Menu();
			switch (choice) {
			case 1://map editor
				ready_state = map_generator.start();
				break;
			case 2: //start game
				if (ready_state) {
					Game_Controller game = new Game_Controller(map_generator.map);
					game.map_view = map_generator.map_view;
					game.Start();
				}else
					System.out.println("\n\t Error: You should load a map first to play the game.");	
				break;
			case 3: //load game
				break;
			case 4: //save game
				break;
			case 5: //Tournament
				Tournament_Controller tournament_controler = new Tournament_Controller();
				tournament_controler.Setup();

				break;
			case 6://Exit
				System.exit(0);
				break;
			default :
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 6)");				
				break;
			}
			
		} while (choice != 6);
	}


//	private static void Setup_Tournament() {
//		Tournament_Controller tournament = new Tournament_Controller();
//		//TODO: get data for tournament
//		
//		tournament.Start();
//	}


	/**
	 * Display main menu for game engine
	 */

	public static int Display_Menu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Risk Game");
		System.out.println("\n==================================");
		System.out.println("\n 1. Map Editor");
		System.out.println("\n 2. Start Game");
		System.out.println("\n 3. Load Game");
		System.out.println("\n 4. Save Game");
		System.out.println("\n 5. Tournament");
		System.out.println("\n 6. Exit ");
		System.out.println("\n\n Please Enter Your Choice(1-6): ");
		return scanner.nextInt();
	}


}
