package models;

/**
 * Cards Class is a model.
 * This class handles the cards given to players at the end of the attack phase,
 * and calculate number of armies based on trade_in rules
 */
public class Cards {
	
	// type of cards: INFANTRY, CAVALRY, ARTILLERY
	
	public int Num_Sets_traded_In = 0;
	public int cavalry = 1; 
	public int infantry = 1; 
	public int artillery = 2; 
	
	private int card_reinforcements; 

	
	
	public boolean Is_Set_Available(){
		// check if either cavalry, infantry, or, 
		if((this.cavalry > 0 && this.infantry >0 && this.artillery > 0) || this.infantry >=3 || this.cavalry >= 3 || this.artillery >= 3){
			return true; 
		
		}else return false; 
	}
	
	public boolean Are_Playable(String[] card1){
		
		int tmp_inf = 0; 
		int tmp_art = 0; 
		int tmp_cav = 0; 
		for (String str : card1){
			
			if(str.contains("infantry")){
				tmp_inf ++; 
			} else if(str.contains("cavalry")){
				tmp_cav++; 
			}else if(str.contains("artillery")){
				tmp_art++; 
			}
			
		}
		
		if(this.infantry >= tmp_inf && this.cavalry >= tmp_cav && this.artillery >= tmp_art){
			
			this.infantry = this.infantry - tmp_inf; 
			this.cavalry = this.cavalry - tmp_cav; 
			this.artillery = this.artillery - tmp_art; 
			
			this.Num_Sets_traded_In++; 
			
			CalculateCardReinforcements(); 
			
			return true; 
		}
		else return false; 
		
		
	}
	
	private void CalculateCardReinforcements(){
		
		//The first set traded in - 4 armies
		//The second set traded in - 6 armies
		//The third set traded in - 8 armies
		//The fourth set traded in - 10 armies
		//The fifth set traded in - 12 armies
		//The sixth set traded in - 15 armies
		// from here on for each set : 5++
		
		
		switch(this.Num_Sets_traded_In){
		
		case 1: 
			this.card_reinforcements = 4; 
			break; 
		case 2: 
			this.card_reinforcements = 6; 
			break; 
		case 3: 
			this.card_reinforcements = 8; 
			break; 
		case 4: 
			this.card_reinforcements = 10; 
			break; 
		case 5:
			this.card_reinforcements = 12; 
			break; 
		case 6: 
			this.card_reinforcements = 15; 
			break; 
		default:
			this.card_reinforcements = this.card_reinforcements +5; 
			break; 
		}
		

		
	}
	
	public int Get_Card_Reinforcement_Qty(){
		return this.card_reinforcements; 
	}

}
