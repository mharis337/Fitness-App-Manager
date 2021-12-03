package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyClassMember extends AppCompatActivity {
    ListView myClassList;
    ArrayList<GymClass> listMyClasses;
    ClassListAdapted adapter;
    //ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_member);

        myClassList = (ListView) findViewById(R.id.ListClasses);
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
        UserDatabaseHelper udb = new UserDatabaseHelper(MyClassMember.this);
        ClassDatabaseHelper cdb = new ClassDatabaseHelper(MyClassMember.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        GymClass temp;
        listMyClasses = new ArrayList<>();


        for(String a: udb.classesUserIsTaking(userName)){
            temp = cdb.findClass(a);
            listMyClasses.add(temp);
        }
        adapter = new ClassListAdapted(this, R.layout.adapter_view_layout, listMyClasses);
        myClassList.setAdapter(adapter);

    }

    public void startEnrollingActivity(View view){
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        Intent intentmyClassList = new Intent(MyClassMember.this, EnrollingClassActivity.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);
    }
}