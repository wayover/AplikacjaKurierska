package com.example.aplikacjakurierska.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.ui.PaczkiPrzypisane.PrzypisaneFragment;
import com.google.firebase.firestore.FirebaseFirestore;

public class PackUserInfo extends AppCompatActivity {

    TextView Id,imie,nazwisko,kod,mail,miasto,telefon,ulica,nrdomu,nrmieszkania,idklienta,idmagazynu;
    Button cofnij;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_user_info);


        cofnij= findViewById(R.id.bUserInfoCofnij);
        fStore=FirebaseFirestore.getInstance();
        Id=findViewById(R.id.tvUserInfoId);
        imie=findViewById(R.id.tvUserInfoImie);
        nazwisko=findViewById(R.id.tvUserInfoNazwisko);
        kod=findViewById(R.id.tvUserInfoKod);
        mail=findViewById(R.id.tvUserInfoMail);
        miasto=findViewById(R.id.tvUserInfoMiasto);
        telefon=findViewById(R.id.tvUserInfoTelefon);
        ulica=findViewById(R.id.tvUserInfoUlica);
        nrdomu=findViewById(R.id.tvUserInfoNrDomu);
        nrmieszkania=findViewById(R.id.tvUserInfoNrMieszkania);
        idklienta=findViewById(R.id.tvUserInfoIdKlienta);
        idmagazynu=findViewById(R.id.tvUserInfoIdMagazynu);

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


        cofnij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrzypisaneFragment.class);
                startActivity(intent);
            }
        });

    }
}