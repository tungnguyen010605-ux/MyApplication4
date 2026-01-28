package com.example.quizz;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    }
}