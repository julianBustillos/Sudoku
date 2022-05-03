package com.juju.sudoku.layer.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.ui.button.NumButton;
import com.juju.sudoku.layer.ui.button.ToggleButton;
import com.juju.sudoku.layer.ui.viewmodel.GridViewModel;


public class ActionFragment extends Fragment {
    private NumButton numButton[];
    private Button clearButton;
    private ToggleButton noteButton;


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

        numButton = new NumButton[9];
        numButton[0] = fragmentView.findViewById(R.id.button_1);
        numButton[1] = fragmentView.findViewById(R.id.button_2);
        numButton[2] = fragmentView.findViewById(R.id.button_3);
        numButton[3] = fragmentView.findViewById(R.id.button_4);
        numButton[4] = fragmentView.findViewById(R.id.button_5);
        numButton[5] = fragmentView.findViewById(R.id.button_6);
        numButton[6] = fragmentView.findViewById(R.id.button_7);
        numButton[7] = fragmentView.findViewById(R.id.button_8);
        numButton[8] = fragmentView.findViewById(R.id.button_9);
        View.OnClickListener onClickNumber = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumButton numButton = (NumButton)view;
                gridModel.setCurrentCellValue(numButton.getValue());
            }
        };
        for (int k = 0; k < 9; k++) {
            numButton[k].setValue(k + 1);
            numButton[k].setOnClickListener(onClickNumber);
        }

        clearButton = fragmentView.findViewById(R.id.button_clear);
        View.OnClickListener onClickClear = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridModel.clearCurrentCellValue();
            }
        };
        clearButton.setOnClickListener(onClickClear);

        noteButton = fragmentView.findViewById(R.id.button_note);
        View.OnClickListener onClickNote = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton noteButton = (ToggleButton)view;
                gridModel.setNoteStatus(!noteButton.getStatus());
            }
        };
        noteButton.setOnClickListener(onClickNote);
        final Observer<Boolean> noteStatusObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(final Boolean status) {
                noteButton.setStatus(status);
            }
        };
        gridModel.getNoteStatus().observe(this.requireActivity(), noteStatusObserver);

        return fragmentView;
    }
}