package com.yh.emil.yatza.models;

/**
 * Created by Emil on 2016-04-08.
 */
import java.util.Comparator;
import java.util.HashMap;

public class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {

    HashMap<K, V> map = new HashMap<K, V>();

    public ValueComparator(HashMap<K, V> map) {
        this.map.putAll(map);
    }

    /**
     * Compares in descending order
     * @param k1 first value for comparison
     * @param k2 second value to compare with
     * @return
     */
    @Override
    public int compare(K k1, K k2) {
        //descending order
        return -map.get(k1).compareTo(map.get(k2));
    }

}
