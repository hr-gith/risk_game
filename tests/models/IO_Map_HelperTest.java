package models;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import utilities.Config;
/**
 * this is a junit test class for input/output of map
 *
 */
public class IO_Map_HelperTest {

	@Test
	/**
	 * this is a test method for importing a new map
	 */
	public void testImport_Map() {
		String path = Config.input_file;
		Map_Helper io_map = new Map_Helper();
		Map_Model map = io_map.Import_Map(path);
		Assert.assertNull(map);
	}

}
