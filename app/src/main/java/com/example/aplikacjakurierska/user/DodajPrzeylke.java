package com.example.aplikacjakurierska.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Reset;
import com.example.aplikacjakurierska.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DodajPrzeylke extends AppCompatActivity {

    Button bAnuluj,bZnajdz;
    EditText etNr,etTele;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    Integer i;
    String telefon,nrPaczki,sTelefon,sPaczka,idKlien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_przeylke);

        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        bAnuluj=findViewById(R.id.bAnuluj);
        bZnajdz=findViewById(R.id.bDodajZnajdz);
        etTele=findViewById(R.id.etDodajTelefon);
        etNr=findViewById(R.id.etDodajNrPrzesylki);

        bAnuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });



        bZnajdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                fStore.collection("paczki").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        sTelefon=etTele.getText().toString();
                        sPaczka=etNr.getText().toString();

                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document :task.getResult()) {

                                nrPaczki = (String) document.getId();
                                telefon = (String) document.get("Telefon").toString();
                                idKlien = (String) document.get("IdKlienta").toString();

                                if (nrPaczki.equals(sPaczka) && telefon.equals(sTelefon)) {

                                    if (idKlien.equals("Brak")) {

                                        DocumentReference docfer = fStore.collection("paczki").document(sPaczka);
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("IdKlienta", mAuth.getUid());

                                        docfer.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        Toast.makeText(getApplicationContext(), R.string.pckadd, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Paczka została już przypisana do użytkownika ", Toast.LENGTH_LONG).show();
     
                                    }

                                }
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                        }

                        Toast.makeText(getApplicationContext(),R.string.PckDsFound,Toast.LENGTH_LONG).show();







                    }
                });
            }
        });
    }
}