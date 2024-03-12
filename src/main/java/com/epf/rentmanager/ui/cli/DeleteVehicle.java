package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteVehicle {

    @Autowired
    public DeleteVehicle(VehicleDao vehicleDao) throws DaoException {
        Vehicle vehicle = vehicleDao.findById(
                IOUtils.readInt("Veuillez saisis l'id du véhicule a supprimer :"));
        vehicleDao.delete(vehicle);
        System.out.println("Le vehciule "+vehicle.getConstructeur()+" a bien été supprimé");
    }
}
