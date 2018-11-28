package models;

import java.util.Random;
import java.util.Set;

public class AI_Random implements Behaviour , java.io.Serializable{

	public Player current_player;
	private Random rand = new Random(); 
	public boolean continue_move; 
	Game_Model game; 
	
	String territory_max = ""; 
	

	public AI_Random(Player cp, Game_Model game){
		this.current_player = cp; 
		this.game = game; 

	}
	
	public void Attack(){
		int units = 0; 		
		//find territory with most units 
		for(String str : current_player.owned_territories.keySet()){
			
			if(current_player.owned_territories.get(str).nb_armies > units){
				
				 territory_max = str; 
				 units = current_player.owned_territories.get(str).nb_armies; 
			}
			
		}
		
		for(String str : current_player.owned_territories.get(territory_max).adj.keySet()){
			
			//check to see if adjacent territories are owned by current player
			if(!(current_player.owned_territories.get(territory_max).adj.get(str).owner_name.equals(current_player.name))){
				
				this.am.from_territory = territory_max; 
				this.am.to_territory = str; 
				this.am.all_out = false; 
				Territory from = game.map.Get_Territory(this.am.from_territory);
				Territory to = game.map.Get_Territory(this.am.to_territory);
				Player defender = game.players.Search_Player(to.owner_name);
				
//				if(r.nextInt(current_player.owned_territories.get(territory_max).adj.get(str).nb_armies) > (current_player.owned_territories.get(territory_max).adj.get(str).nb_armies)/3){
				if(rand.nextBoolean()){
					this.am.continue_attack  = true; 
				}
				this.am.attacker_nb_dices = this.Get_Random_Int(1,this.am.Get_Max_NB_Dices(from, true)); 	
				
				this.am.Attack_Model_Update(current_player, defender, from, to);					
				break; 				
			}
			else {
				this.am.continue_attack = false; 
			}
		}
	}
	
	public void Fortify(){
	
		for(String str : current_player.owned_territories.keySet()){
			
			for(String adj : current_player.owned_territories.get(str).adj.keySet()){
				
				if(!(current_player.owned_territories.get(str).adj.get(adj).owner_name.equals(current_player.name))){
					this.fm.to_territory = str; 	
				}
			}
		}
		
		this.fm.nb_armies =  this.current_player.owned_territories.get(this.territory_max).nb_armies;
		this.fm.from_territory = this.territory_max; 
		
		current_player.behavior.fm.continue_fortify = false; 
		
	}
	
	public void Reinforce(){
		
		while(game.current_state == State_Game.REINFORCEMENT && current_player.cards.Is_Set_Available()){
				this.PlayCards();
		}
		//find territory with most units 
		String[] territory_array  = current_player.owned_territories.keySet().toArray(new String[current_player.owned_territories.size()]); 
					
		int random_element= this.Get_Random_Int(0,current_player.owned_territories.size() - 1);
		current_player.behavior.rm.to_territory = territory_array[random_element]; 
		current_player.behavior.rm.nb_armies = current_player.reinforcements; 
	}
	
	public void PostAttack(){
		int random_num_armies= this.Get_Random_Int(1, this.am.from.nb_armies -1); 
		this.pm.nb_armies =  random_num_armies;			
	}
	
	public void PlayCards(){
		String[] tmpCards = new String[3];
		
		if ((current_player.cards.cavalry > 0 && current_player.cards.infantry > 0 && current_player.cards.artillery > 0)){
			 tmpCards = new String[]{"cavalry", "infantry", "artillery"}; 
			
		} 
		else if (current_player.cards.infantry >= 3 ) {
			 tmpCards = new String[]{"infantry", "infantry", "infantry"};
			
		}
		else if (current_player.cards.cavalry >= 3 ) {
			 tmpCards = new String[]{"cavalry", "cavalry", "cavalry"};
			
		}
		else if (current_player.cards.artillery >= 3 ) {
			 tmpCards = new String[]{"artillery", "artillery", "artillery"};
			
		}	
		current_player.cards.Are_Playable(tmpCards);
		current_player.reinforcements = current_player.reinforcements
				+ current_player.cards.Get_Card_Reinforcement_Qty();
	}
	
	private int Get_Random_Int(int min, int max) {
		if (min < max)
			return min + rand.nextInt(max);
		else if (min == max)
			return min;
		else return 0;
	}	
	
}
