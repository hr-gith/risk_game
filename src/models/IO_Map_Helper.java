package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import utilities.Config;
import utilities.File_Operations;

public class IO_Map_Helper {

	/** 
	 * reads a .map file and converts it to a Map object
	 * NOTE: if two territories have the same name or the same position, we ignore the second one
	 * @param path address of a .map file
	 * @return the generated map
	 */
		public Map Import_Map(String path) {
			Map map = null;
			ArrayList<String> map_text = File_Operations.Read_File(path);
			if (map_text.isEmpty()) return map;
			map = new Map();
			
			int line_map = map_text.indexOf("[Map]");
			int line_continents = map_text.indexOf("[Continents]");
			int line_territories = map_text.indexOf("[Territories]");
			//get map info
			for(int i = line_map; i < line_continents; ++i) {
				String[] map_info = (map_text.get(i).toLowerCase()).split("=");
				if (map_info[0].trim() == "image")
					map.image = map_info[1];
				else if (map_info[0].trim() == "wrap")
					map.wrap = Boolean.parseBoolean(map_info[1]);
				else if (map_info[0].trim() == "scroll")
					map.scroll = map_info[1];
				else if (map_info[0].trim() == "author")
					map.author = map_info[1];
				else if (map_info[0].trim() == "warn")
					map.warn = Boolean.parseBoolean( map_info[1]);
			}
			//get continents
			for (int i = line_continents + 1; i < line_territories; ++i) {
				String[] continent_info = (map_text.get(i).toLowerCase()).split("=");
				int id = i - line_continents + 2;
				Continent new_continent = new Continent(id, continent_info[0], Integer.parseInt(continent_info[1]));
				map.Add_Continent(new_continent);
			}
			//get territories
			//1-add territories
			for (int i = line_territories + 1; i < map_text.size(); ++i) {
				String[] territory_info = (map_text.get(i).toLowerCase()).split(",");
				int id = i - line_territories + 2;
				Continent continent = map.continents.get(territory_info[3]);
				if (continent != null) {
					//TODO: check if positions are integer
					Territory new_territory = new Territory(id, territory_info[0], Integer.valueOf(territory_info[1]), Integer.valueOf(territory_info[2]), continent.name);
					continent.Add_Territory(new_territory);
				}
			}
			//2-add edges
			for (int i = line_territories + 1; i < map_text.size(); ++i) {
				String[] territory_info = (map_text.get(i).toLowerCase()).split(",");
				Territory territory = map.Get_Territory(territory_info[0]);
				for (int j = 4; j < territory_info.length; j++) {
					Territory neighbour = map.Get_Territory(territory_info[j]);
					territory.Add_Neighbour(neighbour);
				}
			}			
			return map;
		}
		
		/**
		 * Convert a map into a text and writes it to a .map file
		 * @param map
		 * @return true if map is written successfully on a file
		 */
		public boolean Export_Map(Map map) {
			if (map.IsEmpty()) return false;
			
			ArrayList<String> map_text = new ArrayList<>();
			map_text.add("[Map]");
			map_text.add("image=" +  map.image.toString());
			map_text.add("wrap=" + ( map.wrap ? "true" : "false"));
			map_text.add("scroll=" + map.scroll);
			map_text.add("author=" + map.author);
			map_text.add("warn=" + ( map.warn ? "true" : "false"));

			map_text.add("[Continents]");
			for (Continent continent : map.continents.values()) {
				map_text.add(continent.name + "=" + continent.score);
			}
			
			map_text.add("[Territories]");
			for (Continent continent : map.continents.values()) {
				for (Territory territory : continent.territories.values()) {
					String territory_info = territory.name;
					territory_info += (","+ territory.pos_x);
					territory_info += (","+ territory.pos_y);
					territory_info += (","+ territory.continent_name);
					for (Territory neighbour : territory.adj.values()) {
						territory_info += (","+ neighbour.name);
					}
					map_text.add(territory_info);
				}
			}
			
			File_Operations.Write_File(map_text, Config.output_file);
			return true;
		}
		
		
}
