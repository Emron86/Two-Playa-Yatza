package com.yh.emil.yatza.models.scores;

import java.util.List;

/**
 * Created by Emil on 2016-04-09.
 */
public class ScoreBoard {

    private List<ScoreEntry> scores;
    private int scoresLeft;
    private boolean isLocked;

    public ScoreBoard() {
        scores = ScoreEntryFactory.generateEntries();
        scoresLeft = scores.size();
        isLocked = false;
    }

    public void updateScores(Integer[] values) {
        for (ScoreEntry entry : scores) {
            if (!entry.isSet()) {
                entry.update(values);
            }
        }
    }

    public void clearScores() {
        for (ScoreEntry entry : scores)
            if (!entry.isSet())
                entry.clear();
    }

    public String getScore(int position) {
        int points = scores.get(position).getPoints();
        return points >= 0 ? String.valueOf(points) : "  ";
    }

    public int getTotalScore() {
        int upperSection = 0;
        int bonus = 0;
        int lowerSection = 0;

        for (ScoreEntry entry : scores) {
            //if entry is not set, continue to next entry
            if (!entry.isSet())
                continue;

            int points = entry.getPoints() > 0 ? entry.getPoints() : 0;
            if (entry.isUpperSection())
                upperSection += points;
            else
                lowerSection += points;
        }

        if (upperSection >= 63)
            bonus = 50;

        return upperSection + lowerSection + bonus;
    }

    public String getName(int position) {
        return scores.get(position).getName();
    }

    public boolean isSet(int position) {
        return scores.get(position).isSet();
    }

    public boolean tryToAddScore(int position) {
        ScoreEntry entry = scores.get(position);

        if (!entry.isSet() && !isLocked()) {
            lockBoard();
            entry.set();
            scoresLeft--;
            return true;
        }

        return false;
    }

    public ScoreEntry getEntry(int position) {
        return scores.get(position);
    }

    public int getScoresLeft() {
        return scoresLeft;
    }

    public int getCount() {
        return scores.size();
    }

    public void lockUpBoard() {
        this.isLocked = false;
    }

    public void lockBoard() {
        this.isLocked = true;
    }

    public boolean isLocked() {
        return this.isLocked;
    }
}