package com.juju.sudoku.layer.ui.observable;

import androidx.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridCell extends BaseObservable {
    private boolean updated = true;
    private final int realValue;
    private final boolean given;
    private int currentValue = 0;
    private boolean[] notes = { false, false, false, false, false, false, false, false, false, false };

    public GridCell(int realValue, boolean given) {
        this.realValue = realValue;
        this.given = given;
        if (given) {
            currentValue = realValue;
        }
    }

    public boolean isUpdated() {
        return updated;
    }

    public void updated() {
         updated = false;
    }

    public boolean isGiven () {
        return given;
    }

    public boolean isGuessed() {
        return !given && currentValue > 0;
    }

    public void setValue (int value) {
        if (!given && value != currentValue) {
            currentValue = value;
            Arrays.fill(notes, false);
            updated = true;
        }
    }

    public List<Integer> getValue() {
        ArrayList<Integer> value = new ArrayList<>();
        if (currentValue > 0) {
            value.add(currentValue);
        }
        else {
            for (int k = 0; k < 9; k++) {
                if (notes[k]) {
                    value.add(k + 1);
                }
            }
        }

        return value;
    }

    public void setNote (int value) {
        if (!given && currentValue == 0) {
            notes[value - 1] = !notes[value - 1];
            updated = true;
        }
    }

    public void clear() {
        if (!given) {
            currentValue = 0;
            Arrays.fill(notes, false);
            updated = true;
        }
    }
}
