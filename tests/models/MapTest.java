package models;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MapTest {

	@Test
	public void testIs_Empty() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd_Continent() {
		Map map = Map.Get_Map();
		Continent new_continent = new Continent("Test_Name", 1);
		boolean result = map.Add_Continent(new_continent);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testAdd_Continent_Existed() {
		Map map = Map.Get_Map();
		Continent new_continent = new Continent("Test_Name", 1);
		map.Add_Continent(new_continent);
		new_continent = new Continent("Test_Name", 2);
		boolean result = map.Add_Continent(new_continent);
		Assert.assertFalse(result);
	}

	@Test
	public void testDelete_Continent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet_Territory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet_Continent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet_Territories() {
		fail("Not yet implemented");
	}

	@Test
	public void testIs_Valid() {
		fail("Not yet implemented");
	}

	@Test
	public void testExist_Path() {
		fail("Not yet implemented");
	}

}
