package models;

import java.util.ArrayList;

/**
 * enumeration of states inside an attack_Model
 *
 */
enum State {
	NONE, VALIDATION, START, END, APPLY_RESULT
};

/**
 * this class has the logic of a single attack from one territory to another
 */
public class Attack_Model {

	public Player attacker;
	public Player defender;

	public Territory from;
	public Territory to;

	public int attacker_nb_dices;
	public int defender_nb_dices;

	public boolean all_out;
	State current_state;

	public ArrayList<Integer> attacker_dice;
	public ArrayList<Integer> defender_dice;

	public int attacker_loss;
	public int defender_loss;

	public String message;
	public String to_territory;
	public String from_territory;

	public boolean continue_attack = false;

	/**
	 * constructor of Attack_model without any argument
	 */
	public Attack_Model() {
		current_state = State.NONE;
		message = "";
	}

	/**
	 * constructor of attack_Model
	 * @param attacker
	 *            Player object of attacker
	 * @param defender
	 *            Player object of defender
	 * @param from
	 *            Attacking Territory object
	 * @param to
	 *            Defending Territory object
	 * @param attacker_nb_dices
	 *            number of dice attacker wish to use
	 * @param all_out
	 *            whether attacker chose All_out mode or not
	 */
	public void Attack_Model_Update(Player attacker, Player defender, Territory from, Territory to) {
		current_state = State.NONE;
		message = "";
		this.attacker = attacker;
		this.defender = defender;
		this.from = from;
		this.to = to;

		if (all_out) {
			Set_Max_NB_Dices();
		} else {

			this.defender_nb_dices = Get_Max_NB_Dices(to, false);
		}
	}

	/**
	 * this method sets number of dice to its maximum
	 */
	public void Set_Max_NB_Dices() {
		this.attacker_nb_dices = Get_Max_NB_Dices(from, true);
		this.defender_nb_dices = Get_Max_NB_Dices(to, false);
	}

	/**
	 * decide about one turn battle	 
	 */
	public void Decide_Battle() {
		attacker_loss = 0;
		defender_loss = 0;
		if (attacker_nb_dices != 0 && defender_nb_dices != 0) {
			attacker_dice = Dice.Roll(attacker_nb_dices);
			defender_dice = Dice.Roll(defender_nb_dices);

			// compare the highest dices
			if (attacker_dice.get(0) > defender_dice.get(0))
				defender_loss = -1;
			else
				attacker_loss = -1;

			if (attacker_nb_dices > 1 && defender_nb_dices > 1) {
				if (attacker_dice.get(1) > defender_dice.get(1))
					defender_loss += -1;
				else
					attacker_loss += -1;
			}
			current_state = State.APPLY_RESULT;
			Attack_Result_To_String();
		}
	}

	/**
	 * method for returning attack result as string
	 */
	public void Attack_Result_To_String() {
		message = "Attack Result: \n(" + this.attacker.name + "-" + this.from.name + " AGAINST " + this.defender.name
				+ "-" + this.to.name + "):\n(Attacker Dice:  ";
		for (int d : attacker_dice)
			message += d + " ";
		message += ") \n- (Defender Dice: ";
		for (int d : defender_dice)
			message += d + " ";
		message += ") \n***********\n Attacker loss: ";
		message += (attacker_loss * -1);
		message += " \n Defender loss: ";
		message += (defender_loss * -1);
	}

	/**
	 * check if the current attack_model is valid or not
	 * @return true if it is valid, false otherwise
	 */
	public boolean Is_Valid_Attack() {
		current_state = State.VALIDATION;
		if (from != null && to != null && !from.name.equalsIgnoreCase(to.name)
				&& !from.owner_name.equalsIgnoreCase(to.owner_name)) {
			// check if from and to are adjacent
			if (from.adj.containsKey(to.name.toLowerCase())) {
				if (attacker_nb_dices <= Get_Max_NB_Dices(from, true)) {
					current_state = State.START;
					return true;
				}
			} else {
				message = "These territories are not adjacent";
			}
		} else {
			message = "These territories are not valid territory name or both of them belong to you";
		}

		return false;
	}

	/**
	 * Apply result of a single battle to armies of attacker and defender assign the
	 * conquered territory to the attacker
	 */
	public void Apply_Result() {

		from.nb_armies += attacker_loss;
		to.nb_armies += defender_loss;
		// check if attacked territory is defeated
		if (to.nb_armies <= 0) {
			// winner is the attacker
			attacker.is_conquerer = true;
			attacker.deserve_card = true;
			defender.Delete_Territory(to.name);
			attacker.Add_Territory(to);
			// move minimum army//?????????????
			to.nb_armies = 1;
			from.nb_armies -= 1;

			// if the defeated player is dead
			if (defender.owned_territories.size() == 0) {
				defender.current_state = State_Player.DEAD;
				// TODO cards of the dead player is given to the conquerer
			}

		}
		current_state = State.END;
	}

	/**
	 * gets max number of dice based on number of armies
	 * @param territory_in_attack
	 *            the territory which is in the battle
	 * @param isAttacker
	 *            true if the territory_in_attack belongs to the attacker, False if
	 *            it belongs to defender
	 * @return Max number of dice , which can be used
	 */
	public int Get_Max_NB_Dices(Territory territory_in_attack, boolean isAttacker) {
		int result = 0;
		int nb_armies = territory_in_attack.nb_armies;
		if (!isAttacker)
			// Defender
			result = (nb_armies > 1) ? 2 : 1;

		else {
			// Attacker
			result = (nb_armies > 3) ? 3 : ((nb_armies == 3) ? 2 : ((nb_armies == 2) ? 1 : 0));
		}
		return result;
	}

}
