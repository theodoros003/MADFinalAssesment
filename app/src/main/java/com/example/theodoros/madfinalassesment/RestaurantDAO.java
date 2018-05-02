package com.example.theodoros.madfinalassesment;

import android.app.AlertDialog;
import android.os.Environment;

import org.osmdroid.views.overlay.OverlayItem;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;

import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import android.os.AsyncTask;

import java.util.concurrent.CopyOnWriteArrayList;




public class RestaurantDAO extends AppCompatActivity {

    // using concurrent class in case accessed concurrently
    List<Restaurant> restaurants = new CopyOnWriteArrayList<>();

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void addResturaunt(Restaurant rest) {
        restaurants.add(rest);
    }

        public void savetofile() {
            String message = "Successfully saved";
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data.csv", true));
                for (Restaurant rest : restaurants) {
                    pw.println(rest.toString());
                }
                pw.close();
            } catch (IOException e) {
                System.out.println("ERROR: " + e);
            }

            System.out.println("Successfully saved");
        }

        public void loadfile() {
            String load = "Successfully loaded";
            try {
                FileReader fr = new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/data.csv");
                BufferedReader reader = new BufferedReader(fr);
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] components = line.split(",");
                    if (components.length == 6) {
                        Restaurant newRest = new Restaurant(components[0], components[1], components[2], Integer.parseInt(components[3]), Double.parseDouble(components[4]), Double.parseDouble(components[5]));
                        restaurants.add(newRest);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Input file data.csv could not be found!");
            } catch (IOException e) {
                System.out.println("General input/output error - hard drive failure?");
            }
        }
    }



