package com.trix.vtufinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jeevan on 22/08/17.
 */
public class teacherinternalsselect extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String tid,sem,inter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherinternalsselect);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
              tid =(String) b.get("tid");
        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner3);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Select Sem");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        Spinner ispinner = (Spinner)findViewById(R.id.spinner5);
        ispinner.setOnItemSelectedListener(this);
        List<String> ia = new ArrayList<String>();
        ia.add("Select Internals");
        ia.add("IA1");
        ia.add("IA2");
        ia.add("IA3");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ia);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ispinner.setAdapter(dataAdapter1);

    }

    public void addinternals(View view){
        if(sem == "Select Sem" || inter == "Select Internals")
        {
            Toast.makeText(this,"Please Select a sem and Internals",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(teacherinternalsselect.this, teacherinternalsadd.class);
            intent.putExtra("sem", sem);
            intent.putExtra("tid", tid);
            intent.putExtra("internals", inter);
            startActivity(intent);
        }
    }

    public void viewinternals(View view){
        Toast.makeText(this, "View Internal Marks List", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner s = (Spinner) parent;
        if (s.getId() == R.id.spinner3) {
            sem = parent.getItemAtPosition(position).toString();
        }
        if (s.getId() == R.id.spinner5) {
            inter = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
