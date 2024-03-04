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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details_")
public class ClientDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClientService clientService;
    private ReservationService reservationService;
    private VehicleService vehicleService;

    public ClientDetailsServlet(){
        this.clientService = ClientService.getInstance();
        this.reservationService = ReservationService.getInstance();
        this.vehicleService = VehicleService.getInstance();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findById(id);
            List<Reservation> reservations = reservationService.findByClientId( (int) id);
            List<Vehicle> vehicles = new ArrayList<>();
            List<Integer> listIdVehicle = new ArrayList<>();
            Vehicle vehicleAAjouter = new Vehicle();
            for( Reservation reservation : reservations) {
                vehicleAAjouter = vehicleService.findById(reservation.getIdVehicule());
                if (!listIdVehicle.contains(vehicleAAjouter.getIdVehicle())){
                    vehicles.add(vehicleAAjouter);
                    listIdVehicle.add(vehicleAAjouter.getIdVehicle());
                }
            }
            request.setAttribute("client", client);
            request.setAttribute("reservations", reservations);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("nbVehicles", vehicles.size());
            request.setAttribute("nbReservations", reservations.size());
            request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

}
