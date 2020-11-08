package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
import com.example.aplikacjakurierska.Manager.PackInfo;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SprawdzPrzesylkiInfo extends AppCompatActivity {


    ListView lvPrzesylkiInfo;
    Button bCofnij,bNawiguj;
    FirebaseFirestore fStore;
    ArrayList<String> paczki;
    ArrayList<Paczka> packlist;
    String Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fStore=FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_sprawdz_przesylki_info);
        lvPrzesylkiInfo=findViewById(R.id.lvSprawdzprzesylkiInfo);
        bCofnij=findViewById(R.id.bSprawdzPrzesylkiInfoCofnij);
        bNawiguj=findViewById(R.id.bSprawdzNawiguj);
        paczki=new ArrayList<>();
        packlist= new ArrayList<>();
        String id=getIntent().getStringExtra("id");
        String mail=getIntent().getStringExtra("mail");

        fStore.collection("paczki").whereEqualTo("IdMagazynu",id).whereEqualTo("MailKuriera",mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override

            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Id=document.getId();
                    Imie=(String)document.get("Imie");
                    Nazwisko=(String)document.get("Nazwisko");
                    Kod=(String)document.get("Kod");
                    Mail=(String)document.get("MailKuriera");
                    Miasto =(String)document.get("Miasto");
                    Nrdomu=(String)document.get("NrDomu");
                    NrMieszkania=(String)document.get("NrMieszkania");
                    Telefon=(String)document.get("Telefon");
                    Ulica=(String)document.get("Ulica");
                    IdKlienta=(String)document.get("IdKlienta");
                    IdMagazyn=(String)document.get("IdMagazynu");

                    Paczka pck=new Paczka(Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn);
                    packlist.add(pck);
                    paczki.add(Miasto+" "+Ulica+" "+Nrdomu);
                }

                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,paczki);
                lvPrzesylkiInfo.setAdapter(arrayAdapter);
            }
        });


        bCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SprawdzPrzesylki.class);
                startActivity(intent);
            }
        });


    }
}