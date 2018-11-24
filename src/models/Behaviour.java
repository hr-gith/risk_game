package models;

public interface Behaviour {
	
	public boolean continue_move = false; 
	

	public Attack_Model am = new Attack_Model(); 
	public Fortify_Model fm = new Fortify_Model(); 
	public Reinforce_Model rm = new Reinforce_Model(); 
	public PostAttack_Model pm = new PostAttack_Model(); 
	
	//	public Startup_Model Startup(); 
	public void Attack();
	public void Fortify();
	public void Reinforce(); 
	public void PostAttack(); 
	public void PlayCards(); 
		
}
	


