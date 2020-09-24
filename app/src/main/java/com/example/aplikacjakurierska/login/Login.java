package com.example.aplikacjakurierska.login;

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

public class Login extends AppCompatActivity {

    EditText etHas,etLog;
    Button bZaloguj,bZarejestruj;
    TextView tvlog,tvhas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etHas=findViewById(R.id.etHaslo);
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

                tvhas.setText(etHas.getText());
                tvlog.setText(etLog.getText());




                if(etLog.getText().toString().equals("admin")&&etHas.getText().toString().equals("admin1")){
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                }else if(etLog.getText().toString().equals("kurier")&&etHas.getText().toString().equals("kurier1")){
                    Intent intent = new Intent(getApplicationContext(), KurierActivity.class);
                    startActivity(intent);
                }
                else{
                    String text = "Błędny login lub hasło";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }

                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);

            }
        });

    }
}