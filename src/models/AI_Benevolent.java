package models;

/**
 * class for implementing player benevolent strategy methods and implement behavior interface
 */
public class AI_Benevolent implements Behaviour, java.io.Serializable {

	public Player current_player;
	public boolean continue_move;
	Game_Model game;
	String territory_min = "";

	/**
	 * constructor initialize current player and game
	 * @param cp
	 * @param game
	 */
	public AI_Benevolent(Player cp, Game_Model game) {
		this.current_player = cp;
		this.game = game;
	}

	/**
	 * attack function for benevolent player
	 */
	public void Attack() {
		this.am.continue_attack = false;
	}

	/**
	 * fortify function for benevolent player
	 */
	public void Fortify() {
		current_player.behavior.fm.continue_fortify = false;
	}

	/**
	 * reinforce function for benevolent player
	 */
	public void Reinforce() {

		while (game.current_state == State_Game.REINFORCEMENT && current_player.cards.Is_Set_Available()) {
			this.Play_Cards();
		}

		int units = 1000000; // arbitrarily large number

		// find territory with least units
		for (String str : current_player.owned_territories.keySet()) {
			if (current_player.owned_territories.get(str).nb_armies < units)
				territory_min = str;
			units = current_player.owned_territories.get(str).nb_armies;
		}
		current_player.behavior.rm.to_territory = territory_min;
		current_player.behavior.rm.nb_armies = current_player.reinforcements;
	}

	/**
	 * post function function for benevolent player
	 */
	public void Post_Attack() {

	}

	/**
	 * player cards function for benevolent player
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
