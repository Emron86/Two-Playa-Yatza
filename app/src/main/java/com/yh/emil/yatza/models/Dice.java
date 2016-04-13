package com.yh.emil.yatza.models;

/**
 * Created by Emil on 2016-04-08.
 */
public class Dice {

    private int value;
    private boolean isSelected;

    public Dice() {
        randomizeDiceValue();
        this.isSelected = false;
    }

    public void toggleSelected() {
        this.isSelected = !this.isSelected;
    }

    public void unSelect() {
        this.isSelected = false;
    }

    public boolean tryRoll() {
        if (!isSelected) {
            randomizeDiceValue();
            return true;
        }
        else {
            unSelect();
            return false;
        }
    }

    private void randomizeDiceValue() {
        this.value = (int)(Math.random() * 6) + 1;
    }

    public Integer getValue() {
        return this.value;
    }

    public boolean isSelected() {
        return this.isSelected;
    }
}
