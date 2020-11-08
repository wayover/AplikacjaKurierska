package com.example.aplikacjakurierska.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.example.aplikacjakurierska.R;

import java.util.Locale;

public class ZmianaJezyka extends AppCompatActivity {

    Button polski,angielski;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmiana_jezyka);

        polski=findViewById(R.id.bPoslski);
        angielski=findViewById(R.id.bAngielski);

        polski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResources("pl");
            }
        });

        angielski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateResources("en");
            }
        });

    }
    private void updateResources(String localeCode) {
        Resources res= getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        Configuration conf= res.getConfiguration();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        }else{
            conf.locale=new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf,dm);

    }

}