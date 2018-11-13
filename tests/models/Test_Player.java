package models;

import static org.junit.Assert.assertEquals;

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
	
	static Player testPlayer = new Player(0,new Game_Model(Map_Model.Get_Map())); 
	static Integer number_territories = 14; 
	static int number_armies;  
	Territory temp_territory; 

	/**
	 * this method assign value to members before the test began
	 */
	@Before public void setUp(){
		
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
		Assert.assertEquals(7, i);
	}
	

	
}
