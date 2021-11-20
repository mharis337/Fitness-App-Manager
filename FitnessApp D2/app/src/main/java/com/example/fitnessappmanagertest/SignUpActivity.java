package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Button signup;

    Switch isInstructor;
    String accountType;
    TextView fname, lname, uname, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup = findViewById(R.id.btnSignUp2);
        fname = findViewById(R.id.firstNameInput);
        lname = findViewById(R.id.lastNameInput);
        uname = findViewById(R.id.username1);
        pwd = findViewById(R.id.passwordSignupInput);
        isInstructor = findViewById(R.id.instructor);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String namReg = "[A-Z][A-Za-z ]{2,}"; // User name must start with a Capital letter


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(SignUpActivity.this);

                if(isInstructor.isChecked()) {
                    accountType = "instructor";
                }
                else{
                    accountType = "member";
                }
                //Throw exception if username or password isnt given
                if(databaseHelper.userFound(uname.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "This user already exits" , Toast.LENGTH_SHORT).show();
                }
                else if(uname.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, "No username was input!" , Toast.LENGTH_SHORT).show();

                }
                else if(pwd.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, "You must enter a password!" , Toast.LENGTH_SHORT).show();
                }
                else if(!uname.getText().toString().trim().matches(emailPattern)){
                    Toast.makeText(SignUpActivity.this, "You must enter a valid Email adress" , Toast.LENGTH_SHORT).show();
                }
                else if(!fname.getText().toString().trim().matches(namReg)){
                    Toast.makeText(SignUpActivity.this, "Name must start with a capital letter" , Toast.LENGTH_SHORT).show();
                }
                else if(!lname.getText().toString().trim().matches(namReg)){
                    Toast.makeText(SignUpActivity.this, "Surname must start a Capital letter" , Toast.LENGTH_SHORT).show();
                }
                else if(!passValidates(pwd.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Password must be at least 6 char long and have a special character" , Toast.LENGTH_SHORT).show();
                }
                else {
                    UserAccounts user = new UserAccounts(fname.getText().toString(), lname.getText().toString(), uname.getText().toString(), pwd.getText().toString(), accountType);
                    databaseHelper.addUser(user);
                    backToMain();

                }

            }
        });

    }
    public boolean passValidates( String password ) {
        int count = 0;

        if( 3 <= password.length() && password.length() <= 32  )
        {
            if( password.matches(".*\\d.*") )
                count ++;
            if( password.matches(".*[a-z].*") )
                count ++;
            if( password.matches(".*[A-Z].*") )
                count ++;
            if( password.matches(".^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$") )
                count ++;
        }

        return count >= 3;
    }


    public void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}