package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {
    private int idReservation;
    private int ID_client;
    private int ID_Vehicle;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation(){}

    public Reservation(int idReservation, int ID_client, int ID_Vehicle, LocalDate debut, LocalDate fin) {
        this.idReservation = idReservation;
        this.ID_client = ID_client;
        this.ID_Vehicle = ID_Vehicle;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int ID_client, int ID_Vehicle, LocalDate debut, LocalDate fin) {
        this.ID_client = ID_client;
        this.ID_Vehicle = ID_Vehicle;
        this.debut = debut;
        this.fin = fin;
    }


    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getID_client() {
        return ID_client;
    }

    public void setID_client(int ID_client) {
        this.ID_client = ID_client;
    }

    public int getID_Vehicle() {
        return ID_Vehicle;
    }

    public void setID_Vehicle(int ID_Vehicle) {
        this.ID_Vehicle = ID_Vehicle;
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
                ", ID_client=" + ID_client +
                ", ID_Vehicle=" + ID_Vehicle +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}