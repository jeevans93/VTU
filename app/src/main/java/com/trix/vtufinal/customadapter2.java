package com.trix.vtufinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jeevan on 25/08/17.
 */
public class customadapter2 extends ArrayAdapter<tb>
{
    tb[] tbitems=null;
    Context context;
    customadapter2(Context c,tb[] resources)
    {
        super(c,R.layout.textbox,resources);
        this.context=c;
        this.tbitems=resources;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.textbox,parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.textView17);
        final EditText ed = (EditText) convertView.findViewById(R.id.editText6);
        name.setText(tbitems[position].getUsn());
        ed.setText(tbitems[position].getMarks());
        ed.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.restartInput(ed);
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final EditText Caption = (EditText) v;
                    tbitems[position].setMarks(Caption.getText().toString());
                }
            }
        });
        return convertView;
    }
}
