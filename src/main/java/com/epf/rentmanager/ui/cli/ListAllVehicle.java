package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ListAllVehicle {


    @Autowired
    public ListAllVehicle(VehicleDao vehicleDao) throws DaoException {
        List<Vehicle> listAllVehicle = vehicleDao.findAll();
        System.out.println("Voici la liste de tous les vehicules :"+listAllVehicle);
    }
}
