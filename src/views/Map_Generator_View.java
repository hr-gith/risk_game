package views;

import java.util.Scanner;


import models.Map;


public class Map_Generator_View {
	
	@SuppressWarnings("resource")
	public int Display_Menu() {	
		System.out.println("\n\t Map Generator");
		System.out.println("\n===========================");
		System.out.println("\n 1. Import From File");
		System.out.println("\n 2. Design By MySelf");
		System.out.println("\n\n Please Enter Your Choice(1,2): ");
		Scanner in = new Scanner(System.in);
		return in.nextInt();
	}
	
	public Map Display_Map_Designer() {
		Map map = new Map();
		Scanner in = new Scanner(System.in);
		
		System.out.println("\n\t Create Your Own Map");
		System.out.println("\n===========================");
		System.out.println("\n How many continents does the map have?");
		int continents_num = in.nextInt();	
		
		//TODO: get edges
		
		return map;
	}
	
	public void Display_Message(String message) {
		System.out.println("\n" + message);
	}
	
	public void Display_Map(Map map) {
		if (map.IsEmpty()) return;
		System.out.println(map.toString());
	}
	
}
