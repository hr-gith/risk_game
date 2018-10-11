package test_model_GameController;

import models.Territory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import org.junit.*;
import static org.junit.Assert.*; 

import model_GameController.Player;


public class Test_StartupPhase {
	
	static Player testPlayer = new Player(0); 
	static Integer number_territories = 14; 
	static int number_armies;  
	Territory temp_territory; 
	

	
	
	@Before public void setUp(){
		
		for(int i = 0; i< number_territories; i++){
			Territory new_territory= new Territory(i, "Territory" + i, 0, 0, "");
			testPlayer.Add_Territory(new_territory);
		
		   	
		}
		
		 number_armies = testPlayer.owned_territories.size() / 3; 
	}
	
//	@Ignore
	@Test
	public void test_Armies_Assigned(){
		
		
		
		assertEquals(4, number_armies); 
		assertEquals(2, (testPlayer.owned_territories.size()%3));
	
		
	}
	
	
	@Test
	public void test_Army_Placment(){
		
		
			
			Territory temp_territory = testPlayer.owned_territories.get("territory2"); 
			
			temp_territory.nb_armies++; 
			
			
			number_armies --; 
			
			assertEquals(3, number_armies); 
			assertEquals(1, temp_territory.nb_armies); 
		
		
		
	}
	
	

	
}
