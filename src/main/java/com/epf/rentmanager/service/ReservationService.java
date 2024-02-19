package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;

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

    public long create(Reservation reservation) throws DaoException {
        return reservationDao.create(reservation);
    }

    public List<Reservation> findByClientId(Reservation reservation) throws DaoException {
        return reservationDao.findResaByClientId(reservation.getID_client());
    }

    public List<Reservation> findByVehicleId(Reservation reservation) throws DaoException {
        return reservationDao.findResaByVehicleId(reservation.getID_Vehicle());
    }



}
