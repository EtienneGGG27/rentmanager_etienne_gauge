package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.utils.IOUtils;

import java.util.regex.Matcher;

public class CreateVehicle {

    public CreateVehicle() throws DaoException {
        String constructeur = IOUtils.readString("Veuillez saisir le constructeur du vehicule :", true);
        String modele = IOUtils.readString("Veuillez saisir le modele du vehicule :", true);
        int nbPlace = IOUtils.readInt("Veuillez entrer le nombre de place :");
        long idVehicle = VehicleDao.getInstance().create(new Vehicle(constructeur, modele, nbPlace));
        System.out.println("Le vehicule "+constructeur+" a été créer avec l'id : "+idVehicle);
    }


}
