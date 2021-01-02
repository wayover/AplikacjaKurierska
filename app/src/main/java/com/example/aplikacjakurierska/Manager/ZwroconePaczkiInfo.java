package com.example.aplikacjakurierska.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.Kurier.OdebranePaczki;
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

public class ZwroconePaczkiInfo extends AppCompatActivity {


    Button cofnij,Zwroc;
    String Id;
    String Imie,Nazwisko,Telefon,Miasto,Ulica,NrDomu,NrMieszkania;
    EditText etImie,etNazwisko,etUlica,etNumerDomu,etNumerMieszkania,etMiasto,etNumerTelefonu;
    Spinner sKurier;
    ArrayList<UserClass> listaKurier;
    ArrayList<String>KurierList;
    FirebaseFirestore fStore;
    String iduser,mail,imie,nazwisko,rola,telefon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zwrocone_paczki_info);

        etImie=findViewById(R.id.etManagerZwroconeInfoImie);
        etNazwisko=findViewById(R.id.etManagerZwroconeInfoNazwisko);
        etUlica=findViewById(R.id.etManagerZwroconeInfoUlica);
        etNumerDomu=findViewById(R.id.etManagerZwroconeInfoNrDomu);
        etNumerMieszkania=findViewById(R.id.etManagerZwroconeInfoNrMieszkani);
        etMiasto=findViewById(R.id.etManagerZwroconeInfoMiasto);
        etNumerTelefonu=findViewById(R.id.etManagerZwroconeInfoTelefon);
        sKurier=findViewById(R.id.sManagerZwroconeInfoKurierzy);
        cofnij=findViewById(R.id.bManagerZwroconeInfoCofnij);
        Zwroc=findViewById(R.id.bManagerZwroconeInfoZwroc);

        listaKurier=new ArrayList<>();
        KurierList=new ArrayList<>();
        fStore= FirebaseFirestore.getInstance();


        Id=getIntent().getStringExtra("id");

        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ZwroconePaczki.class);
                startActivity(intent);
            }
        });




        KurierList.add("Wybierz kuriera");
        fStore.collection("users").whereEqualTo("Role","2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                        mail=(String)document.get("Email");
                        imie=(String)document.get("Imie");
                        nazwisko=(String)document.get("Nazwisko");
                        rola=(String)document.get("Role");
                        telefon=(String)document.get("Telefon");
                        iduser=document.getId();
                        listaKurier.add(new UserClass(iduser,mail,imie,nazwisko,rola,telefon));
                        KurierList.add(imie+"  "+nazwisko+"  "+mail);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }


            }
        });


        ArrayAdapter adapterKurier=new ArrayAdapter(this,android.R.layout.simple_spinner_item, KurierList);
        sKurier.setAdapter(adapterKurier);





        Zwroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Imie=etImie.getText().toString();
                Nazwisko=etNazwisko.getText().toString();
                Telefon =etNumerTelefonu.getText().toString();
                Miasto=etMiasto.getText().toString();
                Ulica=etUlica.getText().toString();
                NrDomu=etNumerDomu.getText().toString();
                NrMieszkania=etNumerMieszkania.getText().toString();

                final int b=sKurier.getSelectedItemPosition()-1;


                if(!Imie.isEmpty()&&!Nazwisko.isEmpty()&&!Telefon.isEmpty()&&!Miasto.isEmpty()&&!Ulica.isEmpty()&&!NrDomu.isEmpty()&&b!=-1) {


                    final DocumentReference docfer = fStore.collection("paczki").document(Id);
                    docfer.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("Dostarczona", "0");
                            map.put("ZwrotDoMagazynu", "0");
                            map.put("Miasto", Miasto);
                            map.put("Ulica", Ulica);
                            map.put("Imie", Imie);
                            map.put("Nazwisko", Nazwisko);
                            map.put("Telefon", Telefon);
                            map.put("NrDomu", NrDomu);
                            map.put("NrMieszkania", NrMieszkania);
                            map.put("Odebrane", "0");
                            map.put("MailKuriera",listaKurier.get(b).getEmail());



                            docfer.update(map).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    });
                    Toast.makeText(getApplicationContext(), "Pomyślnie nadano paczke", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }



            }
        });


    }
}