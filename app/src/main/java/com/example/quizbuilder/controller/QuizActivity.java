package com.example.quizbuilder.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizbuilder.R;
import com.example.quizbuilder.model.Question;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_CORRECT_ANSWER = "correct answer";
    private Button mButtonCheat;
    private TextView mTextViewQuestion;
    private Question[] mQuestionBank;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonNext;
    private Button mButtonPrevious;
    private Button mButtonFirst;
    private Button mButtonLast;
    private TextView mTextViewTotalScore;
    private TextView mTextViewScore;
    private LinearLayout mScoreLayout;
    private LinearLayout mQuestionsLayout;
    private Button mButtonReset;
    private int mCurrentIndex = 0;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        findAllViews();
        Intent intent = getIntent();
        mQuestionBank = parseQuestion(intent.getStringExtra(QuizBuilderActivity.EXTRA_INPUT_QUESTION));

        setClickListeners();
        updateQuestion();
//        if (savedInstanceState != null)
    }

    private void findAllViews() {
        mButtonCheat = findViewById(R.id.button_cheat);
        mTextViewQuestion = findViewById(R.id.textView_question);
        mButtonTrue = findViewById(R.id.button_true);
        mButtonFalse = findViewById(R.id.button_false);
        mButtonNext = findViewById(R.id.button_next);
        mButtonPrevious = findViewById(R.id.button_previous);
        mButtonFirst = findViewById(R.id.button_first);
        mButtonLast = findViewById(R.id.button_last);
        mTextViewTotalScore = findViewById(R.id.text_view_total_score);
        mTextViewScore = findViewById(R.id.text_view_score);
        mScoreLayout = findViewById(R.id.score_layout);
        mQuestionsLayout = findViewById(R.id.question_layout);
        mButtonReset = findViewById(R.id.button_reset);
    }

    private void setClickListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (--mCurrentIndex + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = 0;
                updateQuestion();
            }
        });

        mButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScoreLayout.setVisibility(View.GONE);
                mQuestionsLayout.setVisibility(View.VISIBLE);
                mScore = 0;
                mCurrentIndex = 0;
                mTextViewScore.setText(getString(R.string.score).concat(String.valueOf(mScore)));
                for (Question question : mQuestionBank) {
                    question.setAnswered(false);
                }
                updateQuestion();
            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mQuestionBank[mCurrentIndex].isCheat())
                    Toast.makeText(QuizActivity.this, "Unfortunately you can not cheat for this question!", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                    boolean answer = mQuestionBank[mCurrentIndex].isAnswer();
                    intent.putExtra(EXTRA_CORRECT_ANSWER, answer);
                    startActivity(intent);
                }
            }
        });
    }

    private Question[] parseQuestion(String string) {
        Question[] question = new Question[3];
        string = string.replace("{", "");
        string = string.replace(",[", "");
        string = string.replace("[", "");
        string = string.replace("}", "");
        String[] outer = string.split("]");
        String[][] inner = new String[3][4];
        for (int i = 0; i < 3; i++) {
            inner[i] = outer[i].split(",");
        }
        for (int i = 0; i < 3; i++) {
            question[i] = new Question(String.valueOf(inner[i][0]), Boolean.parseBoolean(inner[i][1]),
                    Boolean.parseBoolean(inner[i][2]), String.valueOf(inner[i][3]));
        }
        return question;
    }

    private void updateQuestion() {
        mTextViewQuestion.setText(mQuestionBank[mCurrentIndex].getQuestionTitle());
//        mTextViewQuestion.setTextColor(mQuestionBank[mCurrentIndex].getColor());
        setDisabled();
    }

    private void setScore() {
        mScore++;
        mTextViewScore.setText(getString(R.string.score) + mScore);
    }

    private void checkAnswer(boolean userPressed) {
        mQuestionBank[mCurrentIndex].setAnswered(true);

        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        if (userPressed == isAnswerTrue) {
            Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_LONG).show();
            setScore();
        } else
            Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_LONG).show();

        setDisabled();
        checkFinish();
    }

    private void checkFinish() {
        for (Question question : mQuestionBank) {
            if (!question.isAnswered())
                return;
        }

        mQuestionsLayout.setVisibility(View.GONE);
        mTextViewTotalScore.setText(getString(R.string.total_score).concat(String.valueOf(mScore)));
        mScoreLayout.setVisibility(View.VISIBLE);

    }

    private void setDisabled() {
        if (mQuestionBank[mCurrentIndex].isAnswered()) {
            mButtonTrue.setEnabled(false);
            mButtonFalse.setEnabled(false);
        } else {
            mButtonTrue.setEnabled(true);
            mButtonFalse.setEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt();
    }
}
//{[{“Tehran is in iran”}, {true}, {false}, {green}],[{“iran language is english”}, {false}, {true}, {red}],[{“England is in usa”}, {false}, {false}, {black}]}
