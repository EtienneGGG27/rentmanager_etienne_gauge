package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;


import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	@Autowired
	private VehicleService(VehicleDao vehicleDao){
		this.vehicleDao = vehicleDao;
	}
	/*
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	*/
	
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

	public int count() throws DaoException {
		return vehicleDao.count();
	}

	public void modifyVehicle(Vehicle vehicle) throws SQLException {
		vehicleDao.modify(vehicle);
	}

}
