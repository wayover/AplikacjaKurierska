package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KurierNieodebraneInfo extends AppCompatActivity {

    Spinner sMagazyny;
    Button Cofnij,ZwrocDoMagaynu,DostarczPonownie;
    FirebaseFirestore fStore;
    ArrayList<MagazynClass> listaMagazyn;
    ArrayList<String>MagazynList;
    String miasto,ulica,numer,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_nieodebrane_info);

        fStore= FirebaseFirestore.getInstance();

        sMagazyny=findViewById(R.id.sKurierNieodebraneMagazyn);
        Cofnij=findViewById(R.id.bKurierNieodebraneInfoCofnij);
        ZwrocDoMagaynu=findViewById(R.id.bKurierNieodebraneZwrot);
        DostarczPonownie=findViewById(R.id.bKurierNieodebraneDostarcz);


        final String Sid=getIntent().getStringExtra("id");


        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
               startActivity(intent);
            }
        });


        MagazynList=new ArrayList<>();
        listaMagazyn=new ArrayList<>();
        MagazynList.add("Wybierz magazyn");
        fStore.collection("Magazyn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                        miasto=(String)document.get("Miasto");
                        ulica=(String)document.get("Ulica");
                        numer=(String)document.get("Numer");
                        id=document.getId();
                        listaMagazyn.add(new MagazynClass(id,miasto,ulica,numer));
                        MagazynList.add(miasto+" "+ulica+" "+numer);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }

            }
        });


        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,MagazynList);
        sMagazyny.setAdapter(adapter);


        DostarczPonownie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        Map<String,Object> map = new HashMap<>();
                        map.put("Dostarczona","0");

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
                        Intent intent = new Intent(getApplicationContext(), KurierNieodebrane.class);
                        startActivity(intent);
                    }
                });
            }
        });



        ZwrocDoMagaynu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int a=sMagazyny.getSelectedItemPosition()-1;

                if(a!=-1) {
                    final DocumentReference docfer = fStore.collection("paczki").document(Sid);
                    docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("Dostarczona", "0");
                            map.put("Miasto", listaMagazyn.get(a).getMiasto());
                            map.put("Ulica", listaMagazyn.get(a).getUlica());
                            map.put("NrDomu", listaMagazyn.get(a).getNumer());
                            map.put("IdMagazynu", listaMagazyn.get(a).getId());
                            map.put("IdKlienta","Brak");
                            map.put("ZwrotDoMagazynu", "1");
                            map.put("Telefon", "Brak");
                            map.put("Imie", "Brak");
                            map.put("Nazwisko", "Brak");


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
                            Intent intent = new Intent(getApplicationContext(), KurierNieodebrane.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Wybierz Magazyn",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}