package test_model_GameController;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.*;
import static org.junit.Assert.*; 

import model_GameController.Player;


public class Test_SetupPhase {
	
	
	static Integer number_of_players = 5; 
	static ArrayList<Player> player_list = new ArrayList<Player>();; 
	ArrayList<Player> shuffled_player_list; 
	
	@BeforeClass public static void setUp(){
		
		for(int i = 0; i<number_of_players; i++){
		
		player_list.add(new Player(i));
		
		}
	}
	
	@Test
	public void test_Player_List_Setup(){
		
		System.out.println("Inside Player List Setup");
		
		for(int i = 0; i<number_of_players; i++){
			
			System.out.println(player_list.get(i).player_id); 
		}
		
		
	}
	
//	@Ignore
	@Test
	public void test_Player_List_Randomize(){
		
		System.out.println("Inside Player List Randomize");
		
		shuffled_player_list =  new ArrayList<Player>(player_list);
		
		Collections.shuffle(shuffled_player_list);
		

		
	for(int i = 0; i<number_of_players; i++){
			
			System.out.println(shuffled_player_list.get(i).player_id); 
		}
	
	
	assertEquals(false, shuffled_player_list.equals(player_list));
	
	}
	
	
}
