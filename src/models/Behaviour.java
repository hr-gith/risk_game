package models;

/**
 *behavior interface for player methods
 */
public interface Behaviour {
	
	public Attack_Model am = new Attack_Model(); 
	public Fortify_Model fm = new Fortify_Model(); 
	public Reinforce_Model rm = new Reinforce_Model(); 
	public Post_Attack_Model pm = new Post_Attack_Model(); 
	
	//	public Startup_Model Startup(); 
	public void Attack();
	public void Fortify();
	public void Reinforce(); 
	public void Post_Attack(); 
	public void Play_Cards(); 
		
}
	


