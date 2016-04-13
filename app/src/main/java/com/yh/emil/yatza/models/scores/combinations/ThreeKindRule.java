package com.yh.emil.yatza.models.scores.combinations;

import java.util.Map;

public class ThreeKindRule extends DiceCombinationRule{

	public ThreeKindRule() {
		super(Combination.THREE_OF_A_KIND);
	}

	@Override
	public int calculatePoints() {
		
		int points = 0;

		insertToMap();
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			
			if (entry.getValue() >= 3) {
				int newPoints = entry.getKey() * 3;
				if (newPoints > points) {
					points = newPoints;
				}
			}
		}

		return returnPointsAndClearMap(points);
	}
	
	@Override
	public String toString() {
		return "Three of a kind";
	}
}
