package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class CreateReservation {

    @Autowired
    public CreateReservation(ClientService clientService, ReservationDao reservationDao, List<Client> listClient) throws DaoException, ServiceException {
        boolean idCient = false;
        int id_client = IOUtils.readInt("Veuillez saisir votre id_client : ");
        if (clientService.findById(id_client) != null) {
            idCient = true;
        }
        while (!idCient) {
            id_client = IOUtils.readInt("Veuillez saisir votre id_client parmis a liste suivante : " + listClient);
            if (clientService.findById(id_client) == null) {
                idCient = true;
            }
            int id_vehicle = IOUtils.readInt("Veuillez saisir votre id de vehicule : ");
            LocalDate debut = IOUtils.readDate("Veuillez saisir la date de début (dans ce format : jj/mm/aaaa) : ", true);
            LocalDate fin = IOUtils.readDate("Veuillez saisir la date de fin (dans ce format : jj/mm/aaaa) : ", true);

            reservationDao.create(new Reservation(id_client, id_vehicle, debut, fin));
        }
    }
}
