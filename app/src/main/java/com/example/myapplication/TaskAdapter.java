package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private Task[] tasks;

    public TaskAdapter(Context context, int resource, Task[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.tasks = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list_entry, parent, false);
        TextView titleview = (TextView) rowView.findViewById(R.id.title);
        TextView dateview = (TextView) rowView.findViewById(R.id.date);
        TextView commentview = (TextView) rowView.findViewById(R.id.comment);
        titleview.setText(tasks[position].title);
        dateview.setText(tasks[position].date);
        commentview.setText(tasks[position].comment);



        return rowView;
    }
}
