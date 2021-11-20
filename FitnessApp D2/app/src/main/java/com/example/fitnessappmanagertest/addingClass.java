package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class addingClass extends AppCompatActivity {
    EditText diff, day, time, cap;
    TextView availableClass;


    ListView myClasses;
    ArrayList<String> classTypes;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_class);

        diff = (EditText) findViewById(R.id.diff);
        day = (EditText) findViewById(R.id.day);
        time = (EditText) findViewById(R.id.hour);
        cap = (EditText)  findViewById(R.id.cap);

        availableClass = (TextView) findViewById(R.id.selectedClass);

        myClasses = (ListView) findViewById(R.id.availableClasses);
        viewData();

        myClasses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = myClasses.getItemAtPosition(i).toString();

                availableClass.setText(text);
                Toast.makeText(addingClass.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(addingClass.this);

        classTypes = new ArrayList<>();
        for(String type : databaseHelper.getClassType()){
            classTypes.add(type);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classTypes);

        myClasses.setAdapter(adapter);
        databaseHelper.close();

    }

    public void addClass(View view) {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        classDatabase db = new classDatabase(addingClass.this);
        DatabaseHelper databaseHelper = new DatabaseHelper(addingClass.this);
        //Toast.makeText(addingClass.this, String.valueOf(db.classFound(availableClass.getText().toString(), day.getText().toString())), Toast.LENGTH_SHORT).show();
        if(diff.getText().toString().isEmpty() || day.getText().toString().isEmpty() || time.getText().toString().isEmpty() || cap.getText().toString().isEmpty()) {
            Toast.makeText(addingClass.this, " One of the fields was left empty!", Toast.LENGTH_SHORT).show();
        }else if(db.classFound(availableClass.getText().toString(), day.getText().toString())) {
            Toast.makeText(addingClass.this, " Same class is booked for this day!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(addingClass.this, "FINISHED", Toast.LENGTH_SHORT).show();
            //Toast.makeText(addingClass.this, String.valueOf(db.classFound(availableClass.getText().toString(), day.getText().toString())), Toast.LENGTH_SHORT).show();
            //Toast.makeText(addingClass.this, " Adding Class", Toast.LENGTH_SHORT).show();
            gymClass newClass = new gymClass(availableClass.getText().toString(), userName, diff.getText().toString(), databaseHelper.getClassDesc(availableClass.getText().toString()), day.getText().toString(), time.getText().toString(), cap.getText().toString());

            while(db.searchClassByID(newClass.getClassID())){
                newClass.setClassID(ThreadLocalRandom.current().nextInt(0, 10000));
            }
            System.out.println(newClass.toString());
            db.addClass(newClass);
        }

        Intent intentmyClassList = new Intent(addingClass.this, myClassInstructor.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);

    }
}