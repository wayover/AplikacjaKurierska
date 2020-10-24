package com.example.aplikacjakurierska.Admin.Magazyn;

public class MagazynClass {

    String Id;
    String Miasto;
    String Numer;
    String Ulica;

    public MagazynClass(String id,String miasto, String ulica, String numer) {
        Id=id;
        Miasto = miasto;
        Numer = numer;
        Ulica = ulica;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMiasto() {
        return Miasto;
    }

    public void setMiasto(String miasto) {
        Miasto = miasto;
    }

    public String getNumer() {
        return Numer;
    }

    public void setNumer(String numer) {
        Numer = numer;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String ulica) {
        Ulica = ulica;
    }
}
