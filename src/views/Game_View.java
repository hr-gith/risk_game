package views;

import controllers.Game_Controller;
import controllers.Map_Generator_Controller;

import java.util.ArrayList;
import java.util.Scanner;

public class Game_View {
    Scanner scanner = new Scanner(System.in);
    Game_View game_View;
    Game_Controller game_controller;
    Map_Generator_Controller map_generator_controller;

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


        map_generator_controller = new Map_Generator_Controller();
        map_generator_controller.start();
    }


    public void Display_Menu_Fortification(int valid_number_move_armies) {
        System.out.println("\nEnter the From territory ");
        String from_territory = scanner.nextLine();
        System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of move armies");
        int number_armies = Integer.valueOf(scanner.nextLine());
        while (valid_number_move_armies < number_armies) {
            System.out.println("the number of armies must less than " + valid_number_move_armies);
            System.out.println("\nEnter the Number of armies");
            number_armies = Integer.valueOf(scanner.nextLine());
        }

        game_controller.Set_From_Territory(from_territory);
        game_controller.Set_To_Territory(to_territory);
        game_controller.Set_Number_of_move_armies(number_armies);

    }

    public void Display_Menu_Reinforcements(int max_number) {
        System.out.println("\nEnter the To territory ");
        String to_territory = scanner.nextLine();
        System.out.println("\nEnter the Number of move armies");
        int number_armies = Integer.valueOf(scanner.nextLine());
        while (max_number < number_armies) {
            System.out.println("the number of armies must less than " + max_number);
            System.out.println("\nEnter the Number of armies");
            number_armies = Integer.valueOf(scanner.nextLine());

        }
        game_controller.Set_From_Territory(to_territory);
        game_controller.Set_Number_of_move_armies(number_armies);

    }


}
