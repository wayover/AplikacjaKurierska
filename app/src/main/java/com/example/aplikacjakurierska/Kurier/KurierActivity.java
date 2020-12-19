package com.example.aplikacjakurierska.Kurier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Login;
import com.google.firebase.auth.FirebaseAuth;

public class KurierActivity extends AppCompatActivity {


    Button bWyloguj,bSprawdzPrzesylki,bOczekujacePaczki,bNieodebrane,bOdrzuconeZlyAdres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_);

        bOczekujacePaczki=findViewById(R.id.bKurierSprawdzNieodebrane);
        bSprawdzPrzesylki=findViewById(R.id.bKurierSprawdzPrzesylki);
        bNieodebrane=findViewById(R.id.bKurierNieodebrane);
        bOdrzuconeZlyAdres=findViewById(R.id.bKurierOdrzuconeZlyAdres);


        bNieodebrane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierNieodebrane.class);
                startActivity(intent);
            }
        });

        //todo
        bOdrzuconeZlyAdres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        bOczekujacePaczki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SprawdzPrzesylki.class);
                startActivity(intent);
            }
        });
        bWyloguj=findViewById(R.id.bKWyloguj);


        bSprawdzPrzesylki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                startActivity(intent);
            }
        });

        bWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), R.string.wylogowany, duration);
                toast.show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}