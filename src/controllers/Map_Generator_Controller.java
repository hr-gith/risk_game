package controllers;


import models.IO_Map_Helper;
import models.Map;
import utilities.Config;
import views.Map_Generator_View;


/**
 * Map generator controller for starting getting map from file or by user design
 */

public class Map_Generator_Controller {
	
	public Map_Generator_View view;
	public Map map;
	public IO_Map_Helper io_map_helper;
	 
	public  Map_Generator_Controller() {
		this.view = new Map_Generator_View();
		this.map =Map.Get_Map();
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
			choice = this.view.Display_Menu();
			while(choice < 1 || choice > 6){
				System.out.println("\n-----------------------------------");			
				this.view.Display_Message("Error: Enter a valid choice (1-6).");
				System.out.println("\n-----------------------------------");			
				choice = this.view.Display_Menu();
			}
			
			switch(choice){
				case 1:	//import from file		
					map = this.io_map_helper.Import_Map(Config.input_file);
					if (map == null || map.Is_Empty())
						this.view.Display_Message("Map is not valid based on the game rules.");
					else
						this.view.Display_Message("Map is imported successfully.");
					break;
				case 2: // design a new map
					this.view.Display_Map_Designer();
					map = view.map;
					break;
				case 3://Edit map
					this.view.Display_Map_Designer();
					map = view.map;	
					break;
				case 4://Save map in a file					
					if (map != null && io_map_helper.Export_Map(map))
						this.view.Display_Message("\n your map is successfully saved.");
					else
						this.view.Display_Message("\n Error occured while saving map in a file.");
					break;			
				case 5://Display map
					if (map != null) {
						this.view.Display_Map(map);
						this.view.Draw_Map();
					}
					break;
				case 7:			
					System.exit(0);				
			}
			
		}while(choice != 6);
		
		//display finalized map
		this.view.Display_Map(map);
		if (map.Is_Valid()) {
			map.Set_Continents_Score();
			return true;
		}
		else
			return false;
	}
}