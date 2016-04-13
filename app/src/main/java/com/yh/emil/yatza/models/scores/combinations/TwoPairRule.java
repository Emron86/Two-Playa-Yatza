package com.yh.emil.yatza.models.scores.combinations;

import java.util.Map;

public class TwoPairRule extends DiceCombinationRule{

	public TwoPairRule() {
		super(Combination.TWO_PAIR);
	}

	@Override
	public int calculatePoints() {
		
		int points = 0;

		insertToMap();
		
		int tempPoints = 0;
		int count = 0;
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			
			if (entry.getValue() >= 2) {
				count++;
				
				tempPoints += entry.getKey() * 2;
			}
		}
		
		if (count >= 2) {
			points = tempPoints;
		}

		return returnPointsAndClearMap(points);
	}
	
	@Override
	public String toString() {
		return "Two pair";
	}
}
