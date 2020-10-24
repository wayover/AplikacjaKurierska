package com.example.aplikacjakurierska.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.Magazyn.MagazynClass;
import com.example.aplikacjakurierska.Manager.PackInfo;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SprawdzMagazyn extends AppCompatActivity {

    ListView lvMagazyn;
    Button bCofnij;
    FirebaseFirestore fStore;
    String miasto,ulica,numer,id;
    ArrayList<MagazynClass>magazyninfo;
    ArrayList<String>infolist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprawdz_magazyn);
        magazyninfo=new ArrayList<>();
        infolist=new ArrayList<>();
        fStore=FirebaseFirestore.getInstance();
        lvMagazyn=findViewById(R.id.lvMagazynSprawdz);
        bCofnij=findViewById(R.id.bMagazynCofnij);

        bCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
            }
        });




        fStore.collection("Magazyn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                        miasto=(String)document.get("Miasto");
                        ulica=(String)document.get("Ulica");
                        numer=(String)document.get("Numer");
                        id=document.getId();


                        MagazynClass pck=new MagazynClass(id,miasto,ulica,numer);
                        magazyninfo.add(pck);
                        infolist.add(miasto+" "+ulica+" "+numer);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,infolist);
                lvMagazyn.setAdapter(arrayAdapter);


                lvMagazyn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), MagazynInfo.class);
                        intent.putExtra("Id",magazyninfo.get(position).getId());
                        intent.putExtra("Miasto",magazyninfo.get(position).getMiasto());
                        intent.putExtra("Ulica",magazyninfo.get(position).getUlica());
                        intent.putExtra("Numer",magazyninfo.get(position).getNumer());
                        startActivity(intent);
                    }
                });


            }
        });


    }
}