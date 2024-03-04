package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@WebServlet("/cars/delete_")
public class VehicleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;

    public VehicleDeleteServlet() {
        this.vehicleService = vehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));

            Vehicle vehicleDelete = new Vehicle();
            vehicleDelete.setIdVehicle((int) id);

            vehicleService.deleteVehicle(vehicleDelete);

            response.sendRedirect(request.getContextPath() + "/cars");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
