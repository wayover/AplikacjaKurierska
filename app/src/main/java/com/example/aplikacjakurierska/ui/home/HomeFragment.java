package com.example.aplikacjakurierska.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aplikacjakurierska.R;
import com.example.aplikacjakurierska.user.DodajPrzeylke;
import com.example.aplikacjakurierska.user.Kontakt;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
Button bDodajp;
Button bKontakt;
Button bWyloguj;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
       final View root = inflater.inflate(R.layout.fragment_home, container, false);

        bDodajp=root.findViewById(R.id.bZnajdzPrzes);

        bDodajp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(root.getContext(), DodajPrzeylke.class);
                startActivity(intent);

            }
        });

        bKontakt=root.findViewById(R.id.bKontakt);

        bKontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), Kontakt.class);
                startActivity(intent);
            }
        });


        bWyloguj=root.findViewById(R.id.bWyloguj);

        bWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = "Zostałeś wylogowany";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getContext(), text, duration);
                toast.show();
            }
        });
        return root;
    }
}