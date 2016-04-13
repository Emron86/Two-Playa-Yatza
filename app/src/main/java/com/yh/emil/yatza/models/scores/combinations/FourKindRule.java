package com.yh.emil.yatza.models.scores.combinations;

import java.util.Map;

public class FourKindRule extends DiceCombinationRule{
	
	public FourKindRule() {
		super(Combination.FOUR_OF_A_KIND);
	}

	@Override
	public int calculatePoints() {
		
		int points = 0;
		
		insertToMap();
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			
			if (entry.getValue() >= 4) {
				int newPoints = entry.getKey() * 4;
				if (newPoints > points) {
					points = newPoints;
				}
			}
		}

		return returnPointsAndClearMap(points);
	}
	@Override
	public String toString() {
		return "Four of a kind";
	}
}
