package models;

import static org.junit.Assert.assertEquals;

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

	static Integer number_of_players = 5; 
	static ArrayList<Player> player_list = new ArrayList<Player>();; 
	ArrayList<Player> shuffled_player_list; 
	/**
	 * this method runs before any test to assign value to each members
	 */
	@BeforeClass public static void setUp(){
		
		for(int i = 0; i<number_of_players; i++){
		
		player_list.add(new Player(i,new Game_Model(new Map_Model())));
		
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
//	@Ignore
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
	
}
