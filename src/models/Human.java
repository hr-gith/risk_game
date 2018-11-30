package models;

public class Human implements Behaviour , java.io.Serializable{

	
	public Player current_player;
	
	Game_Model game; 
	

	public Human(Player cp, Game_Model game){
		this.current_player = cp; 
		this.game = game; 

		
	}

	
	public void Attack(){
		
//		Territory from = this.map.Get_Territory(from_name);
//		Territory to = this.map.Get_Territory(to_name);
//		Player defender = players.Search_Player(to.owner_name);
	//
//		this.attack_plan = new Attack_Model(current_player, defender, from, to, nb_dice, all_out);
		
		Territory from = game.map.Get_Territory(this.am.from_territory);
		Territory to = game.map.Get_Territory(this.am.to_territory);
		Player defender = game.players.Search_Player(to.owner_name);

		this.am.Attack_Model_Update(current_player, defender, from, to);
		
		
	}
	
	public void Fortify(){
		
	}
	
	public void Reinforce(){
		
	}
	
	public void Post_Attack(){
		
	}
	
	public void Play_Cards(){
		
	}
}
