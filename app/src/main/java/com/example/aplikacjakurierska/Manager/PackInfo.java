package com.example.aplikacjakurierska.Manager;

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

public class PackInfo extends AppCompatActivity {


    TextView Id,imie,nazwisko,kod,mail,miasto,telefon,ulica,nrdomu,nrmieszkania,idklienta,idmagazynu;
    Button cofnij,bUsun;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_info);

        cofnij= findViewById(R.id.bInfoCof);
        bUsun=findViewById(R.id.bInfoUsun);

        fStore=FirebaseFirestore.getInstance();
        Id=findViewById(R.id.tvInfoId);
        imie=findViewById(R.id.tvInfoImie);
        nazwisko=findViewById(R.id.tvInfoNazwisko);
        kod=findViewById(R.id.tvInfoKod);
        mail=findViewById(R.id.tvInfoMail);
        miasto=findViewById(R.id.tvInfoMiasto);
        telefon=findViewById(R.id.tvInfoTelefon);
        ulica=findViewById(R.id.tvInfoUlica);
        nrdomu=findViewById(R.id.tvInfoNrDomu);
        nrmieszkania=findViewById(R.id.tvInfoNrMieszkania);
        idklienta=findViewById(R.id.tvInfoIdKlienta);
        idmagazynu=findViewById(R.id.tvInfoIdMagazynu);

        Id.setText(getIntent().getStringExtra("id"));
        imie.setText(getIntent().getStringExtra("imie"));
        nazwisko.setText(getIntent().getStringExtra("nazwisko"));
        kod.setText(getIntent().getStringExtra("kod"));
        mail.setText(getIntent().getStringExtra("mail"));
        miasto.setText(getIntent().getStringExtra("miasto"));
        telefon.setText(getIntent().getStringExtra("telefon"));
        ulica.setText(getIntent().getStringExtra("ulica"));
        nrdomu.setText(getIntent().getStringExtra("nrdomu"));
        nrmieszkania.setText(getIntent().getStringExtra("nrmieszkania"));
        idklienta.setText(getIntent().getStringExtra("idKlienta"));
        idmagazynu.setText(getIntent().getStringExtra("idMagazynu"));



        bUsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        final AlertDialog.Builder dialog= new AlertDialog.Builder(PackInfo.this);
                        dialog.setTitle(R.string.rmPack);
                        dialog.setMessage(R.string.AreUsus);
                        dialog.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DocumentReference docfer=fStore.collection("paczki").document(getIntent().getStringExtra("id"));
                                docfer.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(),R.string.PackDelete,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), SprawdzPaczki.class);
                                        startActivity(intent);
                                    }
                                });
                                
                            }
                        });
                        dialog.setNegativeButton(R.string.Anuluj, null);
                        dialog.create().show();




            }
        });

        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SprawdzPaczki.class);
                startActivity(intent);
            }
        });


    }
}