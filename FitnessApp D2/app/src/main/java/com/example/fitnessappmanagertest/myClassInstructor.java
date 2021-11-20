package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class myClassInstructor extends AppCompatActivity {

    ListView myClassList;
    ArrayList<String> listMyClasses;
    ArrayAdapter adapter;




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
                Toast.makeText(myClassInstructor.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        classDatabase databaseHelper = new classDatabase(myClassInstructor.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");


        //Toast.makeText(myClassInstructor.this, ""+databaseHelper.specificSearch(userName, "").length, Toast.LENGTH_SHORT).show();

        if(databaseHelper.specificSearch(userName, "").length > 0) {
            listMyClasses = new ArrayList<>();

            for (String i : databaseHelper.specificSearch(userName, "")) {
                listMyClasses.add(i);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMyClasses);

            myClassList.setAdapter(adapter);
            databaseHelper.close();
        }
    }

    public void startAddingActivity(View view){
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        Intent intentmyClassList = new Intent(myClassInstructor.this, addingClass.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);
    }
}