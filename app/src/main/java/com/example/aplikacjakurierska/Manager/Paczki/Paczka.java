package com.example.aplikacjakurierska.Manager.Paczki;

public class Paczka {
    String Id;
    String Imie;
    String Kod;
    String Nazwisko;
    String Mail;
    String Miasto;
    String Nrdomu;
    String NrMieszkania;
    String Telefon;
    String Ulica;
    String IdKlienta;
    String IdMagazynu;

    public Paczka(String id, String imie, String kod, String nazwisko, String mail, String miasto, String nrdomu, String nrMieszkania, String telefon, String ulica,String idKlienta,String idMagazynu) {
        Id = id;
        Imie = imie;
        Kod = kod;
        Nazwisko = nazwisko;
        Mail = mail;
        Miasto = miasto;
        Nrdomu = nrdomu;
        NrMieszkania = nrMieszkania;
        Telefon = telefon;
        Ulica = ulica;
        IdKlienta=idKlienta;
        IdMagazynu=idMagazynu;
    }

    public String getIdMagazynu() {
        return IdMagazynu;
    }

    public void setIdMagazynu(String idMagazynu) {
        IdMagazynu = idMagazynu;
    }

    public String getIdKlienta() {
        return IdKlienta;
    }

    public void setIdKlienta(String idKlienta) {
        IdKlienta = idKlienta;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getKod() {
        return Kod;
    }

    public void setKod(String kod) {
        Kod = kod;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getMiasto() {
        return Miasto;
    }

    public void setMiasto(String miasto) {
        Miasto = miasto;
    }

    public String getNrdomu() {
        return Nrdomu;
    }

    public void setNrdomu(String nrdomu) {
        Nrdomu = nrdomu;
    }

    public String getNrMieszkania() {
        return NrMieszkania;
    }

    public void setNrMieszkania(String nrMieszkania) {
        NrMieszkania = nrMieszkania;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String ulica) {
        Ulica = ulica;
    }
}
