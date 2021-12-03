package com.example.fitnessappmanagertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String user_table = "USER_TABLE";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String user_classes = "USER_CLASSES_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CLASSES = "CLASSES";




    //public static final String CLASS_TYPE_TABLE = "CLASS_TYPE_TABLE";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TEST", "TEST");
        String createStatements = "CREATE TABLE " + user_table + "(" + COLUMN_USERNAME + " TEXT PRIMARY KEY, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_ACCOUNT_TYPE + " TEXT)";
        db.execSQL(createStatements);

        String createAnotherStatement = "CREATE TABLE " + user_classes + "(" + COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_USERNAME + " TEXT, " + COLUMN_CLASSES + " TEXT)";
        db.execSQL(createAnotherStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addUserToClass(String username, String className, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_CLASSES, className);
        cv.put(COLUMN_ID, id);

        long insert = db.insert(user_classes, null, cv);

        if(insert == -1) {
            return false;
        }else{
            return  true;
        }
    }

    public boolean doesClassExist(String id){
        String query = "SELECT * FROM " + user_classes + " WHERE " + COLUMN_ID + "='"+ id +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0){
            return false;
        }
        else{
            return true;
        }

    }

    public String[] getClasses(){
        try {
            String query = String.format("SELECT * FROM USER_CLASSES_TABLE");

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            String[] classes = new String[cursor.getCount()];
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                classes[i] = cursor.getString(2);
                cursor.moveToNext();
            }
            return classes;
        }catch(SQLException e){
            System.out.println("Database has not been created yet");

        }
        return new String[0];
    }


    public boolean addUser(UserAccounts user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_FIRST_NAME, user.getFname());
        cv.put(COLUMN_LAST_NAME, user.getLname());
        cv.put(COLUMN_PASSWORD, user.getPwd());
        cv.put(COLUMN_ACCOUNT_TYPE, user.getAccountType());



        long insert = db.insert(user_table, null, cv);

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean deleteUser(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(user_table, COLUMN_USERNAME + "= ?", new String[]{user}) > 0;
    }

    public boolean userFound(String user){
        String userName;

        String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_USERNAME + "='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToFirst()) {
            userName = cursor.getString(0);
            if(user.equals(userName)){
                return true;
            }
        }

        cursor.close();
        db.close();

        return false;

    }

    public String userType(String user){
        ContentValues cv = new ContentValues();
        String type = "";

        String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_USERNAME + "='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            type = cursor.getString(4);
            return type;

        }
        return type;
    }



    public String userPassword(String user){
        ContentValues cv = new ContentValues();
        String password = "";

        String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_USERNAME + "='"+ user +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            password = cursor.getString(3);
            return password;

        }
        return password;
    }





}
