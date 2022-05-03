package com.juju.sudoku.layer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.juju.sudoku.layer.data.repository.GridRepository;
import com.juju.sudoku.layer.ui.observable.GridCell;

public class GridViewModel extends ViewModel {
    private final GridRepository repository = new GridRepository();
    private final MutableLiveData<Integer> cellIndex = new MutableLiveData<>();
    private final MutableLiveData<GridCell[]> grid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> noteStatus = new MutableLiveData<>();

    public void initialize() {
        cellIndex.setValue(-1);
        grid.setValue(repository.fetchGrid());
        noteStatus.setValue(false);
    }

    public void setCurrentCellIndex(int index) {
        cellIndex.setValue(index);
    }

    public LiveData<Integer> getCurrentCellIndex() {
        return cellIndex;
    }

    public void setCurrentCellValue(int value) {
        if (cellIndex.getValue() >= 0) {
            if (!noteStatus.getValue()) {
                grid.getValue()[cellIndex.getValue()].setValue(value);
            }
            else {
                grid.getValue()[cellIndex.getValue()].setNote(value);
            }
            grid.setValue(grid.getValue());
        }
    }

    public void clearCurrentCellValue() {
        if (cellIndex.getValue() >= 0) {
            grid.getValue()[cellIndex.getValue()].clear();
            grid.setValue(grid.getValue());
        }
    }

    public LiveData<GridCell[]> getCurrentGrid() {
        return grid;
    }

    public LiveData<Boolean> getNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(boolean status) {
        noteStatus.setValue(status);
    }
}
