package com.example.yogesh.train;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class Forlive extends AppCompatActivity {
 EditText tn,td;
 TextView tv1,tv2;
    String cstn,position;
    ListView llv;
    Calendar calendar;
    int y,m,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forlive);
        td=(EditText)findViewById(R.id.td);
        tn=(EditText)findViewById(R.id.tn);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        llv=(ListView)findViewById(R.id.llv);
        calendar= Calendar.getInstance();
        y=calendar.get(Calendar.YEAR);
        m=calendar.get(Calendar.MONTH);
        d=calendar.get(Calendar.DAY_OF_MONTH);

    }
    public void chosedate2(View view) {
        DatePickerDialog.OnDateSetListener datesetlistner= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                td.setText(dayOfMonth+"-"+(month+1)+"-"+year);
            }
        };
        DatePickerDialog ob=new DatePickerDialog(this,datesetlistner,y,m,d);
        ob.show();
    }


    public void livestatusbtn(View view) {
        Livedata ob = new Livedata(this);
        ob.execute();

    }



    class Livedata extends AsyncTask<String, Void, String> {
        Context ctx;
        Livedata(Context ctx) {
            this.ctx = ctx;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                 String train_no = tn.getText().toString().trim();
                String date = td.getText().toString().trim();
                String addrs = "https://api.railwayapi.com/v2/live/train/"+train_no+"/date/"+date+"/apikey/mr3ivk4qmu/";
                java.net.URL url = new java.net.URL(addrs);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.connect();
                InputStream is = con.getInputStream();
                InputStreamReader ins = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(ins);
                StringBuffer st = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {


                    st.append(line + "\n");
                }
                return st.toString();

            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
 //            Toast.makeText(ctx,result, Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String, String>> trainlist = new ArrayList();
            try {
                JSONObject jsonobj = new JSONObject(result);
                JSONObject cstation=jsonobj.getJSONObject("current_station");
                 cstn=cstation.getString("name");
                 position=jsonobj.getString("position");
                JSONArray news = jsonobj.getJSONArray("route");
                for (int i = 0; i < news.length(); i++) {
                    JSONObject d = news.getJSONObject(i);
                    String has_departed = d.getString("has_departed");
                    String has_arrived = d.getString("has_arrived");
                    String status = d.getString("status");
                    String latemin = d.getString("latemin");
                    String tdistance = d.getString("distance");
                    String scharr = d.getString("scharr");
                    String schdep = d.getString("schdep");
                    String actarr = d.getString("actarr");
                    String actdep = d.getString("actdep");
                    JSONObject e = d.getJSONObject("station");
                    String stationname=e.getString("name");



                    HashMap<String, String> hnews = new HashMap<String, String>();
                    hnews.put("k1", ""+stationname);
                    hnews.put("k2", "("+status+")");
                    hnews.put("k3", "Late min.: "+latemin);
                    hnews.put("k4", "Arrived: "+has_arrived);
                    hnews.put("k5", " Departed: "+has_departed);
                    hnews.put("k6", " Travelled dist.: "+tdistance+"km");
                    hnews.put("k7", " arr.: "+scharr);
                    hnews.put("k8", " dep.: "+schdep);
                    hnews.put("k9", " arr.: "+actarr);
                    hnews.put("k10", " dep.: "+actdep);
                    trainlist.add(hnews);
                }
                ListAdapter listAdapter = new SimpleAdapter(Forlive.this
                        , trainlist, R.layout.livelist, new String[]{"k1", "k2", "k3", "k4", "k5", "k6", "k7", "k8", "k9", "k10"}, new int[]{R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6, R.id.t7, R.id.t8, R.id.t9, R.id.t10});
                llv.setAdapter(listAdapter);
                tv1.setText(cstn);
                tv2.setText(position);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error 2 " + result, Toast.LENGTH_LONG).show();
            }


        }
    }














}
