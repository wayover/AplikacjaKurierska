package com.example.aplikacjakurierska.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aplikacjakurierska.MainActivity;
import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
Button bWyloguj;
    Button bZnajdz;
    EditText etNr,etTele;
    FirebaseFirestore fStore;
    Integer i;
    String telefon,nrPaczki,sTelefon,sPaczka,idKlien;
    private FirebaseAuth mAuth;
        FirebaseUser firebaseUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
       final View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser= mAuth.getCurrentUser();

        fStore=FirebaseFirestore.getInstance();
        bZnajdz=root.findViewById(R.id.bDodajZnajdz);
        etTele=root.findViewById(R.id.etDodajTelefon);
        etNr=root.findViewById(R.id.etDodajNrPrzesylki);



        bZnajdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=0;
                fStore.collection("paczki").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        sTelefon=etTele.getText().toString();
                        sPaczka=etNr.getText().toString();

                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot document :task.getResult()) {

                                nrPaczki = (String) document.getId();
                                telefon = (String) document.get("Telefon").toString();
                                idKlien = (String) document.get("IdKlienta").toString();

                                if (nrPaczki.equals(sPaczka) && telefon.equals(sTelefon)) {

                                    if (idKlien.equals("Brak")) {

                                        DocumentReference docfer = fStore.collection("paczki").document(sPaczka);
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("IdKlienta", mAuth.getUid());

                                        docfer.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(root.getContext(), R.string.pckadd, Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(root.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });


                                        Intent intent = new Intent(root.getContext(), MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(root.getContext(), "Paczka została już przypisana do użytkownika ", Toast.LENGTH_LONG).show();

                                    }

                                }
                            }
                        }else{
                            Toast.makeText(root.getContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                        }







                    }
                });
            }
        });










        bWyloguj=root.findViewById(R.id.bWyloguj);

        bWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getContext(), R.string.wylogowany, duration);
                toast.show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(root.getContext(), Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return root;
    }
}