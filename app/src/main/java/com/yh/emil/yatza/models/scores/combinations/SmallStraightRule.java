package com.yh.emil.yatza.models.scores.combinations;

public class SmallStraightRule extends DiceCombinationRule{

	public SmallStraightRule() {
		super(Combination.SMALL_STRAIGHT);
	}

	@Override
	public int calculatePoints() {
		
		int points = 0;
		
		boolean isSmallStraight = true;
		
		int straightCheck = 1;
		
		for (Integer valueOnDice : dices) {
			if (valueOnDice != straightCheck) {
				isSmallStraight = false;
				break;
			}
			
			straightCheck++;
		}
		
		//In Yatzy a Small Straight is 15 points, because: 1+2+3+4+5 = 15
		if (isSmallStraight) {
			points = 15;
		}

		return returnPointsAndClearMap(points);
	}
	
	@Override
	public String toString() {
		return "Small straight";
	}
}
