package com.trix.vtufinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by jeevan on 22/08/17.
 */
public class viewattendenceteacher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendencesubmit);

        ListView lv;
        checkbox[] modelItems;
        lv = (ListView) findViewById(R.id.listView);
        modelItems = new checkbox[34];
        modelItems[0] = new checkbox("pizza", false);
        customadapter adapter = new customadapter(this, modelItems);
        lv.setAdapter(adapter);

    }

    public void edit(View view){
        Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show();
    }
    public void close(View view){
        Toast.makeText(this, "CLOSE", Toast.LENGTH_SHORT).show();
    }

}
