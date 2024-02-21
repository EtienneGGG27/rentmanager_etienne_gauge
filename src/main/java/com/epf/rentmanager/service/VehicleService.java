package com.epf.rentmanager.service;

import java.util.List;


import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		if(!vehicle.getConstructeur().isEmpty() && vehicle.getNb_places()>1){
			return vehicleDao.create(vehicle);
		}
		else{
			throw new ServiceException();
		}
    }

	public Vehicle findById(long id) throws ServiceException, DaoException {
		return vehicleDao.findById(id);
    }

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return vehicleDao.findAll();
    }

	public void deleteVehicle(Vehicle vehicle) throws DaoException {
		vehicleDao.delete(vehicle);
	}

}
