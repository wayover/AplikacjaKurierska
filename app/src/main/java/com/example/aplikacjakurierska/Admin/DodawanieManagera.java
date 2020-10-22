package com.example.aplikacjakurierska.Admin;

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

import com.example.aplikacjakurierska.Admin.user.UserClass;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class DodawanieManagera extends AppCompatActivity {

    ListView lvUserList;
    FirebaseFirestore fStore;
    ArrayList<String>userlist;
    ArrayList<UserClass>infolist;
    String mail,imie,nazwisko,rola,telefon;
    EditText Znajdzmail;
    Button znajdz,cofnij;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_managera);

        Znajdzmail=findViewById(R.id.etInfoFind);
        znajdz=findViewById(R.id.bInfoZnajdz);
        cofnij=findViewById(R.id.bDodajCofnij);
        lvUserList=findViewById(R.id.lvUserList);
        fStore=FirebaseFirestore.getInstance();
        userlist=new ArrayList<>();
        infolist=new ArrayList<>();

        //DocumentReference df=fStore.collection("users").document();

        fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                      //  lista.append(document.getId()+ " => " + document.getData() + "\n");
                        mail= (String) document.get("Email");
                        imie= (String) document.get("Imie");
                        nazwisko= (String) document.get("Nazwisko");
                        rola= (String) document.get("Role");

                        telefon= (String) document.get("Telefon");
                        UserClass user=new UserClass(mail,imie,nazwisko,rola,telefon);
                        infolist.add(user);

                        if(document.get("Role").equals("0")){
                            userlist.add(mail+"   Rola: user");
                        }else if(document.get("Role").equals("1")){
                            userlist.add(mail+"   Rola: admin");
                        }else if(document.get("Role").equals("2")){
                            userlist.add(mail+"   Rola: kurier");
                        }else if(document.get("Role").equals("3")){
                            userlist.add(mail+"   Rola: manager");
                        }


                    }
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }


                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,userlist);
                lvUserList.setAdapter(arrayAdapter);

                lvUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                        intent.putExtra("mail",infolist.get(position).getEmail());
                        intent.putExtra("imie",infolist.get(position).getImie());
                        intent.putExtra("nazwisko",infolist.get(position).getNazwisko());
                        intent.putExtra("rola",infolist.get(position).getRole());
                        intent.putExtra("telefon",infolist.get(position).getTelefon());
                        startActivity(intent);
                    }
                });

            }
        });



        znajdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j=0;
                for(int i=0;i<infolist.size();i++){
                    if(infolist.get(i).getEmail().equals(Znajdzmail.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                        intent.putExtra("mail",infolist.get(i).getEmail());
                        intent.putExtra("imie",infolist.get(i).getImie());
                        intent.putExtra("nazwisko",infolist.get(i).getNazwisko());
                        intent.putExtra("rola",infolist.get(i).getRole());
                        intent.putExtra("telefon",infolist.get(i).getTelefon());
                        startActivity(intent);
                    }else{
                        j++;
                    }
                }

                if(infolist.size()==j){
                    Toast.makeText(getApplicationContext(),R.string.idNFind,Toast.LENGTH_LONG).show();
                }
            }
        });


        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });



    }
}