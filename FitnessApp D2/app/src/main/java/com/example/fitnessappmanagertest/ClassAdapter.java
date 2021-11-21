package com.example.fitnessappmanagertest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String[]> list = new ArrayList<String[]>();
    //private String[][] list;
    private Context context;
    private ClassDatabaseHelper databaseHelper;

    public ClassAdapter(ArrayList<String[]> list, Context context, ClassDatabaseHelper databaseHelper) {
        this.list = list;
        this.context = context;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public int getCount() {
        return list.size();//list.length;
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);//list[pos];
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.class_adapter, null);
        }
        TextView text= (TextView)view.findViewById(R.id.text);
        //text.setText(list[position][0]);
        text.setText(list.get(position)[1]);

        Button button_delete = (Button)view.findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //D.log("sdf");
                databaseHelper.deleteClass(list.get(position)[0]);
                list.remove(position);

                notifyDataSetChanged(); // refresh
                //do something

            }
        });

        Button button_edit = (Button)view.findViewById(R.id.button_edit);

        button_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentmyClassList = new Intent(context, EditingClassActivity.class);
                /*intentmyClassList.putExtra("class_id", list.get(position)[0]);
                intentmyClassList.putExtra("diff", list.get(position)[3]);
                intentmyClassList.putExtra("selectedClass", list.get(position)[4]);
                intentmyClassList.putExtra("day", list.get(position)[5]);
                intentmyClassList.putExtra("time", list.get(position)[6]);
                intentmyClassList.putExtra("cap", list.get(position)[7]);*/
                intentmyClassList.putExtra("data", list.get(position));

                context.startActivity(intentmyClassList);

            }
        });

        return view;
    }
}
