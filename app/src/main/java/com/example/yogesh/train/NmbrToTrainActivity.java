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


public class NmbrToTrainActivity extends AppCompatActivity {
EditText tna,tno;
ListView lvno;
int check=0;
    String notonameapi;
    String Nm;
    String No;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmbr_to_train);
     tna=(EditText)findViewById(R.id.tna);
        tno=(EditText)findViewById(R.id.tno);
        lvno=(ListView)findViewById(R.id.lvno);


    }


    public void srchnamebtn(View view) {
        No=tno.getText().toString().trim();
        check=1;
        TrainNo ob = new TrainNo(this);
        ob.execute();

    }

    public void srchnobtn(View view) {
        Nm=tna.getText().toString().trim();
        check=2;
        TrainNo ob = new TrainNo(this);
        ob.execute();

    }

    class TrainNo extends AsyncTask<String,Void,String> {
        Context ctx;
        TrainNo(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                if (check==1)
                { notonameapi="https://api.railwayapi.com/v2/name-number/train/"+No+"/apikey/mr3ivk4qmu/";}
                 if (check==2)
                 {notonameapi="https://api.railwayapi.com/v2/name-number/train/"+Nm+"/apikey/mr3ivk4qmu/";}
                java.net.URL url=new java.net.URL(notonameapi);
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
                Toast.makeText(ctx,"dikkat", Toast.LENGTH_LONG).show();
            }


            return null;
        }

        @Override
        protected  void onPostExecute(String result) {

            //Toast.makeText(ctx,result, Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String, String>> trainlist = new ArrayList();
            try {
                JSONObject jsonobj = new JSONObject(result);
                JSONObject news = jsonobj.getJSONObject("train");

                    String name = news.getString("name");
                    String number = news.getString("number");

                    Toast.makeText(ctx,number+name, Toast.LENGTH_LONG).show();
                   HashMap<String, String> hnews = new HashMap<String, String>();
                      hnews.put("k1", "train name: " + name);
                      hnews.put("k2", "train no.: " + number);
                    trainlist.add(hnews);


                ListAdapter listAdapter = new SimpleAdapter(NmbrToTrainActivity.this
                       , trainlist, R.layout.trainlist, new String[]{"k1", "k2"}, new int[]{R.id.t1, R.id.t2});
                lvno.setAdapter(listAdapter);


            }
            catch (Exception e){
                Toast.makeText(ctx,"problem", Toast.LENGTH_LONG).show();
            }
        }

    }



}
