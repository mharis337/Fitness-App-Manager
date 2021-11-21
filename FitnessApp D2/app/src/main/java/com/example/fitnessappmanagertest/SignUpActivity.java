package com.example.fitnessappmanagertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private Button signup;
    private Switch isInstructor;
    private String accountType;
    private TextView fname, lname, uname, pwd;

    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Z][A-Za-z ]{2,}"); // First/Last name must start with a Capital letter
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[A-Z][A-Za-z0-9 ]{2,}"); // Username must start with a Capital letter
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
                    "(?=.*[!@#&()–[{}]:;'_,?/*%~$^+=<>])." +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{3,}" +                // at least 3 characters
                    "$");

//    private static final String NAME_PATTERN = "[A-Z][A-Za-z ]{2,}"; // Name must start with a Capital letter
//    private static final String USERNAME_PATTERN = "[A-Z][A-Za-z0-9 ]{2,}"; // Username must start with a Capital letter

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
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


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
                // Throw exception if username or password isnt given
                if(databaseHelper.userFound(uname.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "This user already exits" , Toast.LENGTH_SHORT).show();
                }
                else if(uname.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, "No username was input!" , Toast.LENGTH_SHORT).show();
                }
                else if(pwd.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, "You must enter a password!" , Toast.LENGTH_SHORT).show();
                }
                else if(!validateUsername(uname.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Username must be 3 char's long and start with a capital letter" , Toast.LENGTH_SHORT).show();
                }
                else if(!validateName(fname.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "First name must be 3 char's long and starts with a capital letter" , Toast.LENGTH_SHORT).show();
                }
                else if(!validateName(lname.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Surname must be 3 char's long and starts with a capital letter" , Toast.LENGTH_SHORT).show();
                }
                else if(!ValidatePass(pwd.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Password must be at least 3 char's long and includes a special character" , Toast.LENGTH_SHORT).show();
                }
                else {
                    UserAccounts user = new UserAccounts(fname.getText().toString(), lname.getText().toString(), uname.getText().toString(), pwd.getText().toString(), accountType);
                    databaseHelper.addUser(user);
                    backToMain();
                }
            }
        });
    }

    public static boolean ValidatePass(String password ) {
        if (PASSWORD_PATTERN.matcher(password).matches()) return true;
        else return false;
    }

    public static boolean validateName(String name ) {
        if (NAME_PATTERN.matcher(name).matches()) return true;
        else return false;
    }

    public static boolean validateUsername(String name ) {
        if (USERNAME_PATTERN.matcher(name).matches()) return true;
        else return false;
    }

    public void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



//    public static boolean ValidatePass(String password ) {
//        int count = 0;
//
//        if( 3 <= password.length() && password.length() <= 32  )
//        {
//            if( password.matches(".*\\d.*") )
//                count ++;
//            if( password.matches(".*[a-z].*") )
//                count ++;
//            if( password.matches(".*[A-Z].*") )
//                count ++;
//            if( password.matches(".*[0-9].*") )
//                count ++;
//            if( password.matches(".^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$") )
//                count ++;
//        }
//
//        return count >= 3;
//    }
}