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

    public boolean isNote() { return !given && currentValue < 0; }

    public void setValue (int value) {
        if (!given && value != currentValue) {
            if (currentValue < 0)
            {
                Arrays.fill(notes, false);
            }
            currentValue = value;
            updated = true;
        }
    }

    public List<Integer> getValue() {
        ArrayList<Integer> value = new ArrayList<>();
        if (currentValue > 0) {
            value.add(currentValue);
        }
        else if (currentValue < 0) {
            for (int k = 0; k < 9; k++) {
                if (notes[k]) {
                    value.add(k + 1);
                }
            }
        }

        return value;
    }

    public void setNote (int value) {
        if (!given && currentValue <= 0) {
            notes[value - 1] = !notes[value - 1];
            currentValue += notes[value - 1] ? -1 : 1;
            updated = true;
        }
    }


    public class Data extends BaseObservable {
        private boolean isNote;
        private List<Integer> value;

        private Data(boolean isNote, List<Integer> value) {
            this.isNote = isNote;
            this.value = value;
        }
    }

    public Data getData() {
        return new Data(isNote(), getValue());
    }

    public void setData(Data data) {
        if (!data.isNote) {
            setValue(!data.value.isEmpty() ? data.value.get(0) : 0);
        }
        else
        {
            setValue(0);
            for (int note : data.value) {
                setNote(note);
            }
        }
    }
}
