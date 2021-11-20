package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreenActivity extends AppCompatActivity {

    TextView welcome;
    Button btnEnter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent intent = getIntent();
        String userName = intent.getStringArrayExtra(MainActivity.uInfo)[0];
        String userRole = intent.getStringArrayExtra(MainActivity.uInfo)[1];

        welcome = findViewById(R.id.welcomeTxt);

        welcome.setText("Welcome " + userName + "! You are logged in as " + userRole);


        /*Toast.makeText(WelcomeScreenActivity.this, "username: " + userName + " Role: " + userRole , Toast.LENGTH_SHORT).show();

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(21);
        textView.setGravity(Gravity.CENTER);
        textView.setText("Welcome "+ userName +"! You are logged in as " + userRole +".");

        // Set the text view as the activity layout
        setContentView(textView);*/
    }
    public void startAdminActivity(View view) {
        Intent intent = getIntent();
        String username = intent.getStringArrayExtra(MainActivity.uInfo)[0];

        if (username.equals("admin")) {
            Intent intentAdmin = new Intent(this, AdminActivity.class);
            startActivity(intentAdmin);
        }
        // Can add more cases for username of members and instructors later.
    }
}