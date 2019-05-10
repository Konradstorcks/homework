package com.example.myapplication;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;



import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        sharedPref = this.getSharedPreferences(
                "daten", Context.MODE_PRIVATE);

        Button createButton = findViewById(R.id.create);
        Button resetButton = findViewById(R.id.reset);

        listView = findViewById(R.id.listview);




        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent activity2Intent = new Intent(getApplicationContext(), Activity2.class);
                startActivity(activity2Intent);

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("homework", "[]");
                editor.apply();

                updateList();

            }



        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                   final int position, long id) {

               final ArrayList tasks = new ArrayList(Arrays.asList(loadData()));

                if (position < tasks.size()){

                    AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                    a_builder.setMessage("Delete the task?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tasks.remove(position);
                                    SharedPreferences.Editor editor = sharedPref.edit();

                                    editor.putString("homework", new Gson().toJson(tasks));
                                    editor.apply();
                                    updateList();
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }) ;
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("");
                    alert.show();

                }
            }
        });

        Button helpText = findViewById(R.id.helpid);

        helpText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent extra_activityIntent = new Intent(getApplicationContext(), extra_activity.class);
                startActivity(extra_activityIntent);

            }
        });


    }

    private void updateList() {
        Task[] data = loadData();
        TaskAdapter adapter = new TaskAdapter(MainActivity.this, R.layout.task_list_entry, data);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private Task[] loadData() {
        String jsonString = sharedPref.getString("homework", "[]");

        Task[] data = new Gson().fromJson(jsonString, Task[].class);
        return data;
    }




}
