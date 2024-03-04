package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;

import java.sql.SQLException;
import java.util.List;

public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService(){this.reservationDao= ReservationDao.getInstance();}

    public static ReservationService getInstance(){
        if(instance == null){
            instance = new ReservationService();
        }
        return instance;
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

}
