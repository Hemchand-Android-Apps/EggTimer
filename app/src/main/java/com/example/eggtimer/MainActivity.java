package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timerLabel;
    Button goButton;
    CountDownTimer countDownTimer;
    boolean counterActive = false;

    public void go(final View view) {
        if (counterActive) {
       stopTimer();
    } else {
            counterActive = true;
            goButton.setText("Stop!");
            seekBar.setEnabled(false);

            int millisInFuture = seekBar.getProgress() * 1000;
            countDownTimer = new CountDownTimer(millisInFuture, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    long remaining = millisUntilFinished / 1000;
                    setTimer((int) remaining);
                    seekBar.setProgress((int) remaining);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.airhorn);
                    mediaPlayer.start();
                    stopTimer();
                }
            };
            countDownTimer.start();
        }
}

    public void stopTimer() {
        counterActive = false;
        countDownTimer.cancel();
        goButton.setText("Go!");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        setTimer(30);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        seekBar = findViewById(R.id.seekBar);
        timerLabel = findViewById(R.id.timeLabel);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setTimer(int progress) {
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if (seconds < 9) {
            secondString = "0" + seconds;
        }
        timerLabel.setText(minutes + ":" + secondString);
    }

}
