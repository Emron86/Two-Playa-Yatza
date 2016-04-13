package com.yh.emil.yatza.models.scores;

import com.yh.emil.yatza.models.scores.combinations.*;

import java.util.ArrayList;

/**
 * Created by Emil on 2016-04-09.
 */
public class ScoreEntryFactory {

    public static ArrayList<ScoreEntry> generateEntries() {

        ArrayList<ScoreEntry> scores = new ArrayList<ScoreEntry>();

        scores.add(new ScoreEntry(new NumberRule(1)));
        scores.add(new ScoreEntry(new NumberRule(2)));
        scores.add(new ScoreEntry(new NumberRule(3)));
        scores.add(new ScoreEntry(new NumberRule(4)));
        scores.add(new ScoreEntry(new NumberRule(5)));
        scores.add(new ScoreEntry(new NumberRule(6)));
        scores.add(new ScoreEntry(new OnePairRule()));
        scores.add(new ScoreEntry(new TwoPairRule()));
        scores.add(new ScoreEntry(new ThreeKindRule()));
        scores.add(new ScoreEntry(new FourKindRule()));
        scores.add(new ScoreEntry(new SmallStraightRule()));
        scores.add(new ScoreEntry(new LargeStraightRule()));
        scores.add(new ScoreEntry(new HouseRule()));
        scores.add(new ScoreEntry(new ChanceRule()));
        scores.add(new ScoreEntry(new YahtzeeRule()));

        return scores;
    }
}
