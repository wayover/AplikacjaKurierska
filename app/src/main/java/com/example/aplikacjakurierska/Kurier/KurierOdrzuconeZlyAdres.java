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

public class KurierOdrzuconeZlyAdres extends AppCompatActivity {


    Button cofnij;
    ListView lvLista;

    FirebaseFirestore fStore;
    FirebaseAuth mAuth;

    ArrayList<String> paczki;
    ArrayList<Paczka> packlist;

    String mail, Id, Imie, Kod, Nazwisko, Mail, Miasto, Nrdomu, NrMieszkania, Telefon, Ulica, IdKlienta, IdMagazyn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_odrzucone_zly_adres);

        paczki = new ArrayList<>();
        packlist = new ArrayList<>();

        cofnij=findViewById(R.id.bKurierOdrzuconeInfoCofnij);
        lvLista=findViewById(R.id.lvKurierOdrzuconeLista);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getUid();

        fStore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mail = (String) task.getResult().get("Email");

                fStore.collection("paczki").whereEqualTo("MailKuriera", mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String od = (String) document.get("Dostarczona");
                            if (od.equals("BlednyAdres") || od.equals("Odrzucona")) {
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
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, paczki);
                        lvLista.setAdapter(arrayAdapter);
                    }
                });

            }
        });



        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), KurierOdrzuconeZlyAdresInfo.class);
                intent.putExtra("miasto", packlist.get(position).getMiasto());
                intent.putExtra("ulica", packlist.get(position).getUlica());
                intent.putExtra("nr", packlist.get(position).getNrdomu());
                intent.putExtra("id", packlist.get(position).getId());
                startActivity(intent);
            }
        });


        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
                startActivity(intent);
            }
        });

    }
}