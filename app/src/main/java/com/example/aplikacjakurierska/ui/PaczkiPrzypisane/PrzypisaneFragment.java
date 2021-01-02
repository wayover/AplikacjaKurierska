package com.example.aplikacjakurierska.ui.PaczkiPrzypisane;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aplikacjakurierska.Manager.Paczki.Paczka;
import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.user.PackUserInfo;
import com.example.aplikacjakurierska.user.UserPrzypisaneInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PrzypisaneFragment extends Fragment {

    private PrzypisaneViewModel przypisaneViewModel;
    ListView lvListaPaczek;
    FirebaseFirestore fStore;
    FirebaseAuth mAuth;
    ArrayList<String> infolist;
    ArrayList<Paczka>packinfo;
    String Id,Imie,Kod,Nazwisko,Mail,Miasto,Nrdomu,NrMieszkania,Telefon,Ulica,IdKlienta,IdMagazynu;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        przypisaneViewModel =
                ViewModelProviders.of(this).get(PrzypisaneViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        lvListaPaczek=root.findViewById(R.id.lvListaPaczekLista);
        infolist=new ArrayList<>();
        packinfo=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        fStore.collection("paczki").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){
                        Id=document.getId();
                        Imie=(String)document.get("Imie");
                        Nazwisko=(String)document.get("Nazwisko");
                        Kod=(String)document.get("Kod");
                        Mail=(String)document.get("MailKuriera");
                        Miasto =(String)document.get("Miasto");
                        Nrdomu=(String)document.get("NrDomu");
                        NrMieszkania=(String)document.get("NrMieszkania");
                        Telefon=(String)document.get("Telefon");
                        Ulica=(String)document.get("Ulica");
                        IdKlienta=(String)document.get("IdKlienta");
                        IdMagazynu=(String)document.get("IdMagazynu");

                        if(IdKlienta.equals(mAuth.getUid().toString())) {
                            Paczka pck = new Paczka(Id, Imie, Kod, Nazwisko, Mail, Miasto, Nrdomu, NrMieszkania, Telefon, Ulica, IdKlienta,IdMagazynu);
                            packinfo.add(pck);
                            infolist.add(Imie + " " + Nazwisko + "   " + Id);
                        }
                    }
                }else{
                    Toast.makeText(root.getContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(root.getContext(),android.R.layout.simple_list_item_1,infolist);
                lvListaPaczek.setAdapter(arrayAdapter);


                lvListaPaczek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(root.getContext(), UserPrzypisaneInfo.class);
                        intent.putExtra("id",packinfo.get(position).getId());
                        intent.putExtra("mail",packinfo.get(position).getMail());
                        startActivity(intent);
                    }
                });


            }
        });


        return root;
    }
}