package com.trix.vtufinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jeevan on 22/08/17.
 */
public class noticeboardstudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeboardstudent);

    }

    public void noticecontent(View view){
        Intent intent = new Intent(noticeboardstudent.this, noticedetails.class);
        startActivity(intent);
    }
}
