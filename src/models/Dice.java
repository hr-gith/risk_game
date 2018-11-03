package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Dice {
    public static int Roll() {
        Random random = new Random(); 
        return random.nextInt(6) + 1;         
    }
    
    public static ArrayList<Integer> Roll(int nb_roll) {
		ArrayList<Integer> numbers = new ArrayList<>(); 
		
		for(int i = 0; i < nb_roll; i++) {
			numbers.add(Roll());
		}
		
		Collections.sort(numbers);
		return numbers;
    }
}
