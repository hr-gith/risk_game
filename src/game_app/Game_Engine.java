package game_app;

import java.util.Scanner;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;
import controllers.Tournament_Controller;
import models.Game_Model;
import utilities.File_Operations;

/**
 * class for calling to start Map generator and Game
 */
public class Game_Engine {
 /**
  * main method
  * @param args
  */
	public static void main(String[] args) {
		Map_Generator_Controller map_generator = new Map_Generator_Controller();
		int choice = 0;
		boolean ready_state = false;
		do {
			choice = Display_Menu();
			switch (choice) {
			case 1://map editor
				ready_state = map_generator.Start();
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
				System.out.println("Enter the filename to load the game: ");
				Scanner scanner = new Scanner(System.in);
				String filename = scanner.nextLine();
				//Game_Model game = Game_Model.Get_Game();
				Game_Model.Set_Game(File_Operations.Deserialize(filename));
				if (Game_Model.Get_Game() != null){
					Game_Controller game_ctrl = new Game_Controller(Game_Model.Get_Game());
					//game_ctrl.map_view = map_generator.map_view;
					game_ctrl.game.Update_State(game_ctrl.game.current_state, "Continue playing the game "+ filename);
				}
				else {
					System.out.println("Error: The game can not be loaded...");
				}
				break;
			case 4: //Tournament
				Tournament_Controller tournament_controler = new Tournament_Controller();
				tournament_controler.Setup();
				break;
			case 5://Exit
				break;
			default :
				System.out.println("\n==================================");
				System.out.println("\n\t Error! Please Enter Your Choice(1 to 5)");				
				break;
			}
			
		} while (choice != 5);
		System.exit(0);
	}


	/**
	 * method for Displaying main menu for game engine
	 */
	public static int Display_Menu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\n\t Risk Game");
		System.out.println("\n==================================");
		System.out.println("\n 1. Map Editor");
		System.out.println("\n 2. Start Game");
		System.out.println("\n 3. Load Game");
		System.out.println("\n 4. Tournament");
		System.out.println("\n 5. Exit ");
		System.out.println("\n\n Please Enter Your Choice(1-5): ");
		return scanner.nextInt();
	}


}
