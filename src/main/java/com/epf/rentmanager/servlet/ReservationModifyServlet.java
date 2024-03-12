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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/rents/modify_")
public class ReservationModifyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Reservation reservation = reservationService.findById((int) id);
            List<Client> clients = clientService.findAll();
            List<Vehicle> vehicles = vehicleService.findAll();


            request.setAttribute("reservation", reservation);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("clients", clients);
            request.getRequestDispatcher("/WEB-INF/views/rents/modify.jsp").forward(request, response);
        }
        catch (NumberFormatException e) {
            throw new ServletException(e);
        } catch (SQLException | DaoException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid client ID", e);
        }

        try {

            Reservation reservation = reservationService.findById((int) id);
            if (reservation != null) {
                int client_id = Integer.parseInt(request.getParameter("client"));
                int vehicle_id = Integer.parseInt(request.getParameter("car"));
                LocalDate debut = LocalDate.parse(request.getParameter("begin"));
                LocalDate fin = LocalDate.parse(request.getParameter("end"));

                reservation.setIdClient(client_id);
                reservation.setIdVehicule(vehicle_id);
                reservation.setDebut(debut);
                reservation.setFin(fin);
                reservationService.modifyReservation(reservation);

                response.sendRedirect(request.getContextPath() + "/rents");

            } else {
                throw new ServletException("Reservation with ID " + id + " not found");
            }
        } catch (DateTimeParseException e) {
            throw new ServletException(String.valueOf(e));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
