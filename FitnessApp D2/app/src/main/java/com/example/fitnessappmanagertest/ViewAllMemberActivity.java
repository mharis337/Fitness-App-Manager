package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

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


public class ViewAllMemberActivity extends AppCompatActivity {

    TextView status;
    EditText className;
    EditText dayOfWeek;
    Button viewAll;

    ListView classList;
    ArrayList<String> listClasses;
    ArrayAdapter adapter;
    //ClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_member);

        status = (TextView) findViewById(R.id.status);
        className = (EditText) findViewById(R.id.classNameTxt);
        dayOfWeek = (EditText) findViewById(R.id.dayOfWeek);
        viewAll = (Button) findViewById(R.id.viewAll);
        viewAll.setVisibility(View.GONE);

        classList = (ListView) findViewById(R.id.classList);
        viewData();

        classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = classList.getItemAtPosition(i).toString();
                Toast.makeText(ViewAllMemberActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        ClassDatabaseHelper databaseHelper = new ClassDatabaseHelper(ViewAllMemberActivity.this);
        if(!databaseHelper.isEmpty()) {
            listClasses = new ArrayList<String>();
            for (String i : databaseHelper.specificSearchDayAndName(dayOfWeek.getText().toString(), className.getText().toString())) {
                listClasses.add(i);
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listClasses);
            classList.setAdapter(adapter);
            databaseHelper.close();
        }
    }

    public void startSearch(View view) {
        if(className.getText().toString().isEmpty() && dayOfWeek.getText().toString().isEmpty()) {
            Toast.makeText(ViewAllMemberActivity.this, "Nothing was input", Toast.LENGTH_SHORT).show();
        } else {
            viewAll.setVisibility(view.VISIBLE);
            viewData();
        }
    }

    public void viewAll(View view) {
        viewAll.setVisibility(view.GONE);
        className.setText("");
        dayOfWeek.setText("");
        viewData();
    }
}