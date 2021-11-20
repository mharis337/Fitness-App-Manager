package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class viewAllInstructor extends AppCompatActivity {

    TextView status;
    EditText className;
    EditText instructorName;
    Button viewAll;

    ListView classList;
    ArrayList<String> listClasses;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_instructor);

        status = (TextView) findViewById(R.id.status);
        className = (EditText) findViewById(R.id.className);
        instructorName = (EditText)  findViewById(R.id.instructorName);
        viewAll = (Button) findViewById(R.id.viewAll);
        viewAll.setVisibility(View.GONE);

        classList = (ListView) findViewById(R.id.classList);
        viewData();

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = classList.getItemAtPosition(i).toString();
                Toast.makeText(viewAllInstructor.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        classDatabase databaseHelper = new classDatabase(viewAllInstructor.this);
        //Toast.makeText(viewAllInstructor.this, ""+databaseHelper.specificSearch("", "").length, Toast.LENGTH_SHORT).show();
        if(databaseHelper.specificSearch("", "").length > 0) {
            listClasses = new ArrayList<>();
            for (String i : databaseHelper.specificSearch(instructorName.getText().toString(), className.getText().toString())) {
                listClasses.add(i);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listClasses);

            classList.setAdapter(adapter);
            databaseHelper.close();
        }
    }

    public void startSearch(View view){
        if(className.getText().toString().isEmpty() && instructorName.getText().toString().isEmpty()){
            Toast.makeText(viewAllInstructor.this, "Nothing was input", Toast.LENGTH_SHORT).show();

        }else {
            viewAll.setVisibility(View.VISIBLE);
            viewData();
        }
    }

    public void viewAll(View view){
        viewAll.setVisibility(View.GONE);
        className.setText("");
        instructorName.setText("");
        viewData();
    }
}