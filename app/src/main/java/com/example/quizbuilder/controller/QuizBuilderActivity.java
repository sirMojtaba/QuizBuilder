package com.example.quizbuilder.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizbuilder.R;
import com.example.quizbuilder.model.Question;

public class QuizBuilderActivity extends AppCompatActivity {
    private EditText mEditTextQuestion;
    private Button mButtonStart;
    public static final String EXTRA_INPUT_QUESTION = "question array";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        findAllViews();
        setClickListeners();

    }

    private void findAllViews() {
        mEditTextQuestion = findViewById(R.id.editText_builder);
        mButtonStart = findViewById(R.id.button_start);
    }

    private void setClickListeners() {
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = String.valueOf(mEditTextQuestion.getText());
                Intent intent = new Intent(QuizBuilderActivity.this, QuizActivity.class);
                intent.putExtra(EXTRA_INPUT_QUESTION, question);
                startActivity(intent);
            }
        });
    }
}