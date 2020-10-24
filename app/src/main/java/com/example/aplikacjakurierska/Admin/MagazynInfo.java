package com.example.aplikacjakurierska.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MagazynInfo extends AppCompatActivity {

    TextView tvMiasto,tvUlica,tvNumer;
    Button busun,bcofnij;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazyn_info);

        fStore=FirebaseFirestore.getInstance();
        tvMiasto=findViewById(R.id.tvMagazynMiasto);
        tvUlica=findViewById(R.id.tvMagazynUlica);
        tvNumer=findViewById(R.id.tvMagazynNumer);

        busun=findViewById(R.id.bMagazynInfoUsun);
        bcofnij=findViewById(R.id.bMagazynInfoCofnij);

        tvMiasto.setText(getIntent().getStringExtra("Miasto"));
        tvUlica.setText(getIntent().getStringExtra("Ulica"));
        tvNumer.setText(getIntent().getStringExtra("Id"));


        bcofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
            }
        });

        busun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder dialog= new AlertDialog.Builder(MagazynInfo.this);
                dialog.setTitle(R.string.rmPack);
                dialog.setMessage(R.string.AreUsus);
                dialog.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DocumentReference docfer=fStore.collection("Magazyn").document(getIntent().getStringExtra("Id"));
                        docfer.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),R.string.MagDelete,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), SprawdzMagazyn.class);
                                startActivity(intent);
                            }
                        });

                    }
                });
                dialog.setNegativeButton(R.string.Anuluj, null);
                dialog.create().show();




            }
        });


    }
}