package views;

import controllers.Game_Controller;
import models.Player;
import models.State_Game;
import models.Game_Model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.lang.Object;

/**
 * Information of players move Army and replace armies
 */
public class Console_View implements Observer{
    private Scanner scanner;
    public Game_Controller game_controller;
    public Player current_player;
    public State_Game current_state;
    public String message;
    private boolean first_time = true; 
    
    public Console_View(Game_Controller game_ctrl) {
    	game_controller = game_ctrl;
    	scanner = new Scanner(System.in);
    }
    /**
     * player info
     */
    public ArrayList<String> Display_Menu_Players() {
        scanner = new Scanner(System.in);
        System.out.println("\n\t Players Info ");
        System.out.println("\nPlease insert the number of players: ");

        int number_of_players = Integer.valueOf(scanner.nextLine());
        while (number_of_players < 2 || number_of_players > 6) {
            System.out.println("\n Number of players  has to be between 2 and 6 !!!");
            number_of_players = Integer.valueOf(scanner.nextLine());

        }
        
        ArrayList<String> name_of_players = new ArrayList<>();
        for (int i = 1; i <= number_of_players; i++) {

            System.out.println("\nEnter the name of player number " + i);
            String name_of_player = scanner.nextLine();
            name_of_players.add(name_of_player);

        }
        return name_of_players;
    }
    

    /**
     * number of movies and the destination country
     *
     * @param currentPlayer the instance of object of player.
     */      
    public void Display_Menu_StartUp_Reinforcements() {
        
		System.out.println("Reinforcement =>player : " + current_player.name + 
				"- Armies left: "+ current_player.reinforcements+ 
				"\n countries :" + current_player.owned_territories.keySet().toString());
        System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of move armies");
        int number_armies = Integer.valueOf(scanner.nextLine());
        while (current_player.reinforcements < number_armies) {
            System.out.println("the number of armies must less than " + current_player.reinforcements);
            System.out.println("\nEnter the Number of armies");
            number_armies = Integer.valueOf(scanner.nextLine());
        }
        game_controller.Reinforcement(to_territory, number_armies);
    }
    
    
    /**
     * number of movies and the destination country
     *
     * @param currentPlayer the instance of object of player.
     */
    public void Display_Menu_Reinforcements() {
    
		System.out.println("Reinforcement =>player : " + current_player.name + 
				"- Armies left: "+ current_player.reinforcements+ 
				"\n countries :" + current_player.owned_territories.keySet().toString());
		System.out.println("Cards Available: \n Infantry: " + current_player.cards.infantry + ", Cavalry: " + current_player.cards.cavalry + ", Artillery: " + current_player.cards.artillery); 
        
		while(current_player.cards.Is_Set_Available()){
			
			String decision;
			if((current_player.cards.artillery + current_player.cards.cavalry + current_player.cards.infantry) < 5 ){
				System.out.println("Do you want to play any reinforment cards? (y/n)" ); 
				decision = scanner.nextLine();
			}
			else {  decision = "y"; }
		 
			if(decision.equals("y") || decision.equals("yes"))
			{
				System.out.println("Which cards do you want to play? (infantry, cavalry, artillery) For example, cavalry, cavalry, artillery" );
				String played_cards = scanner.nextLine();
				String[] played_cards_array = played_cards.split(",");
				
				if(current_player.cards.Are_Playable(played_cards_array)){
					
					current_player.reinforcements = current_player.reinforcements + current_player.cards.Get_Card_Reinforcement_Qty();
					
					System.out.println("Reinforcement =>player : " + current_player.name + 
							"- Armies left: "+ current_player.reinforcements+ 
							"\n countries :" + current_player.owned_territories.keySet().toString());
					System.out.println("Cards Available: \n Infantry: " + current_player.cards.infantry + ", Cavalry: " + current_player.cards.cavalry + ", Artillery: " + current_player.cards.artillery);
					
					game_controller.game.Update_State(current_state, "");
					
					
				}		
			} else break; 		
		}		
		
		System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of armies to move");
        int number_armies = Integer.valueOf(scanner.nextLine());
        while (current_player.reinforcements < number_armies) {
            System.out.println("the number of armies must less than " + current_player.reinforcements);
            System.out.println("\nEnter the Number of armies");
            number_armies = Integer.valueOf(scanner.nextLine());
        }
        game_controller.Reinforcement(to_territory, number_armies);
    }
    
    public void Display_Menu_Attack() {
        System.out.println("Attack =>player : " + current_player.name + " has countries :" + current_player.owned_territories.keySet().toString());
        System.out.println("Would you like to attack(y/n)?");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
	        System.out.println("Enter your attacker territory:");
	        String from_territory = scanner.nextLine();
	        System.out.println("Enter a territory to attack: ");
	        String to_territory = scanner.nextLine();
	        
	        
	        System.out.println("Would you like to play in all out mode(y/n)? ");
	        answer = scanner.nextLine();
	        boolean all_out = (answer.equalsIgnoreCase("y"));
	        int number_dices = 0;
	        if (!all_out) {
	            System.out.println("Enter number of dices: ");
	            number_dices = Integer.valueOf(scanner.nextLine());
	        }
	        game_controller.Attack(from_territory, to_territory, number_dices, all_out);
        }
        else
        	game_controller.Move_To_Next_Phase();
    }
    
    /**
     * number or armies for replacement
     * @param currentPlayer instance of current player
     */
    public void Display_Menu_Fortification() {
    	
    	if(current_player.Has_Extra_Army_To_Move()) {
    		
	        System.out.println("Fortification =>player : " + current_player.name + " has countries :" + current_player.owned_territories.keySet().toString());
	        System.out.println("Do you want to fortify any units? (yes/no)");
	        String answer = scanner.nextLine(); 
	        
	        if(answer.equals("yes")){
		        System.out.println("\nEnter the From territory ");
		        String from_territory = scanner.nextLine();
		        System.out.println("\nEnter the To territory ");
		        String to_territory = scanner.nextLine();
		        System.out.println("\nEnter the Number of move armies");
		        int number_armies = Integer.valueOf(scanner.nextLine());
		
		        game_controller.Fortification(from_territory, to_territory, number_armies);
	        }
	        else{
	        	game_controller.Move_To_Next_Phase();
	        }
    	}
    	   else {
           	game_controller.Move_To_Next_Phase();
           }
           
     
    }
    
    public void Display_Winner(String winner) {
    	System.out.println("\n Congratulation "+winner+", you are the winner!!!");
    }

    public void Update_Menu() {
    	
    	
    	
    	switch(current_state){
    	
    	case SETUP: 
    		//modify the game_controller setup phase to go here
    		break;
    	case STARTUP: 
    		game_controller.card_view.jPanel.setVisible(false);
    		Display_Menu_StartUp_Reinforcements();
    		break;
    	case REINFORCEMENT: 
    		game_controller.card_view.jPanel.setVisible(true);

    		Display_Menu_Reinforcements();
    		break;
    	case ATTACKING:
    		game_controller.card_view.jPanel.setVisible(false);

    		Display_Menu_Attack();

    		break;
    	case FORTIFICATION: 
    		game_controller.card_view.jPanel.setVisible(false);

    		Display_Menu_Fortification();

    		break;
    	case POST_ATTACK: 
    		//Don't know what post_attack is
    		break;
 
    	}
    	
    	game_controller.RedrawViews();
    }
    
	@Override
	public void update(Observable obs, Object arg1) {
		
		
		
		current_player = ((Game_Model) obs).current_player;
		current_state = ((Game_Model) obs).current_state;
		message = ((Game_Model) obs).message;
		game_controller.map_view.Draw_Map(game_controller.game.map);
		game_controller.players_world_domination_view.Draw_Players_World_Domination_View_Window();
		game_controller.phase_view.Draw_Phase_View_Window();
		game_controller.card_view.Draw_Card_View();

		if(first_time){
		game_controller.game_view.Draw_Window();
		game_controller.game_view.Add_Panel(game_controller.map_view.jPanel, 1);
		game_controller.game_view.Add_Panel(game_controller.players_world_domination_view.jPanel, 1);
		game_controller.game_view.Add_Panel(game_controller.phase_view.jPanel, 1);
		game_controller.game_view.Add_Panel(game_controller.card_view.jPanel, 1);
		first_time = false; 
		}
		Update_Menu();
	}

    /**
     * it shows the Place army on their own countries
     *
     * @param currentPlayer
     * @return boolean yes or no to show if the player wants to place armies in other country
     *
    public Boolean Display_Menu_Replace_army(Player currentPlayer) {
        if (currentPlayer.owned_territories != null) {
            System.out.println("Replace Army =>>player : " + currentPlayer.player_name + " has countries :" + currentPlayer.owned_territories.keySet().toString());
            System.out.println("\nEnter the name of country ");
            String to_territory = scanner.nextLine();
            System.out.println("\nEnter the Number of place armies");
            int number_armies = Integer.valueOf(scanner.nextLine());

            game_controller.Set_Replace_To_Territory(to_territory);
            game_controller.Set_Replace_Number_Of_Move_Armies(number_armies);


            System.out.println("\nDo you want to replace armies in another country ?(yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes"))
                return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }*/


}