package com.example.theodoros.madfinalassesment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;


public class MainActivity extends AppCompatActivity implements LocationListener
{
    boolean isRecording = false;
    RestaurantDAO restDao = new RestaurantDAO();
    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;
    ArrayList<Restaurant> restaurants = new ArrayList<>();
    double lat;
    double lon;

    @Override
    public void onStart()
    {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
    public void onDestroy()
    {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean ("isRecording", isRecording);
        editor.commit();
    }
    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean("isRecording", isRecording);
    }
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restDao.savetofile();
        restDao.loadfile();
        List<Restaurant> restaurants = restDao.getRestaurants();
        for (Restaurant rest:restaurants){
            System.out.print(rest.getName());
            System.out.print(rest.getAddress());
            System.out.print(rest.getCuisine());
            System.out.print(rest.getRating());
            System.out.print(rest.getLat());
            System.out.print(rest.getLon());
        }

        //LocationManager mgr=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);

        TextView la = (TextView)findViewById(R.id.la1);
        TextView vla = (TextView)findViewById(R.id.vla1);
        TextView lo = (TextView)findViewById(R.id.lo1);
        TextView vlo = (TextView)findViewById(R.id.vlo1);

        mv = (MapView)findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(16);

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
        {
            public boolean onItemLongPress(int i, OverlayItem item)
            {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem item)
            {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        //set up location manager
        LocationManager mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Location location = mgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        lat = location.getLatitude();
        lon = location.getLongitude();
        mv.getController().setCenter(new GeoPoint(lat, lon));

        //convert double to string
        String stlat = new Double (lat).toString();
        String stlon = new Double (lon).toString();

        //set and show location

        vla.setText(stlat.toString());
        vlo.setText(stlon.toString());

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        mv.getOverlays().add(items);
    }

    public void onLocationChanged(Location newLoc)
    {
        lat = Double.parseDouble(String.valueOf(newLoc.getLatitude()));
        lon = Double.parseDouble(String.valueOf(newLoc.getLongitude()));

        mv.getController().setCenter(new GeoPoint(lat, lon));
        //convert double to string
        String stlat = new Double (lat).toString();
        String stlon = new Double (lon).toString();

        TextView vla = (TextView)findViewById(R.id.vla1);
        vla.setText(stlat.toString());
        TextView vlo = (TextView)findViewById(R.id.vlo1);
        vlo.setText(stlon.toString());
    }
    public void onProviderDisabled(String provider)
    {
        Toast.makeText(this, "Provider " + provider +
                " disabled", Toast.LENGTH_LONG).show();
    }
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(this, "Provider " + provider +
                " enabled", Toast.LENGTH_LONG).show();
    }
    public void onStatusChanged(String provider,int status,Bundle extras)
    {
        Toast.makeText(this, "Status changed: " + status,
                Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d("assignment", "onOptionsItemSelected()");
        if(item.getItemId() == R.id.addRest){
            Intent intent = new Intent(this,AddRestaurant.class);
            startActivityForResult(intent,0);
            return true;
        }
        else if(item.getItemId() == R.id.saveFile){
            Log.d("assignment","Succesfully save");
            restDao.savetofile();
            Toast.makeText(this, "saved resturaunt", Toast.LENGTH_LONG).show();
            return true;
        }
        else if(item.getItemId() == R.id.loadFile){
            Log.d("assignment","Succesfully loaded");
            restaurants.clear();
            restDao.loadfile();
            List<Restaurant> restaurants = restDao.getRestaurants();
            for (Restaurant rest:restaurants) {
                OverlayItem restaurant = new OverlayItem(rest.getName(),"Restaurant Name: "+rest.getName()+"\n"+"Address: "+ rest.getAddress()+"\n"+"Cuisine: "+rest.getCuisine()+"\n"+"Rating: "+rest.getRating(), new GeoPoint(rest.getLat(),rest.getLon()));
                restaurant.setMarker(getResources().getDrawable(R.drawable.marker));
                items.addItem(restaurant);
                mv.invalidate();
            }
            Toast.makeText(this, "loaded resturaunts from file", Toast.LENGTH_LONG).show();
            // loop through restaurants and add a marker for each
            return true;
        }
        else if(item.getItemId() == R.id.prefer){
            Intent intent = new Intent(this,Preference.class);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,1);
            return true;
        }
        else if(item.getItemId() == R.id.loadWeb){
            Loadfromweb lfw = new Loadfromweb();
            lfw.execute();

            return true;
        }
        return false;
    }



    class Loadfromweb extends AsyncTask<String, Void, String> {

        List<Restaurant> restaurants2 = new ArrayList<Restaurant>();

        public List<Restaurant> getRestaurants2() {
            return restaurants2;
        }

        public String doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {

                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/get.php?year=18&username=user002&format=csv");
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                if (conn.getResponseCode() == 200) {
                    restaurants2.clear();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while ((line = br.readLine()) != null) {
                        String[] components = line.split(",");
                        if (components.length == 6) {

                            //note reversed lat lon
                            Restaurant newRest = new Restaurant(components[0], components[1], components[2], Integer.parseInt(components[3]), Double.parseDouble(components[5]), Double.parseDouble(components[4]));
                            restaurants2.add(newRest);
                        }
                    }
                    return result;
                } else {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }
            } catch (IOException e) {
                return e.toString();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        public void onPostExecute(String result) {
            List<Restaurant> restaurants = restDao.getRestaurants();
            restaurants.clear();
            restaurants.addAll(restaurants2);
            System.out.println("debug *** size:"+ restaurants.size());
            System.out.println("debug *** items size:"+ items.size());
            for (Restaurant rest:restaurants) {
                System.out.println("debug ***"+ rest.getName());
                OverlayItem restaurant = new OverlayItem(rest.getName(),"Restaurant Name: "+rest.getName()+"\n"+"Address: "+ rest.getAddress()+"\n"+"Cuisine: "+rest.getCuisine()+"\n"+"Rating: "+rest.getRating(), new GeoPoint(rest.getLat(),rest.getLon()));
                restaurant.setMarker(getResources().getDrawable(R.drawable.marker));
                items.addItem(restaurant);
            }
            System.out.println("debug *** items size:"+ items.size());
            Toast.makeText(MainActivity.this, "loaded resturaunts from web", Toast.LENGTH_LONG).show();
        }
    }
















    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
    if (requestCode == 0) {
        if (resultCode==RESULT_OK){
            Bundle extras = intent.getExtras();
            String name = extras.getString("com.example.name");
            String address = extras.getString("com.example.address");
            String cuisine = extras.getString("com.example.cuisine");
            int rating = extras.getInt("com.example.rating");


            restDao.addResturaunt(new Restaurant(name, address, cuisine, rating, lat, lon));
            OverlayItem restaurant = new OverlayItem(name,"Restaurant Name: "+name+"\n"+"Address: "+ address+"\n"+"Cuisine: "+cuisine+"\n"+"Rating: "+rating, new GeoPoint(lat,lon));
            restaurant.setMarker(getResources().getDrawable(R.drawable.marker));
            items.addItem(restaurant);

        }
    }
}
}
