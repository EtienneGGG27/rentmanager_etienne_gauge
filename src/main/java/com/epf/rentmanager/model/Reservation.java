package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private int idReservation;
    private int idClient;
    private int idVehicule;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(){}

    public Reservation(int idReservation, int idClient, int idVehicule, LocalDate debut, LocalDate fin) {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idVehicule = idVehicule;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int ID_client, int idVehicule, LocalDate debut, LocalDate fin) {
        this.idClient = ID_client;
        this.idVehicule = idVehicule;
        this.debut = debut;
        this.fin = fin;
    }


    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", idClient=" + idClient +
                ", idVehicle=" + idVehicule +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}