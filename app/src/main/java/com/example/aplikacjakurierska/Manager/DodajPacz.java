package com.example.aplikacjakurierska.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.user.Kontakt;

public class DodajPacz extends AppCompatActivity {

    EditText etImie,etNazwisko,etTelefon,etMailKurier,etMiasto,etKod,etUlica,etNrDomu,etNrMieszkania;
    Button bDodaj;
    String Imie,Nazwisko,Telefon,MailKurier,Miasto,Kod,Ulica,NrDomu,NrMieszkania;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_pacz);


        etImie=findViewById(R.id.etDodajImieKlienta);
        etNazwisko=findViewById(R.id.etDodajNazwiskoKlienta);
        etTelefon=findViewById(R.id.etDodajTelefonKlienta);
        etMailKurier=findViewById(R.id.etDodajMailKuriera);
        etMiasto=findViewById(R.id.etDodajMiasto);
        etKod=findViewById(R.id.etDodajKod);
        etUlica=findViewById(R.id.etDodajUlica);
        etNrDomu=findViewById(R.id.etDodajNrDomu);
        etNrMieszkania=findViewById(R.id.etDodajNrMieszkania);


        bDodaj=findViewById(R.id.bDodajDodaj);

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imie=etImie.getText().toString();
                Nazwisko=etNazwisko.getText().toString();
                Telefon =etTelefon.getText().toString();
                MailKurier=etMailKurier.getText().toString();
                Miasto=etMiasto.getText().toString();
                Kod=etKod.getText().toString();
                Ulica=etUlica.getText().toString();
                NrDomu=etNrDomu.getText().toString();
                NrMieszkania=etNrMieszkania.getText().toString();

                if(!Imie.isEmpty()&&!Nazwisko.isEmpty()&&!Telefon.isEmpty()&&!MailKurier.isEmpty()&&!Miasto.isEmpty()&&!Kod.isEmpty()&&!Ulica.isEmpty()&&!NrDomu.isEmpty()){

                }else{
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}