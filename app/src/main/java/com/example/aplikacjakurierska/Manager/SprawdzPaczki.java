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
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.DodawanieManagera;
import com.example.aplikacjakurierska.Admin.UserInfo;
import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SprawdzPaczki extends AppCompatActivity {

    ListView lvSprawdz;
    Button bCofnij,bZnajdz;
    FirebaseFirestore fStore;
    ArrayList<String>infolist;
    ArrayList<Paczka>packinfo;
    EditText etZnajdz;
    String Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprawdz_paczki);


        fStore=FirebaseFirestore.getInstance();
        lvSprawdz=findViewById(R.id.lvSprawdz);
        bCofnij=findViewById(R.id.bSprawdzCofnij);
        infolist=new ArrayList<>();
        packinfo=new ArrayList<>();
        bZnajdz=findViewById(R.id.bSprawdzZnajdz);
        etZnajdz=findViewById(R.id.etSprawdzFind);

        bZnajdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j=0;
                for(int i=0;i<packinfo.size();i++){
                    if(packinfo.get(i).getId().equals(etZnajdz.getText().toString())){

                        Intent intent = new Intent(getApplicationContext(), PackInfo.class);
                        intent.putExtra("id",packinfo.get(i).getId());
                        intent.putExtra("imie",packinfo.get(i).getImie());
                        intent.putExtra("nazwisko",packinfo.get(i).getNazwisko());
                        intent.putExtra("kod",packinfo.get(i).getKod());
                        intent.putExtra("mail",packinfo.get(i).getMail());
                        intent.putExtra("nrdomu",packinfo.get(i).getNrdomu());
                        intent.putExtra("nrmieszkania",packinfo.get(i).getNrMieszkania());
                        intent.putExtra("telefon",packinfo.get(i).getTelefon());
                        intent.putExtra("ulica",packinfo.get(i).getTelefon());
                        intent.putExtra("miasto",packinfo.get(i).getMiasto());
                        intent.putExtra("idKlienta",packinfo.get(i).getIdKlienta());
                        startActivity(intent);
                    }else{
                        j++;
                    }
                }

                if(packinfo.size()==j){
                    Toast.makeText(getApplicationContext(),R.string.idNFind,Toast.LENGTH_LONG).show();
                }
            }
        });


        fStore.collection("paczki").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                        Id=document.getId();
                        Imie=(String)document.get("Imie");
                        Nazwisko=(String)document.get("Nazwisko");
                        Kod=(String)document.get("Kod");
                        Mail=(String)document.get("MailKuriera");
                        Miasto =(String)document.get("Miasto");
                        Nrdomu=(String)document.get("NrDomu");
                        NrMieszkania=(String)document.get("NrMieszkania");
                        Telefon=(String)document.get("Telefon");
                        Ulica=(String)document.get("Ulica");
                        IdKlienta=(String)document.get("IdKlienta");
                        IdMagazyn=(String)document.get("IdMagazynu");

                        Paczka pck=new Paczka(Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazyn);
                        packinfo.add(pck);
                        infolist.add(Imie+" "+Nazwisko+"   "+Id);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.row,infolist);
                lvSprawdz.setAdapter(arrayAdapter);


                lvSprawdz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), PackInfo.class);
                        intent.putExtra("id",packinfo.get(position).getId());
                        intent.putExtra("imie",packinfo.get(position).getImie());
                        intent.putExtra("nazwisko",packinfo.get(position).getNazwisko());
                        intent.putExtra("kod",packinfo.get(position).getKod());
                        intent.putExtra("mail",packinfo.get(position).getMail());
                        intent.putExtra("nrdomu",packinfo.get(position).getNrdomu());
                        intent.putExtra("nrmieszkania",packinfo.get(position).getNrMieszkania());
                        intent.putExtra("telefon",packinfo.get(position).getTelefon());
                        intent.putExtra("ulica",packinfo.get(position).getTelefon());
                        intent.putExtra("miasto",packinfo.get(position).getMiasto());
                        intent.putExtra("idKlienta",packinfo.get(position).getIdKlienta());
                        intent.putExtra("idMagazynu",packinfo.get(position).getIdMagazynu());
                        startActivity(intent);
                    }
                });


            }
        });









        bCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                startActivity(intent);
            }
        });

    }
}