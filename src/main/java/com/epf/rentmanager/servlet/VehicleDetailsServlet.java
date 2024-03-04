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

@WebServlet("/cars/details_")
public class VehicleDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClientService clientService;
    private ReservationService reservationService;
    private VehicleService vehicleService;

    public VehicleDetailsServlet(){
        this.clientService = ClientService.getInstance();
        this.reservationService = ReservationService.getInstance();
        this.vehicleService = VehicleService.getInstance();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Vehicle vehicle = vehicleService.findById(id);
            List<Reservation> reservations = reservationService.findByVehicleId( (int) id);
            List<Client> clients = new ArrayList<>();
            List<Integer> listIdClient = new ArrayList<>();
            Client clientAAjouter = new Client();
            for( Reservation reservation : reservations) {
                clientAAjouter = clientService.findById(reservation.getIdClient());
                if (!listIdClient.contains(clientAAjouter.getIdClient())){
                    clients.add(clientAAjouter);
                    listIdClient.add(clientAAjouter.getIdClient());
                }
            }
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("reservations", reservations);
            request.setAttribute("clients", clients);
            request.setAttribute("nbClients", clients.size());
            request.setAttribute("nbReservations", reservations.size());
            request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

}
