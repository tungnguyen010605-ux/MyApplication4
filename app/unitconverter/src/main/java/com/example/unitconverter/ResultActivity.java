package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textViewResult = findViewById(R.id.txtResult);
        Button buttonBack = findViewById(R.id.buttonBack);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double fahrenheit = extras.getDouble("fahrenheit");
            textViewResult.setText(String.format("Result: %.2f Fahrenheit", fahrenheit));
        }
        buttonBack.setOnClickListener(v -> {
            finish();
        });
    }

}