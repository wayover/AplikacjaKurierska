package com.example.aplikacjakurierska.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Login;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerActivity extends AppCompatActivity {

    Button bWyloguj,bDodajPacz,bSprawdz,bZwrocone,bDostarczone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        bWyloguj=findViewById(R.id.bMWyloguj);
        bDodajPacz=findViewById(R.id.bManDodajPaczke);
        bSprawdz=findViewById(R.id.bManaSprawdz);
        bZwrocone=findViewById(R.id.bManaZwrocona);
        bDostarczone=findViewById(R.id.bManaDostarczone);

        bDostarczone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManagerDostarczonePaczki.class);
                startActivity(intent);
            }
        });


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


                Toast toast = Toast.makeText(getApplicationContext(), R.string.wylogowany, Toast.LENGTH_SHORT);
                toast.show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        bZwrocone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZwroconePaczki.class);
                startActivity(intent);
            }
        });


    }

}