package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KurierPackActivity extends AppCompatActivity {

    TextView miasto,ulica,numer;
    Button Nawiguj,Odebrana,Nieodebrana,Czas,Cofnij,Odrzucona,BlednyAdres,Zwroc;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurier_pack);

        miasto = findViewById(R.id.tvKurierPackMiasto);
        ulica = findViewById(R.id.tvKurierPckUlica);
        numer = findViewById(R.id.tvKurierPckNumer);


        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        Czas=findViewById(R.id.bKuriterPackCzas);
        Nawiguj=findViewById(R.id.bKurierPackNavigate);
        Odebrana=findViewById(R.id.bKurierPackDostarczona);
        Nieodebrana=findViewById(R.id.bKurierPackBrakOdbiorcy);
        Cofnij=findViewById(R.id.bKurierPackCofnij);
        Odrzucona=findViewById(R.id.bKurierPackNieprzyjeta);
        BlednyAdres=findViewById(R.id.bKurierPackBledyAdres);
        Zwroc=findViewById(R.id.bKurierPackActivityZwroc);

        final String Smiasto=getIntent().getStringExtra("miasto");
        final String Sulica=getIntent().getStringExtra("ulica");
        final String Snumer=getIntent().getStringExtra("nr");
        final String Sid=getIntent().getStringExtra("id");
        final String SZwrot=getIntent().getStringExtra("zwrot");
        final String SNumer=getIntent().getStringExtra("numer");


        Odrzucona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierOdrzucona.class);
                intent.putExtra("miasto", Smiasto);
                intent.putExtra("ulica", Sulica);
                intent.putExtra("nr", Snumer);
                intent.putExtra("id", Sid);
                startActivity(intent);

                startActivity(intent);
            }
        });


        if(SZwrot.equals("1")){
            Odebrana.setVisibility(View.GONE);
            Nieodebrana.setVisibility(View.GONE);
            Czas.setVisibility(View.GONE);
            Odrzucona.setVisibility(View.GONE);
            BlednyAdres.setVisibility(View.GONE);
            Zwroc.setVisibility(View.VISIBLE);
        }

        Zwroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        Map<String,Object> map = new HashMap<>();
                        map.put("Dostarczona","ZwroconaDoMagazynu");

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

                        Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                        startActivity(intent);
                    }
                });


            }
        });


        BlednyAdres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        Map<String,Object> map = new HashMap<>();
                        map.put("Dostarczona","BlednyAdres");

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

                        Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                        startActivity(intent);
                    }
                });


            }
        });



        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                startActivity(intent);
            }
        });


        Nawiguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Smiasto+" "+Sulica+" "+Snumer);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        miasto.setText(Smiasto);
        ulica.setText(Sulica);
        numer.setText(Snumer);

numer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: "+numer.getText()));
        startActivity(intent);
    }
});


        Czas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(KurierPackActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {

                        final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                        docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Map<String,Object> map = new HashMap<>();
                                map.put("PrzewidywanyCzas",hourOfDay + ":" + minute);

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
                                Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                                startActivity(intent);
                            }
                        });

                    }
                }, 0, 0, false);


                timePickerDialog.show();
            }
        });

        Nawiguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Smiasto+" "+Sulica+" "+Snumer);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        Odebrana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        Map<String,Object> map = new HashMap<>();
                        map.put("Dostarczona","Odebrane");
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


                DocumentReference df=fStore.collection("Dostarczona").document();
                Map<String,Object> MagazynInfo= new HashMap<>();
                MagazynInfo.put("IdPaczki",Sid);
                MagazynInfo.put("Potwierdzone","0");
                MagazynInfo.put("Dzien",Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                MagazynInfo.put("Miesiac",Calendar.getInstance().get(Calendar.MONTH)+1);
                MagazynInfo.put("Rok",Calendar.getInstance().get(Calendar.YEAR));
                MagazynInfo.put("Godzina",Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                MagazynInfo.put("NumerKlienta",SNumer);


                df.set(MagazynInfo).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


                Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                startActivity(intent);

            }
        });


        Nieodebrana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DocumentReference docfer=fStore.collection("paczki").document(Sid);
                docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                        Map<String,Object> map = new HashMap<>();
                        map.put("Dostarczona","Nieodebrane");

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
                        Intent intent = new Intent(getApplicationContext(), OdebranePaczki.class);
                        startActivity(intent);
                    }
                });
            }
        });



    }


}

