package com.example.aplikacjakurierska.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikacjakurierska.Manager.ManagerActivity;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DodajMagazyn extends AppCompatActivity {

    FirebaseFirestore fStore;
    Button Dodaj,Cofnij;
    EditText miasto,ulica,numer;
    String SMiasto,SUlica,SNumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_magazyn);

        Dodaj=findViewById(R.id.bMagazynDodaj);
        Cofnij=findViewById(R.id.bMagazynCofnij);
        fStore=FirebaseFirestore.getInstance();

        miasto=findViewById(R.id.etMagazynMiasto);
        ulica=findViewById(R.id.etMagazynUlica);
        numer=findViewById(R.id.etMagazynNumer);




        Dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SMiasto=miasto.getText().toString();
                SUlica=ulica.getText().toString();
                SNumer=numer.getText().toString();

                if(!SMiasto.isEmpty()&&!SUlica.isEmpty()&&!SNumer.isEmpty()){

                    DocumentReference df=fStore.collection("Magazyn").document();
                    Map<String,Object> MagazynInfo= new HashMap<>();
                    MagazynInfo.put("Miasto",SMiasto);
                    MagazynInfo.put("Ulica",SUlica);
                    MagazynInfo.put("Numer",SNumer);


                    df.set(MagazynInfo).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(getApplicationContext(), R.string.MagAdd, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }
            }
        });

        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
            }
        });
    }
}