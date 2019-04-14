package com.example.yogesh.train;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



    }

    public void searchtrainbtn(View view) {
        Intent intent=new Intent(this,Main2Activity.class);
        startActivity(intent);

    }


    public void nametocodebtn(View view) {
        Intent intent=new Intent(this,StationCodeActivity.class);
        startActivity(intent);
    }

    public void nmbrtonamebtn(View view) {
        Intent intent=new Intent(this,NmbrToTrainActivity.class);
        startActivity(intent);
    }

    public void trainroutebtn(View view) {
        Intent intent=new Intent(this,Trainroute.class);
        startActivity(intent);
    }

    public void livebtn(View view) {
        Intent intent=new Intent(this,Forlive.class);
        startActivity(intent);

    }

    public void canclbtn(View view) {
        Intent intent=new Intent(this,Cancel.class);
        startActivity(intent);
    }

    public void pnrbtn(View view) {
        Intent intent=new Intent(this,PnrActivity.class);
        startActivity(intent);

    }


    public void abtclick(MenuItem item) {
        Intent intent=new Intent(this,Aboutus.class);
        startActivity(intent);

    }

    public void mailusclck(MenuItem item) {
        Intent intent=new Intent(this,Mailus.class);
        startActivity(intent);


    }

    public void shareclck(MenuItem item) {
        Intent myintent=new Intent(Intent.ACTION_SEND);
        myintent.setType("text/plain");
        String link="https://github.com/shankeymundey/Train-apk";
        myintent.putExtra(Intent.EXTRA_TEXT,link);




        Intent chooser= Intent.createChooser(myintent,"Share using");
        startActivity(chooser);

    }
}
