package com.juju.sudoku.layer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.juju.sudoku.R;
import com.juju.sudoku.layer.data.object.GameLevel;

public class MainActivity extends AppCompatActivity {
    protected GameLevel.Value activityLevel = GameLevel.Value.EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.button_start);
        View.OnClickListener onClickStart = view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra(GameLevel.key, activityLevel);
            MainActivity.this.startActivity(intent);
        };
        startButton.setOnClickListener(onClickStart);

        Button levelButton = findViewById(R.id.button_level);
        setLevelText(levelButton, activityLevel);
        View.OnClickListener onClickLevel = view -> {
            activityLevel = activityLevel.next();
            setLevelText(levelButton, activityLevel);
        };
        levelButton.setOnClickListener(onClickLevel);
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