package controllers;
import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * this is a junit test class for Tournament_Controller
 *
 */
public class Tournament_ControllerTest {
	
	String map_name = "map3";
	Tournament_Controller TC;
	
	/**
	 * this method runs before other tests to set up the environment
	 */
	@Before public void setUp_Test(){
		TC = new Tournament_Controller();		
		}

	/**
	 * this method test if the tournament map has been correctly added
	 */
	@Test
	public void Tournament_Add_Map_Test() {
		
		Assert.assertTrue(TC.Add_Map(map_name));
	}
	
	
	/**
	 * this method test if the tournament view has been correctly created
	 */
	@Test
	public void Tournament_view_Test() {
		
		Assert.assertNotNull(TC.tournament_view);
	}
	
	
	

}