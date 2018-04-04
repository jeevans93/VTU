package com.trix.vtufinal;

/**
 * Created by jeevan on 25/08/17.
 */
public class tb {
    String usn;
    String marks;
    tb(String usn)
    {
        this.usn=usn;
    }

    public String getMarks() {
        return marks;
    }

    public String getUsn() {
        return usn;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }
}
