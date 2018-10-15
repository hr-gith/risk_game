package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Map;

public class Game_View {
	Scanner scanner= new Scanner(System.in);
    Game_View game_View;
    
	public int Setup_GetNumberOfPlayers() {
		
		int number_of_players;
		System.out.println("Please insert the number of players!!!");
		number_of_players=scanner.nextInt();
		return number_of_players;
	}
	
	
		public List<String> Setup_GetNameOfPlayers() {
			
			List<String> arrayName=new ArrayList();
			int numberOfPlayers;
			numberOfPlayers=game_View.Setup_GetNumberOfPlayers();
			String name;
			for (int i=0; i<numberOfPlayers; i++) {
			System.out.println("Please insert the number of players!!!");
			name=String.valueOf(scanner.nextLine());
			arrayName.add(name);
			}
			return  arrayName; 
		
		
	}
		
		public String PossibleCommands() {
			System.out.println("Show the State");
			String state=scanner.nextLine();

			return state;
			
		}
	
		
		
//		public Map Setup_GetMap(){
//			
//			Map map = new Map(); 
	//	
//			
//			
//			
//			return map;
//		}
		
		
		
		
		
		

}
