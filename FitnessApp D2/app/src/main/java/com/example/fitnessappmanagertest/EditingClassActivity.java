package com.example.fitnessappmanagertest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ThreadLocalRandom;

public class EditingClassActivity extends AddingClassActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void viewData() {
        super.viewData();

        Intent intent = getIntent();
        /*intentmyClassList.putExtra("class_id", list.get(position)[0]);
                intentmyClassList.putExtra("diff", list.get(position)[3]);
                intentmyClassList.putExtra("selectedClass", list.get(position)[4]);
                intentmyClassList.putExtra("day", list.get(position)[5]);
                intentmyClassList.putExtra("time", list.get(position)[6]);
                intentmyClassList.putExtra("cap", list.get(position)[7]);*/
        String[] data = intent.getStringArrayExtra("data");

        cap.setText((data[7]));
        difficultyBtn.setText((data[3]));
        dayBtn.setText((data[5]));
        timeBtn.setText((data[6]));
        selectedClass.setText((data[4]));
        choseDifficulty = true;
        choseDay = true;
        ChoseTime = true;

        Button addBtn = (Button) findViewById(R.id.add);
        addBtn.setText("Save");
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String userName = data[2];
                ClassDatabaseHelper db = new ClassDatabaseHelper(EditingClassActivity.this);
                ClassTypesDatabase cdb = new ClassTypesDatabase(EditingClassActivity.this);


                GymClass newClass = new GymClass(selectedClass.getText().toString(), userName, selectedDifficulty,
                        cdb.getClassDesc(selectedClass.getText().toString()), selectedDay, selectedTime,
                        cap.getText().toString());

                newClass.setClassID(Integer.parseInt(data[0]));

                db.editClass(newClass);



                //redirect out
                Intent intentmyClassList = new Intent(EditingClassActivity.this, MyClassInstructorActivity.class);
                intentmyClassList.putExtra("UserRole", userName);
                startActivity(intentmyClassList);


            }
        });
    }
}
