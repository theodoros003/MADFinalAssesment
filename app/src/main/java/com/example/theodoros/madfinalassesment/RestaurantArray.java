package com.example.theodoros.madfinalassesment;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;

public class RestaurantArray {
    String Aname, Aaddress, Acuisine;
    int Arating;
    Double lat, lon;

    public RestaurantArray(String AnameIn, String AaddressIn, String AcuisineIn, int AratingIn, Double latIn, Double lonIn){
        Aname = AnameIn;
        Aaddress = AaddressIn;
        Acuisine = AcuisineIn;
        Arating = AratingIn;
        lat = latIn;
        lon = lonIn;
    }

    public String getAname() {
        return Aname;
    }
    public String getAaddress() {
        return Aaddress;
    }
    public String getAcuisine() {
        return Acuisine;
    }
    public int getArating() {
        return Arating;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLon() {
        return lon;
    }

    //*
    @Override
    public String toString() {
        return Aname + "," + Aaddress + "," + Acuisine + "," + Arating + "," + lat + "," + lon;
    }
}
