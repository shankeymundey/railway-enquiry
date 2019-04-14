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

public class Cancel extends AppCompatActivity {
    EditText td2;
    Calendar calendar;
    ListView lvcan;
    int y,m,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        calendar= Calendar.getInstance();
        y=calendar.get(Calendar.YEAR);
        m=calendar.get(Calendar.MONTH);
        d=calendar.get(Calendar.DAY_OF_MONTH);

        td2=(EditText)findViewById(R.id.td2);
        lvcan=(ListView)findViewById(R.id.lvcan);
    }
    public void chosedt(View view) {
        DatePickerDialog.OnDateSetListener datesetlistner= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                td2.setText(dayOfMonth+"-"+(month+1)+"-"+year);
            }
        };
        DatePickerDialog ob=new DatePickerDialog(this,datesetlistner,y,m,d);
        ob.show();
    }


    public void canclbtnclick(View view) {
        Canceldata ob = new Canceldata(this);
        ob.execute();

    }

    class Canceldata extends AsyncTask<String, Void, String> {
        Context ctx;
        Canceldata(Context ctx) {
            this.ctx = ctx;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {

                String date = td2.getText().toString().trim();
                String addrs = "https://api.railwayapi.com/v2/cancelled/date/"+date+"/apikey/dowkgyyi2v/";
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
          //  Toast.makeText(ctx,result, Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String, String>> trainlist = new ArrayList();
            try {
                JSONObject jsonobj = new JSONObject(result);
                JSONArray news = jsonobj.getJSONArray("trains");
                for (int i = 0; i < news.length(); i++) {
                    JSONObject d = news.getJSONObject(i);
                    String name = d.getString("name");
                    String number = d.getString("number");
                    String type = d.getString("type");
                    String start_time= d.getString("start_time");
                    JSONObject src = d.getJSONObject("source");
                    String sname=src.getString("name");
                    JSONObject dst = d.getJSONObject("dest");
                    String dname=dst.getString("name");


                        HashMap<String, String> hnews = new HashMap<String, String>();
                        hnews.put("k1", ""+name);
                        hnews.put("k2", "("+number+")");
                        hnews.put("k3", "  "+type+"  ");
                        hnews.put("k4", ""+start_time);
                    hnews.put("k5", ""+sname);
                    hnews.put("k6", ""+dname);
                        trainlist.add(hnews);
                }
                ListAdapter listAdapter = new SimpleAdapter(Cancel.this
                        , trainlist, R.layout.cancellist, new String[]{"k1", "k2", "k3", "k4", "k5", "k6"}, new int[]{R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6});
                lvcan.setAdapter(listAdapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error 2 " + result, Toast.LENGTH_LONG).show();
            }


        }


    }
    }






