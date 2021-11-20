package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// >> 5 fields were validated, 2 in MainActivity and 3 in AdminActivity <<
public class MainActivity extends AppCompatActivity {
    public final static String uInfo = "com.example.myfirstapp.MESSAGE";

    Button btnSignUp;
//    Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
    classDatabase classHelper = new classDatabase(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserAccounts admin = new UserAccounts("Admin", "Admin","admin", "admin123", "admin");
        gymClass yoga = new gymClass("Yoga", "This is a yoga class");

        classHelper.addClass(yoga);
        databaseHelper.addUser(admin);
    }

    /* Called when the user clicks the SIGN UP button */
    public void startSignupActivity(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /* checks admin username and password: admin, admin123 */
    public void startWelcomeScreenActivity(View view) {
        // Getting username and password from input fields
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        if(databaseHelper.userFound(username.getText().toString())) {
            if(databaseHelper.userPassword(username.getText().toString()).equals(password.getText().toString())){
                String userType = databaseHelper.userType(username.getText().toString());

                Intent intent;
                if(userType.equals("admin")) {
                    intent = new Intent(MainActivity.this, WelcomeScreenActivity.class);
                } else if(userType.equals("instructor")){
                    intent = new Intent(MainActivity.this, InstructorScreen.class);
                    Toast.makeText(MainActivity.this, "GOING TO INSTRUCTOR" , Toast.LENGTH_SHORT).show();

                }
                else {
                    intent = new Intent(MainActivity.this, WelcomeScreenActivity.class);
                }

                intent.putExtra(uInfo, new String[]{username.getText().toString(), userType});
                startActivity(intent);
            }
            else{
                // Validated field 1
                Toast.makeText(MainActivity.this, "The password you entered was incorrect!" , Toast.LENGTH_SHORT).show();
            }
        }
        else{
            // Validated field 2
            Toast.makeText(MainActivity.this, "The username you entered does not exist!" , Toast.LENGTH_SHORT).show();
        }
    }

}




