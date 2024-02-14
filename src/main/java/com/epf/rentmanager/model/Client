package com.epf.rentmanager.model;


import java.time.LocalDate;

public class Client {
    private int ID_Client;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(String nom, String prenom, String email, LocalDate naissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public Client(int ID_Client, String nom, String prenom, String email, LocalDate naissance) {
        this.ID_Client = ID_Client;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public Client(){}

    public int getID_Client() {
        return ID_Client;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setID_Client(int ID_Client) {
        this.ID_Client = ID_Client;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID_Client=" + ID_Client +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}