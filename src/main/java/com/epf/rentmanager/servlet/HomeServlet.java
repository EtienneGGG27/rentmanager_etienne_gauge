package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private VehicleService vehicleService;
	private ClientService clientService;
	private ReservationService reservationService;

	public HomeServlet(){
		this.vehicleService = VehicleService.getInstance();
		this.clientService = ClientService.getInstance();
		this.reservationService = ReservationService.getInstance();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			request.setAttribute("nb_vehicle", vehicleService.count() );
			request.setAttribute("nb_reservation", reservationService.count() );
			request.setAttribute("nb_client", clientService.count() );
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (DaoException e) {
            throw new RuntimeException(e);
        }
	}
}
