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
import java.util.regex.Pattern;


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
            String a = "Monday Tuesday Wednesday Thursday Friday Saturday Sunday";
            String b = "Hard Medium Easy";
            String c = "1:00 2:00 3:00 4:00 5:00 6:00 7:00 8:00 9:00 10:00 11:00 12:00 13:00 14:00 15:00 16:00 17:00 18:00 19:00 20:00 21:00 22:00 23:00 24:00";
            String d = "12334567890";

            if (!Pattern.compile(Pattern.quote(day.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(a).find()){
                Toast.makeText(addingClass.this, " Invalid Day Options: Monday Tuesday Wednesday Thursday Friday Saturday Sunday", Toast.LENGTH_SHORT).show();
            }else if (!Pattern.compile(Pattern.quote(time.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(c).find()){
                Toast.makeText(addingClass.this, " Invalid Time Options: 1:00 2:00 3:00 4:00 5:00 6:00 7:00 8:00 9:00 10:00 11:00 12:00 13:00 14:00 15:00 16:00 17:00 18:00 19:00 20:00 21:00 22:00 23:00 24:00", Toast.LENGTH_SHORT).show();
            } else if(!Pattern.compile(Pattern.quote(diff.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(b).find()){
                Toast.makeText(addingClass.this, " Invalid Difficulty Options: Hard Medium Easy", Toast.LENGTH_SHORT).show();
            }else if(!d.contains(cap.getText().toString())){
                Toast.makeText(addingClass.this, " Invalid Capacity Options: 0123456789", Toast.LENGTH_SHORT).show();
            }else{
                gymClass newClass = new gymClass(availableClass.getText().toString(), userName, diff.getText().toString(), databaseHelper.getClassDesc(availableClass.getText().toString()), day.getText().toString(), time.getText().toString(), cap.getText().toString());

                while (db.searchClassByID(newClass.getClassID())) {
                    newClass.setClassID(ThreadLocalRandom.current().nextInt(0, 10000));
                }
                System.out.println(newClass.toString());
                db.addClass(newClass);
            }
        }

        Intent intentmyClassList = new Intent(addingClass.this, myClassInstructor.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);

    }
}