package views;

import controllers.Game_Controller;
import models.Player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.lang.Object;

/**
 * Information of players move Army and replace armies
 *
 * @author Elham
 */
public class Game_View {
    Scanner scanner = new Scanner(System.in);
    Game_Controller game_controller;

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
    public AbstractMap.SimpleEntry<String, Integer> Display_Menu_Reinforcements(Player player) {
    
		System.out.println("Reinforcement =>player : " + player.name + "- Armies left: "+ player.reinforcements+ "\n countries :" + player.owned_territories.keySet().toString());
        System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of move armies");
        int number_armies = Integer.valueOf(scanner.nextLine());
        while (player.reinforcements < number_armies) {
            System.out.println("the number of armies must less than " + player.reinforcements);
            System.out.println("\nEnter the Number of armies");
            number_armies = Integer.valueOf(scanner.nextLine());
        }
        return new AbstractMap.SimpleEntry<String, Integer>(to_territory, number_armies);    	
    }
    
    /**
     * number or armies for replacement
     *
     * @param currentPlayer instance of current player
     */
    public void Display_Menu_Fortification(Player currentPlayer) {
        System.out.println("Fortification =>player : " + currentPlayer.name + " has countries :" + currentPlayer.owned_territories.keySet().toString());
        System.out.println("Do you want to move any units? (yes/no)");
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
    }
    
    public void Display_Winner(String winner) {
    	System.out.println("\n Congratulation "+winner+", you are the winner!!!");
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
