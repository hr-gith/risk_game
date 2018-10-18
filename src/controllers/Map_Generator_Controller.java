package controllers;


import models.IO_Map_Helper;
import models.Map;
import utilities.Config;
import views.Game_View;
import views.Map_Generator_View;

public class Map_Generator_Controller {
	
	public Map_Generator_View view;
	public Game_View game_view;
	public Map map;
	public IO_Map_Helper io_map_helper;
	 
	public  Map_Generator_Controller() {
		this.view = new Map_Generator_View();
		this.map = new Map();
		this.io_map_helper = new IO_Map_Helper();
		game_view=new Game_View();
	}
	
	public void start() {
		int choice = this.view.Display_Menu();;
		while(choice != 1 && choice != 2&& choice != 3){
			System.out.println("\n-----------------------------------");			
			this.view.Display_Message("Error: Enter a valid choice (1,2).");
			System.out.println("\n-----------------------------------");			
			choice = this.view.Display_Menu();
		}
		
		if (choice == 1) {
			map = this.io_map_helper.Import_Map(Config.input_file);
		}if (choice == 2) {
			map = this.view.Display_Map_Designer();
		}if (choice == 3) {
			game_view.Display_Menu_Players();
		}
		this.view.Display_Map(map);
		if (map.Is_Valid())
			this.view.Display_Message("The map is valid");
		else
			this.view.Display_Message("Error: The map is not valid");
		if (map.Exist_Path(map.Get_Territories(), "territory1", "territory5"))
			this.view.Display_Message("path exist");
		else
			this.view.Display_Message("Error: path does not exist");
		
		this.io_map_helper.Export_Map(map);
	}
	
	public Map Get_Map(){
		return this.map; 
	}
}
