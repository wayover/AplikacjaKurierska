package com.example.aplikacjakurierska.login;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.res.ColorStateList;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.aplikacjakurierska.R;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.firestore.FirebaseFirestore;

public class Rejestracja extends AppCompatActivity {

    Button bZarejes;
    EditText etMail,etHaslo,etHaslo2;
    TextView tvblad;
    ProgressBar pbReje;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        pbReje=findViewById(R.id.pbRejestracja);

        etMail=findViewById(R.id.etRMail);
        etHaslo=findViewById(R.id.etrHaslo);
        etHaslo2=findViewById(R.id.etRhaslo2);
        tvblad=findViewById(R.id.tvRBlad);
        fStore= FirebaseFirestore.getInstance();

        bZarejes=findViewById(R.id.bRZarejestruj);
        bZarejes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbReje.setVisibility(View.VISIBLE);
                String mail=etMail.getText().toString();
                String haslo=etHaslo.getText().toString();
                String haslo2=etHaslo2.getText().toString();
                if(mail.isEmpty()||haslo.isEmpty()||haslo2.isEmpty()) {
                    pbReje.setVisibility(View.INVISIBLE);
                    tvblad.setText(R.string.polapuste);
                    tvblad.setTextColor(Color.RED);
                    tvblad.setVisibility(View.VISIBLE);
                }
                else{
                    if(haslo.equals(haslo2)){
                        mAuth.createUserWithEmailAndPassword(mail,haslo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pbReje.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), R.string.rejestudana, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        tvblad.setVisibility(View.INVISIBLE);
                    }else{
                        pbReje.setVisibility(View.INVISIBLE);
                        tvblad.setText(R.string.roznehasla);
                        tvblad.setTextColor(Color.RED);
                        tvblad.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

}