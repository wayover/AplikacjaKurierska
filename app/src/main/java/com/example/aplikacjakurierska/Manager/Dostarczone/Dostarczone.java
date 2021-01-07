package com.example.aplikacjakurierska.Manager.Dostarczone;

public class Dostarczone {
    String Id;
    Number Rok;
    Number Miesiac;
    Number Dzien;
    Number Godzina;
    String IdPaczki;
    String Potwierdzone;
    String NumerKlienta;

    public Dostarczone(String id, Number rok, Number miesiac, Number dzien, Number godzina, String idPaczki, String potwierdzone,String Numer) {
        Id = id;
        Rok = rok;
        Miesiac = miesiac;
        Dzien = dzien;
        Godzina = godzina;
        IdPaczki = idPaczki;
        Potwierdzone = potwierdzone;
        NumerKlienta=Numer;
    }

    public String getNumerKlienta() {
        return NumerKlienta;
    }

    public void setNumerKlienta(String numerKlienta) {
        NumerKlienta = numerKlienta;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Number getRok() {
        return Rok;
    }

    public void setRok(Number rok) {
        Rok = rok;
    }

    public Number getMiesiac() {
        return Miesiac;
    }

    public void setMiesiac(Number miesiac) {
        Miesiac = miesiac;
    }

    public Number getDzien() {
        return Dzien;
    }

    public void setDzien(Number dzien) {
        Dzien = dzien;
    }

    public Number getGodzina() {
        return Godzina;
    }

    public void setGodzina(Number godzina) {
        Godzina = godzina;
    }

    public String getIdPaczki() {
        return IdPaczki;
    }

    public void setIdPaczki(String idPaczki) {
        IdPaczki = idPaczki;
    }

    public String getPotwierdzone() {
        return Potwierdzone;
    }

    public void setPotwierdzone(String potwierdzone) {
        Potwierdzone = potwierdzone;
    }
}
