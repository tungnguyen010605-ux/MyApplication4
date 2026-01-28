package com.example.note;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    EditText noteEditText;
    Button saveButton;
    String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        noteEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            fileName = String.format("%02d_%02d_%04d", dayOfMonth, month + 1, year);
            Toast.makeText(MainActivity.this, fileName, Toast.LENGTH_SHORT).show();
        });

        saveButton.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(calendarView.getDate()));
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            fileName = String.format("%02d_%02d_%04d", dayOfMonth, month + 1, year);
            Toast.makeText(this, String.format("%02d_%02d_%04d", dayOfMonth, month + 1, year), Toast.LENGTH_LONG).show();
        });

        saveButton.setOnClickListener(view -> {
            String noteContent = noteEditText.getText().toString();
            try {
                // Ghi nội dung vào file
                FileOutputStream fos = MainActivity.this.openFileOutput(fileName, MODE_PRIVATE);
                fos.write(noteContent.getBytes());
                fos.close();
                Toast.makeText(this, "Đã lưu ghi chú", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi khi lưu ghi chú", Toast.LENGTH_LONG).show();
            }
        });

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            fileName = String.format("%02d_%02d_%04d", dayOfMonth, month + 1, year);
            noteEditText.setText("");
            try {
                FileInputStream fis = openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                fis.close();
                noteEditText.setText(sb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}