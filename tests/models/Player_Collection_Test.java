package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class Player_Collection_Test {
	Player_Collection players;
	/**
	 * set player before test
	 */
	@Before
	public void Set_Players_Before() {
		players=new Player_Collection();
		players.Add_Player("p1", State_Player_Strategy.HUMAN, Game_Model.Get_Game());
		players.Add_Player("p2", State_Player_Strategy.HUMAN, Game_Model.Get_Game());
	}
	
/**
 * get active player test correctly
 */
	@Test
	public void Get_Active_Players_Test() {
	players.player_list.get(0).current_state = State_Player.DEAD;
		 Assert.assertEquals(1, players.Get_Active_Players().size());
	}
/**
 * 	add player correctly test
 */
	@Test
	public void Add_Player_Test() {
		players.Add_Player("p3", State_Player_Strategy.HUMAN, Game_Model.Get_Game());
		 Assert.assertEquals(3, players.player_list.size());
	}

}
