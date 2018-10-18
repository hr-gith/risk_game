package models;

import org.junit.Assert;
import org.junit.Test;

/**
 * test class related to continent object
 *
 */

public class ContinentTest {
	Continent new_continent;
	Territory new_territory;
	Territory new_territory_neighbour;
	
	/**
	 * test add territory to the continent
	 */

	@Test
	public void testAdd_Territory() {
		new_continent = new Continent("new_continent_name" , 1);
		new_territory = new Territory("new_territory_name",120,300,"test_name_continent");
		boolean result = new_continent.Add_Territory(new_territory);
		Assert.assertTrue(result);
	}
	
	/**
	 * test delete territory from the continent
	 *
	 */

	@Test
	public void testDelete_Territory() {
		new_continent = new Continent("new_continent_name" , 1);
		new_territory = new Territory("new_territory_name",120,300,"new_continent_name");
		new_continent.Add_Territory(new_territory);
		boolean result = new_continent.Delete_Territory("new_territory_name");
		Assert.assertTrue(result);
	}
	/**
	 * test delete territories from the continent
	 *
	 */

	@Test
	public void testDelete_Territories() {
		new_continent = new Continent("new_continent_name" , 1);
		
		new_territory = new Territory("new_territory_name_1",120,300,"new_continent_name");
		new_continent.Add_Territory(new_territory);
		
		new_territory_neighbour = new Territory("new_territory_name_2",320,432,"new_continent_name");
		new_continent.Add_Territory(new_territory_neighbour);
		
		new_territory.Add_Neighbour(new_territory_neighbour);
		
		boolean result = new_continent.Delete_Territories();
		
		Assert.assertTrue(result);
		
		
	}

}
