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

import com.example.a10sec.MainActivity;
import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmGameBinding;
import com.example.a10sec.models.SingeltonAppData;
import com.example.a10sec.models.UserModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameFragment extends BaseFragment {
    View view;
    private FragmGameBinding gameBinding;
    private String correctAnswer;
    private int lastScore;
    ArrayList<Integer> uniqueNumberList;
    PorterDuffColorFilter waitFilter,whiteFilter,redFilter,greenFilter;
    private Handler timeoutHandler,questionTimerHandler;
    private Runnable removeCallbacks;
    CountDownTimer mCountDownTimer;
    private int progressCounter,questionTimer = 10000,answerTimeOut=2000;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gameBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_game,container,false);
        view = gameBinding.getRoot();
        createColorsFilter();
        enableTimersBackPress();
        lastScore = SingeltonAppData.getInstance().getmyUserModel().getScore();
        gameBinding.txtUsernameScore.setText(SingeltonAppData.getInstance().getmyUserModel().getUsername()+" : "+lastScore);
        getQuestion();
        click();
        return view;
    }

    @Override
    public void onStop() {
        try{
            mCountDownTimer.cancel();
            questionTimerHandler.removeCallbacks(removeCallbacks);
        }catch (NullPointerException ignored){
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        try{
            mCountDownTimer.cancel();
            questionTimerHandler.removeCallbacks(removeCallbacks);
        }catch (NullPointerException ignored){
        }
        super.onDestroy();
    }

    private void questionTimer(){
        questionTimerHandler = new Handler();
        try {
            questionTimerHandler.postDelayed(removeCallbacks = new Runnable() {
                @Override
                public void run() {
                    lastScore -= 1;
                    setScore(lastScore);
                    getQuestion();
                }
            },questionTimer);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getQuestion() {
        buttonsSetEnable(true);
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

    private void checkCorrectAnswer(String answer,int buttonNumber){
        questionTimerHandler.removeCallbacks(removeCallbacks);
        progressBar();
        timeoutHandler= new Handler();
        mCountDownTimer.cancel();
        gameBinding.animCount.cancelAnimation();
        gameBinding.animCount.setVisibility(View.INVISIBLE);
        try {
            timeoutHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
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
                        lastScore += 3;
                        setScore(lastScore);
                    }else{
                        lastScore -= 1;
                        setScore(lastScore);
                    }
                    nextQuestionTimer();
                }
            },answerTimeOut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setScore(int score){
        SingeltonAppData.getInstance().getmyUserModel().setScore(score);
        gameBinding.txtUsernameScore.setText(SingeltonAppData.getInstance().getmyUserModel().getUsername()+" : "+score);
        newScorePost(SingeltonAppData.getInstance().getmyUserModel());
    }


    private void newScorePost(UserModel model){
        Call<UserModel> call= MainActivity.iApiInterface.postUser(MainActivity.mAuth.getUid(),model);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                assert response.body() != null;
                Log.e("Response Succes", "Post Response:" + response.body().getUsername()+response.body().getEmail()+response.body().getUrl()+response.body().getScore());
            }
            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                Log.e("Response onFailure", "onFailure:" + t.toString());
            }
        });
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
                gameBinding.progressBar.setProgress(0);
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

    void enableTimersBackPress(){
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    questionTimerHandler.removeCallbacks(removeCallbacks);
                    return false;
                }
                return false;
            }
        });
    }

    void click() {
        gameBinding.txtAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar();
                gameBinding.txtAnswer1.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer1.getText().toString().trim(),1);
            }
        });
        gameBinding.txtAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar();
                gameBinding.txtAnswer2.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer2.getText().toString().trim(),2);
            }
        });
        gameBinding.txtAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar();
                gameBinding.txtAnswer3.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer3.getText().toString().trim(),3);
            }
        });

        gameBinding.txtAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar();
                gameBinding.txtAnswer4.getBackground().setColorFilter(waitFilter);
                buttonsSetEnable(false);
                checkCorrectAnswer(gameBinding.txtAnswer4.getText().toString().trim(),4);
            }
        });
    }

}
