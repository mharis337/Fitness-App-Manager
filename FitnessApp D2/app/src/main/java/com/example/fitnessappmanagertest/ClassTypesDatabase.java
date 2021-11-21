package com.example.fitnessappmanagertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ClassTypesDatabase extends SQLiteOpenHelper {


    //public static final String CLASS_TYPE_TABLE = "CLASS_TYPE_TABLE";

    public ClassTypesDatabase(@Nullable Context context) {
        super(context, "classTypes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TEST", "TEST");
        String createClassTypes = "CREATE TABLE CLASS_TYPE_TABLE ( " +
                "   NAME TEXT PRIMARY KEY," +
                "   DESCRIPTION TEXT" +
                ")";
        db.execSQL(createClassTypes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addClassType(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", name);
        cv.put("DESCRIPTION", description);
        long insert = db.insert("CLASS_TYPE_TABLE", null, cv);

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    //Create classtype if it doesnt exist, otherwise edit.
    public boolean editClassType(String name, String description) {
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

}
