package models;

import static org.junit.Assert.fail;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

/**
 * test class related to map object
 *
 */
public class MapTest {
	Map map = Map.Get_Map();
	Continent new_continent;
	Territory new_territory;
	
	/**
	 * test if the is empty
	 */

	@Test
	public void testIs_Empty() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}
	/**
	 * test Exist_Path
	 */

	@Test
	public void testExist_Path() {
		fail("Not yet implemented");
	}

}
