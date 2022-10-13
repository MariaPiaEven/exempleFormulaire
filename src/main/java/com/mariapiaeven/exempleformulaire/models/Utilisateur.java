package com.mariapiaeven.exempleformulaire.models;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    protected String civilite;
    protected String nom;
    protected String prenom;
    protected String email;
    protected Pays pays;
    protected int age;
    protected boolean marie;

    public Utilisateur(String civilite, String nom, String prenom, String email, Pays pays, int age, boolean marie) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pays = pays;
        this.age = age;
        this.marie = marie;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarie() {
        return marie;
    }

    public void setMarie(boolean marie) {
        this.marie = marie;
    }
}
