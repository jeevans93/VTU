package com.trix.vtufinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeevan on 05/08/17.
 */
public class customadapter extends ArrayAdapter<checkbox> {
    checkbox[] modelItems = null;
    Context context;
    ArrayList<String> Selectedstrings = new ArrayList<String>();
    public customadapter(Context context, checkbox[] resource) {
        super(context,R.layout.checkbox,resource);
        this.context = context;
        this.modelItems = resource;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.checkbox, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name.setText(modelItems[position].getName());
        if(modelItems[position].getValue() == true)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    modelItems[position].setValue(isChecked);

            }
        });
        return convertView;
    }
}
