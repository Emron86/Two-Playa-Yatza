package com.yh.emil.yatza.models.scores.combinations;

public class NumberRule extends DiceCombinationRule {
	
	private int number;
	
	public NumberRule(int number) {
		super(Combination.NUMBER);
		this.number = number;
		isUpperSectionRule = true;
	}

	@Override
	public int calculatePoints() {
		int points = 0;
		for (Integer valueOnDice : dices) {
			if (valueOnDice == number) {
				points+=valueOnDice;
			}
		}
		return points;
	}
	
	@Override
	public String toString() {
		return "Number " + number;
	}
}
