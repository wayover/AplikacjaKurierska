package com.example.aplikacjakurierska.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.AdminActivity;
import com.example.aplikacjakurierska.Kurier.KurierActivity;
import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.Manager.ManagerActivity;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;

public class Login extends AppCompatActivity {

    EditText etHas,etLog;
    Button bZaloguj,bZarejestruj, bReset;
    ProgressBar pbLogin;
    FirebaseFirestore fStore;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        if (firebaseUser != null) {
            role(mAuth.getUid());
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(intent);
        }
        etHas=findViewById(R.id.etrHaslo);
        etLog=findViewById(R.id.etLogin);
        pbLogin=findViewById(R.id.pbLogin);


        bZarejestruj=findViewById(R.id.bZarejestruj);
        bZarejestruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Rejestracja.class);
                startActivity(intent);
            }
        });


        bZaloguj=findViewById(R.id.bZaloguj);
        bZaloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLogin.setVisibility(View.VISIBLE);
                String login=etLog.getText().toString();
                String haslo=etHas.getText().toString();

                if(!login.isEmpty()&&!haslo.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(login, haslo)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pbLogin.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()) {

                                        role(mAuth.getUid());

                                    } else {
                                        pbLogin.setVisibility(View.INVISIBLE);
                                         Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }else{
                    pbLogin.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }
            }
        });


        bReset=findViewById(R.id.bReset);
        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reset.class);
                startActivity(intent);
            }
        });

    }
    //0-user 1-admin 2- courier 3-managment
    private void role(String uid) {
        DocumentReference df=fStore.collection("users").document(uid);
        //extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+documentSnapshot.getData());



      //  TextView rola=findViewById(R.id.tvrola);
       // rola.setText((documentSnapshot.getString("Role")));
                //identify access level

                if(documentSnapshot.getString("Role").equals("1")){
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(getApplicationContext(), "Witamy admina", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }else if(documentSnapshot.getString("Role").equals("2")){
                    Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(getApplicationContext(), "Witamy Kuriera", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }else if(documentSnapshot.getString("Role").equals("3")){
                    Intent intent = new Intent(getApplicationContext(), ManagerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(getApplicationContext(), "Witamy Menegera", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(getApplicationContext(), "Witamy Użytkownika", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });

    }





}