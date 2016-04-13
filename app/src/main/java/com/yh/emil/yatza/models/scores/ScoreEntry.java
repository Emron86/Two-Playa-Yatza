package com.yh.emil.yatza.models.scores;

import com.yh.emil.yatza.models.scores.combinations.DiceCombinationRule;

/**
 * Created by Emil on 2016-04-09.
 */
public class ScoreEntry {

    private DiceCombinationRule rule;
    private int score;
    private boolean set;

    public ScoreEntry(DiceCombinationRule rule) {
        this.rule = rule;
        this.score = -1;
        this.set = false;
    }

    public void update(Integer[] values) {
            rule.setCombination(values);
            this.score = rule.calculatePoints();
    }

    public void clear() {
        this.score = -1;
    }

    public boolean isSet() {
        return this.set;
    }

    public void set() {
        this.set = true;
    }

    public int getPoints() {
        return score;
    }

    public String getName() {
        return rule.toString();
    }

    public boolean isUpperSection() {
        return rule.isUpperSectionRule();
    }
}
