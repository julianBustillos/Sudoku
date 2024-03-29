package com.juju.sudoku.layer.data.repository;

import com.juju.sudoku.layer.ui.observable.GameLevel;
import com.juju.sudoku.layer.ui.observable.GridCell;

public class GridRepository {

    public GridCell[] fetchGrid(GameLevel.Value level) {
        int value[] = { 4, 2, 6, 5, 7, 1, 3, 9, 8,
                        8, 5, 7, 2, 9, 3, 1, 4, 6,
                        1, 3, 9, 4, 6, 8, 2, 7, 5,
                        9, 7, 1, 3, 8, 5, 6, 2, 4,
                        5, 4, 3, 7, 2, 6, 8, 1, 9,
                        6, 8, 2, 1, 4, 9, 7, 5, 3,
                        7, 9, 4, 6, 3, 2, 5, 8, 1,
                        2, 6, 5, 8, 1, 4, 9, 3, 7,
                        3, 1, 8, 9, 5, 7, 4, 6, 2};

        boolean given[] = {false, true, false, true, false, true, false, true, false,
                           true, false, false, true, false, true, false, false, true,
                           false, true, false, false, true, false, false, true, false,
                           false, false, true, false, false, false, true, false, false,
                           true, true, false, false, false, false, false, true, true,
                           false, false, true, false, false, false, true, false, false,
                           false, true, false, false, true, false, false, true, false,
                           true, false, false, true, false, true, false, false, true,
                           false, true, false, true, false, true, true, false, true};

        GridCell[] grid = new GridCell[81];
        for (int k = 0; k < 81; k++) {
            grid[k] = new GridCell(value[k], given[k]);
        }

        return grid;
    }
}
