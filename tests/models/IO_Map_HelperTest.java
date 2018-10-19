package models;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import utilities.Config;

public class IO_Map_HelperTest {

	@Test
	public void testImport_Map() {
		String path = Config.input_file;
		IO_Map_Helper io_map = new IO_Map_Helper();
		Map map = io_map.Import_Map(path);
		Assert.assertNull(map);
	}

}
