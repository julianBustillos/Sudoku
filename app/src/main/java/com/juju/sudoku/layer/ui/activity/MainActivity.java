package com.juju.sudoku.layer.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.ui.viewmodel.GridViewModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridViewModel gridModel = new ViewModelProvider(this).get(GridViewModel.class);
        View layoutView = findViewById(R.id.layout_main);

        layoutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_UP:
                        gridModel.setCurrentCellIndex(-1);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}