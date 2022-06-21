package com.example.quizlist;

import static com.example.quizlist.SplashActivity.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;


public class DashboardActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int timerValue = 50;
    ProgressBar progressBar;

    //Take all the questions into DashboardActivity from SplashActivity
    List<Modelclass> allQuestionsList;
    Modelclass modelclass;

    int index = 0;

    TextView text_question, opta, optb, optc, optd;
    CardView cardOA, cardOB, cardOC, cardOD;

    //Count number of answers selected as correct or wrong :
    int correctCount = 0;
    int wrongCount = 0;

    //Next Button :
    LinearLayout nextBtn;

    //Back and Refresh (Exit) button :
    ImageView ic_back;
    TextView ic_exit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        progressBar = findViewById(R.id.quiz_timer);
        //Create method named as "Hooks()", to find all the Ids :
        Hooks();


        //Take all the questions into DashboardActivity from SplashActivity
        allQuestionsList = list;
//        Collections.shuffle(allQuestionsList);                        //For bringing random questions
        //Store question data into object "model" from "list" in index wise:
        modelclass = list.get(index);

        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));

        nextBtn.setClickable(false);

        //Functioning of progress bar :
        countDownTimer = new CountDownTimer(50000, 1000) {
            @Override
            public void onTick(long l) {
                timerValue = timerValue - 1;
                progressBar.setProgress(timerValue);

            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(DashboardActivity.this);

                //Make background blurr :
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

                dialog.setContentView(R.layout.time_out_dialog);

                //Whenever user will click on "Try Again", he will get return back to the main activity
                dialog.findViewById(R.id.btn_tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DashboardActivity.this, SplashActivity.class);
                        startActivity(intent);
                    }
                });


                dialog.show();
            }
        }.start();                                          //to start progress bar


        //Create method named as "setAllData()", to set all data i.e. questions and options :
        setAllData();

        //Back and Exit button :
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });

        ic_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });

    }



    //Set all data i.e. questions and options using below method named as "setAllData" method :
    private void setAllData() {

        text_question.setText(modelclass.getQuestion());
        opta.setText(modelclass.getOptionA());
        optb.setText(modelclass.getOptionB());
        optc.setText(modelclass.getOptionC());
        optd.setText(modelclass.getOptionD());

    }


    //Find all ids using below method named as "Hooks()" method :
    private void Hooks() {

        //Progress Bar :
        progressBar = findViewById(R.id.quiz_timer);

        //Text view :
        text_question = findViewById(R.id.tv_question);
        opta = findViewById(R.id.tv_optiona);
        optb = findViewById(R.id.tv_optionb);
        optc = findViewById(R.id.tv_optionc);
        optd = findViewById(R.id.tv_optiond);

        //Card View :
        cardOA = findViewById(R.id.cardA);
        cardOB = findViewById(R.id.cardB);
        cardOC = findViewById(R.id.cardC);
        cardOD = findViewById(R.id.cardD);

        //Next Button :
        nextBtn = findViewById(R.id.nextBtn);

        //Back and Exit button ;
        ic_back = findViewById(R.id.ic_back);
        ic_exit = findViewById(R.id.ic_exit);

    }


    //Two methods for performing action based on answer selected as correct or wrong :
    public void Correct(CardView cardView) {

        cardView.setBackgroundColor(getResources().getColor(R.color.green));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;

                //Then, take next question :
                index++;
                modelclass = list.get(index);
                resetColor();
                setAllData();
                enableButton();

//                //Each time, when user will enter into another question, timer will reset to 20s again
//                timerValue = 20;
//                progressBar.setProgress(timerValue);

            }
        });

    }


    public void Wrong(CardView cardView) {

        cardView.setBackgroundColor(getResources().getColor(R.color.red));

        wrongCount++;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < list.size() - 1) {
                    //Then, take next question :
                    index++;
                    modelclass = list.get(index);
                    setAllData();
                    resetColor();   //Make option as white for next question
                    enableButton();

//                    //Each time, when user will enter into another question, timer will reset to 20s again
//                    timerValue = 20;
//                    progressBar.setProgress(timerValue);

                } else {
                    GameWon();
                }
            }
        });


    }


    private void GameWon() {
        Intent intent = new Intent(DashboardActivity.this, WonActivity.class);

        //Take correct and wrong count and pass it to the score circular progress bar :
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);

        startActivity(intent);
    }

    public void enableButton() {
        cardOA.setClickable(true);
        cardOB.setClickable(true);
        cardOC.setClickable(true);
        cardOD.setClickable(true);
    }


    public void disableButton() {
        cardOA.setClickable(false);
        cardOB.setClickable(false);
        cardOC.setClickable(false);
        cardOD.setClickable(false);
    }


    public void resetColor() {
        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));
    }


    //Below will happen if option A is clicked :
    public void OptionAClick(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if(modelclass.getOptionA().equals(modelclass.getAns())) {
            cardOA.setBackgroundColor(getResources().getColor(R.color.green));

            if(index < list.size()-1) {
                Correct(cardOA);
            }
            else {
                GameWon();
            }
        }
        else {                                    //index == list.size()-1
            Wrong(cardOA);
        }
    }



    //Below will happen if option B is clicked :
    public void OptionBClick(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if(modelclass.getOptionB().equals(modelclass.getAns())) {
            cardOB.setBackgroundColor(getResources().getColor(R.color.green));

            if(index < list.size()-1) {
                Correct(cardOB);
            }
            else {
                GameWon();
            }
        }
        else {                                    //index == list.size()-1
            Wrong(cardOB);
        }
    }


    //Below will happen if option C is clicked :
    public void OptionCClick(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if(modelclass.getOptionC().equals(modelclass.getAns())) {
            cardOC.setBackgroundColor(getResources().getColor(R.color.green));

            if(index < list.size()-1) {
                Correct(cardOC);
            }
            else {
                GameWon();
            }
        }
        else {                                    //index == list.size()-1
            Wrong(cardOC);
        }
    }


    //Below will happen if option D is clicked :
    public void OptionDClick(View view) {
        disableButton();
        nextBtn.setClickable(true);
        if(modelclass.getOptionD().equals(modelclass.getAns())) {
            cardOD.setBackgroundColor(getResources().getColor(R.color.green));

            if(index < list.size()-1) {
                Correct(cardOD);
            }
            else {
                GameWon();
            }
        }
        else {                                    //index == list.size()-1
            Wrong(cardOD);
        }
    }


}


