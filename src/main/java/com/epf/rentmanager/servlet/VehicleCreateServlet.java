package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Vehicle;
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
import java.util.List;


@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
        }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String constructeur  = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nb_places = Integer.parseInt(request.getParameter("seat"));

        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(constructeur);
        vehicle.setModele(modele);
        vehicle.setNb_places(nb_places);

        try {
            vehicleService.create(vehicle);
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (ServiceException | IOException | DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
