package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;

public class CreateReservation {

    public CreateReservation() throws DaoException {

        int id_client = IOUtils.readInt("Veuillez saisir votre id_client : ");
        int id_vehicle = IOUtils.readInt("Veuillez saisir votre id de vehicule : ");
        LocalDate debut = IOUtils.readDate("Veuillez saisir la date de début (dans ce format : jj/mm/aaaa) : ", true);
        LocalDate fin = IOUtils.readDate("Veuillez saisir la date de fin (dans ce format : jj/mm/aaaa) : ", true);

        ReservationDao.getInstance().create(new Reservation(id_client, id_vehicle, debut, fin));
    }

}
