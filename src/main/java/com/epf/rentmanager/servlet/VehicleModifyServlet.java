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
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@WebServlet("/cars/modify_")
public class VehicleModifyServlet extends HttpServlet {

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
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Vehicle vehicle = vehicleService.findById(id);
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/modify.jsp").forward(request, response);
        }
        catch (NumberFormatException | ServiceException e) {
            throw new ServletException(e);
        } catch (DaoException e) {
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
            Vehicle vehicle = vehicleService.findById(id);
            if (vehicle != null) {
                String constructeur = request.getParameter("manufacturer");
                String modele = request.getParameter("modele");
                int nb_places = Integer.parseInt(request.getParameter("seat"));

                vehicle.setConstructeur(constructeur);
                vehicle.setModele(modele);
                vehicle.setNb_places(nb_places);
                vehicleService.modifyVehicle(vehicle);

                response.sendRedirect(request.getContextPath() + "/cars");

            } else {
                throw new ServletException("Vehicle with ID " + id + " not found");
            }
        } catch (ServiceException | DaoException | DateTimeParseException e) {
            throw new ServletException(String.valueOf(e));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

