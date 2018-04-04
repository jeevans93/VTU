package com.trix.vtufinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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
public class studentinternals extends AppCompatActivity {

    String USN;
    String[][] iamarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentinternals);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            USN =(String) b.get("usn");
        }
        getmarks();
//        RelativeLayout stu = (RelativeLayout)this.findViewById(R.id.studentinternals);
//        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        TextView tv=new TextView(this);
//        tv.setLayoutParams(lparams);
//        tv.setText("test");
//        stu.addView(tv);
    }

    public void getmarks() {
        class getmarksAsync extends AsyncTask<Void, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(studentinternals.this, "Please wait", "Updating");
            }
            @Override
            protected String doInBackground(Void... params) {

                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("usn", USN));
                String result = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web + "/internals/internal_view.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
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
                    iamarks = new String[2][4];
                    JSONArray slist = jsonResponse.getJSONArray("internals");
                    for (int j = 0; j < slist.length(); j++) {
                        JSONObject sub = slist.getJSONObject(j);
                        iamarks[j][0]=String.valueOf(j);
                        iamarks[j][1]=sub.getString("ia1");
                        iamarks[j][2]=sub.getString("ia2");
                        iamarks[j][3]=sub.getString("ia3");
                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
//                    Toast.makeText(attendencesubmit.this, "JsonArray fail", Toast.LENGTH_SHORT).show();
                }
                return "";
            }
            @Override
            protected void onPostExecute(String result) {
                loadingDialog.dismiss();
                TextView ia1 = (TextView)findViewById(R.id.textView14);
                ia1.setText("Sub " + iamarks[0][0]);
                TextView ia2 = (TextView)findViewById(R.id.textView18);
                ia2.setText("Sub "+iamarks[1][0]);
                TextView subia1 = (TextView)findViewById(R.id.textView19);
                subia1.setText("Internals 1 :   "+iamarks[0][1]);
                TextView sub1ia2 = (TextView)findViewById(R.id.textView20);
                sub1ia2.setText("Internals 2 :   "+iamarks[0][2]);
                TextView sub1ia3 = (TextView)findViewById(R.id.textView21);
                sub1ia3.setText("Internals 3 :   "+iamarks[0][3]);
                TextView sub2ia1 = (TextView)findViewById(R.id.textView22);
                sub2ia1.setText("Internals 1 :   "+iamarks[1][1]);
                TextView sub2ia2 = (TextView)findViewById(R.id.textView23);
                sub2ia2.setText("Internals 2 :   "+iamarks[1][2]);
                TextView sub2ia3 = (TextView)findViewById(R.id.textView24);
                sub2ia3.setText("Internals 3 :   "+iamarks[1][3]);

            }
        }
        getmarksAsync gm = new getmarksAsync();
        gm.execute();
    }
}

