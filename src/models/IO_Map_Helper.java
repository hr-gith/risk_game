package models;

import java.util.ArrayList;


import utilities.File_Operations;

public class IO_Map_Helper {

		public Map Import_Map(String path) {
			Map map = null;
			ArrayList<String> map_text = File_Operations.Read_File(path);
			if (map_text.isEmpty()) return map;
			map = new Map();
			
			int line_map = map_text.indexOf("[Map]");
			int line_continents = map_text.indexOf("[Continents]");
			int line_territories = map_text.indexOf("[Territories]");
			//get map info
			for (int i = line_map; i < line_continents; ++i) {
				String[] map_info = (map_text.get(i)).split("=");
				//TODO: get maps information
			}
			//get continents
			for (int i = line_continents + 1; i < line_territories; ++i) {
				String[] continent_info = (map_text.get(i)).split("=");
				int id = i - line_continents + 2;
				Continent new_continent = new Continent(id, continent_info[0]);
				map.continents.put(new_continent.name, new_continent);
			}
			//get territories
			//1-add territories
			for (int i = line_territories + 1; i < map_text.size(); ++i) {
				String[] territory_info = (map_text.get(i)).split(",");
				int id = i - line_territories + 2;
				Continent continent = map.continents.get(territory_info[3].toLowerCase());
				if (continent != null) {
					//TODO: check if positions are integer
					Territory new_territory = new Territory(id, territory_info[0], Integer.valueOf(territory_info[1]), Integer.valueOf(territory_info[2]), continent.name);
					continent.Add_Territory(new_territory);
				}
			}
			//2-add edges
			for (int i = line_territories + 1; i < map_text.size(); ++i) {
				String[] territory_info = (map_text.get(i)).split(",");
				Territory territory = map.Get_Territory(territory_info[0]);
				for (int j = 4; j < territory_info.length; j++) {
					Territory neighbour = map.Get_Territory(territory_info[j]);
					territory.Add_Neighbour(neighbour);
				}
			}			
			return map;
		}
		
		public boolean Export_Map(Map map) {
			if (map.IsEmpty()) return false;
			
			ArrayList<String> map_text = new ArrayList<>();
			map_text.add("[Map]");
			//TODO: map info
			map_text.add("[Continents]");
			//TODO: list of continents
			map_text.add("[Territories]");
			//TODO: list of territories			
			
			return true;
		}
}
