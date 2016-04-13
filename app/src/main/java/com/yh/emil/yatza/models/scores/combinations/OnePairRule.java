package com.yh.emil.yatza.models.scores.combinations;

import java.util.Map;

public class OnePairRule extends DiceCombinationRule {

	public OnePairRule() {
		super(Combination.ONE_PAIR);
	}

	@Override
	public int calculatePoints() {

		int points = 0;

		insertToMap();
		
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			
			if (entry.getValue() >= 2) {
				int newPoints = entry.getKey() * 2;
				if (newPoints > points) {
					points = newPoints;
				}
			}
		}

		return returnPointsAndClearMap(points);
	}

	@Override
	public String toString() {
		return "One pair";
	}
}
