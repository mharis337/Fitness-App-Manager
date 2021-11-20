package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InstructorScreen extends AppCompatActivity {
    TextView welcome;
    String username, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_screen);
        Intent intent = getIntent();
        username = intent.getStringArrayExtra(MainActivity.uInfo)[0];
        role = intent.getStringArrayExtra(MainActivity.uInfo)[1];

        welcome = findViewById(R.id.welcomeText);

        welcome.setText("Welcome " + username + "! You are logged in as " + role);

    }

    public void startViewAll(View view){
        Intent intentViewAllInstructor = new Intent(this, viewAllInstructor.class);
        startActivity(intentViewAllInstructor);
    }

    public void startMyClass(View view){
        Intent intentmyClassList = new Intent(InstructorScreen.this, myClassInstructor.class);
        intentmyClassList.putExtra("UserRole", username);
        startActivity(intentmyClassList);
    }
}