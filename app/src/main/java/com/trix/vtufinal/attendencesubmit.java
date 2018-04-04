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
public class attendencesubmit extends AppCompatActivity {
    private String tid,sem;
    private ArrayList<String> usn = new ArrayList<String>();
    private ArrayList<String> absent = new ArrayList<String>();
    ListView lv;
    checkbox[] modelItems;
    customadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            tid =(String) b.get("tid");
            sem =(String) b.get("sem");
        }
        setContentView(R.layout.attendencesubmit);
        TextView abs = (TextView) findViewById(R.id.textView12);
        abs.setText(abs.getText()+" OF SEM "+sem);
        getusn(tid);


    }


    private void getusn(final String ttid) {
        class usnAsync extends AsyncTask<String, Void, String> {

            private Dialog myProgressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                myProgressDialog = ProgressDialog.show(attendencesubmit.this, "Please Wait", "Loading USN", true);
            }
            @Override
            protected String doInBackground(String... params) {
                String dev_id = params[0];
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("tid", ttid));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web+"/attendance/attendance_t_view.php");
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

                    JSONArray slist = jsonResponse.getJSONArray("attendence");
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

            @Override
            protected void onPostExecute(String result){
                lv = (ListView) findViewById(R.id.listView);
                modelItems = new checkbox[usn.size()];
                for(int i=0;i<usn.size();i++) {
                    modelItems[i] = new checkbox(usn.get(i),true);
                }
                adapter = new customadapter(attendencesubmit.this, modelItems);
                lv.setAdapter(adapter);
                myProgressDialog.dismiss();
            }
        }

        usnAsync la = new usnAsync();
        la.execute(ttid);

    }


    public void submit(View view){
        for(int i=0;i<lv.getCount();i++)
        {
            checkbox cc = (checkbox) lv.getItemAtPosition(i);
            if(cc.getValue()==true)
            {
                absent.add(cc.getName());
            }
        }
        String[] absnt = absent.toArray(new String[0]);
        attendencesubmit(absnt);
        absent.removeAll(absent);
    }
    protected void attendencesubmit(final String[] abslist)
    {
        class attendanceupdateAsync extends AsyncTask<String[], Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(attendencesubmit.this, "Please wait", "Updating");
            }

            @Override
            protected String doInBackground(String[]... params) {
                String[] absslist = params[0];
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for(int k =0;k<abslist.length;k++) {
                    nameValuePairs.add(new BasicNameValuePair("absentees[]", abslist[k]));
                }
                nameValuePairs.add(new BasicNameValuePair("tid", tid));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web+"/attendance/attendance_t_update.php");
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
                    Toast.makeText(attendencesubmit.this,"Attendance Updated",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(attendencesubmit.this,"Attendance Updation Failure",Toast.LENGTH_SHORT).show();
                }
            }
        }
        attendanceupdateAsync sync = new attendanceupdateAsync();
        sync.execute(abslist);
    }
}
