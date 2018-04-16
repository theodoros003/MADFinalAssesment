package com.example.theodoros.madfinalassesment;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


public class MainActivity extends Activity implements LocationListener
{
    MapView mv;

    Double lat = 51.05;
    Double lon = -0.72;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mv = (MapView)findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(16);


        LocationManager mgr=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);

        TextView la = (TextView)findViewById(R.id.la1);
        TextView vla = (TextView)findViewById(R.id.vla1);
        TextView lo = (TextView)findViewById(R.id.lo1);
        TextView vlo = (TextView)findViewById(R.id.vlo1);
        //vla.setText(lat.toString());
        //vlo.setText(lon.toString());

    }

    public void onLocationChanged(Location newLoc)
    {
        mv.getController().setCenter(new GeoPoint(newLoc.getLatitude(), newLoc.getLongitude()));
        lat = Double.parseDouble(String.valueOf(newLoc.getLatitude()));
        lon = Double.parseDouble(String.valueOf(newLoc.getLongitude()));
        //Toast.makeText
         //       (this, "Location=" +
          //              newLoc.getLatitude()+ " " +
          //              newLoc.getLongitude() , Toast.LENGTH_LONG).show();
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
        if(item.getItemId() == R.id.addRestaurant)
        {
            Intent intent = new Intent(this,AddRestaurant.class);
            startActivityForResult(intent,0);
            return true;
        }
        return false;
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
        if (requestCode == 0) {
            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                Double lat = extras.getDouble("com.example.latitude");
                Double lon = extras.getDouble("com.example.longitude");

                mv.getController().setCenter(new GeoPoint(lat,lon));
                TextView vla = (TextView)findViewById(R.id.vla1);
                vla.setText(lat.toString());
                TextView vlo = (TextView)findViewById(R.id.vlo1);
                vlo.setText(lon.toString());
            }
        }
    }
}
