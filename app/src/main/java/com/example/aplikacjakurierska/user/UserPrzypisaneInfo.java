package com.example.aplikacjakurierska.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Kurier.OdebranePaczki;
import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.ui.PaczkiPrzypisane.PrzypisaneFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserPrzypisaneInfo extends AppCompatActivity {

    String mail,id,IdPotwierdzenia,CzyPotwierdzone;
    Number Rok,Miesiac,Dzien,Godzina,AktRok,AktMiesiac,AktDzien,AktGodzina;
    Long IRok,IMiesiac,IDzien,IGodzina,IAktRok,IAktMiesiac,IAktDzien,IAktGodzina;
    Button cofnij,dostarczona,niedostarczona;
    TextView czas,telefon,GodzinaInfo,GodzinaDostarczenia,NiedostarczoneInfo,Infolinia;
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
        GodzinaInfo=findViewById(R.id.tvGodzinaDostarczenia);
        GodzinaDostarczenia=findViewById(R.id.tvUserPrzypisaneGodzianDostarczenia);
        NiedostarczoneInfo=findViewById(R.id.tvUserPrzypisaneKontakt);
        Infolinia=findViewById(R.id.tvInfoInfolinia);


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


                if(task.getResult().get("PrzewidywanyCzas")==null){
                    czas.setText("Brak");
                }else{
                    czas.setText((String)task.getResult().get("PrzewidywanyCzas"));
                }
            }
        });

        fStore.collection("Dostarczona").whereEqualTo("IdPaczki",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot document :task.getResult()) {
                    Rok = (Number) document.get("Rok");
                    Miesiac = (Number)document.get("Miesiac");
                    Dzien = (Number)document.get("Dzien");
                    Godzina = (Number)document.get("Godzina");
                    IdPotwierdzenia =  document.getId();
                    CzyPotwierdzone=(String)document.get("Potwierdzone");
                    GodzinaDostarczenia.setText(Rok+"/"+Miesiac+"/"+Dzien+" "+Godzina);

                    AktDzien=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    AktMiesiac=Calendar.getInstance().get(Calendar.MONTH)+1;
                    AktRok=Calendar.getInstance().get(Calendar.YEAR);
                    AktGodzina=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);


                    IRok=Rok.longValue();
                    IMiesiac=Miesiac.longValue();
                    IDzien=Dzien.longValue();
                    IGodzina=Godzina.longValue();
                    IAktRok=AktRok.longValue();
                    IAktMiesiac=AktMiesiac.longValue();
                    IAktDzien=AktDzien.longValue();
                    IAktGodzina=AktGodzina.longValue();


                    GodzinaInfo.setVisibility(View.VISIBLE);
                    GodzinaDostarczenia.setVisibility(View.VISIBLE);


                    if(CzyPotwierdzone.equals("2")) {
                        NiedostarczoneInfo.setVisibility(View.VISIBLE);
                        Infolinia.setVisibility(View.VISIBLE);
                    }

                    if(IAktRok+IRok==IRok*2&&IAktMiesiac==IMiesiac&&IAktDzien==IDzien&&IAktGodzina-IGodzina<24){
                        if(CzyPotwierdzone.equals("0")) {
                            dostarczona.setVisibility(View.VISIBLE);
                            niedostarczona.setVisibility(View.VISIBLE);
                        }
                    }else if(IAktRok+IRok==IRok*2&&IAktMiesiac==IMiesiac&&IAktDzien-IDzien==1&&IGodzina-IAktGodzina<24){
                        if(CzyPotwierdzone.equals("0")) {
                            dostarczona.setVisibility(View.VISIBLE);
                            niedostarczona.setVisibility(View.VISIBLE);
                        }
                    }else if(IAktRok+IRok==IRok*2&&IAktMiesiac-IMiesiac==1&&IDzien-IAktDzien>26&&IGodzina-IAktGodzina<24){//do sprawdzenia
                        if(CzyPotwierdzone.equals("0")) {
                            dostarczona.setVisibility(View.VISIBLE);
                            niedostarczona.setVisibility(View.VISIBLE);
                        }
                    }else if(IAktRok+IRok==IRok*2+1&&IMiesiac-IAktMiesiac==11&&IDzien-IAktDzien==30&&IGodzina-IAktGodzina<24){//do sprawdzenia
                        if(CzyPotwierdzone.equals("0")) {
                            dostarczona.setVisibility(View.VISIBLE);
                            niedostarczona.setVisibility(View.VISIBLE);
                        }
                    }




                }
            }
        });



        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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


        dostarczona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DocumentReference docfer=fStore.collection("Dostarczona").document(IdPotwierdzenia);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Potwierdzone","1");

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
                        Intent intent = new Intent(getApplicationContext(), PrzypisaneFragment.class);
                        startActivity(intent);
                    }
                });
            }
        });


        niedostarczona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DocumentReference docfer=fStore.collection("Dostarczona").document(IdPotwierdzenia);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Potwierdzone","2");

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

                        niedostarczona.setVisibility(View.GONE);
                        dostarczona.setVisibility(View.GONE);
                        NiedostarczoneInfo.setVisibility(View.VISIBLE);
                        Infolinia.setVisibility(View.VISIBLE);

                    }
                });


            }
        });

        Infolinia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: "+telefon.getText()));
                startActivity(intent);
            }
        });


    }
}