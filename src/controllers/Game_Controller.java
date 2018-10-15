package controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import models.Game_Model;
import models.Map;
import views.Game_View;

/**
 * 
 * @author e_narang
 *
 */
public class Game_Controller {


private static final String MA = null;

private static final String PA = null;

/**
 *This method gets number of armies in set up phase
 * @author e_narang
 *
 */
	// Place army:PA ,MA: Move Army, HIc :hand in card
		
Game_Model gm = new Game_Model(); 

Game_View view=new Game_View();

enum possibeInputs{ MA,PA,HIC}
	
	public void Set_NumberOfPlayers() {
		
		int number_of_players = view.Setup_GetNumberOfPlayers(); 
		
	//	gm.setNumberOfPlayers();
		
		
		
	}
	
	/**
	 * give the controller of map
	 * @return specific map 
	 */
	
//	public Map Set_GetMap(){
//		
//		Map map = new Map(); 
//		
//		
//		return map;
//	}
	/**
	 * 
	 * @return
	 */
	
	public ArrayList<String> Set_GetNameOfPlayers() {
	
	List<String> name_of_players=view.Setup_GetNameOfPlayers() ;
	
	return new ArrayList<String>(); 
}
	
/**
 * 	
 * @return
 */
	
//	public boolean Game_ParseInput() {
//		List<String> commandList=new ArrayList();
//		String[] COM =view.PossibleCommands();
//		// parse first command input (ex. "MA,T1,T2,5")
//    for (int i=0;i <COM.length; i++ ) {
//			
//			String value = COM[i];
//			commandList.add(value);
//		
//
//    }
		
//		switch(COM[0]) {
//	
//		case MA: 
//			//parse next three inputs 
//			//parse second to Territory 1 = arg 2
//			//parse thrid to Territory 2 = arg 3
//			//pare fourth to int numarmies = arg 4 
//			
//		// boolean isValid =  gm.activePlayer.moveArmy(t1, t2, numarmies); 
//			
//		//if(!isValid) {
//			System.out.println("This was an invalid move!");
//		//} else {
//			
//			//update the views in the graphic display (ex. change army numbers on the map)
//			
//		}
//		 
//			
//			break; 
//			
//		case PA:
//			
//			break; 
//			
//		default: 
//			
//			
//		
//	return true; 
//		
	
	}
	
	
	
