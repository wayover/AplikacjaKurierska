package com.example.aplikacjakurierska.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {

    EditText etReset;
    Button bZresetuj,bCofnij;
    FirebaseAuth auth;
    ProgressBar pbReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        etReset=findViewById(R.id.etReset);
        bZresetuj=findViewById(R.id.bZresetujHaslo);
        pbReset=findViewById(R.id.pbReset);

        auth= FirebaseAuth.getInstance();
        bZresetuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbReset.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(etReset.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pbReset.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),R.string.PasSend,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        

        bCofnij=findViewById(R.id.bResCofnij);
        bCofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });




    }
}