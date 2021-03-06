package models;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utilities.Config;

/**
 * This is a unit test class for attack model
 *
 */
public class Attack_ModelTest {
	Map_Model map =new Map_Model();
	Game_Model game_model1 = Game_Model.Get_Game();
	Player attacker = new Player(0,Color.BLUE,State_Player_Strategy.HUMAN, game_model1);
	Player defender = new Player(1,Color.BLUE,State_Player_Strategy.HUMAN, game_model1);
	Territory from;
	Territory to;
	Attack_Model attack_model = new Attack_Model();

	/**
	 * this method sets territories and map before any test
	 */
	@Before
	public void Set_Attack_Plan() {
		game_model1.map = map;
		Continent new_continent = new Continent("new_continent_name", 1);
		map.Add_Continent(new_continent);

		from = new Territory("attackingTerritory", 120, 300, "new_continent_name");
		new_continent.Add_Territory(from);
		from.nb_armies = 5;

		to = new Territory("defendingTerritory", 120, 300, "new_continent_name");
		new_continent.Add_Territory(to);
		to.nb_armies = 2;
		from.Add_Neighbour(to);

		Territory t3 = new Territory("attackingTerritory", 120, 300, "new_continent_name");
		new_continent.Add_Territory(t3);
		to.Add_Neighbour(t3);

		attacker.Add_Territory(from);
		defender.Add_Territory(to);
		

		attack_model.attacker= attacker;
		attack_model.defender= defender;
		attack_model.from= from;
		attack_model.to= to;
		
		
		attack_model.Set_Max_NB_Dices();
	}

	/**
	 * this method tests if the attack is valid
	 */
	@Test
	public void testIs_Valid_Attack() {
		
		Assert.assertTrue(attack_model.Is_Valid_Attack());
	}

	/**
	 * this method tests if the maximum number of dice are correctly calculated
	 */
	@Test
	public void testGet_Max_NB_Dices() {
		Attack_Model attack_model = new Attack_Model();
		Assert.assertEquals(3, attack_model.Get_Max_NB_Dices(from, true));
	}
	
	/**
	 * this method tests Apply_Result to check if corrected number of armies are applied to territories
	 */
	@Test
	public void testApply_Result() {
		attack_model.attacker_loss =-1;
		attack_model.defender_loss =-1;
		attack_model.Apply_Result();
		Assert.assertEquals(1, to.nb_armies);
		Assert.assertEquals(4, from.nb_armies);
	}
	
	/**
	 * this method tests Apply_Result after conquering
	 */
	@Test
	public void testAfter_Conquering() {
		attack_model.attacker_loss =-1;
		attack_model.defender_loss =-2;
		attack_model.Apply_Result();
		Assert.assertEquals(true, to.owner_name.equalsIgnoreCase(from.owner_name));
	}

}
