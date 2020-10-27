package com.example.a10sec.fragments;

import android.os.Bundle;
import android.util.Log;
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
import java.util.Random;

public class GameFragment extends BaseFragment {
    View view;
    private FragmGameBinding gameBinding;
    private String correctAnswer;
    ArrayList<Integer> uniqueNumberList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        gameBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_game,container,false);
        view = gameBinding.getRoot();
        getQuestion();
        click();
        return view;
    }

    private void getQuestion() {
        final int max = SingeltonAppData.getInstance().getQuestions().size();
        final int random = new Random().nextInt(max);
        correctAnswer = SingeltonAppData.getInstance().getQuestions().get(random).getAnswers().get(0);
        suffleAnswer();
        gameBinding.animCount.playAnimation();
        gameBinding.txtQuestion.setText(SingeltonAppData.getInstance().getQuestions().get(random).getQuestion());
        gameBinding.txtAnswer1.setText(SingeltonAppData.getInstance().getQuestions().get(random).getAnswers().get(uniqueNumberList.get(0)));
        gameBinding.txtAnswer2.setText(SingeltonAppData.getInstance().getQuestions().get(random).getAnswers().get(uniqueNumberList.get(1)));
        gameBinding.txtAnswer3.setText(SingeltonAppData.getInstance().getQuestions().get(random).getAnswers().get(uniqueNumberList.get(2)));
        gameBinding.txtAnswer4.setText(SingeltonAppData.getInstance().getQuestions().get(random).getAnswers().get(uniqueNumberList.get(3)));

    }

    void click() {
        gameBinding.txtAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(gameBinding.txtAnswer1.getText().toString().trim());
            }
        });
        gameBinding.txtAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(gameBinding.txtAnswer2.getText().toString().trim());
            }
        });
        gameBinding.txtAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(gameBinding.txtAnswer3.getText().toString().trim());
            }
        });

        gameBinding.txtAnswer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCorrectAnswer(gameBinding.txtAnswer4.getText().toString().trim());
            }
        });
    }

    private void checkCorrectAnswer(String answer){
        gameBinding.animCount.cancelAnimation();
        if (answer.equals(correctAnswer)){
            gameBinding.txtUsernameScore.setText("Doğru");
        }else{
            gameBinding.txtUsernameScore.setText("Yanlış");
        }
        getQuestion();
    }

    private void suffleAnswer(){
        uniqueNumberList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            uniqueNumberList.add(i);
        }
        Collections.shuffle(uniqueNumberList);
    }
}
