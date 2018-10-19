package models;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;
import views.Game_View;

import java.util.*;
import java.util.Map;

import java.util.Scanner;

/**
 * Game_Model Class is a model 
 * Controls the flow of the game logic for the entire game
 */
public class Game_Model {

	static Scanner scanner = new Scanner(System.in);

    static Integer number_of_players;
    static ArrayList<String> name_of_players;


    static Map_Generator_Controller map_generator;

    static Game_Controller game_controller;

    static Game_View game_view;

    static HashMap<String, Territory> game_territories;

    static ArrayList<Player> player_list;
    static ArrayList<Player> active_player_list;
    static ArrayList<Player> defeated_player_list;

    public static Boolean isFighting;

	/** 
	 * Controls the game logic for the game setup phase
	 * 
	 */
    
    public static void Game_Setup() {

        game_view = new Game_View();
        //Game setup phase

        map_generator = new Map_Generator_Controller();
        game_controller = new Game_Controller();
        map_generator.start();

        game_view.Display_Menu_Players();

        //GAME CONTROLLER INPUT //PROMPT number of players instead of hard coding
        
        

        number_of_players = game_controller.Get_Num_Players();
        name_of_players = game_controller.Get_Name_Players();


        //GAME CONTROLLER INPUT //PROMPT OPTIONAL FOR BUILD 2, insert the player names and modify the ArrayList type

        player_list = new ArrayList<Player>(number_of_players);

        Player_List_Setup(player_list);
        active_player_list = Player_List_Randomize(player_list);
        defeated_player_list = new ArrayList<Player>();

        //GAME-CONTROLLER OUTPUT
        Print_List_Order(active_player_list);


        game_territories = map_generator.map.Get_Territories();

        Assign_Territories();
        
//        for (Player p : player_list) {
//            if (p.owned_territories != null && p.owned_territories.size() > 0) {
//                Boolean want_to_replace = Boolean.TRUE;
//                while (want_to_replace) {
//                    want_to_replace = game_view.Display_Menu_Replace_army(p);
//                    String territory_name = game_controller.Get_Replace_To_Territory();
//                    Territory territory = map_generator.map.Get_Territory(territory_name);
//                    if (territory != null) {
//                        territory.nb_armies += game_controller.Get_Replace_Number_Of_Move_Armies();
//                    } else {
//                        System.out.println("the input territory is invalid");
//                    }
//                }
//
//            }
//        }


        for (Player p : active_player_list) {
            p.Number_StartUp_Reinforcements();
//			p.Number_Territory_Reinforcements();
            p.state_game_phase = State_GamePhase.STARTUP;
        }

        //GAME CONTROLLER OUTPUT
        System.out.println("Ready to Begin?");
        // GAME CONTROLLER INPUT //PROMPT ready to begin
        // y/ to continue


        isFighting = true;
    }

    static Integer current_player_order = 0;
    static boolean player_flag = true;

    //output which player is up
    
    
    
	/** 
	 * Controls the game logic and process flow once the setup is complete and the game begins
	 */
    
    public static void Game_Loop() {

        //Game-Phase


        //game loop

        Player current_player = active_player_list.get(current_player_order);

        if (player_flag) {

            System.out.println(current_player.toString());
            player_flag = false;

        }

        //GAME_CONTROLLER OUTPUT to display the active player only if new

        if (active_player_list.size() > 1) {

            //GAME CONTROLLER OUTPUT
            Print_State_Once(current_player, current_player.state_game_phase.toString());


            switch (current_player.state_game_phase) {

                case STARTUP:

                    current_player.old_state_game_phase = State_GamePhase.STARTUP;
                    // does player have armies to place?
                    	
                    System.out.println("Number of armies to place");
                    System.out.println(current_player.reinforcements); 

                    if (current_player.reinforcements > 0) {
                        // GAME CONTROLLER OUTPUT
                        Print_State_Once(current_player, "Place army on territory");
                        
                        System.out.println("Fortification =>player : " + current_player.player_name + " has countries :" + current_player.owned_territories.keySet().toString());


                        // //GAME CONTROLLER INPUT  //prompt active player to place an army on a territory
                        System.out.println("Input territory name");
                        
                        String t_in = scanner.nextLine(); 

                        //replace argument in below with game controller input
                        current_player.Add_Army(t_in);


                        // update active player
                        current_player_order = (current_player_order + 1) % Game_Model.number_of_players;
                        player_flag = true;


                    } else {
                        //n -> set player to reinforcement phase


                        current_player.state_game_phase = State_GamePhase.REINFORCEMENT;

                    }


                    break;
                // Reinforcements
                case REINFORCEMENT:

                    if (current_player.old_state_game_phase != State_GamePhase.STARTUP)
                        current_player.Number_Territory_Reinforcements();

                    
                    
                    current_player.old_state_game_phase = State_GamePhase.REINFORCEMENT;


                    if (current_player.reinforcements > 0) {
                        // GAME CONTROLLER OUTPUT
                        game_view.Display_Menu_Reinforcements(current_player);
                        //todo fix it
                        //now we have  the amount of them
//                        game_controller.Get_Reinforcement_Number_of_move_armies();
//                        game_controller.Get_Reinforcement_To_Territory();

                        Print_State_Once(current_player, "Place any Reinforcement");


                        // //GAME CONTROLLER INPUT  //prompt active player to place an army on a territory

                        //replace argument in below with game controller input
                        current_player.Add_Army(current_player.owned_territories.keySet().iterator().next());


                    } else {
                        //n -> set player to reinforcement phase


                        current_player.state_game_phase = State_GamePhase.ATTACKING;

                    }

                    //check if card set can be used
                    //if so ask if user wants to play cards

                    //take input
                    //y-> add armies to reinforcement list, decrement cards, increase cards played

                    //if reinforcement list is empty
                    //n-> prompt user to place troops
                    //take input and update troop positions
                    // update map
                    //y -> notify user they are finished fortifying and are now ready to attack
                    // transition to attack phase

                    break;

                //Attack Phase
                case ATTACKING:

                    current_player.old_state_game_phase = State_GamePhase.ATTACKING;
                    //check if user satisfies any territories to attack from
                    //y-> update map with potential attackers
                    //prompt attack move
                    // call attack method
                    // update army counts and army locations on map
                    // if defeated Player
                    //increment cards
                    //move to reinforcement phase

                    //or  end attack phase
                    // increment cards if conquered
                    //set phase to fortification


                    //n-> notify user they are finished attacking and are now ready to fortify
                    // increment cards if conquered
                    //set phase to fortification

                    current_player.state_game_phase = State_GamePhase.FORTIFICATION;

                    break;

                // Fortification Phase
                case FORTIFICATION:

                    current_player.old_state_game_phase = State_GamePhase.FORTIFICATION;

//                    game_view.Display_Menu_Fortification(current_player);
                    

                    //Test if any units to fortify
                    if (Units_To_Move(current_player)) {

                        Print_State_Once(current_player, "Enter Fortifications");
                        
                        System.out.println("Do you want to move any units? (yes/no)");
                        String answer = scanner.nextLine(); 
                        
                        if(answer.equals("yes")){
                        	 //PROMPT prompt user for troop movements
                            System.out.println("Input territory 1");
                            String t_1 = scanner.nextLine(); 
                            
                            
                            System.out.println("Input territory 2");
                            String t_2 = scanner.nextLine(); 
                            //todo elham test it
                            
                            System.out.println("Input number of armies to move"); 
                            
                            String t_3 = scanner.nextLine(); 
                            
                            
                            current_player.Move_Army(current_player.owned_territories.get(t_1), current_player.owned_territories.get(t_2), Integer.valueOf(t_3)); 
//                            current_player.Move_Army(map_generator.map.Get_Territory(game_controller.Get_From_Territory()), map_generator.map.Get_Territory(game_controller.Get_To_Territory()), game_controller.Get_Number_of_move_armies());
                        
                            game_view.Display_Menu_Reinforcements(current_player);
                            
                        }else{
                        	  current_player_order = (current_player_order + 1) % Game_Model.number_of_players;
                              player_flag = true;
                        	
                        	
                        }
                        

                       
                    } else {


                        current_player_order = (current_player_order + 1) % Game_Model.number_of_players;
                        player_flag = true;
                    }

                    break;

            }


        } else {
            // game over
            isFighting = false;
            System.out.println("GAME OVER: PLAYER " + active_player_list.get(0) + " HAS CONQUERED THE WORLD!");
        }


    }

	/** 
	 * Sets up the Player list with their name and corresponding player name
	 * @param ArrayList<Player> the list of players in the game
	 */
    
    private static void Player_List_Setup(ArrayList<Player> player_list) {

//	System.out.println("Setting up Players");
        int i = 0;
        for (String s : name_of_players) {

            player_list.add(i, new Player(i, s));
            i++;
        }

    }
    
	/** 
	 * Randomizes the player's turn order upon the setup of the game. 
	 * @param ArrayList<Player> the list of players in the game
	 * @return A randomized order of players. Type: (ArrayList<Player>) 
	 */

    private static ArrayList<Player> Player_List_Randomize(ArrayList<Player> player_list) {

//		System.out.println("Randomizing Players");

        ArrayList<Player> shuffled_player_list = new ArrayList<Player>(player_list);

        Collections.shuffle(shuffled_player_list);


        return shuffled_player_list;


    }

	/** 
	 * Prints the order of the players in the game
	 * @param ArrayList<Player> the list of players in the game
	 */
    
    private static void Print_List_Order(ArrayList<Player> list_to_print) {


        for (int i = 0; i < list_to_print.size(); i++) {

            System.out.println(list_to_print.get(i).player_id);
        }

    }
    
	/** 
	 * Assigns the game territories acrross the players playing the game during the setup
	 */

    private static void Assign_Territories() {


//	List keys = new ArrayList(game_territories.keySet());
//	Collections.shuffle(keys);
//
//	//add neutrals if needed 
//	
//	int index = 0; 
//	
//	
//	for (Object obj : keys) {
//	    // Access keys/values in a random order
//		
//		if(index<(Game_Model.number_of_players - (game_territories.size()%Game_Model.number_of_players))){
//			active_player_list.get((index%Game_Model.number_of_players)).Add_Territory(game_territories.get(obj));	
//			game_territories.get(obj).owner_id = index; 
//		}
//		
//		index++;
//		 
//	}

        int index = 0;

        Iterator it = game_territories.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry entry = (Map.Entry) it.next();
            Territory territory = (Territory) entry.getValue();

            if (index < (game_territories.size() - (game_territories.size() % Game_Model.number_of_players))) {
                active_player_list.get((index % Game_Model.number_of_players)).Add_Territory(territory);
                territory.owner_name = active_player_list.get(index % Game_Model.number_of_players).player_name;
            }
            index++;
        }


    }

    
	/** 
	 * Checks whether a player has any units that are capable of moving territories
	 * @param Player The current game player
	 * @return A boolean value representing whether the player is capable of moving any units
	 */
    
    public static Boolean Units_To_Move(Player player) {

        for (String key : player.owned_territories.keySet()) {

            if (player.owned_territories.get(key).nb_armies > 1)

                return true;
        }

        return false;
    }

	/** 
	 * Prints the current game phase of a player at the start of the phase
	 * @param Player The current game player
	 * @param String The current game state
	 */

    public static void Print_State_Once(Player player, String input) {

        if (player.state_game_phase != player.old_state_game_phase) {
            System.out.println(input);
        }


    }

	/** 
	 * Prints the current game phase of a player at the start of the phase
	 * @return A boolean value corresponding to whether more than one player are still playing the game.
	 */

    public static Boolean isStillFighting() {

        return isFighting;
    }


}
	


