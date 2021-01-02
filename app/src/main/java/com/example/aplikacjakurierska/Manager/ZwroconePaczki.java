package com.example.aplikacjakurierska.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ZwroconePaczki extends AppCompatActivity {

    ListView lvZwrocone;
    Button Cofnij;
    String Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn;

    FirebaseFirestore fStore;
    ArrayList<String> infolist;
    ArrayList<Paczka>packinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwrocone_paczki);

        Cofnij=findViewById(R.id.bManagerZwroconeCofnij);
        lvZwrocone=findViewById(R.id.lvManagerZwrocone);
        infolist=new ArrayList<>();
        packinfo=new ArrayList<>();
        fStore=FirebaseFirestore.getInstance();


        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(intent);
            }
        });


        fStore.collection("paczki").whereEqualTo("Dostarczona","ZwroconaDoMagazynu").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Id = document.getId();
                        Imie = (String) document.get("Imie");
                        Nazwisko = (String) document.get("Nazwisko");
                        Kod = (String) document.get("Kod");
                        Mail = (String) document.get("MailKuriera");
                        Miasto = (String) document.get("Miasto");
                        Nrdomu = (String) document.get("NrDomu");
                        NrMieszkania = (String) document.get("NrMieszkania");
                        Telefon = (String) document.get("Telefon");
                        Ulica = (String) document.get("Ulica");
                        IdKlienta = (String) document.get("IdKlienta");
                        IdMagazyn = (String) document.get("IdMagazynu");

                        Paczka pck = new Paczka(Id, Imie, Kod, Nazwisko, Mail, Miasto, Nrdomu, NrMieszkania, Telefon, Ulica, IdKlienta, IdMagazyn);
                        packinfo.add(pck);
                        infolist.add(Id+" ");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }

                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,infolist);
                lvZwrocone.setAdapter(arrayAdapter);

            }
        });



        lvZwrocone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ZwroconePaczkiInfo.class);
                intent.putExtra("id",packinfo.get(position).getId());
                startActivity(intent);
            }
        });




    }
}