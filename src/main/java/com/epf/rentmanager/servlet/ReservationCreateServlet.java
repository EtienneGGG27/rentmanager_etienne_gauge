package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReservationService reservationService;
    private VehicleService vehicleService;
    private ClientService clientService;

    public ReservationCreateServlet(){
        this.clientService = ClientService.getInstance();
        this.reservationService = ReservationService.getInstance();
        this.vehicleService = VehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{

            List<Client> clients = null;
            try {
                clients = clientService.findAll();
            } catch (ServiceException | DaoException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("clients", clients);

            List<Vehicle> vehicles = null;
            try {
                vehicles = vehicleService.findAll();
            } catch (ServiceException | DaoException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("vehicles",vehicles);

            request.setAttribute("listIdVehicle", vehicleService.findAll());
            request.setAttribute("listIdClient", clientService.findAll());
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException(e);
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idVehicule = Integer.parseInt(request.getParameter("car"));
        int idClient = Integer.parseInt(request.getParameter("client"));

        DateTimeFormatter debut_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate debut = LocalDate.parse(request.getParameter("begin"), debut_formatter);

        DateTimeFormatter fin_formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fin = LocalDate.parse(request.getParameter("end"), fin_formatter);

        Reservation reservation = new Reservation();
        reservation.setIdClient(idClient);
        reservation.setIdVehicule(idVehicule);
        reservation.setDebut(debut);
        reservation.setFin(fin);

        try {
            reservationService.create(reservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (IOException | DaoException e) {
            throw new RuntimeException(e);
        }


    }

}
