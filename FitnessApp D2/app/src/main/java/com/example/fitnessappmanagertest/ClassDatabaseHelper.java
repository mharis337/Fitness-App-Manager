package com.example.fitnessappmanagertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ClassDatabaseHelper extends SQLiteOpenHelper {

    public static final String user_table = "CLASS_TABLE";
    public static final String COLUMN_CLASS_ID = "CLASS_ID";
    public static final String COLUMN_CLASS_NAME = "CLASS_NAME";
    public static final String COLUMN_INSTRUCTOR_NAME = "INSTRUCTOR_NAME";
    public static final String COLUMN_DESC = "DESCR";
    public static final String COLUMN_DIFF = "DIFF";
    public static final String COLUMN_DAY = "DAY";
    public static final String COLUMN_HOUR = "HOUR";
    public static final String COLUMN_CAPACITY = "CAPACITY";

    //public static final String CLASS_TYPE_TABLE = "CLASS_TYPE_TABLE";

    public ClassDatabaseHelper(@Nullable Context context) {
        super(context, "classes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TEST", "TEST");
        String createStatements = "CREATE TABLE " + user_table + "(" + COLUMN_CLASS_ID + " TEXT PRIMARY KEY, " + COLUMN_CLASS_NAME + " TEXT, " + COLUMN_INSTRUCTOR_NAME + " TEXT, " + COLUMN_DIFF + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_DAY + " TEXT, " + COLUMN_HOUR + " TEXT, " + COLUMN_CAPACITY + " TEXT)";

        db.execSQL(createStatements);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + user_table;
        Cursor cursor = db.rawQuery(query, null);


        if(cursor.getCount() > 0){
            return false;
        }

        cursor.close();
        db.close();

        return true;
    }

    public boolean addClass(GymClass name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLASS_ID, name.getClassID());
        cv.put(COLUMN_INSTRUCTOR_NAME, name.getInstructor());
        cv.put(COLUMN_DESC, name.getDesc());
        cv.put(COLUMN_DIFF, name.getDifficulty());
        cv.put(COLUMN_CLASS_NAME, name.getName());
        cv.put(COLUMN_CAPACITY, name.getCapacity());
        cv.put(COLUMN_DAY, name.getDay());
        cv.put(COLUMN_HOUR, name.getHours());

        long insert = db.insert(user_table, null, cv);

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }
    //Create classtype if it doesnt exist, otherwise edit.
    public boolean editClass(GymClass name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLASS_ID, name.getClassID());
        cv.put(COLUMN_INSTRUCTOR_NAME, name.getInstructor());
        cv.put(COLUMN_DESC, name.getDesc());
        cv.put(COLUMN_DIFF, name.getDifficulty());
        cv.put(COLUMN_CLASS_NAME, name.getName());
        cv.put(COLUMN_CAPACITY, name.getCapacity());
        cv.put(COLUMN_DAY, name.getDay());
        cv.put(COLUMN_HOUR, name.getHours());

       /* long insert = db.insert(user_table, null, cv);

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }*/

        return (db.update(user_table, cv, COLUMN_CLASS_ID + " = ?", new String[]{ String.valueOf(name.getClassID())}) != -1);
    }

    /*public String[] allClasses(){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] classNames = new String[cursor.getCount()];

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            classNames[i] = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return classNames;
    }*/

    public ArrayList<String[]> getClasses(String instructor, String className){
        String query = "";
        if(className == null) {
            className = "";
        }
        if(instructor == null) {
            instructor = "";
        }
        if(!instructor.isEmpty() && !className.isEmpty()) {
            query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_CLASS_NAME + " ='" + className + "'" + " AND " + COLUMN_INSTRUCTOR_NAME + "='" + instructor + "'";
        }
        else if(instructor.isEmpty() && !className.isEmpty()){
            query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_CLASS_NAME + " ='" + className + "'";
        }
        else if(!instructor.isEmpty() && className.isEmpty()){
            query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_INSTRUCTOR_NAME + " ='" + instructor + "'";
        }
        else{
            query = "SELECT * FROM " + user_table;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //String[][] classNames = new String[cursor.getCount()][4];
        ArrayList<String[]> classNames = new ArrayList<String[]>();

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            //id, Class name, instructor name, Cap
            String[] str = new String[8];

            /*classNames.add(new String[] {cursor.getString(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(7)});*/
            for(int j = 0; j < str.length; j++) {
                str[j] = cursor.getString(j);
            }

            classNames.add(str);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return classNames;
    }

    public boolean deleteClass(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(user_table, COLUMN_CLASS_ID + "= '" + id + "'",null) > 0;
    }

    public String[] specificSearch(String instructor, String className){
        String query = "";
        if(!instructor.isEmpty() && !className.isEmpty()) {
            query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_CLASS_NAME + " ='" + className + "'" + " AND " + COLUMN_INSTRUCTOR_NAME + "='" + instructor + "'";
        }
        else if(instructor.isEmpty() && !className.isEmpty()){
            query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_CLASS_NAME + " ='" + className + "'";
        }
        else if(!instructor.isEmpty() && className.isEmpty()){
            query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_INSTRUCTOR_NAME + " ='" + instructor + "'";
        }
        else{
            query = "SELECT * FROM " + user_table;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String[] classNames = new String[cursor.getCount()];

        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++){
            classNames[i] = "Class: "
                    + cursor.getString(1)
                    + " | Instructor: " + cursor.getString(2)
                    + " |     Cap:" + cursor.getString(7);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return classNames;
    }


    public String[] classOnThisDay(String day){
        String[] cName;

        String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_DAY + "='"+ day +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        cName = new String[cursor.getCount()];
        for(int i = 0; i < cursor.getCount(); i++){
            cName[i] = cursor.getString(1);
            cursor.moveToNext();
        }



        cursor.close();
        db.close();

        return cName;

    }

    public String getID(String className, String classDay){
            String id = "";

            String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_DAY + "='"+ classDay +"'" + " AND " + COLUMN_CLASS_NAME + " ='" + className +"'";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(query, null);

            cursor.moveToFirst();

            for(int i = 0; i < cursor.getCount(); i++){
                id = cursor.getString(0);
                cursor.moveToNext();
            }



            cursor.close();
            db.close();

            return id;
    }

    public boolean searchClassByID(int id){
        String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_CLASS_ID + "='"+ Integer.toString(id) +"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        if(cursor.getCount() > 0){
            return true;
        }

        cursor.close();
        db.close();

        return false;

    }



}
