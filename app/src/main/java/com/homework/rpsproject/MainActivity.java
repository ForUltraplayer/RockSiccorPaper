package com.homework.rpsproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;
import java.util.Random;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageView HandAnimationView;
    ImageView SetHandView;
    ImageButton scissorsButton;
    ImageButton rockButton;
    ImageButton paperButton;
    ImageButton replayButton;
    AnimationDrawable animationDrawable;
    Button end_btn;
    TextToSpeech textToSpeech;
    int win = 0;
    int draw = 0;
    int lose = 0;
    TextView resultTextView; // 추가된 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandAnimationView = findViewById(R.id.hand_animation_view);
        SetHandView = findViewById(R.id.set_hand_view);
        scissorsButton = findViewById(R.id.scissors_button);
        rockButton = findViewById(R.id.rock_button);
        paperButton = findViewById(R.id.paper_button);
        replayButton = findViewById(R.id.replay_button);
        end_btn = findViewById(R.id.end_btn);
        animationDrawable = (AnimationDrawable) HandAnimationView.getDrawable();
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm); // your_bgm_file은 BGM 파일의 이름입니다
        mediaPlayer.setLooping(true); // BGM을 반복 재생하려면 true로 설정합니다.
        mediaPlayer.start(); // BGM을 시작합니다.
        textToSpeech = new TextToSpeech(getApplicationContext(), (i) -> {
            if (i != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.KOREAN);
                textToSpeech.setPitch(1.0f);
                textToSpeech.setSpeechRate(1.0f);
            }
        });
        resultTextView = findViewById(R.id.result_textview); // TextView 초기화

        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), score.class);
                intent.putExtra("winCount", win);
                intent.putExtra("drawCount", draw);
                intent.putExtra("loseCount", lose);
                startActivity(intent);
            }
        });
    }

    public void btn_click(View view) {
        int viewId = view.getId();

        if (viewId == R.id.replay_button) {
            SetHandView.setVisibility(View.GONE);
            HandAnimationView.setVisibility(View.VISIBLE);
            animationDrawable.start();
            voicePlay("가위바위보");
            replayButton.setEnabled(false);
            scissorsButton.setEnabled(true);
            rockButton.setEnabled(true);
            paperButton.setEnabled(true);
            resultTextView.setText(""); // 결과 메시지 초기화
        } else if (viewId == R.id.scissors_button || viewId == R.id.rock_button || viewId == R.id.paper_button) {
            replayButton.setEnabled(true);
            scissorsButton.setEnabled(false);
            rockButton.setEnabled(false);
            paperButton.setEnabled(false);
            animationDrawable.stop();
            HandAnimationView.setVisibility(View.GONE);
            SetHandView.setVisibility(View.VISIBLE);

            int userHand = Integer.parseInt(view.getTag().toString());
            int comHand = setComHand();
            winCheck(userHand, comHand);
        } else {
            // 다른 경우에 대한 처리
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.shutdown();
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // BGM 정지
            mediaPlayer.release(); // 미디어 플레이어 해제
        }
    }

    public void voicePlay(String voiceText) {
        textToSpeech.speak(voiceText, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public int setComHand() {
        int getComHand = new Random().nextInt(3) + 1;
        switch (getComHand) {
            case 1:
                SetHandView.setImageResource(R.drawable.scissors);
                break;
            case 2:
                SetHandView.setImageResource(R.drawable.rock);
                break;
            case 3:
                SetHandView.setImageResource(R.drawable.paper);
                break;
        }
        return getComHand;
    }

    public void winCheck(int userHand, int comHand) {
        int result = (3 + userHand - comHand) % 3;
        switch (result) {
            case 0: // 비김
                voicePlay("비겼어요");
                draw += 1;
                resultTextView.setText("무승부"); // 결과 메시지 업데이트
                break;
            case 1: // user 이김
                voicePlay("이겼어요.");
                win += 1;
                resultTextView.setText("승리"); // 결과 메시지 업데이트
                break;
            case 2: // com 이김
                voicePlay("졌어요.");
                lose += 1;
                resultTextView.setText("패배"); // 결과 메시지 업데이트
                break;
        }
    }
}
