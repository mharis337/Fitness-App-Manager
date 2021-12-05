package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EnrollingClassActivity extends AppCompatActivity {
    TextView selectedClass, difficulty, time, capacity, day;
    int classId;

    ListView availableClasses;
    ArrayList<GymClass> classes;
    //ClassListAdapted adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolling_class);

        selectedClass = (TextView) findViewById(R.id.selectedClass);
        difficulty = (TextView) findViewById(R.id.textView9);
        time = (TextView) findViewById(R.id.textView11);
        capacity = (TextView) findViewById(R.id.textView12);
        day = (TextView) findViewById(R.id.textView10);

        availableClasses = (ListView) findViewById(R.id.availableClasses);
        classes = new ArrayList<>();

        viewData();

        availableClasses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GymClass sClass = (GymClass) availableClasses.getItemAtPosition(i);

                selectedClass.setText(sClass.getName());
                difficulty.setText(sClass.getDifficulty());
                time.setText(sClass.getHours());
                capacity.setText(sClass.getCapacity());
                day.setText(sClass.getDay());
                classId = sClass.getClassID();
            }
        });
    }


    protected void viewData() {
        ClassDatabaseHelper databaseHelper = new ClassDatabaseHelper(EnrollingClassActivity.this);
        GymClass temp;

        ArrayList<String[]> getClasses = new ArrayList<>();

        if(!databaseHelper.isEmpty()) {
            getClasses = databaseHelper.getClasses("", "");

            for(int i =0; i < getClasses.size(); i++){
                temp = databaseHelper.findClass(getClasses.get(i)[0]);
                classes.add(temp);
            }
            ClassListAdapted adapter = new ClassListAdapted(this, R.layout.adapter_view_layout, classes);
            availableClasses.setAdapter(adapter);
            databaseHelper.close();
        }
    }

//    public void enrolClass(View view){
//        UserDatabaseHelper udb = new UserDatabaseHelper(EnrollingClassActivity.this);
//        ClassDatabaseHelper cdb = new ClassDatabaseHelper(EnrollingClassActivity.this);
//        Intent intent = getIntent();
//        String userName = intent.getStringExtra("UserRole");
//        GymClass temp;
//
//        int id = ThreadLocalRandom.current().nextInt(0, 10000);
//
//
//        while (udb.doesClassExist(Integer.toString(id))) {
//            id = ThreadLocalRandom.current().nextInt(0, 10000);
//        }
//
//        temp = cdb.findClass(Integer.toString(classId));
//
//        if(udb.numberClasses(Integer.toString(classId)) < temp.getClassID()) {
//            udb.addUserToClass(userName, selectedClass.getText().toString(), Integer.toString(classId), Integer.toString(id));
//
//            Intent intentmyClassList = new Intent(EnrollingClassActivity.this, MyClassMember.class);
//            intentmyClassList.putExtra("UserRole", userName);
//            startActivity(intentmyClassList);
//        }else{
//            Toast.makeText(EnrollingClassActivity.this, " Class has reached capacity",  Toast.LENGTH_SHORT).show();
//        }
//
//    }


    public void enrolClass(View view){
        UserDatabaseHelper udb = new UserDatabaseHelper(EnrollingClassActivity.this);
        ClassDatabaseHelper cdb = new ClassDatabaseHelper(EnrollingClassActivity.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        GymClass temp;
        int numOfMembers, cap;

        int id = ThreadLocalRandom.current().nextInt(0, 10000);


        while (udb.doesClassExist(Integer.toString(id))) {
            id = ThreadLocalRandom.current().nextInt(0, 10000);
        }

        temp = cdb.findClass(Integer.toString(classId));
        numOfMembers = temp.getNumOfMembers();
        cap = Integer.parseInt(temp.getCapacity());

        if(numOfMembers < cap) {
            temp.setNumOfMembers(numOfMembers + 1);
            cdb.incrementClassNumOfMembs(temp);
            udb.addUserToClass(userName, selectedClass.getText().toString(), Integer.toString(classId), Integer.toString(id));

            Intent intentmyClassList = new Intent(EnrollingClassActivity.this, MyClassMember.class);
            intentmyClassList.putExtra("UserRole", userName);
            startActivity(intentmyClassList);
        }else{
            Toast.makeText(EnrollingClassActivity.this, " Class has reached capacity",  Toast.LENGTH_SHORT).show();
        }

    }


}