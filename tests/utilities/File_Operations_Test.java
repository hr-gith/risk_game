package utilities;

import  models.Game_Model;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * this is a junit test class for File_Operations
 * it checks save/load operations
 *
 */
public class File_Operations_Test {

	Game_Model game_model;  
	Game_Model gm = Game_Model.Get_Game();  
	String filename = "saveTest1";  
	
	/**
	 * this method runs before all tests to load the game model 
	 */
	@Before
	public void Set_Deserialize_Test() {
		game_model = File_Operations.Deserialize(filename);	
	}
	
	/**
	 * this method checks if the object has been load
	 */
	@Test
	public void Load_object_Test() {	
		Assert.assertNotNull(game_model);
	}
	
	/**
	 * this method checks if the map is loaded correctly
	 */
	@Test
	public void Load_Map_Test() {	
		Assert.assertTrue(game_model.map.Is_Valid());
	}
	
	/**
	 * this method check if the Players list has been loaded and is not empty
	 */
	@Test
	public void Load_Player_List_Test() {	
		Assert.assertFalse(game_model.players.player_list.isEmpty());
	}
	
	/**
	 * this method check if Serialization correctly save game model in "JunitSaveTest" file
	 */
	@Test
	public void Save_Game_Model_Test() {
		String outputName = "JunitSaveTest"; 
		Assert.assertTrue(File_Operations.Serialize(gm, outputName));
	}
	
}

