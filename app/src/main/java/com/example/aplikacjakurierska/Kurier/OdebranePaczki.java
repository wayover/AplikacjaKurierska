package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OdebranePaczki extends AppCompatActivity {


    Button Cofnij,Trasa;
    ListView lvPaczki;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String mail,Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn;
    ArrayList<String> paczki;
    ArrayList<Paczka> packlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odebrane_paczki);

        Cofnij=findViewById(R.id.bOdebraneCofnij);
        Trasa=findViewById(R.id.bOdebraneNajTrasa);
        lvPaczki=findViewById(R.id.lvOdebrane);
        paczki=new ArrayList<>();
        packlist= new ArrayList<>();

        fStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        String id=mAuth.getUid();


        fStore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mail = (String) task.getResult().get("Email");

                fStore.collection("paczki").whereEqualTo("Odebrane", "1").whereEqualTo("MailKuriera", mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                            packlist.add(pck);
                            paczki.add(Miasto + " " + Ulica + " " + Nrdomu);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, paczki);
                        lvPaczki.setAdapter(arrayAdapter);
                    }
                });

            }
            });


        lvPaczki.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), KurierPackActivity.class);
                intent.putExtra("miasto",packlist.get(position).getMiasto());
                intent.putExtra("ulica",packlist.get(position).getUlica());
                intent.putExtra("nr",packlist.get(position).getNrdomu());
                startActivity(intent);

            }
        });




        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
                startActivity(intent);
            }
        });


        //TODO
        Trasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}