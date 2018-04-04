package com.trix.vtufinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by jeevan on 22/08/17.
 */
public class teacherinternalsadd extends AppCompatActivity {
    private String tid,sem,internals;
    private ArrayList<String> usn = new ArrayList<String>();
    private ArrayList<String> mark2 = new ArrayList<String>();
    ListView lv;
    tb[] tblist;
    customadapter2 ca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachersinternalsadd);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            tid =(String) b.get("tid");
            sem =(String) b.get("sem");
            internals =(String) b.get("internals");
        }
        TextView tt = (TextView)findViewById(R.id.semsel);
        tt.setText(tt.getText() + sem);
        getusn();
    }

    public void getusn() {
        class usnAsync extends AsyncTask<Void, Void, String> {
            private Dialog myProgressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                myProgressDialog = ProgressDialog.show(teacherinternalsadd.this, "Please Wait", "Loading USN", true);
            }
            @Override
            protected String doInBackground(Void... params) {
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("tid", tid));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web+"/internals/internals_t_view.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("log res", result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    JSONObject jsonResponse = new JSONObject(result);

                    JSONArray slist = jsonResponse.getJSONArray("internals");
                    for (int j=0; j<slist.length(); j++) {
                        JSONObject sub = slist.getJSONObject(j);
                        Log.e("usn", sub.getString("usns"));
                        usn.add(sub.getString("usns"));
                    }
                    Log.e("usnlen",String.valueOf(usn.size()));
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
//                    Toast.makeText(attendencesubmit.this, "JsonArray fail", Toast.LENGTH_SHORT).show();
                }
                return "";
            }
            protected void onPostExecute(String result){
                lv = (ListView) findViewById(R.id.listView3);
                tblist = new tb[usn.size()];
                for(int i=0;i<usn.size();i++) {
                    tblist[i] = new tb(usn.get(i));
                }
                ca = new customadapter2(teacherinternalsadd.this, tblist);
                lv.setAdapter(ca);
                lv.invalidate();
                myProgressDialog.dismiss();
            }
        }

        usnAsync la = new usnAsync();
        la.execute();
    }

    public void updatemarks(View view) {
        for(int i=0;i<lv.getCount();i++)
        {
            tb tc = (tb) lv.getItemAtPosition(i);
            if(tc.getMarks()!=null && !tc.getMarks().isEmpty())
            {
                mark2.add(tc.getMarks());
            }
            else
            {
                mark2.add("0");
            }
        }
        String[] markslis = mark2.toArray(new String[0]);
        String[] usnlis = usn.toArray(new String[0]);
        internalssubmit(markslis,usnlis);
        mark2.removeAll(mark2);
        usn.remove(usn);
    }
    public void internalssubmit(final String[] marks, final String[] usns)
    {
        class internalsupdateAsync extends AsyncTask<Void, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(teacherinternalsadd.this, "Please wait", "Updating");
            }

            @Override
            protected String doInBackground(Void... params) {
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for(int k =0;k<marks.length;k++) {
                    nameValuePairs.add(new BasicNameValuePair("iamarks[]",marks[k]));
                }
                for(int l =0;l<usns.length;l++)
                {
                    nameValuePairs.add(new BasicNameValuePair("usn[]",usns[l]));
                }
                nameValuePairs.add(new BasicNameValuePair("tid", tid));

                nameValuePairs.add(new BasicNameValuePair("ia", internals));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web+"/internals/internals_t_update.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("log res", result);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                loadingDialog.dismiss();
                String s = "";
                try {
                    s = result.trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(s.equalsIgnoreCase("success")) {
                    Toast.makeText(teacherinternalsadd.this, "Marks Updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(teacherinternalsadd.this,"Marks Updation Failure",Toast.LENGTH_SHORT).show();
                }
            }
        }
        internalsupdateAsync sync = new internalsupdateAsync();
        sync.execute();
    }
}