package com.example.quizz;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button optionButton1, optionButton2, optionButton3, optionButton4;

    private List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

        new Thread(() -> {
            Question question = new Question();
            question.content = "What is the capital of France?";
            question.options = "[\"Paris\", \"London\", \"Berlin\", \"Madrid\"]";
            question.answer = "London";
            db.questionDao().insert(question);
        }).start();

        new Thread(() -> {
            Question question = db.questionDao().getById(1);
            Log.d("debug", question.content);
            Log.d("debug", question.options);
        }).start();

        new Thread(() -> {
            Question question = db.questionDao().getById(1);
            question.content = "What is the capital of England?";
            db.questionDao().update(question);
        }).start();

        new Thread(() -> {
            Question question = db.questionDao().getById(1);
            db.questionDao().delete(question);
        }).start();

        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Log.d("debug", json);
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        questionTextView = findViewById(R.id.questionTextView);
        optionButton1 = findViewById(R.id.optionButton1);
        optionButton2 = findViewById(R.id.optionButton2);
        optionButton3 = findViewById(R.id.optionButton3);
        optionButton4 = findViewById(R.id.optionButton4);

        new Thread(() -> {
            Question question = db.questionDao().getById(1);
            runOnUiThread(() -> showQuestion(question));
        }).start();

        new Thread(() -> {
            questions.addAll(db.questionDao().getAll());
            runOnUiThread(() -> showQuestion(questions.get(0)));
        }).start();

    }

    private void showQuestion(Question question) {
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> options = new Gson().fromJson(question.options, type);
        optionButton1.setVisibility(View.VISIBLE);
        optionButton2.setVisibility(View.VISIBLE);
        optionButton3.setVisibility(View.VISIBLE);
        optionButton4.setVisibility(View.VISIBLE);

        questionTextView.setText(question.content);
        optionButton1.setText(options.get(0));
        optionButton2.setText(options.get(1));
        optionButton3.setText(options.get(2));
        optionButton4.setText(options.get(3));
    }

}