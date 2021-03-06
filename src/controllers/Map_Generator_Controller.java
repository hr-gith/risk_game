package controllers;

import models.Map_Helper;
import models.Map_Model;
import utilities.Config;
import views.Game_View;
import views.Map_Generator_View;
import views.Map_View;

/**
 * Map generator controller for starting getting map from file or by user design
 */
public class Map_Generator_Controller {

	public Map_Generator_View map_generator_view;
	public Map_View map_view;
	public Game_View game_view;
	public Map_Model map;
	public Map_Helper io_map_helper;

	public Map_Generator_Controller() {
		this.map_generator_view = new Map_Generator_View();
		this.map_view = new Map_View();
		this.game_view = new Game_View(false);
		this.map = new Map_Model();
		this.io_map_helper = new Map_Helper();
	}

	/**
	 * method start phase of map
	 */
	public boolean Start() {
		int choice = 0;

		do {
			// generating map from file or by user design
			choice = this.map_generator_view.Display_Menu();
			while (choice < 1 || choice > 6) {
				System.out.println("\n-----------------------------------");
				this.map_generator_view.Display_Message("Error: Enter a valid choice (1-6).");
				System.out.println("\n-----------------------------------");
				choice = this.map_generator_view.Display_Menu();
			}

			switch (choice) {
			case 1: // import from file
				String map_name = this.map_generator_view.Display_Get_Map_Name();
				String path = ".\\src\\"+map_name+".map";
				map = this.io_map_helper.Import_Map(path);//Config.input_file);
				if (map == null || map.Is_Empty())
					this.map_generator_view.Display_Message("Map is not valid based on the game rules.");
				else
					this.map_generator_view.Display_Message("Map is imported successfully.");
				break;
			case 2: // design a new map
				this.map_generator_view.Display_Map_Designer(game_view, map_view);
				map = map_generator_view.map;

				break;
			case 3:// Edit map
				this.map_generator_view.Display_Map_Designer(game_view, map_view);
				map = map_generator_view.map;

				break;
			case 4:// Save map in a file
				if (map != null && io_map_helper.Export_Map(map))
					this.map_generator_view.Display_Message("\n your map is successfully saved.");
				else
					this.map_generator_view.Display_Message("\n Error occured while saving map in a file.");
				break;
			case 5:// Display map
				if (map != null) {
					this.game_view.Draw_Window();
					this.game_view.Add_Panel(map_view.jPanel, 1);
					this.map_generator_view.Display_Map(map);
					this.map_view.Draw_Map(map);
					this.game_view.Redraw();
				}
				break;
			case 6:// Back to main menu
				this.game_view.Close();
				break;
			}

		} while (choice != 6);

		// display finalized map
		this.map_generator_view.Display_Map(map);
		if (map.Get_Territories().size() > 2 && map.Is_Valid()) {
			map.Set_Continents_Score();
			return true;
		} else
			return false;
	}
}