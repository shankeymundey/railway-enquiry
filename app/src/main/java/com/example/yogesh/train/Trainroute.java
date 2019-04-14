package com.example.yogesh.train;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class Trainroute extends AppCompatActivity {
    EditText t1;
    ListView lvroute;
TextView tnm,tno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainroute);
        t1 = (EditText) findViewById(R.id.t1);
        tno= (TextView) findViewById(R.id.tno);
        tnm= (TextView) findViewById(R.id.tnm);
        lvroute = (ListView) findViewById(R.id.lvroute);
    }

    public void routebtn(View view) {

        Traindata ob = new Traindata(this);
        ob.execute();

    }

    class Traindata extends AsyncTask<String, Void, String> {
        Context ctx;

        Traindata(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                String nameornumber = t1.getText().toString().trim();
                String addrs = "https://api.railwayapi.com/v2/route/train/" + nameornumber + "/apikey/dowkgyyi2v/";
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
            //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String, String>> trainlist = new ArrayList();
            try {
                JSONObject jsonobj = new JSONObject(result);
                JSONObject tt = jsonobj.optJSONObject("train");
                String name = tt.getString("name");
                String num = tt.getString("number");
                tnm.setText(name);
                tno.setText(num);
                JSONArray news = jsonobj.getJSONArray("route");
                for (int i = 0; i < news.length(); i++) {
                    JSONObject d = news.getJSONObject(i);
                    String Sarrive = d.getString("scharr");
                    String Sdepart = d.getString("schdep");
                    JSONObject e = d.getJSONObject("station");
                    String stationname = e.getString("name");

                    HashMap<String, String> hnews = new HashMap<String, String>();
                    hnews.put("k1", "Arrival time: " + Sarrive);
                    hnews.put("k2", "departure time: " + Sdepart);
                    hnews.put("k3", "" + stationname);
                    // hnews.put("k4", "Train number: "+num);
                    trainlist.add(hnews);
                }

            ListAdapter listAdapter = new SimpleAdapter(Trainroute.this
                    , trainlist, R.layout.routelist, new String[]{"k1", "k2", "k3"}, new int[]{R.id.t1, R.id.t3, R.id.t2});
            lvroute.setAdapter(listAdapter);
        } catch(Exception e)

        {
            Toast.makeText(getApplicationContext(), "Error 2 " + result, Toast.LENGTH_LONG).show();
        }
    }
}

        }




