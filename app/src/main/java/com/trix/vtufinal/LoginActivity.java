package com.trix.vtufinal;

import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    EditText us;
    EditText pass;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        us= (EditText)findViewById(R.id.editTextEmail);
        pass= (EditText)findViewById(R.id.editTextPassword);
        if(!networkConnectivity())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean networkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
    public void gotostudenthome(View view) {

        if(!networkConnectivity())
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
        else {
            String uname,passwd;
            uname=us.getText().toString();
            passwd=pass.getText().toString();
            login(uname,passwd);
        }
    }

    private void login(final String uname, final String passwd) {
        class LoginAsync extends AsyncTask<Void, Void, String> {
            private Dialog myProgressDialog;

            @Override
            protected void onPreExecute() {
                myProgressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", "Logging in", true);
                super.onPreExecute();
            }
            @Override
            protected String doInBackground(Void... params) {
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("uname", uname));
                nameValuePairs.add(new BasicNameValuePair("pass", passwd));
                String result = null;

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    String web = getResources().getString(R.string.web);
                    HttpPost httpPost = new HttpPost(web+"/login/login.php");
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
            protected void onPostExecute(String result){
                myProgressDialog.dismiss();
                String s="";
                try {
                    s = result.trim();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Log.e("s val",s);
                if(s.equalsIgnoreCase("failure")){
                    Toast.makeText(getApplicationContext(),"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                }
                else if(s.length()==10) {
                    Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                    intent.putExtra("usn",s);
                    finish();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                }
                else if(s.length()<5)
                {
                    Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                    intent.putExtra("tid",s);
                    finish();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();

                }
                else if(s.length()>10 && s.charAt(10)=='P')
                {
                    Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                    intent.putExtra("pid",s);
                    finish();
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_SHORT).show();
                }
            }
        }

        LoginAsync la = new LoginAsync();
        la.execute();

    }

}

