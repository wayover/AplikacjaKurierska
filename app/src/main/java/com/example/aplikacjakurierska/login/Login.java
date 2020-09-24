package com.example.aplikacjakurierska.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.Admin.AdminActivity;
import com.example.aplikacjakurierska.Kurier.KurierActivity;
import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Field;

public class Login extends AppCompatActivity {

    EditText etHas,etLog;
    Button bZaloguj,bZarejestruj;
    TextView tvlog,tvhas;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        etHas=findViewById(R.id.etrHaslo);
        etLog=findViewById(R.id.etLogin);

        tvlog=findViewById(R.id.tvlog);
        tvhas=findViewById(R.id.tvhas);


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

                String login=etLog.getText().toString();
                String haslo=etHas.getText().toString();
                tvhas.setText(haslo);
                tvlog.setText(login);
                if(!login.isEmpty()&&!haslo.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(login, haslo)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                         Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}