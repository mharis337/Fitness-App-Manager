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

        availableClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        if (!databaseHelper.isEmpty()) {
            getClasses = databaseHelper.getClasses("", "");

            for (int i = 0; i < getClasses.size(); i++) {
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


    public void enrolClass(View view) {
        UserDatabaseHelper udb = new UserDatabaseHelper(EnrollingClassActivity.this);
        ClassDatabaseHelper cdb = new ClassDatabaseHelper(EnrollingClassActivity.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        GymClass temp;
        int numOfMembers, cap;
        boolean conflict;
        String chosenTime, chosenDay;

        int id = ThreadLocalRandom.current().nextInt(0, 10000);


        while (udb.doesClassExist(Integer.toString(id))) {
            id = ThreadLocalRandom.current().nextInt(0, 10000);
        }

        temp = cdb.findClass(Integer.toString(classId));
        numOfMembers = temp.getNumOfMembers();
        chosenTime = temp.getHours();
        chosenDay = temp.getDay();
        cap = Integer.parseInt(temp.getCapacity());
        conflict = false;

        String[] userClassesIds = udb.classesUserIsTaking(userName); // Class id's that user is taking
        GymClass[] userGymClasses = new GymClass[userClassesIds.length];
        for (int i =0; i<userClassesIds.length; i++) {
            userGymClasses[i] = cdb.findClass(userClassesIds[i]);
        }

        // Find class conflict, if any
//        String[] classDays = new String[userGymClasses.length];
//        String[] classHours = new String[userGymClasses.length];
//        for (int i=0; i<userGymClasses.length; i++) {
//            classDays[i] = userGymClasses[i].getDay();
//        }
//        for (int i=0; i<userGymClasses.length; i++) {
//            classHours[i] = userGymClasses[i].getHours();
//        }
//
//        for (int i=0; i<userGymClasses.length; i++) {
//            if (chosenDay == classDays[i]) {
//                if (chosenTime == classHours[i]) {
//                    conflict = true;
//                }
//            }
//        }
        for (int i =0; i<userGymClasses.length; i++) {
            if (userGymClasses[i].getDay().equals(chosenDay) && userGymClasses[i].getHours().equals(chosenTime)) {
                conflict = true;
            }
        }

        if (conflict) {
            Toast.makeText(EnrollingClassActivity.this, "You can't have 2 classes at the same time and day.", Toast.LENGTH_SHORT).show();
        } else if (numOfMembers >= cap) {
            Toast.makeText(EnrollingClassActivity.this, " Class has reached capacity", Toast.LENGTH_SHORT).show();
        } else {
            temp.setNumOfMembers(numOfMembers + 1);
            cdb.incrementClassNumOfMembs(temp);
            udb.addUserToClass(userName, selectedClass.getText().toString(), Integer.toString(classId), Integer.toString(id));

            Intent intentmyClassList = new Intent(EnrollingClassActivity.this, MyClassMember.class);
            intentmyClassList.putExtra("UserRole", userName);
            startActivity(intentmyClassList);
        }
    }
}


