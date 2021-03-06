package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AddingClassActivity extends AppCompatActivity {
    EditText diff, day, time, cap;
    TextView selectedClass;
    Button difficultyBtn, dayBtn, timeBtn;

    ListView myClasses;
    ArrayList<String> classTypes;
    ArrayAdapter adapter;

    Boolean choseDifficulty = false;
    Boolean choseDay = false;
    Boolean ChoseTime = false;
    String selectedDifficulty = "Beginner";
    String selectedDay = "Monday";
    String selectedTime = "1:00";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_class);

//        diff = (EditText) findViewById(R.id.diff);
//        day = (EditText) findViewById(R.id.day);
//        time = (EditText) findViewById(R.id.hour);
        difficultyBtn = (Button) findViewById(R.id.difficultyBtn);
        dayBtn = (Button) findViewById(R.id.dayBtn);
        timeBtn = (Button) findViewById(R.id.timeBtn);
        cap = (EditText)  findViewById(R.id.cap);
        cap.setInputType(InputType.TYPE_CLASS_NUMBER);

        selectedClass = (TextView) findViewById(R.id.selectedClass);
        myClasses = (ListView) findViewById(R.id.availableClasses);
        viewData();

        myClasses.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = myClasses.getItemAtPosition(i).toString();

                selectedClass.setText(text);
                Toast.makeText(AddingClassActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });

        difficultyBtn.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDifficultyOptionsDialog();
            }

            private void showDifficultyOptionsDialog() {
                String[] difficulties = {"Beginner", "Intermediate", "Advanced"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddingClassActivity.this);
                builder.setTitle("Choose your class difficulty:");
                builder.setSingleChoiceItems(difficulties, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDifficulty = difficulties[which];
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choseDifficulty = true;
                        difficultyBtn.setText(selectedDifficulty);
                        Toast.makeText(AddingClassActivity.this, "Difficulty set to "+selectedDifficulty+"", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        dayBtn.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View view) {
                showDayOptionsDialog();
            }

            private void showDayOptionsDialog() {
                String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddingClassActivity.this);
                builder.setTitle("Choose the day of your class:");
                builder.setSingleChoiceItems(days, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedDay = days[which];
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choseDay = true;
                        dayBtn.setText(selectedDay);
                        Toast.makeText(AddingClassActivity.this, "Day set to "+selectedDay+"", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        timeBtn.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View view) {
                showTimeOptionsDialog();
            }

            private void showTimeOptionsDialog() {
                String[] times = {"1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00",
                        "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
                        "20:00", "21:00", "22:00", "23:00", "24:00"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddingClassActivity.this);
                builder.setTitle("Choose your class time:");
                builder.setSingleChoiceItems(times, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedTime = times[which];
                    }
                });
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChoseTime= true;
                        timeBtn.setText(selectedTime);
                        Toast.makeText(AddingClassActivity.this, "Time set to "+selectedTime+"", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    protected void viewData() {
        ClassTypesDatabase cdb = new ClassTypesDatabase(AddingClassActivity.this);

        classTypes = new ArrayList<>();
        for(String type : cdb.getClassType()){
            classTypes.add(type);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classTypes);

        myClasses.setAdapter(adapter);
        cdb.close();
    }

    public boolean addClass(View view) {
        return this.addClass(view, true);
    }

    public boolean addClass(View view, Boolean redirect) {
        Boolean playSameDay = false;
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        ClassDatabaseHelper db = new ClassDatabaseHelper(AddingClassActivity.this);
        ClassTypesDatabase cdb = new ClassTypesDatabase(AddingClassActivity.this);

        if (!(choseDifficulty || choseDay || ChoseTime)) {
            Toast.makeText(AddingClassActivity.this, "Difficulty, day or time have not been picked.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedClass.getText().toString().equals("Not Assigned")) {
            Toast.makeText(AddingClassActivity.this, "Class type have not been selected.", Toast.LENGTH_SHORT).show();
            return false;
        } else{

            String sDay = selectedDay;
            for(String i : db.classOnThisDay(sDay)){
                if(i.equalsIgnoreCase(selectedClass.getText().toString())){
                    playSameDay = true;
                }
            }
            if(cap.getText().toString().equals("")){
                Toast.makeText(AddingClassActivity.this, " Please input the capacity of your class.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if(!playSameDay) {
                    GymClass newClass = new GymClass(selectedClass.getText().toString(), userName, selectedDifficulty,
                            cdb.getClassDesc(selectedClass.getText().toString()), selectedDay, selectedTime,
                            cap.getText().toString());

                    while (db.searchClassByID(newClass.getClassID())) {
                        newClass.setClassID(ThreadLocalRandom.current().nextInt(0, 10000));
                        System.out.println("Inside: " + newClass.toString());
                    }
                    System.out.println(newClass.toString());
                    db.addClass(newClass);
                }else{
                    Toast.makeText(AddingClassActivity.this, " You already have a class of this type on this day!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        if(redirect) {
            this.redirect();
        }
        return true;
    }

    public void redirect() {
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");

        Intent intentmyClassList = new Intent(AddingClassActivity.this, MyClassInstructorActivity.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);
    }
}