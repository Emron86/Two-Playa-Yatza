package com.yh.emil.yatza.models.scores.combinations;

import com.yh.emil.yatza.models.scores.combinations.data_structures.SortedLinkList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class DiceCombinationRule {

	public SortedLinkList<Integer> dices;
	protected boolean isUpperSectionRule;
	protected Map<Integer, Integer> map;
	protected Combination combination;
	
	public DiceCombinationRule(Combination combination) {
		isUpperSectionRule = false;
		map = new HashMap<Integer, Integer>();
		this.combination = combination;
	}
	
	public abstract int calculatePoints();
	
	public void setCombination(Integer[] diceUnsorted) {

		this.dices = SortedLinkList.parseArray(diceUnsorted);
	}
	
	public boolean isUpperSectionRule() {
		return isUpperSectionRule;
	}
	
	//this is used for inserting correct id-value into database
	public int getCombinationID() {
		return combination.getId();
	}
	
	public void insertToMap() {
		
		Iterator<Integer> it = dices.iterator();
		
		while (it.hasNext()) {
			
			Integer integer = it.next();
			
			int val = map.get(integer) == null ? 0 : map.get(integer);
			map.put(integer, ++val);
		}
	}
	
	protected Integer returnPointsAndClearMap(Integer points) {
		map.clear();
		return points;
	}
}
