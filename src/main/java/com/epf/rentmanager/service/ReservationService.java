package com.epf.rentmanager.service;
import java.time.LocalDate;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }


    public int create(Reservation reservation) throws DaoException {
        return reservationDao.create(reservation);
    }

    public List<Reservation> findByClientId(int idClient) throws DaoException {
        return reservationDao.findResaByClientId(idClient);
    }

    public List<Reservation> findByVehicleId(int idVehicle) throws DaoException {
        return reservationDao.findResaByVehicleId(idVehicle);
    }

    public List<Reservation> listAll() throws DaoException {
        return reservationDao.findAll();
    }

    public void deleteReservationbyId(int idReservation) throws DaoException {
        reservationDao.delete(idReservation);
    }

    public int count() throws DaoException {
        return reservationDao.count();
    }

    public Reservation findById(int id) throws SQLException {
        return reservationDao.findById(id);
    }

    public void modifyReservation(Reservation reservation) throws SQLException {
        reservationDao.modify(reservation);
    }

    public List<Reservation> orderPerDateReservation() throws SQLException {
        return reservationDao.orderReservationPerDate();
    }

    public List<LocalDate> verificationSiDateSeChevauche(Reservation reservationAVerifier) throws DaoException {
        List<Reservation> listeReservation = reservationDao.findAll();
        List<LocalDate> dateReservationVehicle = new ArrayList<>();
        for (Reservation reservation : listeReservation) {
            if (reservation.getIdVehicule() == reservationAVerifier.getIdVehicule() &&reservation.getIdReservation()!=reservationAVerifier.getIdReservation()) {
                if (reservation.getDebut().isBefore(reservationAVerifier.getDebut()) && reservation.getFin().isAfter(reservationAVerifier.getDebut())) {
                    dateReservationVehicle.add(reservation.getDebut());
                    dateReservationVehicle.add(reservation.getFin());
                    return dateReservationVehicle;
                } else if (reservation.getDebut().isBefore(reservationAVerifier.getDebut()) && reservation.getFin().isAfter(reservationAVerifier.getFin()) || reservation.getDebut().isAfter(reservationAVerifier.getFin()) && reservation.getDebut().isBefore(reservationAVerifier.getFin())) {
                    dateReservationVehicle.add(reservation.getDebut());
                    dateReservationVehicle.add(reservation.getFin());
                    return dateReservationVehicle;
                } else if (reservation.getDebut().isBefore(reservationAVerifier.getFin()) && reservation.getFin().isAfter(reservationAVerifier.getFin())) {
                    dateReservationVehicle.add(reservation.getDebut());
                    dateReservationVehicle.add(reservation.getFin());
                    return dateReservationVehicle;
                } else if (reservation.getDebut().isAfter(reservationAVerifier.getDebut()) && reservation.getFin().isBefore(reservationAVerifier.getFin())) {
                    dateReservationVehicle.add(reservation.getDebut());
                    dateReservationVehicle.add(reservation.getFin());
                    return dateReservationVehicle;
                }
                else if (reservation.getDebut().isEqual(reservationAVerifier.getDebut()) || reservation.getDebut().isEqual(reservationAVerifier.getFin())  || reservation.getFin().isEqual(reservationAVerifier.getFin()) || reservation.getFin().isEqual(reservationAVerifier.getDebut())){
                    dateReservationVehicle.add(reservation.getDebut());
                    dateReservationVehicle.add(reservation.getFin());
                    return dateReservationVehicle;
                }
            }
        }
        return dateReservationVehicle;
    }

    public int verificationMoinsDe30Jours(Reservation reservationAAJouter) throws DaoException, SQLException {

        List<Reservation> reservationList = reservationDao.orderReservationPerDate();
        List<Reservation> reservatioDuVehicle = new ArrayList<>();
        for (Reservation reservation : reservationList){
            if (reservation.getIdVehicule() == reservationAAJouter.getIdVehicule()){
                reservatioDuVehicle.add(reservation);
            }
        }
        int nbJourConsecutif = 0;
        List<Reservation> listeReservation30Jour = new ArrayList<>();

        for (Reservation reservation : reservatioDuVehicle){
            if (reservation.getFin().isAfter(reservationAAJouter.getDebut().minusDays(31)) && reservation.getFin().isBefore(reservationAAJouter.getDebut())){
                listeReservation30Jour.add(reservation);
            }
        }
        if (listeReservation30Jour.size()<3){
            return 0;
        }

        for (int i = 0; i < listeReservation30Jour.size(); i++) {
            if (i < listeReservation30Jour.size()-1) {
                if (listeReservation30Jour.get(i).getFin().plusDays(1).isEqual(listeReservation30Jour.get(i+1).getDebut())) {
                    nbJourConsecutif += 1+(int) ChronoUnit.DAYS.between(listeReservation30Jour.get(i).getDebut(), listeReservation30Jour.get(i).getFin());
                } else {
                    nbJourConsecutif = 0;
                }
            }
            else if (listeReservation30Jour.get(i).getFin().plusDays(1).isEqual(reservationAAJouter.getDebut())){
                nbJourConsecutif += 1+(int) ChronoUnit.DAYS.between(listeReservation30Jour.get(i).getDebut(), listeReservation30Jour.get(i).getFin());
                nbJourConsecutif+=(int) ChronoUnit.DAYS.between(listeReservation30Jour.get(i).getDebut(), listeReservation30Jour.get(i).getFin());
            }
        }
        return nbJourConsecutif;
    }

}
