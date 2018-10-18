package controllers;

import models.Game_Model;
import models.Map;
import models.Territory;
import views.Game_View;

import java.util.ArrayList;

/**
 * @author e_narang
 */
public class Game_Controller {
/*
    private static final int Number_Army_For_Two_Players = 40;
    private static final int Number_Army_For_Three_Players = 35;
    private static final int Number_Army_For_Four_Players = 30;
    private static final int Number_Army_For_Five_Players = 25;
    private static final int Number_Army_For_Six_Players = 20;*/

    private static final String MA = "MOVE_ARMY";
    private static final String PA = "PLACE_ARMY";
    Game_Model gm = new Game_Model();

    Game_View view = new Game_View();

    enum Possible_Inputs {MA, PA, HIC}
    // Place army:PA ,MA: Move Army, HIc :hand in card


    private static int number_of_players;
//    private static int initial_number_of_armies;
    private static ArrayList<String> name_of_players;
    private static Territory from_Territory;
    private static Territory to_Territory;
    private static int number_of_move_armies;
    private static Territory reinforcement_to_Territory;
    private static int reinforcement_number_of_move_armies;



    public void Set_Name_Players(ArrayList<String> name_of_players) {
        this.name_of_players = name_of_players;
    }


    public ArrayList<String> Get_Name_Players() {

        return name_of_players;
    }


    public void Set_Number_Players(int number_of_players) {
        /*switch (number_of_players) {
            case (2):
                initial_number_of_armies = Number_Army_For_Two_Players;
                break;
            case (3):
                initial_number_of_armies = Number_Army_For_Three_Players;
                break;
            case (4):
                initial_number_of_armies = Number_Army_For_Four_Players;
                break;
            case (5):
                initial_number_of_armies = Number_Army_For_Five_Players;
                break;
            case (6):
                initial_number_of_armies = Number_Army_For_Six_Players;
        }
*/
        this.number_of_players = number_of_players;

    }


    public int Get_Num_Players() {
        return number_of_players;
    }


    public Territory Get_From_Territory() {
        return from_Territory;
    }

    public void Set_From_Territory(String from_country) {
        Map map = Map.Get_Map();
        Territory territory = map.Get_Territory(from_country);
        this.from_Territory = territory;
    }

    public Territory Get_To_Territory() {
        return to_Territory;
    }

    public void Set_To_Territory(String to_country) {
        Map map = Map.Get_Map();
        Territory territory = map.Get_Territory(to_country);
        this.to_Territory = territory;
    }
    public Territory Get_Reinforcement_To_Territory() {
        return reinforcement_to_Territory;
    }

    public void Set_Reinforcement_To_Territory(String reinforcement_to_Territory) {
        Map map = Map.Get_Map();
        Territory territory = map.Get_Territory(reinforcement_to_Territory);
        this.reinforcement_to_Territory = territory;
    }

    public int Get_Number_of_move_armies() {
        return number_of_move_armies;
    }

    public void Set_Number_of_move_armies(int number_of_move_armies) {
        this.number_of_move_armies = number_of_move_armies;
    }

    public int Get_Reinforcement_Number_of_move_armies() {
        return reinforcement_number_of_move_armies;
    }

    public void Set_Reinforcement_Number_of_move_armies(int reinforcement_number_of_move_armies) {
        this.reinforcement_number_of_move_armies = reinforcement_number_of_move_armies;
    }




//	public Map Set_GetMap(){
//		
//		Map map = new Map(); 
//		
//		
//		return map;
//	}

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
	
	
	
