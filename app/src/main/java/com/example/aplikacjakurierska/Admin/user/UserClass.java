package com.example.aplikacjakurierska.Admin.user;

import android.widget.ScrollView;

public class UserClass {
    String Email;
    String Imie;
    String Nazwisko;
    String Role;
    String Telefon;


    public UserClass() {
        this.Email = "brak";
        this.Imie = "brak";
        this.Nazwisko = "brak";
        this.Role = "brak";
        this.Telefon = "000000000";
    }

    public UserClass(String mail, String imie, String nazwisko, String rola, String telefon) {
        this.Email = mail;
        this.Imie = imie;
        this.Nazwisko = nazwisko;
        this.Role = rola;
        this.Telefon = telefon;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }
}


