package com.seneca.android.senefit;

/**
 * Created by dance on 4/3/2017.
 */

public class Muscle {
    private int id;
    private String name;

    public Muscle(){

    }
    public Muscle(int id,String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
