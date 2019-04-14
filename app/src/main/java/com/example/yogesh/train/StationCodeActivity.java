package com.example.yogesh.train;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class StationCodeActivity extends AppCompatActivity {
    EditText e1;
    ListView lv;
    String station;
    HashMap hnews;
    //LayoutInflater inflater;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stationcodeactivity);
        e1 = (EditText) findViewById(R.id.e1);
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
                //v=inflater.inflate(R.layout.trainlist,null);

                //  Toast.makeText(getApplicationContext(),"ID ",Toast.LENGTH_LONG).show();
                TextView vurl=(TextView)view.findViewById(R.id.t1);
                TextView surl=(TextView)view.findViewById(R.id.t3);
                TextView turl=(TextView)view.findViewById(R.id.t4);
                String loc=vurl.getText().toString().trim();
                String lat=surl.getText().toString().trim();
                String lon=turl.getText().toString().trim();
                Toast.makeText(getApplicationContext(),loc+lat+lon,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
               intent.putExtra("lockey",loc);
               intent.putExtra("latkey",lat);
                intent.putExtra("lonkey",lon);
                startActivity(intent);

            }
        });



    }

    class Traindata extends AsyncTask<String, Void, String> {
        Context ctx;
        Traindata(Context ctx) {
            this.ctx = ctx;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                station = e1.getText().toString().trim();
                String addrs = "https://api.railwayapi.com/v2/name-to-code/station/" + station + "/apikey/dowkgyyi2v/";
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
            //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String, String>> trainlist = new ArrayList();
            try {
                JSONObject jsonobj = new JSONObject(result);
                JSONArray news = jsonobj.getJSONArray("stations");
                for (int i = 0; i < news.length(); i++) {
                    JSONObject d = news.getJSONObject(i);
                    String name = d.getString("name");
                    String code = d.getString("code");
                    String lat = d.getString("lat");
                    String lng = d.getString("lng");
                    if(name.equalsIgnoreCase(station)||name.contains(station.toUpperCase())) {

                        HashMap<String, String> hnews = new HashMap<String, String>();
                        hnews.put("k1", "Station name: "+name);
                        hnews.put("k2", "Station code: "+code);
                        hnews.put("k3", "Latitude: "+lat);
                        hnews.put("k4", "Longitude: "+lng);
                        trainlist.add(hnews);}
                }
                ListAdapter listAdapter = new SimpleAdapter(StationCodeActivity.this
                        , trainlist, R.layout.trainlist, new String[]{"k1", "k2", "k3", "k4"}, new int[]{R.id.t1, R.id.t2, R.id.t3, R.id.t4});
                lv.setAdapter(listAdapter);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error 2 " + result, Toast.LENGTH_LONG).show();
            }


        }
    }

        public void getbtn(View view) {
            Traindata ob = new Traindata(this);
            ob.execute();


        }


}