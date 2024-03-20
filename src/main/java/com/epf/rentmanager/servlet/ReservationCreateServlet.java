package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        if (reservation.getDebut().isAfter(reservation.getFin())){
            request.setAttribute("DateSeSuiventPas", "La date de début de réservation doit etre avat celle de fin");
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
            return;
        }

        try {
            List<LocalDate> dateReservationVehicle = reservationService.verificationSiDateSeChevauche(reservation);
            if (!dateReservationVehicle.isEmpty()){
                request.setAttribute("DateReservationError", dateReservationVehicle);
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
                return;
            }
        } catch (DaoException | ServletException e) {
            throw new RuntimeException(e);
        }



        try {
            reservationService.create(reservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (IOException | DaoException e) {
            throw new RuntimeException(e);
        }


    }

}
