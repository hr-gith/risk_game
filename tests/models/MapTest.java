package models;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * test class related to map object
 *
 */
public class MapTest {
	Map_Model map = new Map_Model();
	Continent new_continent;
	Territory new_territory;
	Territory new_territory_neighbour_1;
	Territory new_territory_neighbour_2;
	
	/**
	 * test if the is empty
	 */

	@Test
	public void testIs_Empty() {
		new_continent = new Continent("new_continent_name", 1);
		boolean result = map.Is_Empty();
		Assert.assertTrue(result);
	}
	/**
	 * test add continent
	 */

	@Test
	public void testAdd_Continent() {
		new_continent = new Continent("new_continent_name", 1);
		boolean result = map.Add_Continent(new_continent);
		Assert.assertTrue(result);
	}
	/**
	 * test add continent that existed
	 */
	
	@Test
	public void testAdd_Continent_Existed() {
		new_continent = new Continent("new_continent_name", 1);
		map.Add_Continent(new_continent);
		new_continent = new Continent("new_continent_name", 2);
		boolean result = map.Add_Continent(new_continent);
		Assert.assertFalse(result);
	}
	/**
	 * test delete continent
	 */

	@Test
	public void testDelete_Continent() {
		new_continent = new Continent("new_continent_name", 1);
		map.Add_Continent(new_continent);
		boolean result=map.Delete_Continent("new_continent_name");
		Assert.assertTrue(result);
	}
	/**
	 * test get existed territory 
	 */

	@Test
	public void testGet_Territory() {
		new_continent = new Continent("new_continent_name", 1);
		map.Add_Continent(new_continent);
		new_territory = new Territory("new_territory_name",120,320,"new_continent_name");
		new_continent.Add_Territory(new_territory);
		new_territory = map.Get_Territory("new_territory_name");
		Assert.assertNotNull(new_territory);
	}
	/**
	 * test get existed continent
	 */

	@Test
	public void testGet_Continent() {
		new_continent = new Continent("new_continent_name", 1);
		map.Add_Continent(new_continent);
		new_continent = map.Get_Continent("new_continent_name");
		Assert.assertNotNull(new_continent);
	}
	/**
	 * test get existed all territories
	 */

	@Test
	public void testGet_Territories() {
		new_continent = new Continent("new_continent_name", 1);
		new_territory = new Territory("new_territory_name",120,320,"new_continent_name");
		new_continent.Add_Territory(new_territory);
		HashMap<String, Territory> territories = new HashMap<>();
		territories = map.Get_Territories();
		Assert.assertNotNull(territories);
		
	}
	/**
	 * test the map is valid
	 */

	@Test
	public void testIs_Valid() {
         new_continent = new Continent("new_continent_name" , 1);
         map.Add_Continent(new_continent);
		
		new_territory = new Territory("new_territory_name_1",120,300,"new_continent_name");
		new_continent.Add_Territory(new_territory);
		
		new_territory_neighbour_1 = new Territory("new_territory_name_2",320,432,"new_continent_name");
		new_continent.Add_Territory(new_territory_neighbour_1);
		
		new_territory_neighbour_2 = new Territory("new_territory_name_3",350,470,"new_continent_name");
		new_continent.Add_Territory(new_territory_neighbour_2);
		
		new_territory.Add_Neighbour(new_territory_neighbour_1);
		new_territory.Add_Neighbour(new_territory_neighbour_2);
		new_territory_neighbour_1.Add_Neighbour(new_territory_neighbour_2);
		
		map.Is_Valid();
		
	}
	/**
	 * test Exist_Path
	 */

	@Test
	public void testExist_Path() {
		new_continent = new Continent("new_continent_name" , 1);
        map.Add_Continent(new_continent);
		
		new_territory = new Territory("new_territory_name_1",120,300,"new_continent_name");
		new_continent.Add_Territory(new_territory);
		
		new_territory_neighbour_1 = new Territory("new_territory_name_2",320,432,"new_continent_name");
		new_continent.Add_Territory(new_territory_neighbour_1);
		
		new_territory_neighbour_2 = new Territory("new_territory_name_3",350,470,"new_continent_name");
		new_continent.Add_Territory(new_territory_neighbour_2);
		
		new_territory.Add_Neighbour(new_territory_neighbour_1);
		new_territory.Add_Neighbour(new_territory_neighbour_2);
		new_territory_neighbour_1.Add_Neighbour(new_territory_neighbour_2);
		HashSet<Territory> territories=new HashSet<Territory>(new_continent.territories.values());
		
		Map_Helper.Exist_Path(territories, "new_territory_name_1","new_territory_name_3");
	}

}
