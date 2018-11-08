package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * this is a model class for Dice
 *
 */
public class Dice {

	/**
	 * this method roll a dice
	 * 
	 * @return number value of the rolled dice
	 */
	public static int Roll() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}

	/**
	 * this method roll a number of dice
	 * 
	 * @param nb_roll
	 *            number of dice to be used
	 * @return a sorted ArrayList of values of rolled dice
	 */
	public static ArrayList<Integer> Roll(int nb_roll) {
		ArrayList<Integer> numbers = new ArrayList<>();

		for (int i = 0; i < nb_roll; i++) {
			numbers.add(Roll());
		}

		Collections.sort(numbers, Collections.reverseOrder());
		return numbers;
	}
}
