package com.example.theodoros.madfinalassesment;



public class Restaurant {
    String name, address, cuisine;
    int rating;
    Double lat, lon;

    public Restaurant(String nameIn, String addressIn, String cuisineIn, int ratingIn, Double latIn, Double lonIn){
        name = nameIn;
        address = addressIn;
        cuisine = cuisineIn;
        rating = ratingIn;
        lat = latIn;
        lon = lonIn;
    }

    public String getName() { return name; }
    public String getAddress() {
        return address;
    }
    public String getCuisine() {
        return cuisine;
    }
    public int getRating() {
        return rating;
    }
    public Double getLat() {return lat;}
    public Double getLon() {return lon;}

    //*
    @Override
    public String toString() {
        return name + "," + address + "," + cuisine + "," + rating + "," + lat + "," + lon;
    }
}
