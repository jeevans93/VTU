package com.trix.vtufinal;

/**
 * Created by jeevan on 05/08/17.
 */
public class checkbox {
    String name;
    boolean value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

   checkbox(String name, boolean value){
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public boolean getValue(){
        return this.value;
    }public void setValue(boolean isChecked){
         this.value=isChecked;
    }
}
