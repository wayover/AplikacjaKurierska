package com.example.aplikacjakurierska.Kurier;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aplikacjakurierska.R;

import java.io.IOException;
import java.util.List;

public class KurierPackActivity extends AppCompatActivity {

    TextView miasto,ulica,numer,x,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_pack);

        miasto = findViewById(R.id.tvKurierPackMiasto);
        ulica = findViewById(R.id.tvKurierPckUlica);
        numer = findViewById(R.id.tvKurierPckNumer);
        x = findViewById(R.id.tvKurierPckX);
        y = findViewById(R.id.tvKurierPckY);

        miasto.setText(getIntent().getStringExtra("miasto"));
        ulica.setText(getIntent().getStringExtra("ulica"));
        numer.setText(getIntent().getStringExtra("nr"));

        String loc= miasto.getText().toString()+" "+ulica.getText().toString()+" "+numer.getText().toString();
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(loc, 5);
            Address location = address.get(0);
            Double Lat=location.getLatitude();
            Double Long =location.getLongitude();
            x.setText(Lat.toString());
            y.setText(Long.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

