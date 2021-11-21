package com.example.fitnessappmanagertest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(AdminActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void setClassType(View view) {
        String name = ((TextView) findViewById(R.id.set_name)).getText().toString();


        if (name.equals("")) {
            // Validated field 3
            Toast.makeText(AdminActivity.this, "You cannot create a class with no name.", Toast.LENGTH_SHORT).show();
        } else if(userDatabaseHelper.classTypeFound(name)) {
            Toast.makeText(AdminActivity.this, "Description of the class Changed", Toast.LENGTH_SHORT).show();
            String description = ((TextView) findViewById(R.id.set_description)).getText().toString();
            userDatabaseHelper.setClassType(name, description);
        }
        else {
            String description = ((TextView) findViewById(R.id.set_description)).getText().toString();
            userDatabaseHelper.setClassType(name, description);
        }
    }

    public void editClassType(View view) {

    }

    public void deleteClassType(View view) {
        String name = ((TextView) findViewById(R.id.delete_name)).getText().toString();

        // Validated field 4
        if (userDatabaseHelper.classTypeFound(name)) {
            userDatabaseHelper.deleteClassType(name);
            Toast.makeText(AdminActivity.this, "Class type " +name+ " has been deleted.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdminActivity.this, "The class type you entered does not exist!" , Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUser(View view) {
        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(AdminActivity.this);
        String username = ((TextView) findViewById(R.id.delete_user)).getText().toString();

        // Validated field 5
        if(userDatabaseHelper.userFound(username)) {
            userDatabaseHelper.deleteUser(username);
            Toast.makeText(AdminActivity.this, "User " +username+ " has been deleted." , Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdminActivity.this, "The user you entered does not exist!" , Toast.LENGTH_SHORT).show();
        }
    }
}
