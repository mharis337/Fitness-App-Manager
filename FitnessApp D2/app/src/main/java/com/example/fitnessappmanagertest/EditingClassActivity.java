package com.example.fitnessappmanagertest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class EditingClassActivity extends AddingClassActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void viewData() {
        super.viewData();

        Intent intent = getIntent();
        String[] data = intent.getStringArrayExtra("data");

        System.out.println("Data[0]: " + data[0] + " Data[1]" + data[1] + "Data[2]: " + data[2] + " Data[3]" + data[3] + "Data[4]: " + data[4] + " Data[5]" + data[5] + "Data[6]: " + data[6] + " Data[7]" + data[7]);
        cap.setText((data[7]));
        difficultyBtn.setText((data[3]));
        dayBtn.setText((data[5]));
        timeBtn.setText((data[6]));
        selectedClass.setText((data[1]));
        choseDifficulty = true;
        choseDay = true;
        ChoseTime = true;

        Button addBtn = (Button) findViewById(R.id.add);
        addBtn.setText("Save");
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = data[2];
                Boolean similarClass = false;
                String personId = "";
                ClassDatabaseHelper db = new ClassDatabaseHelper(EditingClassActivity.this);
                ClassTypesDatabase cdb = new ClassTypesDatabase(EditingClassActivity.this);

                System.out.println("Similar: " + !data[0].equals(db.getID(data[1], dayBtn.getText().toString())));
                    for(int i = 0; i < db.classOnThisDay(dayBtn.getText().toString()).length; i++){
                        if(data[1].equals(db.classOnThisDay(dayBtn.getText().toString())[i])){
                           similarClass = true;
                           personId = db.getID(data[1], dayBtn.getText().toString());
                        }
                    }
                    if(!similarClass) {
                        GymClass newClass = new GymClass(selectedClass.getText().toString(), userName, selectedDifficulty,
                                cdb.getClassDesc(selectedClass.getText().toString()), selectedDay, selectedTime,
                                cap.getText().toString());

                        newClass.setClassID(Integer.parseInt(data[0]));
                        System.out.println(newClass.toString());
                        db.editClass(newClass);


                        //redirect out
                        Intent intentmyClassList = new Intent(EditingClassActivity.this, MyClassInstructorActivity.class);
                        intentmyClassList.putExtra("UserRole", userName);
                        startActivity(intentmyClassList);
                    }else{
                        //System.out.println(personId);
                        Toast.makeText(EditingClassActivity.this, " Sorry " + db.getInstructor(personId) + " already has this day booked", Toast.LENGTH_SHORT).show();

                    }


            }
        });
    }
}
