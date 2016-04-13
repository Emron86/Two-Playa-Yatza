package com.yh.emil.yatza.models.scores.combinations;;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiceRuleLister implements Iterable<DiceCombinationRule> {

	private List<DiceCombinationRule> diceCombinationRules;

	public DiceRuleLister() {
		diceCombinationRules = new ArrayList<DiceCombinationRule>();

		diceCombinationRules.add(new NumberRule(1));
		diceCombinationRules.add(new NumberRule(2));
		diceCombinationRules.add(new NumberRule(3));
		diceCombinationRules.add(new NumberRule(4));
		diceCombinationRules.add(new NumberRule(5));
		diceCombinationRules.add(new NumberRule(6));
		diceCombinationRules.add(new OnePairRule());
		diceCombinationRules.add(new TwoPairRule());
		diceCombinationRules.add(new ThreeKindRule());
		diceCombinationRules.add(new FourKindRule());
		diceCombinationRules.add(new SmallStraightRule());
		diceCombinationRules.add(new LargeStraightRule());
		diceCombinationRules.add(new HouseRule());
		diceCombinationRules.add(new ChanceRule());
		diceCombinationRules.add(new YahtzeeRule());
	}

	@Override
	public Iterator<DiceCombinationRule> iterator() {
		return new DiceCombinationRuleIterator();
	}

	private class DiceCombinationRuleIterator implements Iterator<DiceCombinationRule> {

		private int index = -1;

		@Override
		public boolean hasNext() {
			if (index + 1 < diceCombinationRules.size()) {
				return true;
			}

			return false;
		}

		@Override
		public DiceCombinationRule next() {
			if (hasNext()) {
				return diceCombinationRules.get(++index);
			} else {
				goToFirstIndex();
				return diceCombinationRules.get(index);
			}
		}

		@Override
		public void remove() {}

		public void goToFirstIndex() {
			index = 0;
		}

	}
}
