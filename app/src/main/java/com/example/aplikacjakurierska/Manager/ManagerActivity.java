package com.example.aplikacjakurierska.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Login;
import com.example.aplikacjakurierska.user.DodajPrzeylke;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerActivity extends AppCompatActivity {

    Button bWyloguj,bDodajPacz,bSprawdz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        bWyloguj=findViewById(R.id.bMWyloguj);
        bDodajPacz=findViewById(R.id.bManDodajPaczke);
        bSprawdz=findViewById(R.id.bManaSprawdz);
        bDodajPacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DodajPacz.class);
                startActivity(intent);

            }
        });


        bSprawdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SprawdzPaczki.class);
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