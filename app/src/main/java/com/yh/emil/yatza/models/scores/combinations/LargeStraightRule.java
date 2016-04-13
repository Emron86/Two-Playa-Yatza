package com.yh.emil.yatza.models.scores.combinations;

public class LargeStraightRule extends DiceCombinationRule {

	public LargeStraightRule() {
		super(Combination.LARGE_STRAIGHT);
	}

	@Override
	public int calculatePoints() {

		int points = 0;

		boolean isLargeStraight = true;

		int straightCheck = 2;

		for (Integer valueOnDice : dices) {
			if (valueOnDice != straightCheck) {
				isLargeStraight = false;
				break;
			}

			straightCheck++;
		}

		// In Yatzy a Small Straight is 15 points, because: 1+2+3+4+5 = 15
		if (isLargeStraight) {
			points = 20;
		}

		return returnPointsAndClearMap(points);
	}

	@Override
	public String toString() {
		return "Large straight";
	}
}
