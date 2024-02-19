package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.utils.IOUtils;

public class DeleteVehicle {

    public DeleteVehicle() throws DaoException {
        Vehicle vehicle = VehicleDao.getInstance().findById(
                IOUtils.readInt("Veuillez saisis l'id du véhicule a supprimer :"));
        VehicleDao.getInstance().delete(vehicle);
        System.out.println("Le vehciule "+vehicle.getConstructeur()+" a bien été supprimé");
    }
}
