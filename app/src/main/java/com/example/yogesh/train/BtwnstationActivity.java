package com.example.yogesh.train;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class BtwnstationActivity extends AppCompatActivity {
String btapi;
String []code=new String[7];
String []runs=new String[7];
ListView listViewsrch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btwnstationact);
        listViewsrch=(ListView)findViewById(R.id.listViewsrch);
        Intent intent=getIntent();
        btapi=intent.getStringExtra("apikey");
        Trainbt ob= new Trainbt(this);
        ob.execute();

    }
class Trainbt extends AsyncTask<String,Void,String>{
    Context ctx;
    Trainbt(Context ctx){
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{

            java.net.URL url=new java.net.URL(btapi);
            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();
            con.connect();
            InputStream is=con.getInputStream();
            InputStreamReader ins=new InputStreamReader(is);
            BufferedReader reader=new BufferedReader(ins);
            StringBuffer st= new StringBuffer();
            String line="";
            while ((line=reader.readLine())!=null)
            {
                st.append(line+"\n");
            }
            return  st.toString();
        }
        catch (Exception ex){
        }
        return null;
    }

    @Override
    protected  void onPostExecute(String result) {

      //  Toast.makeText(ctx,result, Toast.LENGTH_LONG).show();
        ArrayList<HashMap<String, String>> trainlist = new ArrayList();
        try {
            JSONObject jsonobj = new JSONObject(result);
            JSONArray news = jsonobj.getJSONArray("trains");
            for (int i = 0; i < news.length(); i++) {
                JSONObject d = news.getJSONObject(i);
                String name = d.getString("name");
                String number = d.getString("number");
                String dest_arrival_time = d.getString("dest_arrival_time");
                String src_departure_time = d.getString("src_departure_time");
                String travel_time = d.getString("travel_time");
                JSONObject e = d.getJSONObject("to_station");
                String toStation=e.getString("name");
                JSONObject f = d.getJSONObject("from_station");
                String fromStation=f.getString("name");
            JSONArray days = d.getJSONArray("days");
               for (int j = 0; j < 7; j++) {
                    JSONObject x = days.getJSONObject(j);
                   code[j] = x.getString("code");
                    runs [j]= x.getString("runs");

                }
                    HashMap<String, String> hnews = new HashMap<String, String>();
                    hnews.put("k1", ""+name);
                    hnews.put("k2", "("+number+")");
                    hnews.put("k3", "  arr:   "+dest_arrival_time);
                    hnews.put("k4", "  dep: "+src_departure_time);
                    hnews.put("k5", "travel time: "+travel_time);
                    hnews.put("k6", ""+toStation);
                    hnews.put("k7", ""+fromStation);
                   hnews.put("k8", ""+code[0]);
                    hnews.put("k9", ""+runs[0]);
                   hnews.put("k10", ""+code[1]);
                    hnews.put("k11", ""+runs[1]);
                    hnews.put("k12", ""+code[2]);
                    hnews.put("k13", ""+runs[2]);
                   hnews.put("k14", ""+code[3]);
                    hnews.put("k15", ""+runs[3]);
                    hnews.put("k16", ""+code[4]);
                    hnews.put("k17", ""+runs[4]);
                    hnews.put("k18", ""+code[5]);
                    hnews.put("k19", ""+runs[5]);
                    hnews.put("k20", ""+code[6]);
                    hnews.put("k21", ""+runs[6]);


                trainlist.add(hnews);
        }

            ListAdapter listAdapter = new SimpleAdapter(BtwnstationActivity.this, trainlist, R.layout.btwlist,
                    new String[]{"k1", "k2", "k3", "k4","k5","k6","k7",
                            "k8", "k9", "k10", "k11","k12","k13","k14","k15", "k16", "k17", "k18","k19","k20","k21"},
                    new int[]{R.id.t1, R.id.t2, R.id.t3, R.id.t4,R.id.t5,R.id.t6, R.id.t7,R.id.t8,
                            R.id.t9, R.id.t10, R.id.t11,R.id.t12,R.id.t13, R.id.t14,R.id.t15, R.id.t16, R.id.t17,
                            R.id.t18,R.id.t19,R.id.t20, R.id.t21});
            listViewsrch.setAdapter(listAdapter);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error 2 "+e , Toast.LENGTH_LONG).show();
        }





    }

    }


}
