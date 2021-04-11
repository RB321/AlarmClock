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

    public void pressed(View view) {
        textTimeLeft.setVisibility(View.VISIBLE);
        int hours = Integer.valueOf(hoursEditText.getText().toString());

        int minutes = Integer.valueOf(minutesEditText.getText().toString());

        //int minDif, hoursDif;
        Date currentTime = Calendar.getInstance().getTime();
        String t = new SimpleDateFormat("HH:mm:ss").format(currentTime);
        String time[] = t.split(":");
        System.out.println("value: " + time[0] + " " + time[1]);

        int current = (Integer.valueOf(time[1]) + 60*Integer.valueOf(time[0]));
        int alarmTime = (hours*60+minutes);
        int timeDifference = alarmTime - current;
        if (timeDifference < 0) {
            timeDifference = 24 * 60 + timeDifference;
        }
        int hoursDif = timeDifference / 60;
        int minDif = timeDifference % 60;

        int timeLeft = (hoursDif * 3600000) + (minDif * 60000);

        System.out.println("value: " + timeLeft);

        new CountDownTimer(timeLeft, 60000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Date currentTime = Calendar.getInstance().getTime();
                String t = new SimpleDateFormat("HH:mm:ss").format(currentTime);
                String time[] = t.split(":");
                int current = (Integer.valueOf(time[1]) + 60*Integer.valueOf(time[0]));
                int alarmTime = (hours*60+minutes);
                int timeDifference = alarmTime - current;
                if (timeDifference < 0) {
                    timeDifference = 24 * 60 + timeDifference;
                }
                int hoursDif = timeDifference / 60;
                int minDif = timeDifference % 60;

                int timeLeft = (hoursDif * 3600000) + (minDif * 60000);
                if (minDif <= 9)
                    timerText.setText(Integer.toString(hoursDif) + " hours  : 0" + Integer.toString(minDif) + " minutes");
                else
                    timerText.setText(Integer.toString(hoursDif) + " hours: " + Integer.toString(minDif) + " minutes");
            }

            @Override
            public void onFinish() {

                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.siren);
                mediaPlayer.start();
            }
        }.start();
    }

    //public void update
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