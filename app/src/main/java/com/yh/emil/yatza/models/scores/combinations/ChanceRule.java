package com.yh.emil.yatza.models.scores.combinations;

public class ChanceRule extends DiceCombinationRule {

	public ChanceRule() {
		super(Combination.CHANCE);
	}

	@Override
	public int calculatePoints() {

		int points = 0;
		for (Integer valueOnDice : dices) {
			points += valueOnDice;
		}
		return points;
	}

	@Override
	public String toString() {
		return "Chance";
	}

}
