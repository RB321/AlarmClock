package com.example.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText hoursEditText;
    EditText minutesEditText;
    TextView timerText;
    TextView textTimeLeft;
    int hoursDif, minDif;

    public void pressed(View view) {
        textTimeLeft.setVisibility(View.VISIBLE);
        updateTime();
        int timeLeft = (hoursDif * 3600000) + (minDif * 60000);
        new CountDownTimer(timeLeft, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTime();
                if (minDif <= 9)
                    timerText.setText(Integer.toString(hoursDif) + " hours  : 0" + Integer.toString(minDif) + " minutes");
                else
                    timerText.setText(Integer.toString(hoursDif) + " hours: " + Integer.toString(minDif) + " minutes");
            }

            @Override
            public void onFinish() {
                textTimeLeft.setVisibility(View.INVISIBLE);
                timerText.setText("Time is up!");
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.siren);
                mediaPlayer.start();
            }
        }.start();
    }

    public void updateTime() {
        int hours = Integer.valueOf(hoursEditText.getText().toString());
        int minutes = Integer.valueOf(minutesEditText.getText().toString());
        Date currentTime = Calendar.getInstance().getTime();
        String t = new SimpleDateFormat("HH:mm:ss").format(currentTime);
        String time[] = t.split(":");
        int current = (Integer.valueOf(time[1]) + 60 * Integer.valueOf(time[0]));
        int alarmTime = (hours * 60 + minutes);
        int timeDifference = alarmTime - current;
        if (timeDifference < 0)
            timeDifference = 24 * 60 + timeDifference;
        hoursDif = timeDifference / 60;
        minDif = timeDifference % 60;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hoursEditText = (EditText) findViewById(R.id.hoursEditTextNumber);
        minutesEditText = (EditText) findViewById(R.id.minutesEditTextNumber);
        timerText = (TextView) findViewById(R.id.timerTextView);
        textTimeLeft = (TextView) findViewById(R.id.timeLeftTextView);
        textTimeLeft.setVisibility(View.INVISIBLE);
    }
}