package com.yh.emil.yatza.models.toplist;

/**
 * Created by Emil on 2016-04-04.
 */
import java.io.Serializable;
import java.util.Comparator;

public class TopListEntry implements Comparable<TopListEntry>, Comparator<TopListEntry>, Serializable {

    private String mName;
    private int mScore;

    public TopListEntry(String name, int score) {
        this.mName = name;
        this.mScore = score;
    }

    public String getName() {
        return this.mName;
    }

    public int getScore() {
        return this.mScore;
    }

    /**
     * Sorts the biggest values first
     * @param o entry to compare with
     * @return
     */
    @Override
    public int compareTo(TopListEntry o) {

        if (this.getScore() == o.getScore()) {
            return 0;
        } else if (this.getScore() < o.getScore()) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int compare(TopListEntry entry1, TopListEntry entry2) {
        return entry1.compareTo(entry2);
    }
}

