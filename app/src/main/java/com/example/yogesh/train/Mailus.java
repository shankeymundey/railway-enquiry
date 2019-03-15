package com.example.yogesh.train;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Mailus extends AppCompatActivity {
    EditText t2;//,t3,t1,;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailus);

        //t1=(EditText)findViewById(R.id.t1);
        t2=(EditText)findViewById(R.id.t2);
        //t3=(EditText)findViewById(R.id.t3);



    }


        public void clk(View view) {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse("email"));
            String[] s={"shankeymundey085@gmail.com"};
            String p="";
          // String[] s={t1.getText().toString()};
           // String s="shankeymundey085@gmail.com";
            i.putExtra(Intent.EXTRA_EMAIL,s);
            i.putExtra(Intent.EXTRA_SUBJECT,p);
            i.putExtra(Intent.EXTRA_TEXT,t2.getText().toString());

            i.setType("message/rfc822");


            Intent chooser= Intent.createChooser(i,"Launch Email");
            startActivity(chooser);
        }


    }











