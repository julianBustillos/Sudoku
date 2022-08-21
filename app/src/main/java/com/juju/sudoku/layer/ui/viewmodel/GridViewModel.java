package com.juju.sudoku.layer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.juju.sudoku.layer.data.repository.GridRepository;
import com.juju.sudoku.layer.ui.observable.GridCell;

import java.util.LinkedList;

public class GridViewModel extends ViewModel {
    private final GridRepository repository = new GridRepository();
    private final MutableLiveData<Integer> cellIndex = new MutableLiveData<>();
    private final MutableLiveData<GridCell[]> grid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> noteStatus = new MutableLiveData<>();
    private final MutableLiveData<LinkedList<Action>> actionsList = new MutableLiveData<>();

    private static class Action {
        private final int cellIndex;
        private final GridCell.Data data;

        private Action(int cellIndex, GridCell.Data data) {
            this.cellIndex = cellIndex;
            this.data = data;
        }
    }

    public void initialize() {
        cellIndex.setValue(-1);
        grid.setValue(repository.fetchGrid());
        noteStatus.setValue(false);
        actionsList.setValue(new LinkedList<>());
    }

    public void setCellIndex(int index) {
        cellIndex.setValue(index);
    }

    public LiveData<Integer> getCellIndex() {
        return cellIndex;
    }

    public void setCurrent(int value) {
        int index = cellIndex.getValue();
        if (index >= 0) {
            storeLastAction();
            GridCell cell = grid.getValue()[index];
            if (!noteStatus.getValue()) {
                cell.setValue(value);
            }
            else {
                cell.setNote(value);
            }
            if (!cell.isUpdated()) {
                removeLastAction();
            }
            grid.setValue(grid.getValue());
        }
    }

    public void clearCurrent() {
        int index = cellIndex.getValue();
        if (index >= 0) {
            storeLastAction();
            GridCell cell = grid.getValue()[index];
            cell.setValue(0);
            if (!cell.isUpdated()) {
                removeLastAction();
            }
            grid.setValue(grid.getValue());
        }
    }

    public void undo() {
        LinkedList<Action> list = actionsList.getValue();
        if (!list.isEmpty()) {
            Action lastAction = list.pollFirst();
            actionsList.setValue(list);

            grid.getValue()[lastAction.cellIndex].setData(lastAction.data);
            grid.setValue(grid.getValue());
        }
    }

    public void reset() {
        LinkedList<Action> list = actionsList.getValue();
        list.clear();
        actionsList.setValue(list);

        for (int k = 0; k < 81; k++) {
            grid.getValue()[k].setValue(0);
        }
        grid.setValue(grid.getValue());
    }

    public LiveData<GridCell[]> getCurrentGrid() {
        return grid;
    }

    public void setNoteStatus(boolean status) {
        noteStatus.setValue(status);
    }

    private void storeLastAction() {
        LinkedList<Action> list = actionsList.getValue();
        int index = cellIndex.getValue();
        if (list.size() == 100) {
            list.pollLast();
        }
        list.addFirst(new Action(index, grid.getValue()[index].getData()));
        actionsList.setValue(list);
    }

    private void removeLastAction() {
        LinkedList<Action> list = actionsList.getValue();
        list.pollLast();
        actionsList.setValue(list);
    }
}
