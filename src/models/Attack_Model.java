package models;

import java.util.ArrayList;

enum State {NONE, VALIDATION, START, END, APPLY_RESULT};

public class Attack_Model {
	public Player attacker;
	public Player defender;
	
	public Territory from;
	public Territory to;
	
	public int attacker_nb_dices;
	public int defender_nb_dices;
		
	boolean all_out;
	State current_state;
	
	public ArrayList<Integer> attacker_dices;
	public ArrayList<Integer> defender_dices;
	
	public int attacker_loss;
	public int defender_loss;
	
	String message;
	
	public Attack_Model() {
		current_state = State.NONE;
		message = "";
	}
	
	public Attack_Model(Player attacker, Player defender, Territory from, Territory to, int attacker_nb_dices, boolean all_out) {
		current_state = State.NONE;
		message = "";
		this.attacker = attacker;
		this.defender = defender;
		this.from = from;
		this.to= to;
		this.all_out = all_out;
		
		if (all_out) {
			Set_Max_NB_Dices();
		}
		else {
			this.attacker_nb_dices = attacker_nb_dices;
			this.defender_nb_dices = Get_Max_NB_Dices(to,false);
		}
	}
	
	public void Set_Max_NB_Dices() {
		this.attacker_nb_dices = Get_Max_NB_Dices(from, true);
		this.defender_nb_dices = Get_Max_NB_Dices(to,false);
	}
	/**
	 * decide about one turn battle
	 * @param attack_nb_dices
	 * @param defend_nb_dices
	 * @return a pair of numbers: the first number is the number of armies lost for attacker and the other one is for attacked one
	 */
	public void Decide_Battle() {
		attacker_loss = 0;
		defender_loss = 0;
		if (attacker_nb_dices != 0 && defender_nb_dices != 0) {
			attacker_dices = Dice.Roll(attacker_nb_dices); 
			defender_dices = Dice.Roll(defender_nb_dices); 
			
			//compare the highest dices
			if (attacker_dices.get(0) > defender_dices.get(0)) 
				defender_loss = -1;
			else
				attacker_loss = -1;
			
			if (attacker_nb_dices > 1 &&  defender_nb_dices > 1 ) {
				if (attacker_dices.get(1) > defender_dices.get(1)) 
					defender_loss += -1;
				else
					attacker_loss += -1;
			}
			current_state = State.APPLY_RESULT;
		}
	}
	
	public boolean Is_Valid_Attack() {
		current_state = State.VALIDATION;
		if (from != null && to != null &&
				!from.name.equalsIgnoreCase(to.name) && !from.owner_name.equalsIgnoreCase(to.owner_name)) {
			//check if from and to are adjacent
			if (from.adj.containsKey(to.name.toLowerCase())) {
				if ( attacker_nb_dices <= Get_Max_NB_Dices(from, true))
					current_state = State.START;
					return true;
			}
			else {
				message = "Error:These territories are not adjacent";
			}		
		}else {
			message = "Error:These territories are not valid territory name or both of them belong to you";
		}
    
		return false;
	}
	
	public void Apply_Result () {
		from.nb_armies += attacker_loss;
		to.nb_armies += defender_loss;
		//check if attacked territory is defeated
		if (to.nb_armies <= 0) {
			//winner is the attacker
			
			defender.Delete_Territory(to.name);
			attacker.Add_Territory(to);
			//move minimum army//?????????????
			to.nb_armies = 1;
			from.nb_armies -= 1;
			
			// if the defeated playee is dead
			if (defender.owned_territories.size() == 0) {
				defender.current_state = State_Player.DEAD;			
				//TODO cards of the dead player is given to the conquerer 
			}
			
			//TODO: move armies to new territory
		}
		//TODO: move as many army as they want?????????????
		current_state = State.END;
	}
	
	public int Get_Max_NB_Dices(Territory territory_in_attack,boolean isAttacker) {
		int result = 0;
		int nb_armies = territory_in_attack.nb_armies;
		if (!isAttacker) 
			//Defender
			result = (nb_armies > 1)? 2 : 1;				
		
		else {
			//Attacker
			result = (nb_armies > 3 ) ? 3 : ((nb_armies == 3)? 2 : ((nb_armies == 2)? 1: 0));  
		}
		return result;
	}

}
