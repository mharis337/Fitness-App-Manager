package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyClassInstructorActivity extends AppCompatActivity {

    ListView myClassList;
    ArrayList<String> listMyClasses;
    //ArrayAdapter adapter;
    ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_instructor);

        myClassList = (ListView) findViewById(R.id.myClassList);
        viewData();

        myClassList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = myClassList.getItemAtPosition(i).toString();
                Toast.makeText(MyClassInstructorActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        ClassDatabaseHelper databaseHelper = new ClassDatabaseHelper(MyClassInstructorActivity.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");

        UserDatabaseHelper db = new UserDatabaseHelper(MyClassInstructorActivity.this);

        //Toast.makeText(myClassInstructor.this, ""+databaseHelper.specificSearch(userName, "").length, Toast.LENGTH_SHORT).show();

        if(!databaseHelper.isEmpty()) {
              ArrayList<String[]> classes = databaseHelper.getClasses(userName, "");
            adapter = new ClassAdapter(classes, this, databaseHelper);
            myClassList.setAdapter(adapter);
            databaseHelper.close();
        }
    }

    public void startAddingActivity(View view){
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        Intent intentmyClassList = new Intent(MyClassInstructorActivity.this, AddingClassActivity.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);
    }


}