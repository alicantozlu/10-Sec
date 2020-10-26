package com.example.a10sec.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionModel {

    @SerializedName("answers")
    @Expose
    private List<String> answers = null;
    @SerializedName("question")
    @Expose
    private String question;


    public QuestionModel() {
    }

    public QuestionModel(List<String> answers, String question) {
        super();
        this.answers = answers;
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
