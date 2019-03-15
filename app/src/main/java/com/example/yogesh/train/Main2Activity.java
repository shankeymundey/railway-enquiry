package com.example.yogesh.train;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    EditText src,dst,dt;
    Calendar calendar;
    int y,m,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        calendar= Calendar.getInstance();
        y=calendar.get(Calendar.YEAR);
        m=calendar.get(Calendar.MONTH);
        d=calendar.get(Calendar.DAY_OF_MONTH);
        src=(EditText)findViewById(R.id.src);
        dst=(EditText)findViewById(R.id.dst);
        dt=(EditText)findViewById(R.id.dt);
    }
    public void chosedate(View view) {
        DatePickerDialog.OnDateSetListener datesetlistner= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dt.setText(dayOfMonth+"-"+(month+1)+"-"+year);
            }
        };
        DatePickerDialog ob=new DatePickerDialog(this,datesetlistner,y,m,d);
        ob.show();
    }

    public void trainbtwstation(View view) {
        String source=src.getText().toString().trim();
        String destination=dst.getText().toString().trim();
        String date=dt.getText().toString().trim();
        String btwapi="https://api.railwayapi.com/v2/between/source/"+source+"/dest/"+destination+"/date/"+date+"/apikey/mr3ivk4qmu/";
        Intent intent=new Intent(this,BtwnstationActivity.class);
        intent.putExtra("apikey",btwapi);
        startActivity(intent);

    }



}
