package com.example.aplikacjakurierska.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import java.util.Calendar;

public class ManagerDostarczonePaczkiInfo extends AppCompatActivity {

    Button cofnij,usun;
    TextView czas,telefon;
    Number AktRok,AktMiesiac,AktDzien,AktGodzina;
    Integer IAktRok,IAktMiesiac,IAktDzien,IAktGodzina;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dostarczone_paczki_info);


        cofnij=findViewById(R.id.bDostarczonePaczkiInfoCofnij);
        czas=findViewById(R.id.tvDostarczonePaczkiInfoCzas);
        usun=findViewById(R.id.bDostarczonePaczkiInfoUsun);
        telefon=findViewById(R.id.tvDostarczoneNumerKlienta);
        fStore=FirebaseFirestore.getInstance();

        final String Srok=getIntent().getStringExtra("rok");
        final String Smiesiac=getIntent().getStringExtra("miesiac");
        final String Sdzien=getIntent().getStringExtra("dzien");
        final String Sgodzina=getIntent().getStringExtra("godzina");
        final String Sid=getIntent().getStringExtra("id");
        final String Spotwierdzenie=getIntent().getStringExtra("potwierdzone");
        final String Snumer=getIntent().getStringExtra("numer");
        final String IdPaczki=getIntent().getStringExtra("IdPaczki");

        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ManagerDostarczonePaczki.class));
            }
        });


        czas.setText(Srok+"/"+Smiesiac+"/"+Sdzien+" "+Sgodzina);
        telefon.setText(Snumer);

        Integer IRok=Integer.parseInt(Srok);
        Integer IMiesiac=Integer.parseInt(Smiesiac);
        Integer IDzien=Integer.parseInt(Sdzien);
        Integer IGodzina=Integer.parseInt(Sgodzina);

        AktDzien= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        AktMiesiac=Calendar.getInstance().get(Calendar.MONTH)+1;
        AktRok=Calendar.getInstance().get(Calendar.YEAR);
        AktGodzina=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: "+telefon.getText()));
                startActivity(intent);
            }
        });


        IAktRok=AktRok.intValue();
        IAktMiesiac=AktMiesiac.intValue();
        IAktDzien=AktDzien.intValue();
        IAktGodzina=AktGodzina.intValue();
        if(IAktRok+IRok==IRok*2&&IAktMiesiac==IMiesiac&&IAktDzien==IDzien&&IAktGodzina-IGodzina<24){
                usun.setVisibility(View.INVISIBLE);
        }else if(IAktRok+IRok==IRok*2&&IAktMiesiac==IMiesiac&&IAktDzien-IDzien==1&&IGodzina-IAktGodzina<24){
                usun.setVisibility(View.INVISIBLE);
        }else if(IAktRok+IRok==IRok*2&&IAktMiesiac-IMiesiac==1&&IDzien-IAktDzien>26&&IGodzina-IAktGodzina<24){//do sprawdzenia
                usun.setVisibility(View.INVISIBLE);
        }else if(IAktRok+IRok==IRok*2+1&&IMiesiac-IAktMiesiac==11&&IDzien-IAktDzien==30&&IGodzina-IAktGodzina<24){//do sprawdzenia
                usun.setVisibility(View.INVISIBLE);
        }else{
            usun.setVisibility(View.VISIBLE);
        }




        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder dialog= new AlertDialog.Builder(ManagerDostarczonePaczkiInfo.this);
                dialog.setTitle(R.string.rmPack);
                dialog.setMessage(R.string.AreUsus);
                dialog.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DocumentReference docfer=fStore.collection("paczki").document(IdPaczki);
                        docfer.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),R.string.PackDelete,Toast.LENGTH_LONG).show();
                            }
                        });


                        docfer=fStore.collection("Dostarczona").document(Sid);
                        docfer.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),R.string.PackDelete,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), ManagerDostarczonePaczki.class);
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