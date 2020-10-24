package com.example.aplikacjakurierska.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Login;
import com.example.aplikacjakurierska.user.DodajPrzeylke;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {


    Button bWyloguj,bDodaj,bDodajMagazyn,bSprawdzMagazyn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bDodaj=findViewById(R.id.bADodajMen);
        bDodajMagazyn=findViewById(R.id.bADodajMagazyn);
        bSprawdzMagazyn=findViewById(R.id.bASprawdzMag);

        bDodajMagazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DodajMagazyn.class));
            }
        });

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DodawanieManagera.class));
            }
        });


        bSprawdzMagazyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SprawdzMagazyn.class));
            }
        });


        bWyloguj=findViewById(R.id.bAWyloguj);

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