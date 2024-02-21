package com.epf.rentmanager.model;

public class Vehicle {
    private int idVehicle;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle(){}

    public Vehicle(String constructeur, int nb_places){
        this.constructeur=constructeur;
        this.nb_places=nb_places;
    }
    public Vehicle(int idVehicle, String constructeur, int nb_places){
        this.idVehicle =idVehicle;
        this.constructeur=constructeur;
        this.nb_places=nb_places;
    }

    public int getIdVehicle() {
        return idVehicle;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setIdVehicle(int idVehicle) {
        this.idVehicle = idVehicle;
    }
    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "idVehicle=" + idVehicle +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }
}