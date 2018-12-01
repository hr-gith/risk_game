package models;

import java.util.HashMap;

/**
 *class for implementing player cheater strategy methods and implement behavior interface
 */
public class AI_Cheater implements Behaviour, java.io.Serializable {

	public Player current_player;
	Game_Model game;

	/**
	 * constructor initialize current player and game
	 * @param cp
	 * @param game
	 */
	public AI_Cheater(Player cp, Game_Model game) {
		this.current_player = cp;
		this.game = game;
	}

	/**
	 * attack function for cheater player
	 */
	public void Attack() {

		HashMap<String, Territory> current_territories = current_player.owned_territories;
		HashMap<String, Territory> swap_territories = new HashMap<String, Territory>();

		for (String str : current_territories.keySet()) {
			for (String adj : current_territories.get(str).adj.keySet()) {
				if (!(current_territories.get(str).adj.get(adj).owner_name.equals(current_player.name))) {
					swap_territories.put(adj, current_territories.get(str).adj.get(adj));
				}
			}
		}

		for (String strSwp : swap_territories.keySet()) {
			Player defender = game.players.Search_Player(swap_territories.get(strSwp).owner_name);
			current_player.owned_territories.put(strSwp, swap_territories.get(strSwp));
			current_player.owned_territories.get(strSwp).owner_name = current_player.name;
			current_player.owned_territories.get(strSwp).nb_armies = 1;
			defender.owned_territories.remove(strSwp);

		}
		this.am.continue_attack = false;
	}

	/**
	 *  fortify function for cheater player
	 */
	public void Fortify() {

		for (String str : current_player.owned_territories.keySet()) {
			for (String adj : current_player.owned_territories.get(str).adj.keySet()) {
				if (!(current_player.owned_territories.get(str).adj.get(adj).owner_name.equals(current_player.name))) {
					current_player.owned_territories.get(str).nb_armies = 2;
				}
			}
		}
		current_player.behavior.fm.continue_fortify = false;
	}

	/**
	 *  reinforce function for cheater player
	 */
	public void Reinforce() {
		while (game.current_state == State_Game.REINFORCEMENT && current_player.cards.Is_Set_Available()) {
			this.Play_Cards();
		}

		if (game.current_state == State_Game.STARTUP) {
			int units = 0;
			String territory_max = "";
			// find territory with most units
			for (String str : current_player.owned_territories.keySet()) {
				if (current_player.owned_territories.get(str).nb_armies > units)
					territory_max = str;
				units = current_player.owned_territories.get(str).nb_armies;
			}
			current_player.behavior.rm.to_territory = territory_max;
			current_player.behavior.rm.nb_armies = current_player.reinforcements;
		}

		if (game.current_state == State_Game.REINFORCEMENT) {
			// find territory with most units
			for (String str : current_player.owned_territories.keySet()) {
				current_player.owned_territories.get(str).nb_armies = 2
						* current_player.owned_territories.get(str).nb_armies;
			}

			current_player.reinforcements = 0;
			current_player.behavior.rm.to_territory = "";
			current_player.behavior.rm.nb_armies = 0;
		}
	}

	/**
	 *  post attack function for cheater player
	 */
	public void Post_Attack() {

	}

	/**
	 * play cards function for cheater player
	 */
	public void Play_Cards() {
		String[] tmpCards = new String[3];

		if ((current_player.cards.cavalry > 0 && current_player.cards.infantry > 0
				&& current_player.cards.artillery > 0)) {
			tmpCards = new String[] { "cavalry", "infantry", "artillery" };

		} else if (current_player.cards.infantry >= 3) {
			tmpCards = new String[] { "infantry", "infantry", "infantry" };

		} else if (current_player.cards.cavalry >= 3) {
			tmpCards = new String[] { "cavalry", "cavalry", "cavalry" };

		} else if (current_player.cards.artillery >= 3) {
			tmpCards = new String[] { "artillery", "artillery", "artillery" };

		}
		current_player.cards.Are_Playable(tmpCards);
		current_player.reinforcements = current_player.reinforcements
				+ current_player.cards.Get_Card_Reinforcement_Qty();
	}

}
