package models;

import org.junit.Assert;
import org.junit.Test;

/**
 * test class related to territory object
 *
 */
public class TerritoryTest {
	Continent new_continent;
	Territory new_territory_1;
	Territory new_territory_2;
	Territory new_territory_3;
	
	/**
	 * test add  neighbour to the territory
	 */

	@Test
	public void testAdd_Neighbour() {
		new_continent = new Continent("new_continent_name"  , 1);
		
		new_territory_1 = new Territory("new_territory_name_1",120,300,"new_continent_name" );
		new_continent.Add_Territory(new_territory_1);
		
		new_territory_2 = new Territory("new_territory_name_2",320,432,"new_continent_name" );
		new_continent.Add_Territory(new_territory_2);
		
		boolean result = new_territory_1.Add_Neighbour(new_territory_2);
		Assert.assertTrue(result);
	}
	
	/**
	 * test delete  neighbour from the territory
	 */
	
	@Test
	public void testDelete_Neighbour() {
		new_continent = new Continent("new_continent_name"  , 1);
		
		new_territory_1 = new Territory("new_territory_name_1",120,300,"new_continent_name" );
		new_continent.Add_Territory(new_territory_1);
		
		new_territory_2 = new Territory("new_territory_name_2",320,432,"new_continent_name" );
		new_continent.Add_Territory(new_territory_2);
		
		new_territory_1.Add_Neighbour(new_territory_2);
		
		boolean result = new_territory_1.Delete_Neighbour("new_territory_name_2");
		Assert.assertTrue(result);
		
	}
	/**
	 * test delete all of the neighbours from the territory
	 */

	@Test
	public void testDelete_Neighbours() {
		new_continent = new Continent("new_continent_name"  , 1);
		new_territory_1 = new Territory("new_territory_name_1",120,300,"new_continent_name" );
		new_continent.Add_Territory(new_territory_1);
		new_territory_2 = new Territory("new_territory_name_2",320,432,"new_continent_name" );
		new_continent.Add_Territory(new_territory_2);
		new_territory_3 = new Territory("new_territory_name_3",320,432,"new_continent_name" );
		new_continent.Add_Territory(new_territory_3);
		
		boolean result = new_territory_1.Delete_Neighbours();
		Assert.assertTrue(result);
	}

}
