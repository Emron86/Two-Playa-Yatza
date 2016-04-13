package com.yh.emil.yatza.models.scores.combinations;

import java.util.Map;

public class HouseRule extends DiceCombinationRule {

	public HouseRule() {
		super(Combination.HOUSE);
	}

	@Override
	public int calculatePoints() {

		int points = 0;

		insertToMap();

		int pairPoints = 0;
		int threeKindPoints = 0;

		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

			// In house only one pair and one Three of a kind will fit
			if (entry.getValue() == 2) {
				pairPoints = entry.getValue() * entry.getKey();
			} else if (entry.getValue() == 3) {
				threeKindPoints = entry.getValue() * entry.getKey();
			}
		}

		// if both pairPoints & threeKindPoints have a value > 0
		// then the player has House. return the sum of the pair+three of a kind
		if (pairPoints > 0 && threeKindPoints > 0) {
			points = pairPoints + threeKindPoints;
		}

		return returnPointsAndClearMap(points);
	}

	@Override
	public String toString() {
		return "House";
	}
}
