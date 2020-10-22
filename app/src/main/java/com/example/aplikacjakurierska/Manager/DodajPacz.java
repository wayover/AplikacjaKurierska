package com.example.aplikacjakurierska.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.UserInfo;
import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Login;
import com.example.aplikacjakurierska.user.Kontakt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DodajPacz extends AppCompatActivity {

    EditText etImie,etNazwisko,etTelefon,etMailKurier,etMiasto,etKod,etUlica,etNrDomu,etNrMieszkania;
    Button bDodaj;
    String Imie,Nazwisko,Telefon,MailKurier,Miasto,Kod,Ulica,NrDomu,NrMieszkania;
    FirebaseFirestore fStore;
    String temp;
    int i=0;
    TextView tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_pacz);

        fStore= FirebaseFirestore.getInstance();
        etImie=findViewById(R.id.etDodajImieKlienta);
        etNazwisko=findViewById(R.id.etDodajNazwiskoKlienta);
        etTelefon=findViewById(R.id.etDodajTelefonKlienta);
        etMailKurier=findViewById(R.id.etDodajMailKuriera);
        etMiasto=findViewById(R.id.etDodajMiasto);
        etKod=findViewById(R.id.etDodajKod);
        etUlica=findViewById(R.id.etDodajUlica);
        etNrDomu=findViewById(R.id.etDodajNrDomu);
        etNrMieszkania=findViewById(R.id.etDodajNrMieszkania);

        bDodaj=findViewById(R.id.bDodajDodaj);

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imie=etImie.getText().toString();
                Nazwisko=etNazwisko.getText().toString();
                Telefon =etTelefon.getText().toString();
                MailKurier=etMailKurier.getText().toString();
                Miasto=etMiasto.getText().toString();
                Kod=etKod.getText().toString();
                Ulica=etUlica.getText().toString();
                NrDomu=etNrDomu.getText().toString();
                NrMieszkania=etNrMieszkania.getText().toString();

                if(!Imie.isEmpty()&&!Nazwisko.isEmpty()&&!Telefon.isEmpty()&&!MailKurier.isEmpty()&&!Miasto.isEmpty()&&!Kod.isEmpty()&&!Ulica.isEmpty()&&!NrDomu.isEmpty()){



                    fStore.collection("paczki").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                temp=random();
                                while(task.getResult().size()!=i) {
                                    temp=random();
                                    i=0;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(temp.equals(document.getId().toString())){
                                            break;
                                        }else{
                                            i++;
                                        }

                                    }
                                }

                            }else{
                                Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    DocumentReference df=fStore.collection("paczki").document(random());
                    Map<String,Object> userInfo= new HashMap<>();
                    userInfo.put("Imie",Imie);
                    userInfo.put("Nazwisko",Nazwisko);
                    userInfo.put("Telefon",Telefon);
                    userInfo.put("MailKuriera",MailKurier);
                    userInfo.put("Miasto",Miasto);
                    userInfo.put("Kod",Kod);
                    userInfo.put("Ulica",Ulica);
                    userInfo.put("NrDomu",NrDomu);
                    if(NrMieszkania.isEmpty()) {
                        userInfo.put("NrMieszkania", "0");
                    }else{
                        userInfo.put("NrMieszkania", NrMieszkania);
                    }
                    userInfo.put("IdKlienta","Brak");
                    df.set(userInfo).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    Toast.makeText(getApplicationContext(), R.string.pckadd, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                    startActivity(intent);


                }else{
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static String random() {

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char tempChar;
        for (int i = 0; i < 20; i++){
            tempChar = (char) (generator.nextInt(25) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

}


