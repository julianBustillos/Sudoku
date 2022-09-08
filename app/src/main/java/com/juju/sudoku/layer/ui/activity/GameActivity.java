package com.juju.sudoku.layer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.data.object.GameLevel;
import com.juju.sudoku.layer.ui.fragment.GridFragment;
import com.juju.sudoku.layer.ui.viewmodel.GridViewModel;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GridViewModel gridModel = new ViewModelProvider(this).get(GridViewModel.class);
        View layoutView = findViewById(R.id.layout_main);
        FragmentManager fragmentManager =  getSupportFragmentManager();
        GridFragment gridFragment = (GridFragment) fragmentManager.findFragmentById(R.id.fragment_grid);
        Intent intent = getIntent();
        GameLevel.Value level = (GameLevel.Value)intent.getSerializableExtra(GameLevel.key);
        Bundle args = new Bundle();
        args.putSerializable(GameLevel.key, level);
        gridFragment.setArguments(args);

        layoutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_UP:
                        gridModel.setCellIndex(-1);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}