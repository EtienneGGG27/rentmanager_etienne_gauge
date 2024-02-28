package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;

    public VehicleListServlet(){
        this.vehicleService = VehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
                List<Vehicle> vehicles = vehicleService.findAll();
                request.setAttribute("vehicles", vehicles);
                request.getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
}