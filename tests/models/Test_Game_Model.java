package models;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.BeforeClass;
import org.junit.Test;

import models.Player;
/**
 * this is a junit class to test Game_Model class of the risk game 
 *
 */
public class Test_Game_Model {
	Game_Model game_model = Game_Model.Get_Game();
	Player only_Player = new Player(0,Color.BLUE,State_Player_Strategy.HUMAN,game_model);
	static Integer number_of_players = 5; 
	static ArrayList<Player> player_list = new ArrayList<Player>();; 
	ArrayList<Player> shuffled_player_list; 
	/**
	 * this method runs before any test to assign value to each members
	 */
	@BeforeClass 
	public static void setUp(){
		
		for(int i = 0; i<number_of_players; i++){
		Game_Model game = Game_Model.Get_Game();
		game.map = new Map_Model();
		player_list.add(new Player(i,Color.BLUE,State_Player_Strategy.HUMAN,game));
		}
	}
	/**
	 * method to test Player_list_Setup() 
	 */
	@Test
	public void test_Player_List_Setup(){
		
		System.out.println("Inside Player List Setup");
		
		for(int i = 0; i<number_of_players; i++){
			
			System.out.println(player_list.get(i).id); 
		}
		
	}
	/**
	 * method to test randomizing the player list
	 */
	@Test
	public void test_Player_List_Randomize(){
		
		System.out.println("Inside Player List Randomize");
		
		shuffled_player_list =  new ArrayList<Player>(player_list);
		
		Collections.shuffle(shuffled_player_list);
		
	for(int i = 0; i<number_of_players; i++){
			
			System.out.println(shuffled_player_list.get(i).id); 
		}
	
	
	assertEquals(false, shuffled_player_list.equals(player_list));
	
	}
	 /**
	  * method to test if the Game is correctly over when only one alive player remains
	  */
	@Test
	public void Test_Is_Game_Over(){
		game_model.current_player = only_Player;
		assertEquals(true, game_model.Is_Game_Over());
	}
	 /**
	  * method to test if the Game is Draw after specific number of Turns
	  */
	@Test
	public void Test_Is_Game_Draw(){
		
		game_model.current_player = only_Player;
		game_model.max_nb_turns = 20;
		game_model.turns_counter = 20;
		assertEquals(true, game_model.Is_Game_Draw());
	}
}
