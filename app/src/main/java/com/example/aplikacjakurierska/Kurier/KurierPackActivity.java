package com.example.aplikacjakurierska.Kurier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aplikacjakurierska.R;

import java.io.IOException;
import java.util.List;

public class KurierPackActivity extends AppCompatActivity {

    TextView miasto,ulica,numer,x,y;
    Button Nawiguj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_pack);

        miasto = findViewById(R.id.tvKurierPackMiasto);
        ulica = findViewById(R.id.tvKurierPckUlica);
        numer = findViewById(R.id.tvKurierPckNumer);
        x = findViewById(R.id.tvKurierPckX);
        y = findViewById(R.id.tvKurierPckY);

        Nawiguj=findViewById(R.id.bKurierPackNavigate);

        final String Smiasto=getIntent().getStringExtra("miasto");
        final String Sulica=getIntent().getStringExtra("ulica");
        final String Snumer=getIntent().getStringExtra("nr");

        Nawiguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Smiasto+" "+Sulica+" "+Snumer);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        miasto.setText(Smiasto);
        ulica.setText(Sulica);
        numer.setText(Snumer);

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

