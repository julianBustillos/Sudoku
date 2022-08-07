package com.juju.sudoku.layer.ui.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class GridView extends View {
    public enum DrawMode { EMPTY, GIVEN, GUESSED, NOTE }
    private class CellState {
        public DrawMode mode = DrawMode.EMPTY;
        public List<Integer> value = new ArrayList<>();
    }

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float thickness;
    private float cellSize;
    private int currentI;
    private int currentJ;
    private CellState[] cellState;

    public GridView(Context context) {
        super(context);
        initialize();
    }

    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    protected void initialize() {
        paint.setTextAlign(Paint.Align.CENTER);
        currentI = currentJ = -1;
        cellState = new CellState[81];
        for (int k = 0; k < 81; k++)
        {
            cellState[k] = new CellState();
        }
    }

    public int computeCellIndex(float x, float y) {
        int i = (int)(y / cellSize);
        int j = (int)(x / cellSize);
        return 9 * i + j;
    }

    public void setCellIndex(int index) {
        if (index >= 0) {
            currentI = index / 9;
            currentJ = index - 9 * currentI;
        }
        else {
            currentI = currentJ = -1;
        }
        invalidate();
    }

    public void setCellState(int pos, DrawMode mode, List<Integer> value) {
        cellState[pos].mode = mode;
        cellState[pos].value = value;
        invalidate();
    }

    protected int getIndex(int i, int j) {
        return 9 * i + j;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        thickness = w / (9f * 10f);
        cellSize = (w - thickness) / 9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (currentI == i & currentJ == j) {
                    drawBackground(i, j, true, canvas);
                }
                else if (currentI == i || currentJ == j) {
                    drawBackground(i, j, false, canvas);
                }
                switch (cellState[getIndex(i, j)].mode)
                {
                    case GIVEN:
                        drawValue(i, j, true, canvas);
                        break;
                    case GUESSED:
                        drawValue(i, j, false, canvas);
                        break;
                    case NOTE:
                        drawNotes(i, j, canvas);
                        break;
                }
            }
        }
        drawGrid(canvas);
    }

    protected void drawBackground(int i, int j, boolean isMain, Canvas canvas) {
        paint.setColor(isMain ? getColorFromAttr(com.google.android.material.R.attr.colorControlActivated) : getColorFromAttr(com.google.android.material.R.attr.colorControlHighlight));

        float offset = thickness * 0.5f;
        float posXMin = offset + j * cellSize;
        float posYMin = offset + i * cellSize;
        float posXMax = offset + (j + 1) * cellSize;
        float posYMax = offset + (i + 1) * cellSize;

        canvas.drawRect(posXMin, posYMin, posXMax, posYMax, paint);
    }

    protected void drawValue(int i, int j, boolean given, Canvas canvas) {
        paint.setColor(getColorFromAttr(given ? com.google.android.material.R.attr.colorPrimaryVariant : com.google.android.material.R.attr.colorPrimary));
        paint.setTextSize(cellSize * 0.7f);

        float offset = thickness * 0.5f;
        float posX = offset + (j + 0.5f) * cellSize;
        float posY = offset + (i + 0.5f) * cellSize - 0.5f * (paint.descent() + paint.ascent());

        canvas.drawText(String.valueOf(cellState[getIndex(i, j)].value.get(0)), posX, posY, paint);
    }

    protected void drawNotes(int i, int j, Canvas canvas) {
        paint.setColor(getColorFromAttr(com.google.android.material.R.attr.colorPrimary));
        paint.setTextSize(cellSize * 0.23f);

        float offset = thickness * 0.5f;

        for (int note : cellState[getIndex(i, j)].value) {
            int subI = (note - 1) / 3;
            int subJ = (note - 1) - 3 * subI;
            float posX = offset + (j + 0.167f + subJ * 0.333f) * cellSize;
            float posY = offset + (i + 0.167f + subI * 0.333f) * cellSize - 0.5f * (paint.descent() + paint.ascent());

            canvas.drawText(String.valueOf(note), posX, posY, paint);
        }

    }

    protected void drawGrid(Canvas canvas) {
        paint.setColor(getColorFromAttr(com.google.android.material.R.attr.colorPrimary));
        float offset = thickness * 0.5f;

        for (int i = 0; i < 10; i++)
        {
            float stroke = (i % 3 == 0) ? thickness : thickness * 0.5f;
            paint.setStrokeWidth(stroke);
            float pos = offset + i * cellSize;
            canvas.drawLine(pos, 0f, pos, this.getHeight(), paint);
            canvas.drawLine(0f, pos, this.getWidth(), pos, paint);
        }
    }

    protected int getColorFromAttr(int attribute)
    {
        TypedValue typedValue=new TypedValue();
        getContext().getTheme().resolveAttribute(attribute,typedValue,true);
        return typedValue.data;
    }
}
