package anurag.ui;

import java.io.Serializable;

/**
 * Created by anurag on 09-07-2017.
 */

public class Details implements Serializable{
    String title;
    String date;
    String notes;
    String time;
    int id;

    public Details(String title,String date,String notes,String time,int id) {
        this.date = date;
        this.title=title;
        this.notes=notes;
        this.time=time;
        this.id=id;
    }
}
