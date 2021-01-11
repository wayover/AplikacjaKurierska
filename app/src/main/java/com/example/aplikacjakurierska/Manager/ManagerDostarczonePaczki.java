package com.example.aplikacjakurierska.Manager;

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

import com.example.aplikacjakurierska.Manager.Dostarczone.Dostarczone;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ManagerDostarczonePaczki extends AppCompatActivity {

    Button cofnij;
    Number Rok,Miesiac,Dzien,Godzina;
    String Id,IdPaczki,Potwierdzone,Numer;
    ListView lvDostarczone;
    FirebaseFirestore fStore;
    ArrayList<String> infolist;
    ArrayList<Dostarczone>packinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dostarczone_paczki);


        cofnij=findViewById(R.id.bManaDostarczoneCofnij);
        lvDostarczone=findViewById(R.id.lvManaDostarczone);
        infolist=new ArrayList<>();
        packinfo=new ArrayList<>();
        fStore=FirebaseFirestore.getInstance();


        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ManagerActivity.class));
            }
        });



        fStore.collection("Dostarczona").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Rok = (Number) document.get("Rok");
                        Miesiac = (Number)document.get("Miesiac");
                        Dzien = (Number)document.get("Dzien");
                        Godzina = (Number)document.get("Godzina");
                        Id =  document.getId();
                        IdPaczki=(String)document.get("IdPaczki");
                        Potwierdzone=(String)document.get("Potwierdzone");
                        Numer=(String)document.get("NumerKlienta");

                        Dostarczone dos = new Dostarczone(Id,Rok,Miesiac,Dzien,Godzina,IdPaczki,Potwierdzone,Numer);
                        packinfo.add(dos);
                        if(Potwierdzone.equals("1")) {
                            infolist.add(Id + "\n"+Rok+"/"+Miesiac+"/"+Dzien+" "+Godzina+"-"+R.string.Confirmed);
                        }else if(Potwierdzone.equals("2")){
                            infolist.add(Id + "\n"+Rok+"/"+Miesiac+"/"+Dzien+" "+Godzina+"-"+R.string.Reportednondelivery);
                        }else{
                            infolist.add(Id + "\n"+Rok+"/"+Miesiac+"/"+Dzien+" "+Godzina);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }

                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.row,infolist);
                lvDostarczone.setAdapter(arrayAdapter);

            }
        });



        lvDostarczone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Number rok=packinfo.get(position).getRok();
                Number Mies=packinfo.get(position).getMiesiac();
                Number Dzien=packinfo.get(position).getDzien();
                Number Godz=packinfo.get(position).getGodzina();





                Intent intent = new Intent(getApplicationContext(), ManagerDostarczonePaczkiInfo.class);
                intent.putExtra("id",packinfo.get(position).getId());
                intent.putExtra("rok",rok.toString());
                intent.putExtra("miesiac",Mies.toString());
                intent.putExtra("dzien",Dzien.toString());
                intent.putExtra("godzina",Godz.toString());
                intent.putExtra("potwierdzone",packinfo.get(position).getPotwierdzone());
                intent.putExtra("numer",packinfo.get(position).getNumerKlienta());
                intent.putExtra("IdPaczki",packinfo.get(position).getIdPaczki());
                startActivity(intent);
            }
        });


    }
}