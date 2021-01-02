package com.example.aplikacjakurierska.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserPrzypisaneInfo extends AppCompatActivity {

    String mail,id;
    Button cofnij,dostarczona,niedostarczona;
    TextView czas,telefon;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_przypisane_info);

        mail=getIntent().getStringExtra("mail");
        id=getIntent().getStringExtra("id");

        fStore = FirebaseFirestore.getInstance();

        cofnij=findViewById(R.id.bUserPrzypisaneCofnij);
        dostarczona=findViewById(R.id.bUserPrzypisaneDostarczona);
        niedostarczona=findViewById(R.id.bUserPrzypisaneNiedotarla);
        czas=findViewById(R.id.tvUserPrzypisaneCzas);
        telefon=findViewById(R.id.tvUserPrzypisaneTelefonKuriera);


        fStore.collection("users").whereEqualTo("Email", mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    telefon.setText((String)document.get("Telefon"));
                }
            }
        });

        fStore.collection("paczki").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().get("Dostarczona").toString().equals("Odebrane")){
                    dostarczona.setVisibility(View.VISIBLE);
                    niedostarczona.setVisibility(View.VISIBLE);
                }

                if(task.getResult().get("PrzewidywanyCzas")==null){
                    czas.setText("Brak");
                }else{
                    czas.setText((String)task.getResult().get("PrzewidywanyCzas"));
                }
            }
        });

        telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: "+telefon.getText()));
                startActivity(intent);
            }
        });

        //TODO
        dostarczona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //TODO
        niedostarczona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}