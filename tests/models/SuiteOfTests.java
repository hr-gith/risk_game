package models;
import utilities.File_Operations_Test;
import View.World_Domination_ViewTest;

import org.junit.runner.*; 
import org.junit.runners.*; 
import controllers.Tournament_ControllerTest;

	
	@RunWith(Suite.class)
	
	@Suite.SuiteClasses({Test_Player.class, Attack_ModelTest.class,ContinentTest.class, IO_Map_HelperTest.class,
		MapTest.class, TerritoryTest.class, Test_Game_Model.class, File_Operations_Test.class, World_Domination_ViewTest.class, Tournament_ControllerTest.class} )
/**
 * this is the Suit class for junit tests
 *
 */
public class SuiteOfTests{
	
	
	

}