package com.example.fitnessappmanagertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String user_table = "USER_TABLE";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_ACCOUNT_TYPE = "ACCOUNT_TYPE";


    //public static final String CLASS_TYPE_TABLE = "CLASS_TYPE_TABLE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TEST", "TEST");
        String createStatements = "CREATE TABLE " + user_table + "(" + COLUMN_USERNAME + " TEXT PRIMARY KEY, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_ACCOUNT_TYPE + " TEXT)";

        db.execSQL(createStatements);

        String createClassTypes = "CREATE TABLE CLASS_TYPE_TABLE ( " +
                                    "   NAME TEXT PRIMARY KEY," +
                                    "   DESCRIPTION TEXT" +
                                    ")";
        db.execSQL(createClassTypes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

    //Create classtype if it doesnt exist, otherwise edit.
    public boolean setClassType(String name, String description) {
        //Check if exists
        String query = String.format("SELECT * FROM CLASS_TYPE_TABLE WHERE NAME = '%s'", name);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        boolean exists = false;
        while(cursor.moveToFirst()) {
            if(name.equals(cursor.getString(0))){
                System.out.println(cursor.getString(0) + " " + cursor.getString(1));
                exists = true;
                break;
            }
        }

        //Now update/insert accordingly
        ContentValues cv = new ContentValues();
        cv.put("NAME", name);
        cv.put("DESCRIPTION", description);

        if(!exists) {
            return (db.insert("CLASS_TYPE_TABLE", null, cv) != -1);
        }

        return (db.update("CLASS_TYPE_TABLE", cv, "NAME = ?", new String[]{name}) != -1);
    }

    public String[] getClassType(){

        String query = String.format("SELECT * FROM CLASS_TYPE_TABLE");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String[] types = new String[cursor.getCount()];
        cursor.moveToFirst();
        for(int i =0; i < cursor.getCount(); i++){
            types[i] = cursor.getString(0);
            cursor.moveToNext();
        }
        return types;
    }

    public String getClassDesc(String name){
        String query = String.format("SELECT * FROM CLASS_TYPE_TABLE WHERE NAME = '%s'", name);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToFirst()) {
            if(name.equals(cursor.getString(0))){
                //System.out.println(cursor.getString(0) + " " + cursor.getString(1));
                break;
            }
        }

        return cursor.getString(1);
    }

    public boolean deleteClassType(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("CLASS_TYPE_TABLE", "NAME = ?", new String[]{name}) > 0;
    }

    public boolean classTypeFound(String name) {
        //Check if exists
        String query = String.format("SELECT * FROM CLASS_TYPE_TABLE WHERE NAME = '%s'", name);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        boolean exists = false;
        while (cursor.moveToFirst()) {
            if (name.equals(cursor.getString(0))) {
                System.out.println(cursor.getString(0) + " " + cursor.getString(1));
                exists = true;
                break;
            }
        }
        return exists;
    }

    public boolean deleteUser(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(user_table, COLUMN_USERNAME + "= ?", new String[]{user}) > 0;
    }

    public boolean userFound(String user){
        ContentValues cv = new ContentValues();
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
