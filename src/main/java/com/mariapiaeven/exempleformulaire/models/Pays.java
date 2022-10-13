package com.mariapiaeven.exempleformulaire.models;

import java.io.Serializable;

public class Pays implements Serializable {
    protected String nom ;
    protected String iso;
    //iso = fr, pe, de par exemple
    protected String image;

    public Pays(String nom, String iso, String image) {
        this.nom = nom;
        this.iso = iso;
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        Pays paysCompare = (Pays)obj;

        return  this.nom.equals(paysCompare.getNom());
    }

    //    @Override
//    public String toString() {
//        return this.iso + "-" + this.nom;
//    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
