package com.juju.sudoku.layer.ui.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class ToggleButton extends Button {
    private boolean status = false;

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean getStatus () {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
