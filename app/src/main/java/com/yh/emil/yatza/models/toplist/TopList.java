package com.yh.emil.yatza.models.toplist;

/**
 * Created by Emil on 2016-04-04.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopList implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<TopListEntry> mEntries;
    private int mMaxSize;

    public TopList(int maxSize, boolean isDefaultListGenerated) {

        this.mEntries = new ArrayList<TopListEntry>();
        this.mMaxSize = maxSize;

        if (isDefaultListGenerated) {
            generateDefaultList();
        }
    }

    private void generateDefaultList() {

        for (int i = 0; i < mMaxSize; i++) {
            tryToAddEntry("testName" + i, (int)(Math.random() * 100) + 1);
        }
    }

    public boolean tryToAddEntry(String name, int score) {

        if (mEntries.size() < mMaxSize) {
            mEntries.add(new TopListEntry(name, score));
        } else {
            if (score > mEntries.get(mMaxSize - 1).getScore()) {
                mEntries.remove(mMaxSize - 1);
                mEntries.add(new TopListEntry(name, score));
            } else {
                return false;
            }
        }
        Collections.sort(mEntries);

        return true;
    }

    public List<TopListEntry> getEntries() {

        return Collections.unmodifiableList(mEntries);
    }
}