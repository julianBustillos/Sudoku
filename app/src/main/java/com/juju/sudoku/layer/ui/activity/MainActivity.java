package com.juju.sudoku.layer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.ui.observable.GameLevel;

public class MainActivity extends AppCompatActivity {
    private final MutableLiveData<GameLevel.Value> level = new MutableLiveData<>(GameLevel.Value.EASY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.button_start);
        View.OnClickListener onClickStart = view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra(GameLevel.key, level.getValue());
            MainActivity.this.startActivity(intent);
        };
        startButton.setOnClickListener(onClickStart);

        Button levelButton = findViewById(R.id.button_level);
        View.OnClickListener onClickLevel = view -> {
            level.setValue(level.getValue().next());
        };
        levelButton.setOnClickListener(onClickLevel);

        final Observer<GameLevel.Value> levelObserver = new Observer<GameLevel.Value>() {
            @Override
            public void onChanged(final GameLevel.Value level) {
                setLevelText(levelButton, level);
            }
        };
        level.observe(this, levelObserver);
    }

    protected void setLevelText(Button levelButton, GameLevel.Value level) {
        switch (level) {
            case EASY:
                levelButton.setText("Easy");
                break;
            case MEDIUM:
                levelButton.setText("Medium");
                break;
            case HARD:
                levelButton.setText("Hard");
                break;
            default:
                levelButton.setText("BUG");
        }
    }
}