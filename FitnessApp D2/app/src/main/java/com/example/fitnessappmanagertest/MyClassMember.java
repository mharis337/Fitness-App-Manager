package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyClassMember extends AppCompatActivity {
    ListView myClassList;
    ArrayList<String> listMyClasses;
    //ArrayAdapter adapter;
    ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_member);

        myClassList = (ListView) findViewById(R.id.myClassList);
        viewData();

        myClassList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = myClassList.getItemAtPosition(i).toString();
                Toast.makeText(MyClassMember.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        ClassDatabaseHelper databaseHelper = new ClassDatabaseHelper(MyClassMember.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");

        UserDatabaseHelper db = new UserDatabaseHelper(MyClassMember.this);

        //Toast.makeText(myClassInstructor.this, ""+databaseHelper.specificSearch(userName, "").length, Toast.LENGTH_SHORT).show();

        if(!databaseHelper.isEmpty()) {
            ArrayList<String[]> classes = databaseHelper.getClasses(userName, "");
            adapter = new ClassAdapter(classes, this, databaseHelper);
            myClassList.setAdapter(adapter);
            databaseHelper.close();
        }
    }

    public void startEnrollingActivity(View view){
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        Intent intentmyClassList = new Intent(MyClassMember.this, EnrollingClassActivity.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);
    }
}