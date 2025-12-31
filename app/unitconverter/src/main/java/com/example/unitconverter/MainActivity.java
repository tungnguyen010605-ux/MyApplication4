package com.example.unitconverter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        EditText editTxtNum =  findViewById(R.id.editTxtNum);
        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(v->{
            double celsius = Double.parseDouble(editTxtNum.getText().toString());
            double fahrenheit = (celsius * 1.8) + 32;

            Intent intent = new Intent(MainActivity.this,ResultActivity.class);
            intent.putExtra("fahrenheit",fahrenheit);
            startActivity(intent);
        });
    }
}