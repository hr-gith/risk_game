package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import models.Player;
import models.Territory;
/**
 * This is a junit class to test Player class of the Risk Game
 *
 */
public class Test_Player {
	static Game_Model game = Game_Model.Get_Game();
	static Player testPlayer = new Player(0,Color.BLUE,State_Player_Strategy.HUMAN,game); 
	static Integer number_territories = 14; 
	static int number_armies;  
	Territory temp_territory; 

	/**
	 * this method assign value to members before the test began
	 */
	@Before public void setUp(){
		game.map =  new Map_Model();
		for(int i = 0; i< number_territories; i++){
			Territory new_territory= new Territory(i, "Territory" + i, 0, 0, "");
			testPlayer.Add_Territory(new_territory);
			
		}
		
		 number_armies = testPlayer.owned_territories.size() / 3; 
	}
	
	/**
	 * this method test if the correct number of armies are assigned 
	 */
	@Test
	public void test_Armies_Assigned() {		
		assertEquals(4, number_armies); 
		assertEquals(2, (testPlayer.owned_territories.size()%3));	
		
	}
	
	/**
	 * this method test if armies are correctly placed in each territory
	 */
	@Test
	public void test_Army_Placment(){			
			
			Territory temp_territory = testPlayer.owned_territories.get("territory2");			
			temp_territory.nb_armies++; 			
			number_armies --; 
			
			assertEquals(3, number_armies); 
			assertEquals(1, temp_territory.nb_armies);		
	}
	/**
	 * test for reinforcement calculation
	 */
	@Test
	public void Test_Set_Number_Territory_Reinforcements() {
		testPlayer.Set_Number_Territory_Reinforcements();
		int i = testPlayer.reinforcements;
		assertEquals(7, i);
	}
	
	/**
	 * test if the player has extra enough army to move
	 */
	@Test
	public void Test_Has_Extra_Army_To_Move(){
		Territory temp_territory = testPlayer.owned_territories.get("territory1");			
		temp_territory.nb_armies = 3;
		assertTrue(testPlayer.Has_Extra_Army_To_Move());
	}

	
}
