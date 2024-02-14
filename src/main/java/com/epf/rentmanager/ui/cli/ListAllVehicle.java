package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;

import java.util.List;

public class ListAllVehicle {
    public ListAllVehicle() throws DaoException {
        List<Vehicle> listAllVehicle = VehicleDao.getInstance().findAll();
        System.out.println("Voici la liste de tous les vehicules :"+listAllVehicle);
    }
}
