package com.example.quizbuilder.model;

import android.graphics.Color;

public class Question {
    private String mQuestionTitle;
    private boolean mAnswer;
    private boolean mIsCheat;
    private String mColor;

    private boolean isAnswered;

    public boolean isAnswerTrue() {
        return mAnswer;
    }


    public Question(String questionTitle, boolean answer, boolean isCheat, String color) {
        mQuestionTitle = questionTitle;
        mAnswer = answer;
        mIsCheat = isCheat;
        mColor = color;
    }

    public String getQuestionTitle() {
        return mQuestionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        mQuestionTitle = questionTitle;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }

    public boolean isCheat() {
        return mIsCheat;
    }

    public void setCheat(boolean cheat) {
        mIsCheat = cheat;
    }

    public int getColor() {
        int mColorInt = 0;
        if(mColor.equals("green"))
            mColorInt = Color.parseColor("#FF62FF00");
        if(mColor.equals("red"))
            mColorInt = Color.parseColor("#FFFF0000");
        if(mColor.equals("black"))
            mColorInt = Color.parseColor("#FF000000");
        if(mColor.equals("blue"))
            mColorInt = Color.parseColor("#FF0800FF");
        return mColorInt;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
