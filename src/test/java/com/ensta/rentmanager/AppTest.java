package com.ensta.rentmanager;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;

public class AppTest {

    @Test
    public void should__be_false_because_email_doesnt_already_exist() throws ServiceException, DaoException {
        ClientDao clientDao = new ClientDao();
        ClientService clientService = new ClientService(clientDao);
        LocalDate birthDate = LocalDate.of(2002, 1, 4);
        Client client = new Client("John", "Doe", "john.doe@gmail.com", birthDate);
        clientService.create(client);
        Client client2 = new Client("Tristan", "Dupont", "new.email@gmail.com", birthDate);
        assertFalse(clientService.verificationMailExistant(client2.getEmail()));
    }

    @Test
    public void should__be_true_because_email_already_exist() throws ServiceException, DaoException {
        ClientDao clientDao = new ClientDao();
        ClientService clientService = new ClientService(clientDao);
        LocalDate birthDate = LocalDate.of(2002, 1, 4);
        Client client = new Client("John", "Doe", "john.doe@gmail.com", birthDate);
        clientService.create(client);
        Client client2 = new Client("Tristan", "Dupont", "john.doe@gmail.com", birthDate);
        assertTrue(clientService.verificationMailExistant(client2.getEmail()));
    }

    @Test
    public void should_be_false_because_name_is_ok(){
        ClientDao clientDao = new ClientDao();
        ClientService clientService = new ClientService(clientDao);
        LocalDate birthDate = LocalDate.of(2002, 1, 4);
        Client client = new Client("John", "Doe", "john.doe@gmail.com", birthDate);
        assertFalse(clientService.verificationNomPrenomTropCourt(client.getNom()));
    }

    @Test
    public void should_be_true_because_name_too_short(){
        ClientDao clientDao = new ClientDao();
        ClientService clientService = new ClientService(clientDao);
        LocalDate birthDate = LocalDate.of(2002, 1, 4);
        Client client = new Client("Jo", "Doe", "john.doe@gmail.com", birthDate);
        assertTrue(clientService.verificationNomPrenomTropCourt(client.getNom()));
    }

    @Test
    public void should_answer_empty_because_date_is_already_reserve() throws DaoException {
        ReservationDao reservationDao = new ReservationDao();
        ReservationService reservationService = new ReservationService(reservationDao);
        reservationService.create(new Reservation(1, 1, 1, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 2)));
        List<LocalDate> date = reservationService.verificationSiDateSeChevauche(new Reservation(2, 1, 1, LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 3)));
        assertFalse(date.isEmpty());
    }
}
