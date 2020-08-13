package com.example.quizbuilder.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizbuilder.R;

public class CheatActivity extends AppCompatActivity {
    private Button mButtonCheat;
    private Button mButtonBack;
    private TextView mTextViewShowAnswer;
    private boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        findViews();
        setClickListeners();
        Intent intent = getIntent();

        answer = intent.getBooleanExtra(QuizActivity.EXTRA_CORRECT_ANSWER, false);
    }

    private void findViews() {
        mButtonBack = findViewById(R.id.button_back);
        mButtonCheat = findViewById(R.id.button_cheat);
        mTextViewShowAnswer = findViewById(R.id.text_view_show_answer);
    }

    private void setClickListeners() {
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewShowAnswer.setText(String.valueOf(answer));
            }
        });
    }
}