package com.yh.emil.yatza.models;

import com.yh.emil.yatza.models.scores.ScoreBoard;

/**
 * Created by Emil on 2016-04-08.
 */
public class Player {

    private String name;
    private ScoreBoard scoreBoard;

    public Player(String name) {
        this.name = name;
        this.scoreBoard = new ScoreBoard();
    }

    public String getName() {
        return this.name;
    }

    public void updateScoreboard(Integer[] combination) {
        this.scoreBoard.updateScores(combination);
    }

    public ScoreBoard getScoreboard() {
        return this.scoreBoard;
    }

    public boolean isFinished() {
        return this.scoreBoard.getScoresLeft() == 0;
    }

    public int getTotalScore() {
        return scoreBoard.getTotalScore();
    }
}
