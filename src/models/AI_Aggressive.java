package models;

public class AI_Aggressive implements Behaviour{

	public Player current_player;


	Game_Model game; 
	

	public AI_Aggressive(Player cp, Game_Model game){
		this.current_player = cp; 
		this.game = game; 

		
	}
	
	public void Attack(){
		
		Territory from = game.map.Get_Territory(this.am.from_territory);
		Territory to = game.map.Get_Territory(this.am.to_territory);
		Player defender = game.players.Search_Player(to.owner_name);

		this.am.Attack_Model_Update(current_player, defender, from, to);
		
	}
	
	public void Fortify(){
		
	}
	
	public void Reinforce(){
		
	}
	
	public void PostAttack(){
		
	}
	
	public void PlayCards(){
		
	}
	
}
