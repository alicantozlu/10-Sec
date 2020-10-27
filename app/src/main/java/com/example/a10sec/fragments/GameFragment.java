package com.example.a10sec.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmGameBinding;
import com.example.a10sec.models.SingeltonAppData;

import java.util.ArrayList;
import java.util.Collections;

public class GameFragment extends BaseFragment {
    View view;
    private FragmGameBinding gameBinding;
    private String correctAnswer;
    ArrayList<Integer> uniqueNumberList;
    PorterDuffColorFilter waitFilter,whiteFilter,redFilter,greenFilter;
    CountDownTimer mCountDownTimer;
    private int progressCounter,answerTimeOut=1500;;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gameBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_game,container,false);
        view = gameBinding.getRoot();
        createColorsFilter();
        getQuestion();
        click();
        return view;
    }

    private void getQuestion() {
        resetAnswers();
        final int max = SingeltonAppData.getInstance().getQuestions().size();
        int questionCount = SingeltonAppData.getInstance().getQuestionCount();
        correctAnswer = SingeltonAppData.getInstance().getQuestions().get(questionCount).getAnswers().get(0);
        suffleAnswer();
        gameBinding.animCount.setVisibility(View.VISIBLE);
        gameBinding.animCount.playAnimation();
        gameBinding.txtQuestion.setText(SingeltonAppData.getInstance().getQuestions().get(questionCount).getQuestion());
        gameBinding.txtAnswer1.setText(SingeltonAppData.getInstance().getQuestions().get(questionCount).getAnswers().get(uniqueNumberList.get(0)));
        gameBinding.txtAnswer2.setText(SingeltonAppData.getInstance().getQuestions().get(questionCount).getAnswers().get(uniqueNumberList.get(1)));
        gameBinding.txtAnswer3.setText(SingeltonAppData.getInstance().getQuestions().get(questionCount).getAnswers().get(uniqueNumberList.get(2)));
        gameBinding.txtAnswer4.setText(SingeltonAppData.getInstance().getQuestions().get(questionCount).getAnswers().get(uniqueNumberList.get(3)));
        questionTimer();
        if ((questionCount+1)!=max){
            SingeltonAppData.getInstance().setQuestionCount(questionCount+1);
        }else{
            SingeltonAppData.getInstance().setQuestionCount(0);
        }
    }

    private void questionTimer(){
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getQuestion();
                }
            },10000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void click() {
        gameBinding.txtAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBinding.txtAnswer1.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer1.getText().toString().trim(),1);
            }
        });
        gameBinding.txtAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBinding.txtAnswer2.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer2.getText().toString().trim(),2);
            }
        });
        gameBinding.txtAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gameBinding.txtAnswer3.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer3.getText().toString().trim(),3);
            }
        });

        gameBinding.txtAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBinding.txtAnswer4.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer4.getText().toString().trim(),4);
            }
        });
    }

    private void checkCorrectAnswer(String answer,int buttonNumber){
        progressBar();
        gameBinding.animCount.cancelAnimation();
        gameBinding.animCount.setVisibility(View.INVISIBLE);
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttonsSetEnable(true);
                    if(buttonNumber==1){
                        gameBinding.txtAnswer1.getBackground().setColorFilter(redFilter);
                    }else if (buttonNumber==2){
                        gameBinding.txtAnswer2.getBackground().setColorFilter(redFilter);
                    }else if (buttonNumber==3){
                        gameBinding.txtAnswer3.getBackground().setColorFilter(redFilter);
                    }else if (buttonNumber==4){
                        gameBinding.txtAnswer4.getBackground().setColorFilter(redFilter);
                    }
                    if (gameBinding.txtAnswer1.getText().toString().equals(correctAnswer)){
                        gameBinding.txtAnswer1.getBackground().setColorFilter(greenFilter);
                    }else if(gameBinding.txtAnswer2.getText().toString().equals(correctAnswer)){
                        gameBinding.txtAnswer2.getBackground().setColorFilter(greenFilter);
                    }else if(gameBinding.txtAnswer3.getText().toString().equals(correctAnswer)){
                        gameBinding.txtAnswer3.getBackground().setColorFilter(greenFilter);
                    }else if(gameBinding.txtAnswer4.getText().toString().equals(correctAnswer)){
                        gameBinding.txtAnswer4.getBackground().setColorFilter(greenFilter);
                    }
                    if (answer.equals(correctAnswer)){
                        //3 sn beklet, puan arttırıp apite sorgu at, yeni soru cağır
                    }else{

                    }
                    nextQuestionTimer();
                }
            },answerTimeOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nextQuestionTimer(){
        progressBar();
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getQuestion();
                }
            },answerTimeOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void progressBar(){
        progressCounter=0;
        gameBinding.progressBar.setProgress(progressCounter);
        mCountDownTimer=new CountDownTimer(answerTimeOut,1) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ progressCounter+ millisUntilFinished);
                progressCounter++;
                gameBinding.progressBar.setProgress(progressCounter);
            }
            @Override
            public void onFinish() {
                progressCounter++;
                gameBinding.progressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();
    }

    private void suffleAnswer(){
        uniqueNumberList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            uniqueNumberList.add(i);
        }
        Collections.shuffle(uniqueNumberList);
    }

    private void resetAnswers(){
        gameBinding.txtAnswer1.getBackground().setColorFilter(whiteFilter);
        gameBinding.txtAnswer2.getBackground().setColorFilter(whiteFilter);
        gameBinding.txtAnswer3.getBackground().setColorFilter(whiteFilter);
        gameBinding.txtAnswer4.getBackground().setColorFilter(whiteFilter);
    }

    private void buttonsSetEnable(Boolean bool){
        gameBinding.txtAnswer1.setEnabled(bool);
        gameBinding.txtAnswer2.setEnabled(bool);
        gameBinding.txtAnswer3.setEnabled(bool);
        gameBinding.txtAnswer4.setEnabled(bool);
    }

    private void createColorsFilter(){
        waitFilter = new PorterDuffColorFilter(Color.parseColor("#FBD46D"),PorterDuff.Mode.MULTIPLY);
        whiteFilter = new PorterDuffColorFilter(Color.parseColor("#FFFFFF"),PorterDuff.Mode.MULTIPLY);
        redFilter = new PorterDuffColorFilter(Color.parseColor("#CF1B1B"),PorterDuff.Mode.MULTIPLY);
        greenFilter = new PorterDuffColorFilter(Color.parseColor("#ADE498"),PorterDuff.Mode.MULTIPLY);
    }

    void disableCountimeBackPress(){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    return true;
                }
                return false;
            }
        });
    }
}
