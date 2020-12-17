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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
import com.example.aplikacjakurierska.Admin.MagazynInfo;
import com.example.aplikacjakurierska.Admin.UserInfo;
import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.Kurier.KurierActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DodajPacz extends AppCompatActivity {

    EditText etImie,etNazwisko,etTelefon,etMailKurier,etMiasto,etKod,etUlica,etNrDomu,etNrMieszkania;
    Button bDodaj;
    String Imie,Nazwisko,Telefon,Miasto,Kod,Ulica,NrDomu,NrMieszkania;
    FirebaseFirestore fStore;
    String temp;
    int i=0;
    String miasto,ulica,numer,id;
    String iduser,mail,imie,nazwisko,rola,telefon;
    Spinner sMagazyn,sMailKuriera;
    ArrayList<String>MagazynList,KurierList;
    ArrayList<MagazynClass>listaMagazyn;
    ArrayList<UserClass> listaKurier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_pacz);

        fStore= FirebaseFirestore.getInstance();
        etImie=findViewById(R.id.etDodajImieKlienta);
        etNazwisko=findViewById(R.id.etDodajNazwiskoKlienta);
        etTelefon=findViewById(R.id.etDodajTelefonKlienta);
        sMailKuriera=findViewById(R.id.sDodajMailKuriera);
        etMiasto=findViewById(R.id.etDodajMiasto);
        etKod=findViewById(R.id.etDodajKod);
        etUlica=findViewById(R.id.etDodajUlica);
        etNrDomu=findViewById(R.id.etDodajNrDomu);
        etNrMieszkania=findViewById(R.id.etDodajNrMieszkania);
        sMagazyn=findViewById(R.id.sDodajMagazyn);
        bDodaj=findViewById(R.id.bDodajDodaj);


        MagazynList=new ArrayList<>();
        KurierList=new ArrayList<>();
        listaKurier=new ArrayList<>();
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
        sMagazyn.setAdapter(adapter);






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
                        KurierList.add(mail+"");
                    }
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }


            }
        });


        ArrayAdapter adapterKurier=new ArrayAdapter(this,android.R.layout.simple_spinner_item, KurierList);
        sMailKuriera.setAdapter(adapterKurier);









        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imie=etImie.getText().toString();
                Nazwisko=etNazwisko.getText().toString();
                Telefon =etTelefon.getText().toString();
                Miasto=etMiasto.getText().toString();
                Kod=etKod.getText().toString();
                Ulica=etUlica.getText().toString();
                NrDomu=etNrDomu.getText().toString();
                NrMieszkania=etNrMieszkania.getText().toString();

                int a=sMagazyn.getSelectedItemPosition()-1;
                int b=sMailKuriera.getSelectedItemPosition()-1;
                


                if(!Imie.isEmpty()&&!Nazwisko.isEmpty()&&!Telefon.isEmpty()&&!Miasto.isEmpty()&&!Kod.isEmpty()&&!Ulica.isEmpty()&&!NrDomu.isEmpty()&&a!=-1&&b!=-1){



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
                    userInfo.put("Miasto",Miasto);
                    userInfo.put("Kod",Kod);
                    userInfo.put("Ulica",Ulica);
                    userInfo.put("NrDomu",NrDomu);
                    userInfo.put("Dostarczona","0");
                    if(NrMieszkania.isEmpty()) {
                        userInfo.put("NrMieszkania", "0");
                    }else{
                        userInfo.put("NrMieszkania", NrMieszkania);
                    }
                    userInfo.put("IdKlienta","Brak");


                    if(a==0){
                        Toast.makeText(getApplicationContext(), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Toast.LENGTH_LONG).show();
                    }
                    userInfo.put("IdMagazynu",listaMagazyn.get(a).getId());
                    df.set(userInfo).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                    userInfo.put("MailKuriera",listaKurier.get(b).getEmail());
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


