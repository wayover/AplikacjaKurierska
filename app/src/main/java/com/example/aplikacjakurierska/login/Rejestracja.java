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
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;


        import java.util.HashMap;
        import java.util.Map;

public class Rejestracja extends AppCompatActivity {

    Button bZarejes;
    EditText etMail,etHaslo,etHaslo2,etImie,etNazwisko,etTelefon;
    ProgressBar pbReje;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        pbReje=findViewById(R.id.pbRejestracja);

        etMail=findViewById(R.id.etRMail);
        etHaslo=findViewById(R.id.etrHaslo);
        etHaslo2=findViewById(R.id.etRhaslo2);
        etImie=findViewById(R.id.etRImie);
        etNazwisko=findViewById(R.id.etRNazw);
        etTelefon=findViewById(R.id.etRTel);


        bZarejes=findViewById(R.id.bRZarejestruj);
        bZarejes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbReje.setVisibility(View.VISIBLE);
                final String imie=etImie.getText().toString();
                final String nazwisko=etNazwisko.getText().toString();
                final String telefon=etTelefon.getText().toString();
                final String mail=etMail.getText().toString();
                String haslo=etHaslo.getText().toString();
                String haslo2=etHaslo2.getText().toString();
                if(mail.isEmpty()||haslo.isEmpty()||haslo2.isEmpty()||imie.isEmpty()||nazwisko.isEmpty()||telefon.isEmpty()) {
                    pbReje.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), R.string.polapuste, Toast.LENGTH_LONG).show();
                }
                else{
                    if(haslo.equals(haslo2)){
                        mAuth.createUserWithEmailAndPassword(mail,haslo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pbReje.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    DocumentReference df=fStore.collection("users").document(user.getUid());
                                    Map<String,Object> userInfo= new HashMap<>();
                                    userInfo.put("Email",mail);
                                    userInfo.put("Imie",imie);
                                    userInfo.put("Nazwisko",nazwisko);
                                    userInfo.put("Telefon",telefon);
                                    userInfo.put("Role","0");//0-user 1-admin 2- courier 3-managment

                                    df.set(userInfo).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });



                                    Toast.makeText(getApplicationContext(), R.string.rejestudana, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(getApplicationContext(), R.string.roznehasla, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

}