package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyClassInstructorActivity extends AppCompatActivity {

    ListView myClassList;
    ArrayList<String> listMyClasses;
    ArrayAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_instructor);

        myClassList = (ListView) findViewById(R.id.myClassList);
        viewData();

        myClassList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = myClassList.getItemAtPosition(i).toString();
                Toast.makeText(MyClassInstructorActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        ClassDatabaseHelper databaseHelper = new ClassDatabaseHelper(MyClassInstructorActivity.this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");

        UserDatabaseHelper db = new UserDatabaseHelper(MyClassInstructorActivity.this);

        //Toast.makeText(myClassInstructor.this, ""+databaseHelper.specificSearch(userName, "").length, Toast.LENGTH_SHORT).show();

        if(!databaseHelper.isEmpty()) {
            listMyClasses = new ArrayList<>();

            for (String i : databaseHelper.specificSearch(userName, "")) {
                listMyClasses.add(i);
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMyClasses);

            myClassList.setAdapter(adapter);
            databaseHelper.close();
        }
    }

    public void startAddingActivity(View view){
        Intent intent = getIntent();
        String userName = intent.getStringExtra("UserRole");
        Intent intentmyClassList = new Intent(MyClassInstructorActivity.this, AddingClassActivity.class);
        intentmyClassList.putExtra("UserRole", userName);
        startActivity(intentmyClassList);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        MyDBHandler dbHandler = new MyDBHandler(this);
        String itemName = productlist.getItemAtPosition(info.position).toString();


        switch (item.getItemId()) {
            case R.id.viewData:
                Product product = dbHandler.findProduct(itemName);

                idView.setText(String.valueOf(product.get_id()));
                productBox.setText(String.valueOf(product.get_productname()));
                priceBox.setText(String.valueOf(product.get_price()));

                Toast.makeText(this, "Item: "+product.get_productname()+
                                ", Price: "+product.get_price()+"",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.deleteItem:
                dbHandler.deleteProduct(itemName);
                listItem.clear();
                viewData();

                idView.setText("Record Deleted");
                Toast.makeText(this, "Item deleted!", Toast.LENGTH_SHORT).show();
                productBox.setText("");
                priceBox.setText("");

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}