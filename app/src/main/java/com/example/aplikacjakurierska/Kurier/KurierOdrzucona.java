package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class KurierOdrzucona extends AppCompatActivity {


    Button cofnij,dodaj;
    EditText powod;
    FirebaseFirestore fStore;
    String id,tekst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_odrzucona);


        final String Smiasto=getIntent().getStringExtra("miasto");
        final String Sulica=getIntent().getStringExtra("ulica");
        final String Snumer=getIntent().getStringExtra("nr");
        final String Sid=getIntent().getStringExtra("id");
        id="null";
        fStore = FirebaseFirestore.getInstance();
        cofnij=findViewById(R.id.bOdrzuconaCofnij);
        dodaj=findViewById(R.id.bOdrzuconaDodaj);
        powod=findViewById(R.id.etOdrzuconaPowod);

        fStore.collection("Odrzucone").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot document :task.getResult()) {
                        if (Sid.equals(document.get("IdPaczka"))) {
                            id = document.getId();
                            tekst=(String)document.get("Powod");
                            powod.setText(tekst);
                        }
                    }
                }
            }

        });


        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierPackActivity.class);
                intent.putExtra("miasto", Smiasto);
                intent.putExtra("ulica", Sulica);
                intent.putExtra("nr", Snumer);
                intent.putExtra("id", Sid);
                startActivity(intent);
            }
        });




        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference df=null;
                if(id.equals("null")) {
                    df = fStore.collection("Odrzucone").document();
                }else {
                    df = fStore.collection("Odrzucone").document(id);
                }
                Map<String,Object> userInfo= new HashMap<>();
                userInfo.put("IdPaczka",Sid);
                userInfo.put("Powod",powod.getText().toString());

                df.set(userInfo).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                Toast.makeText(getApplicationContext(), "Powod dodany", Toast.LENGTH_LONG).show();


                final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Dostarczona","Odrzucona");//TODO

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
                    }
                });




                Intent intent = new Intent(getApplicationContext(), KurierPackActivity.class);
                intent.putExtra("miasto", Smiasto);
                intent.putExtra("ulica", Sulica);
                intent.putExtra("nr", Snumer);
                intent.putExtra("id", Sid);
                startActivity(intent);


            }
        });
    }
}