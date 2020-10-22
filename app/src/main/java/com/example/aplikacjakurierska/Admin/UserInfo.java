package com.example.aplikacjakurierska.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class UserInfo extends AppCompatActivity {

    TextView imie,nazwisko,mail,telefon,rola;
    Button bMana,bKurier,bUser,bCofnij;
    FirebaseFirestore db;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        reference = FirebaseDatabase.getInstance().getReference("users");

        db = FirebaseFirestore.getInstance();

        mail=findViewById(R.id.tvInfoMail);
        imie=findViewById(R.id.tvInfoImie);
        nazwisko=findViewById(R.id.tvInfoNazwisko);
        telefon=findViewById(R.id.tvInfoTelefon);
        rola=findViewById(R.id.tvInfoRola);
        bMana=findViewById(R.id.bInfoMan);
        bKurier=findViewById(R.id.bInfomKurier);
        bUser=findViewById(R.id.bInfoUser);
        bCofnij=findViewById(R.id.bInfoCofnij);

        bCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DodawanieManagera.class);
                startActivity(intent);
            }
        });

        mail.setText(getIntent().getStringExtra("mail"));
        imie.setText(getIntent().getStringExtra("imie"));
        nazwisko.setText(getIntent().getStringExtra("nazwisko"));
        rola.setText(getIntent().getStringExtra("rola"));
        if(rola.getText().equals("0")){//user
            bMana.setVisibility(View.VISIBLE);
            bKurier.setVisibility(View.VISIBLE);
        }else if(rola.getText().equals("1")){//admin
            bMana.setVisibility(View.VISIBLE);
            bKurier.setVisibility(View.VISIBLE);
            bUser.setVisibility(View.VISIBLE);
        }else if(rola.getText().equals("2")){//courier
            bMana.setVisibility(View.VISIBLE);
            bUser.setVisibility(View.VISIBLE);
        }else if(rola.getText().equals("3")){//managment
            bKurier.setVisibility(View.VISIBLE);
            bUser.setVisibility(View.VISIBLE);
        }
        telefon.setText(getIntent().getStringExtra("telefon"));

        bUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document :task.getResult()){
                                if(document.get("Email").equals(mail.getText().toString())){

                                    DocumentReference docfer=db.collection("users").document(document.getId());
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("Role","0");

                                    docfer.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_LONG).show();
//
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//
                                        }
                                    });


                                    //0-user 1-admin 2- courier 3-managment

                                    Intent intent = new Intent(getApplicationContext(), DodawanieManagera.class);
                                    startActivity(intent);
                                }

                            }
                        }

                    }
                });
            }
        });

        bKurier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document :task.getResult()){
                                if(document.get("Email").equals(mail.getText().toString())){

                                    DocumentReference docfer=db.collection("users").document(document.getId());
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("Role","2");

                                    docfer.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_LONG).show();
//
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//
                                        }
                                    });


                                    //0-user 1-admin 2- courier 3-managment

                                    Intent intent = new Intent(getApplicationContext(), DodawanieManagera.class);
                                    startActivity(intent);
                                }

                            }
                        }

                    }
                });
            }
        });


        bMana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document :task.getResult()){
                                if(document.get("Email").equals(mail.getText().toString())){

                                    DocumentReference docfer=db.collection("users").document(document.getId());
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("Role","3");

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


                                    //0-user 1-admin 2- courier 3-managment

                                    Intent intent = new Intent(getApplicationContext(), DodawanieManagera.class);
                                    startActivity(intent);
                                }

                            }
                        }

                    }
                });
            }
        });

    }
}