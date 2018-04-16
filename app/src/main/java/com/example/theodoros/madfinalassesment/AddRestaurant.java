package com.example.theodoros.madfinalassesment;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.content.Context;
import android.widget.Toast;


public class AddRestaurant extends AppCompatActivity implements OnClickListener
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant);

        LocationManager mgr=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        TextView name = (TextView)findViewById(R.id.name1);
        EditText edname = (EditText) findViewById(R.id.name2);
        TextView address = (TextView)findViewById(R.id.address1);
        EditText edaddress = (EditText) findViewById(R.id.address2);
        TextView cuisine = (TextView)findViewById(R.id.cuisine1);
        EditText edcuesine = (EditText) findViewById(R.id.cuisine2);
        TextView rating = (TextView)findViewById(R.id.rating1);
        EditText edrating = (EditText) findViewById(R.id.rating2);
        Button addRestBtn = (Button)findViewById(R.id.restBtn);
        addRestBtn.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        TextView name = (TextView)findViewById(R.id.name1);
        EditText edname = (EditText) findViewById(R.id.name2);
        TextView address = (TextView)findViewById(R.id.address1);
        EditText edaddress = (EditText) findViewById(R.id.address2);
        TextView cuisine = (TextView)findViewById(R.id.cuisine1);
        EditText edcuesine = (EditText) findViewById(R.id.cuisine2);
        TextView rating = (TextView)findViewById(R.id.rating1);
        EditText edrating = (EditText) findViewById(R.id.rating2);

        String restName = edname.getText().toString();
        String restAddr = edaddress.getText().toString();
        String restCuisine = edcuesine.getText().toString();
        int restRating = Integer.getInteger(edrating.getText().toString());


        if (restRating<10 || restRating>0){
            new AlertDialog.Builder(this).setMessage("Rating must be between  1-10").
                    setPositiveButton("OK", null).show();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();

            bundle.putString("com.example.name", restName);
            bundle.putString("com.example.address", restAddr);
            bundle.putString("com.example.cuisine", restCuisine);
            bundle.putInt("com.example.rating", restRating);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
