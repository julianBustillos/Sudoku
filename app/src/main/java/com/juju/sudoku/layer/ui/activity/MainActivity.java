package com.juju.sudoku.layer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.juju.sudoku.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.button_start);
        View.OnClickListener onClickStart = view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            //intent.putExtra("key", value);
            MainActivity.this.startActivity(intent);
        };
        startButton.setOnClickListener(onClickStart);
    }
}