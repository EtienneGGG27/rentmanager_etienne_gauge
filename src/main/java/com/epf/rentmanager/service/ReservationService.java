package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationDao reservationDao;

    @Autowired
    private ReservationService(ReservationDao reservationDao) {
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

    public List<LocalDate> verificationSiDateSeChevauche(Reservation reservationAVerifier) throws DaoException {

        List<Reservation> listeReservation = reservationDao.findAll();
        List<LocalDate> dateReservationVehicle = new ArrayList<>();
        for (Reservation reservation : listeReservation) {
            if (reservation.getIdVehicule() == reservationAVerifier.getIdVehicule()) {
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
            }
        }
        return dateReservationVehicle;
    }
}
