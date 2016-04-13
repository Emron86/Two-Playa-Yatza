package com.yh.emil.yatza.models.scores.combinations;

public enum Combination {

	NUMBER(1), ONE_PAIR(2), TWO_PAIR(3), THREE_OF_A_KIND(4), FOUR_OF_A_KIND(5), SMALL_STRAIGHT(6), LARGE_STRAIGHT(
			7), HOUSE(8), CHANCE(9), YAHTZEE(10);

	private int id;

	private Combination(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static String getNameFromID(int id) {

		String combinationName;

		switch (id) {
		case 1:
			combinationName = NUMBER.toString();
			break;
		case 2:
			combinationName = ONE_PAIR.toString();
			break;
		case 3:
			combinationName = TWO_PAIR.toString();
			break;
		case 4:
			combinationName = THREE_OF_A_KIND.toString();
			break;
		case 5:
			combinationName = FOUR_OF_A_KIND.toString();
			break;
		case 6:
			combinationName = SMALL_STRAIGHT.toString();
			break;
		case 7:
			combinationName = LARGE_STRAIGHT.toString();
			break;
		case 8:
			combinationName = HOUSE.toString();
			break;
		case 9:
			combinationName = CHANCE.toString();
			break;
		case 10:
			combinationName = YAHTZEE.toString();
			break;
		default:
			combinationName = "unknown";
		}

		return combinationName;
	}
}