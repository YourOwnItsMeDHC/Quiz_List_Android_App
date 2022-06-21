package com.example.quizlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class WonActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultText;
    int correct, wrong;
    LinearLayout btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        //Take correct and wrong count from dashboard activity from "correct" and "wrong" variable ,
        //which in turn take values from "Correct()" and "Wrong()" method :
        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);


        //Find score circular progress bar and score (text) by Id :
        circularProgressBar = findViewById(R.id.circularProgressBar);
        resultText = findViewById(R.id.resultText);

        //Share :
        btnShare = findViewById(R.id.btnShare);

        //Set circular progress bar based on correct answer
        circularProgressBar.setProgress(correct);

        //Set score based on correct answer
        resultText.setText(correct + " / 10");

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //https://stackoverflow.com/questions/4969217/share-application-link-in-android

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nHey Dear, I have scored " + correct + " out of 10, You should also try Quiz List by Deepak Chourasiya \n\n" + " " + "\n Have a try : \n\n";
                    shareMessage = shareMessage + "https://drive.google.com/drive/u/0/folders/1cllFn3c9gEyJxW0Mx0czJ9c_Y6u0qf76";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }

            }
        });

    }

    public void wonBack(View view) {
        Intent intent = new Intent(WonActivity.this, SplashActivity.class);
        startActivity(intent);
    }

    public void wonExit(View view) {
        Intent intent = new Intent(WonActivity.this, SplashActivity.class);
        startActivity(intent);
    }
}