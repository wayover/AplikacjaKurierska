package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.DodawanieManagera;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class OdbierzPaczki extends AppCompatActivity {

    Button Odbierz,Nawiguj,Cofnij;
    String IdMagazynu,miasto,ulica,numer;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odbierz_paczki);


        fStore=FirebaseFirestore.getInstance();
        IdMagazynu=getIntent().getStringExtra("idMagazynu");
        Odbierz=findViewById(R.id.bKurierOdierzPaczki);
        Nawiguj=findViewById(R.id.bKurierNagigujDoMagazynu);
        Cofnij=findViewById(R.id.bKurierMagCofnij);

        fStore.collection("Magazyn").document(IdMagazynu).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                miasto = (String) task.getResult().get("Miasto");
                ulica = (String) task.getResult().get("Ulica");
                numer = (String) task.getResult().get("Numer");
            }
        });




        Odbierz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("paczki").whereEqualTo("IdMagazynu", IdMagazynu).whereEqualTo("Odebrane", "0").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference docfer = fStore.collection("paczki").document(document.getId());
                                Map<String, Object> map = new HashMap<>();
                                map.put("Odebrane", "1");

                                docfer.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Odebrano", Toast.LENGTH_LONG).show();
//
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//
                                    }
                                });
                        }
                        Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
                        startActivity(intent);

                    }
                });
            }
        });


                Nawiguj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + miasto + " " + ulica + " " + numer);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SprawdzPrzesylki.class);
                startActivity(intent);
            }
        });
    }
}