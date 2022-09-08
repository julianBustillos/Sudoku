package com.juju.sudoku.layer.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.ui.observable.GameLevel;
import com.juju.sudoku.layer.ui.observable.GridCell;
import com.juju.sudoku.layer.ui.view.GridView;
import com.juju.sudoku.layer.ui.viewmodel.GridViewModel;

public class GridFragment extends Fragment {

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_grid, container, false);
        GridView gridView = fragmentView.findViewById(R.id.view_grid);
        GridViewModel gridModel = new ViewModelProvider(this.requireActivity()).get(GridViewModel.class);

        gridView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_UP:
                        int cellIndex = gridView.computeCellIndex(event.getX(), event.getY());
                        gridModel.setCellIndex(cellIndex);
                        return true;
                    default:
                        return false;
                }
            }
        });

        final Observer<Integer> cellIndexObserver = new Observer<Integer>() {
            @Override
            public void onChanged(final Integer index) {
                gridView.setCellIndex(index);
            }
        };

        final Observer<GridCell[]> gridObserver = new Observer<GridCell[]>() {
            @Override
            public void onChanged(@Nullable final GridCell[] grid) {
                for (int pos = 0; pos < 81; pos++) {
                    if (grid[pos].isUpdated()) {
                        GridView.DrawMode mode = GridView.DrawMode.EMPTY;
                        if (grid[pos].isGuessed()) {
                            mode = GridView.DrawMode.GUESSED;
                        }
                        else if (grid[pos].isNote()) {
                            mode = GridView.DrawMode.NOTE;
                        }
                        else if (grid[pos].isGiven()) {
                            mode = GridView.DrawMode.GIVEN;
                        }
                        gridView.setCellState(pos, mode, grid[pos].getValue());
                        grid[pos].updated();
                    }
                }
            }
        };

        Bundle args = getArguments();
        GameLevel.Value level = (GameLevel.Value)args.getSerializable(GameLevel.key);
        gridModel.getCellIndex().observe(this.requireActivity(), cellIndexObserver);
        gridModel.getCurrentGrid().observe(this.requireActivity(), gridObserver);
        gridModel.initialize(level);

        return fragmentView;
    }
}