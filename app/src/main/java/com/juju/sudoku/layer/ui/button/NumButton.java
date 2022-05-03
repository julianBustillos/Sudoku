package com.juju.sudoku.layer.ui.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class NumButton extends Button {
    private int value;

    public NumButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setValue(int value) {
        this.value = value;
        setText(String.valueOf(value));
    }

    public int getValue() {
        return value;
    }
}
