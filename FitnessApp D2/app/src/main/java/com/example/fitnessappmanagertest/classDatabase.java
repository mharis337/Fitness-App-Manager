package com.example.fitnessappmanagertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class classDatabase extends SQLiteOpenHelper {

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

    public classDatabase(@Nullable Context context) {
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

    public boolean addClass(gymClass name){

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
    public String[] specificSearch(String instructor, String className){
        ContentValues cv = new ContentValues();
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
            classNames[i] = "Class: " + cursor.getString(1) + " Capacity: " + cursor.getString(7);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return classNames;
    }

    public boolean deleteClass(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(user_table, COLUMN_CLASS_ID + "= ?", new String[]{id}) > 0;
    }


    public boolean classFound(String name, String day){
        ContentValues cv = new ContentValues();
        String cName;

        String query = "SELECT * FROM " + user_table + " WHERE " + COLUMN_CLASS_NAME + "='"+ name +"'" + " AND " + COLUMN_DAY + "='" + day + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        while(cursor.moveToFirst()) {
            cName = cursor.getString(0);
            if(name.equals(cName)){
                return true;
            }
        }

        cursor.close();
        db.close();

        return false;

    }

    public boolean searchClassByID(int id){
        ContentValues cv = new ContentValues();
        String cName;

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
