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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class PnrActivity extends AppCompatActivity {
     EditText t1;
     ListView lvpnr;
 /*    String []cs=new String[];
    String []bs=new String[];
    String []psngr=new String[];  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr);
        t1=(EditText)findViewById(R.id.t1);
        lvpnr = (ListView) findViewById(R.id.lvpnr);
    }

    public void pnrgetbtn(View view) {
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

                String pnrrnumber = t1.getText().toString().trim();
                String addrs = "https://api.railwayapi.com/v2/pnr-status/pnr/"+pnrrnumber+"/apikey/mr3ivk4qmu/";
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
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String, String>> trainlist = new ArrayList();
            try {
                JSONObject jsonobj = new JSONObject(result);
                String pnr=jsonobj.getString("pnr");
                String doj=jsonobj.getString("doj");
                String total_passenger=jsonobj.getString("total_passenger");

                JSONObject tt = jsonobj.getJSONObject("boarding_point");
                String boarding = tt.getString("name");

                JSONObject ss = jsonobj.getJSONObject("reservation_upto");
                String reservation_upto = ss.getString("name");

                JSONObject rr = jsonobj.getJSONObject("train");
                String trainname = rr.getString("name");
                String trainno = rr.getString("number");

                JSONObject xx = jsonobj.getJSONObject("journey_class");
                String classname = xx.getString("name");
                String classcode = xx.getString("code");


             //   JSONArray news = jsonobj.getJSONArray("passengers");
             /*   for (int i = 0; i < news.length(); i++) {
                    JSONObject d = news.getJSONObject(i);
                     psngr[i] =d.getString("no");
                     cs[i] =d.getString("current_status");
                     bs[i] =d.getString("booking_status");
                }
*/
                    HashMap<String, String> hnews = new HashMap<String, String>();
                    hnews.put("k1", "Boarding: " + boarding);
                    hnews.put("k2", "reservation upto: " + reservation_upto);
                    hnews.put("k3", "pnr no." + pnr);
                     hnews.put("k4", "Total passenger: "+total_passenger);
                hnews.put("k5", "doj." + doj);
                hnews.put("k6", "train name." + trainname);
                hnews.put("k7", "train no." + trainno);
                hnews.put("k8", "class name." + classname);
                hnews.put("k9", "class code" + classcode);
                     trainlist.add(hnews);


                ListAdapter listAdapter = new SimpleAdapter(PnrActivity.this
                        , trainlist, R.layout.pnrlist, new String[]{"k1", "k2", "k3","k4", "k5", "k6","k7", "k8", "k9"}, new int[]{R.id.t1, R.id.t2, R.id.t3,R.id.t4, R.id.t5, R.id.t6,R.id.t7, R.id.t8, R.id.t9});
                lvpnr.setAdapter(listAdapter);
            } catch(Exception e)

            {
                Toast.makeText(getApplicationContext(), "Error 2 " + result, Toast.LENGTH_LONG).show();
            }
        }
    }











}
