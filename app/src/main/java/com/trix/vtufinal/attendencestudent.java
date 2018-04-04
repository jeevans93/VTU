package com.trix.vtufinal;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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

public class attendencestudent extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart pieChart;
    private String USN;
    ArrayList<Entry> yvalues = new ArrayList<Entry>();
    ArrayList<String> xVals = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendencestudent);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
//            Log.e("tidmain",tid);
            USN =(String) b.get("usn");
//            name.setText(tid);
        }
        pieChart = (PieChart) findViewById(R.id.chart);
        //pieChart.setUsePercentValues(true);

        getpievalue();

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null) {
            return;
        }
        else
        {
            Log.e("VAL SELECTED",
                    "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                            + ", DataSet index: " + dataSetIndex);
        }
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    public void getpievalue() {
        class getpieAsync extends AsyncTask<Void, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(attendencestudent.this, "Please wait", "Updating");
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
                    HttpPost httpPost = new HttpPost(web + "/attendance/attendance_view.php");
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

                    JSONArray slist = jsonResponse.getJSONArray("attendence");
                    for (int j = 0; j < slist.length(); j++) {
                        JSONObject sub = slist.getJSONObject(j);
                        int k = j * 2;
                        yvalues.add(new Entry(Integer.parseInt(sub.getString("ac")), k));
                        yvalues.add(new Entry(Integer.parseInt(sub.getString("pc")), k + 1));
                        xVals.add("sub " + j + " Absent count");
                        xVals.add("sub" + j + " Present count");
                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
//                    Toast.makeText(attendencesubmit.this, "JsonArray fail", Toast.LENGTH_SHORT).show();
                }
                return "";
            }
            @Override
            protected void onPostExecute(String result){
                loadingDialog.dismiss();
                PieDataSet dataSet = new PieDataSet(yvalues, "");
                PieData data = new PieData(xVals, dataSet);
               // data.setValueFormatter(new PercentFormatter());
                pieChart.setData(data);
                pieChart.setDescription("");
                pieChart.setContentDescription("");
                pieChart.setDrawHoleEnabled(true);
                pieChart.setCenterText("Attendence");
                pieChart.setHoleRadius(70f);
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                data.setValueTextSize(13f);
                data.setValueTextColor(Color.DKGRAY);
                pieChart.setOnChartValueSelectedListener(attendencestudent.this);
                pieChart.animateXY(1400, 1400);
                pieChart.getLegend().setEnabled(false);
                pieChart.invalidate();
            }
        }
        getpieAsync gpa = new getpieAsync();
        gpa.execute();
    }
}