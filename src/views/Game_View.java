package views;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;
import models.Player;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Information of players move Army and replace armies
 *
 * @author Elham
 */
public class Game_View {
    Scanner scanner = new Scanner(System.in);
    Game_View game_View;
    Game_Controller game_controller;
    Map_Generator_Controller map_generator_controller;

    /**
     * player info
     */
    public void Display_Menu_Players() {
        scanner = new Scanner(System.in);
        System.out.println("\n\t Players Info ");
        System.out.println("\nPlease insert the number of players!!!");
        try {

            int number_of_players = Integer.valueOf(scanner.nextLine());
            while (number_of_players < 2 || number_of_players > 6) {
                System.out.println("\n Number of players  has to be between 2 and 6 !!!");
                number_of_players = Integer.valueOf(scanner.nextLine());

            }
            game_controller = new Game_Controller();
            game_controller.Set_Number_Players(number_of_players);
            ArrayList<String> name_of_players = new ArrayList<>();
            for (int i = 1; i <= number_of_players; i++) {

                System.out.println("\nEnter the name of player number " + i);
                String name_of_player = scanner.nextLine();
                name_of_players.add(name_of_player);

            }
            game_controller.Set_Name_Players(name_of_players);

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * number or armies for replacement
     *
     * @param currentPlayer instance of current player
     */
    public void Display_Menu_Fortification(Player currentPlayer) {
        System.out.println("Fortification =>player : " + currentPlayer.player_name + " has countries :" + currentPlayer.owned_territories.keySet().toString());

        System.out.println("\nEnter the From territory ");
        String from_territory = scanner.nextLine();
        System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of move armies");
        int number_armies = Integer.valueOf(scanner.nextLine());

        game_controller.Set_From_Territory(from_territory);
        game_controller.Set_To_Territory(to_territory);
        game_controller.Set_Number_of_move_armies(number_armies);

    }

    /**
     * number of movies and the destination country
     *
     * @param currentPlayer the instance of object of player.
     */
    public void Display_Menu_Reinforcements(Player currentPlayer) {
        System.out.println("Reinforcement =>player : " + currentPlayer.player_name + " has countries :" + currentPlayer.owned_territories.keySet().toString());
        System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of move armies");
        int number_armies = Integer.valueOf(scanner.nextLine());
        while (currentPlayer.reinforcements < number_armies) {
            System.out.println("the number of armies must less than " + currentPlayer.reinforcements);
            System.out.println("\nEnter the Number of armies");
            number_armies = Integer.valueOf(scanner.nextLine());

        }
        game_controller.Set_From_Territory(to_territory);
        game_controller.Set_Number_of_move_armies(number_armies);

    }

    /**
     * it shows the Place army on their own countries
     *
     * @param currentPlayer
     * @return boolean yes or no to show if the player wants to place armies in other country
     */

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

    }


}
