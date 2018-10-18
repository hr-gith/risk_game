package controllers;


import models.IO_Map_Helper;
import models.Map;
import utilities.Config;
import views.Map_Generator_View;

public class Map_Generator_Controller {
	
	public Map_Generator_View view;
	public Map map;
	public IO_Map_Helper io_map_helper;
	 
	public  Map_Generator_Controller() {
		this.view = new Map_Generator_View();
		this.map =Map.Get_Map();
		this.io_map_helper = new IO_Map_Helper();
	}
	
	public boolean start() {
		//generating map from file or by user design
		int choice = this.view.Display_Menu();
		while(choice != 1 && choice != 2 && choice != 3){
			System.out.println("\n-----------------------------------");			
			this.view.Display_Message("Error: Enter a valid choice (1-3).");
			System.out.println("\n-----------------------------------");			
			choice = this.view.Display_Menu();
		}
		
		if (choice == 1) {
			map = this.io_map_helper.Import_Map(Config.input_file);
		}else if (choice == 2) {
			this.view.Display_Map_Designer();
			map = view.map;
		}				
		else if (choice == 3){			
			System.exit(0);
		}
		
		//display finalized map
		this.view.Display_Map(map);
		if (map.Is_Valid())
			return true;
		else
			return false;
	}
}