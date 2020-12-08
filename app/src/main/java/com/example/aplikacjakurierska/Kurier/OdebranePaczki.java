package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OdebranePaczki extends AppCompatActivity {


    class Data
    {
        public Paczka paczka1;
        public Paczka paczka2;
        public float  distance;

        Data(){}
    };

    class Pack{
        public Location loc;
        public Paczka paczka;
    }

    Button Cofnij,Trasa;
    ListView lvPaczki;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String mail,Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn;
    Double MyLat,MyLong;
    ArrayList<String> paczki;
    ArrayList<Paczka> packlist;
    ArrayList<Data> dystans;
    ArrayList<Pack> packs;
    TextView test;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odebrane_paczki);

        Cofnij=findViewById(R.id.bOdebraneCofnij);
        Trasa=findViewById(R.id.bOdebraneNajTrasa);
        lvPaczki=findViewById(R.id.lvOdebrane);
        paczki=new ArrayList<>();
        packlist= new ArrayList<>();
        dystans=new ArrayList<>();
        packs=new ArrayList<>();
        test=findViewById(R.id.tvTest);

//        if(ActivityCompat.checkSelfPermission(OdebranePaczki.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {




        fStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        String id=mAuth.getUid();


        fStore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mail = (String) task.getResult().get("Email");

                fStore.collection("paczki").whereEqualTo("Odebrane", "1").whereEqualTo("MailKuriera", mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Id = document.getId();
                            Imie = (String) document.get("Imie");
                            Nazwisko = (String) document.get("Nazwisko");
                            Kod = (String) document.get("Kod");
                            Mail = (String) document.get("MailKuriera");
                            Miasto = (String) document.get("Miasto");
                            Nrdomu = (String) document.get("NrDomu");
                            NrMieszkania = (String) document.get("NrMieszkania");
                            Telefon = (String) document.get("Telefon");
                            Ulica = (String) document.get("Ulica");
                            IdKlienta = (String) document.get("IdKlienta");
                            IdMagazyn = (String) document.get("IdMagazynu");

                            Paczka pck = new Paczka(Id, Imie, Kod, Nazwisko, Mail, Miasto, Nrdomu, NrMieszkania, Telefon, Ulica, IdKlienta, IdMagazyn);
                            packlist.add(pck);
                            paczki.add(Miasto + " " + Ulica + " " + Nrdomu);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, paczki);
                        lvPaczki.setAdapter(arrayAdapter);
                    }
                });

            }
            });



        lvPaczki.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), KurierPackActivity.class);
                intent.putExtra("miasto",packlist.get(position).getMiasto());
                intent.putExtra("ulica",packlist.get(position).getUlica());
                intent.putExtra("nr",packlist.get(position).getNrdomu());
                startActivity(intent);

            }
        });




        Cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
                startActivity(intent);
            }
        });



        Trasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    for(int i=0;i<packlist.size();i++){
                        Double Lat=null;
                        Double Long=null;
                        Pack pack=new Pack();
                        String loc = packlist.get(i).getMiasto() + " " + packlist.get(i).getUlica() + " " + packlist.get(1).getNrdomu();
                        Geocoder coder = new Geocoder(getApplicationContext());
                        List<Address> address;
                        try {
                            address = coder.getFromLocationName(loc, 5);
                            Address location = address.get(0);
                            Lat = location.getLatitude();
                            Long = location.getLongitude();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Location locc1 = new Location("");
                        locc1.setLatitude(Lat);
                        locc1.setLongitude(Long);
                        pack.paczka=packlist.get(i);
                        pack.loc=locc1;
                        packs.add(pack);
                    }

                    for(int i=0;i<packs.size();i++){
                        for(int j=packs.size()-1;j>i;j--){

                            Data data=new Data();
                            data.paczka1=packs.get(i).paczka;
                            data.paczka2=packs.get(j).paczka;
                            float distance = packs.get(i).loc.distanceTo(packs.get(j).loc);
                            data.distance=distance;
                            dystans.add(data);

                        }
                    }




//                for(int i=1;i<packlist.size();i++){
//                    Data data=new Data();
//                    data.paczka1=packlist.get(i);
//                    data.paczka2=packlist.get(i-1);
//
//                    String loc = packlist.get(i-1).getMiasto() + " " + packlist.get(i-1).getUlica() + " " + packlist.get(i-1).getNrdomu();
//                    Geocoder coder = new Geocoder(getApplicationContext());
//                    List<Address> address;
//                    try {
//                        address = coder.getFromLocationName(loc, 5);
//                        Address location = address.get(0);
//                         Lat = location.getLatitude();
//                        Long = location.getLongitude();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    Location locc1 = new Location("");
//                    locc1.setLatitude(Lat);
//                    locc1.setLongitude(Long);
//
//
//                    String loc2 = packlist.get(i).getMiasto() + " " + packlist.get(i).getUlica() + " " + packlist.get(i).getNrdomu();
//                    Geocoder coder2 = new Geocoder(getApplicationContext());
//                    List<Address> address2;
//                    try {
//                        address2 = coder2.getFromLocationName(loc2, 5);
//                        Address location = address2.get(0);
//                        Lat2 = location.getLatitude();
//                        Long2 = location.getLongitude();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    Location locc2 = new Location("");
//                    locc2.setLatitude(Lat2);
//                    locc2.setLongitude(Long2);
//
//                    float distance = locc1.distanceTo(locc2);
//                    data.distance=distance;
//                    dystans.add(data);
//                }

                String tmp="";
                for(int i=0;i<dystans.size();i++) {
                tmp+=dystans.get(i).paczka1.getMiasto()+" - "+dystans.get(i).paczka2.getMiasto()+" = "+dystans.get(i).distance+'\n';
               }

                test.setText(tmp);
            }
        });
    }


}