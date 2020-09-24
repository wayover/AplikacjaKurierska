package com.example.aplikacjakurierska.login;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.res.ColorStateList;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.example.aplikacjakurierska.R;

public class Rejestracja extends AppCompatActivity {

    Button bZarejes;
    EditText etImie,etNazw,etMail,etNrtel;
    TextView tvblad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        etImie=findViewById(R.id.etRimie);
        etNazw=findViewById(R.id.etRNazwisko);
        etMail=findViewById(R.id.etRMail);
        etNrtel=findViewById(R.id.etRtelefon);
        tvblad=findViewById(R.id.tvRBlad);

        bZarejes=findViewById(R.id.bRZarejestruj);
        bZarejes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etImie.getText().toString().isEmpty()||etNazw.getText().toString().isEmpty()||etMail.getText().toString().isEmpty()||etNrtel.getText().toString().isEmpty()) {
                    tvblad.setText("Pola nie mogą być puste");
                    tvblad.setTextColor(Color.RED);
                    tvblad.setVisibility(View.VISIBLE);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);

                }
            }
        });
    }

}