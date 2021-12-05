package com.example.fitnessappmanagertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClassListAdapted extends ArrayAdapter<GymClass> {

    private static final String TAG = "ClassListAdapted";
    private Context mContext;
    int mResource;
    private UserDatabaseHelper db;
    private String username;

    private MyClassMember adapter;
    public ClassListAdapted(Context context, MyClassMember activity, int resource,ArrayList<GymClass> objects, UserDatabaseHelper db, String username) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.db = db;
        this.username = username;
        this.adapter = activity;
    }

    public ClassListAdapted(Context context, int resource,ArrayList<GymClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.db = db;
    }

 //   @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String instructor = getItem(position).getInstructor();
        String difficulty = getItem(position).getDifficulty();
        String desc = getItem(position).getDesc();
        String day = getItem(position).getDay();
        String hours = getItem(position).getHours();
        String capacity = getItem(position).getCapacity();
        String id = Integer.toString(getItem(position).getClassID());

        System.out.println("Name: " + name + "Diff: " + difficulty + " Cap: " + capacity);
        GymClass gym = new GymClass(name, instructor, difficulty, desc, day, hours, capacity);
        gym.setClassID(Integer.parseInt(id));

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent, false);

        TextView className = (TextView) convertView.findViewById(R.id.classNameMember);
        TextView classDif = (TextView) convertView.findViewById(R.id.classDif);
        TextView classCap = (TextView) convertView.findViewById(R.id.classCap);

        //if(name!=null && difficulty!=null && capacity!=null) {
            className.setText(name);
            classDif.setText(difficulty);
            classCap.setText(capacity);
        //}


        Button buttonLeave = (Button)convertView.findViewById(R.id.leave_class);
        if(buttonLeave != null) {
            buttonLeave.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.removeUserFromClass(username, id);
                    adapter.recreate();
                   // adapter.notifyAll();
                    notifyDataSetChanged();
                   // adapter.notifyDataSetChanged(); //refresh

                }
            });
        }


        return convertView;
    }
}
