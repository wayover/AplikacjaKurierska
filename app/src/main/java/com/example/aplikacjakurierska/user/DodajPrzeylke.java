package com.example.aplikacjakurierska.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.ui.home.HomeFragment;

public class DodajPrzeylke extends AppCompatActivity {

    Button bAnuluj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_przeylke);

        bAnuluj=findViewById(R.id.bAnuluj);

        bAnuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String text = "Hello toast!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(getContext(), text, duration);
//                toast.show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }
}