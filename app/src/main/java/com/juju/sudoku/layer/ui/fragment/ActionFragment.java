package com.juju.sudoku.layer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.ui.button.NumButton;
import com.juju.sudoku.layer.ui.viewmodel.GridViewModel;


public class ActionFragment extends Fragment {

    public ActionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_action, container, false);
        GridViewModel gridModel = new ViewModelProvider(this.requireActivity()).get(GridViewModel.class);

        NumButton numButton[] = new NumButton[9];
        numButton[0] = fragmentView.findViewById(R.id.button_1);
        numButton[1] = fragmentView.findViewById(R.id.button_2);
        numButton[2] = fragmentView.findViewById(R.id.button_3);
        numButton[3] = fragmentView.findViewById(R.id.button_4);
        numButton[4] = fragmentView.findViewById(R.id.button_5);
        numButton[5] = fragmentView.findViewById(R.id.button_6);
        numButton[6] = fragmentView.findViewById(R.id.button_7);
        numButton[7] = fragmentView.findViewById(R.id.button_8);
        numButton[8] = fragmentView.findViewById(R.id.button_9);
        View.OnClickListener onClickNumber = view -> {
            NumButton button = (NumButton)view;
            gridModel.setCurrent(button.getValue());
        };
        for (int k = 0; k < 9; k++) {
            numButton[k].setValue(k + 1);
            numButton[k].setOnClickListener(onClickNumber);
        }

        Button clearButton = fragmentView.findViewById(R.id.button_clear);
        View.OnClickListener onClickClear = view -> gridModel.clearCurrent();
        clearButton.setOnClickListener(onClickClear);

        Button undoButton = fragmentView.findViewById(R.id.button_undo);
        View.OnClickListener onClickUndo = view -> gridModel.undo();
        undoButton.setOnClickListener(onClickUndo);

        Button resetButton = fragmentView.findViewById(R.id.button_reset);
        View.OnClickListener onClickReset = view -> gridModel.reset();//TODO CONFIRMATION POPUP
        resetButton.setOnClickListener(onClickReset);

        ToggleButton noteButton = fragmentView.findViewById(R.id.button_note);
        CompoundButton.OnCheckedChangeListener onCheckedNote = (view, isChecked) -> {
            ToggleButton button = (ToggleButton)view;
            gridModel.setNoteStatus(isChecked);
        };
        noteButton.setOnCheckedChangeListener(onCheckedNote);

        return fragmentView;
    }
}