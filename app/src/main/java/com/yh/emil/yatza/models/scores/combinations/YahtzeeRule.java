package com.yh.emil.yatza.models.scores.combinations;

import java.util.Map;

public class YahtzeeRule extends DiceCombinationRule{

	public YahtzeeRule() {
		super(Combination.YAHTZEE);
	}

	@Override
	public int calculatePoints() {
		
		int points = 0;

		insertToMap();

		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

			// If the player has Yatzy(all 5 dices are same), the points will be 50
			if (entry.getValue() == 5) {
				points = 50;
			} 
		}

		return returnPointsAndClearMap(points);
	}
	
	@Override
	public String toString() {
		return "Yahtzee";
	}
}
