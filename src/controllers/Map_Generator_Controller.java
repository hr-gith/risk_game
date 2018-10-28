package controllers;


import models.IO_Map_Helper;
import models.Map_Model;
import utilities.Config;
import views.Map_Generator_View;
import views.Map_View;


/**
 * Map generator controller for starting getting map from file or by user design
 */

public class Map_Generator_Controller {
	
	public Map_Generator_View map_generator_view;
	public Map_View map_view;
	public Map_Model map;
	public IO_Map_Helper io_map_helper;
	 
	public  Map_Generator_Controller() {
		this.map_generator_view = new Map_Generator_View();
		this.map_view = new Map_View();
		this.map =Map_Model.Get_Map();
		this.io_map_helper = new IO_Map_Helper();
	}
	
	/**
	 * start phase of read or designing map
	 * @return boolean
	 */
	
	public boolean start() {
		int choice = 0;
		do {
			//generating map from file or by user design
			choice = this.map_generator_view.Display_Menu();
			while(choice < 1 || choice > 6){
				System.out.println("\n-----------------------------------");			
				this.map_generator_view.Display_Message("Error: Enter a valid choice (1-6).");
				System.out.println("\n-----------------------------------");			
				choice = this.map_generator_view.Display_Menu();
			}
			
			switch(choice){
				case 1:	//import from file		
					map = this.io_map_helper.Import_Map(Config.input_file);
					if (map == null || map.Is_Empty())
						this.map_generator_view.Display_Message("Map is not valid based on the game rules.");
					else
						this.map_generator_view.Display_Message("Map is imported successfully.");
					break;
				case 2: // design a new map
					this.map_generator_view.Display_Map_Designer();
					map = map_generator_view.map;
					break;
				case 3://Edit map
					this.map_generator_view.Display_Map_Designer();
					map = map_generator_view.map;	
					break;
				case 4://Save map in a file					
					if (map != null && io_map_helper.Export_Map(map))
						this.map_generator_view.Display_Message("\n your map is successfully saved.");
					else
						this.map_generator_view.Display_Message("\n Error occured while saving map in a file.");
					break;			
				case 5://Display map
					if (map != null) {
						this.map_generator_view.Display_Map(map);
						this.map_view.Draw_Map(map);
					}
					break;
				case 7:			
					System.exit(0);				
			}
			
		}while(choice != 6);
		
		//display finalized map
		this.map_generator_view.Display_Map(map);
		if (map.Is_Valid()) {
			map.Set_Continents_Score();
			return true;
		}
		else
			return false;
	}
}