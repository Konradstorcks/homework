package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Activity2 extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText dateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        dateInput = (EditText) findViewById(R.id.date);

        initializeDateInput();

        final EditText titleInput = findViewById(R.id.title);
        final EditText commentInput = findViewById(R.id.comment);

        Button buttonOne = findViewById(R.id.save);
        buttonOne.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String comment = commentInput.getText().toString();
                String date = dateInput.getText().toString();
                JSONObject parent = new JSONObject();
                SharedPreferences sharedPref = Activity2.this.getSharedPreferences(
                        "daten", Context.MODE_PRIVATE);
                String jsonString = sharedPref.getString("homework", "[]");

                try {
                    JSONArray initialData = new JSONArray(jsonString);
                    parent.put("title", title);
                    parent.put("comment", comment);
                    parent.put("date", date);
                    initialData.put(parent);
    
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("homework", initialData.toString());
                    editor.apply();
                }catch(Exception exception) {
                    System.out.println("Falsch formatiertes JSON");

                    exception.printStackTrace();
                }



                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
            }


        } )  ;
    }

    private void initializeDateInput(){
       final  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Activity2.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd.MM.yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        dateInput.setText(sdf.format(myCalendar.getTime()));
    }
}