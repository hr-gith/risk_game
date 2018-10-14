package models;

import java.util.ArrayList;
import java.util.Collections;

import models.*; 



public class Game_Model{
	


	 static Integer number_of_players; 
	// prompt number of players
	// do we have max players
	// assign players names
	
	Map map; 
	
	static ArrayList<Player> player_list; 
	
	
	

	
	
	//output which player is up
	public static void game_loop(String[] args){
	
		
		//Game setup phase
			//prompt number of players
		number_of_players = 5; 
		
		 player_list = new ArrayList<Player>(number_of_players);
		
		Player_List_Setup(player_list); 
		ArrayList<Player> active_player_list = Player_List_Randomize(player_list);	
		ArrayList<Player> defeated_player_list = new ArrayList<Player>(); 
		
			
			//prompt map
			//randomly assign territories
			//randomly assign armies 
			//assign order
			// ask ready to begin
		
		//Game-Phase 
		
			
		//game loop
		
		PrintListOrder(active_player_list);
		
		
		Integer current_player_order = 0;
		Player current_player = active_player_list.get(current_player_order); 
		
		 
		
			while(active_player_list.size() > 1){
			
				//get active player 
				
				switch(current_player.state_game_phase){
					
					case STARTUP: 
						// does player have armies to place?
							// y->  prompt active player to place an army on a territory 
								// update active player
						//n -> set player to reinforcement phase 
						
						break ;
					// Reinforcements
					case REINFORCEMENT: 
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
						
						
						
						break;
						
					// Fortification Phase
					case FORTIFICATION: 
						//Test if any units to fortify
							//y-> prompt user for troop movements 
								//test if continuous path between territories exists (A* or Dijkstra)
								//y-> update troop location
									//ensure one troop is left on any location 
								
						
						
						break; 
				
				
					
				}
				
				
			
			}
		
		
				
		
		
		
		
		
	
	}
	
	
private static void Player_List_Setup(ArrayList<Player> player_list){
		
//	System.out.println("Setting up Players");
		
		for(int i = 0; i<number_of_players; i++){
			
			player_list.add(i,new Player(i));
			
		}

	}
	
private static ArrayList<Player> Player_List_Randomize(ArrayList<Player> player_list){
		
//		System.out.println("Randomizing Players");
		
	 	ArrayList<Player> shuffled_player_list =  new ArrayList<Player>(player_list);
		
		Collections.shuffle(shuffled_player_list);
		
		
		return shuffled_player_list; 

	
	}

private static void PrintListOrder(ArrayList<Player> list_to_print){
	
	
	for(int i = 0; i<list_to_print.size(); i++){
		
		System.out.println(list_to_print.get(i).player_id); 
	}

}
	
}
