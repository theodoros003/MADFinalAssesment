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


public class AddRestaurant extends AppCompatActivity implements OnClickListener
{

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant);

        Button addRestBtn = (Button)findViewById(R.id.restBtn);
        addRestBtn.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        //set and show location
        TextView name = (TextView)findViewById(R.id.name1);
        EditText edname = (EditText) findViewById(R.id.name2);
        TextView address = (TextView)findViewById(R.id.address1);
        EditText edaddress = (EditText) findViewById(R.id.address2);
        TextView cuisine = (TextView)findViewById(R.id.cuisine1);
        EditText edcuesine = (EditText) findViewById(R.id.cuisine2);
        TextView rating = (TextView)findViewById(R.id.rating1);
        EditText edrating = (EditText) findViewById(R.id.rating2);

        String Name = edname.getText().toString();
        String Address = edaddress.getText().toString();
        String Cuisine = edcuesine.getText().toString();
        int Rating = Integer.parseInt(edrating.getText().toString());

        if (Rating >10 || Rating <0){
            new AlertDialog.Builder(this).setMessage("Please enter rating from 1-10").
                    setPositiveButton("OK", null).show();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("com.example.name", Name);
            bundle.putString("com.example.address", Address);
            bundle.putString("com.example.cuisine", Cuisine);
            bundle.putInt("com.example.rating", Rating);

            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
