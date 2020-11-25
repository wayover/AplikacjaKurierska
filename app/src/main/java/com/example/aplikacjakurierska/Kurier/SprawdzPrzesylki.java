package com.example.aplikacjakurierska.Kurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aplikacjakurierska.Admin.AdminActivity;
import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
import com.example.aplikacjakurierska.Admin.MagazynInfo;
import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.Manager.PackInfo;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SprawdzPrzesylki extends AppCompatActivity {


    ListView lvMagazyny;
    Button bCofnij;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    String mail;
    ArrayList<MagazynClass> MagazynList;
    ArrayList<String>infolist;
    ArrayList<MagazynClass> magazyny = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprawdz_przesylki);
        lvMagazyny=findViewById(R.id.lvAdminSprawdzMagazyn);
        bCofnij=findViewById(R.id.bAdminSprawdzCofnij);
        infolist=new ArrayList<>();
        MagazynList=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        String id=mAuth.getUid();


        fStore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                mail=(String)task.getResult().get("Email");



                fStore.collection("Magazyn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot document :task.getResult()) {

                            String miasto = (String) document.get("Miasto");
                            String ulica= (String) document.get("Ulica");
                            String numer= (String) document.get("Numer");
                            String id = document.getId();
                            MagazynClass pck = new MagazynClass(id, miasto, ulica, numer);
                            MagazynList.add(pck);
                        }



                        fStore.collection("paczki").whereEqualTo("MailKuriera",mail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot document :task.getResult()){

                                    for(int i=0;i<MagazynList.size();i++){
                                        if(document.get("IdMagazynu").toString().equals(MagazynList.get(i).getId())){

                                            int temp=0;
                                            for (MagazynClass m : magazyny) {
                                                if(m.getId().equals((MagazynList.get(i).getId()))){
                                                    //
                                                }else {
                                                    temp++;
                                                }
                                            }
                                            if(temp==magazyny.size()) {
                                                magazyny.add(MagazynList.get(i));
                                            }


                                        }
                                    }

                                }


                                for (MagazynClass i : magazyny) {
                                    infolist.add(i.getMiasto()+" "+i.getUlica()+" "+i.getNumer());
                                }
                                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,infolist);
                                lvMagazyny.setAdapter(arrayAdapter);
                            }
                        });


                    }
                });

            }
        });


        lvMagazyny.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SprawdzPrzesylkiInfo.class);
                intent.putExtra("id",magazyny.get(position).getId());
                intent.putExtra("mail",mail);
                startActivity(intent);
            }
        });




        bCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KurierActivity.class));
            }
        });
    }
}

