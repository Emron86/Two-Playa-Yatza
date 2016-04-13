package com.yh.emil.yatza.models;

import java.util.Comparator;

/**
 * Created by Emil on 2016-04-10.
 */
/*
A class that implements comparator. If sorted, then
 */
public class PlayerScore implements Comparator<PlayerScore>, Comparable<PlayerScore> {

    private Player player;
    private Integer score;

    public PlayerScore(Player player) {
        this.player = player;
        this.score = player.getTotalScore();
    }

    /*
    Used if there's a need to add the score manually
     */
    public PlayerScore(Player player, Integer score) {
        this.player = player;
        this.score = score;
    }

    /*
    Sorted from highest to lowest
     */
    @Override
    public int compare(PlayerScore p1, PlayerScore p2) {
        return p1.score.compareTo(p2.score);
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getScore() {
        return score;
    }

    public String getName() {
        return player.getName();
    }

    @Override
    public int compareTo(PlayerScore another) {
        if (this.getScore() > another.getScore()) {
            return -1;
        } else if (this.getScore() > another.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
