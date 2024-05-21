package com.homework.rpsproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class score extends AppCompatActivity {

    TextView win;
    TextView draw;
    TextView lose;

    Button main_btn;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        win = findViewById(R.id.winCount);
        draw = findViewById(R.id.drawCount);
        lose = findViewById(R.id.loseCount);

        intent = getIntent();

        int win1 = intent.getExtras().getInt("winCount");
        win.setText("승리횟수 : " + win1);
        int draw1 = intent.getExtras().getInt("drawCount");
        draw.setText("무승부횟수 : " + draw1);
        int lose1 = intent.getExtras().getInt("loseCount");
        lose.setText("패배횟수 : " + lose1);

        main_btn = findViewById(R.id.main_btn);

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mainlobby.class);
                startActivity(intent);
            }
        });

    }
}