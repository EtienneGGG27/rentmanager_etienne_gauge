package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Reservation WHERE vehicle_id = ?; DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places =? WHERE id = ?";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				int ID_Vehicle = resultSet.getInt(1);
				connexion.close();
				return ID_Vehicle;
			}
			connexion.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        return 0;
    }

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setInt(1, vehicle.getIdVehicle());
			preparedStatement.setInt(2, vehicle.getIdVehicle());
			preparedStatement.execute();
			connexion.close();
			return vehicle.getIdVehicle();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setInt(1, (int) id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nb_places = resultSet.getInt("nb_places");
				Vehicle vehicle = new Vehicle((int) id, constructeur, modele, nb_places);
				connexion.close();
				return vehicle;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        return null;
    }

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> listVehicle = new ArrayList<>();
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				int ID_vehicle = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nb_places = resultSet.getInt("nb_places");
				Vehicle vehicle=new Vehicle(ID_vehicle, constructeur, modele, nb_places);
				listVehicle.add(vehicle);
			}
			connexion.close();
			return listVehicle;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() throws DaoException {
        return this.findAll().size();
    }

	public void modify(Vehicle vehicle) throws SQLException {
		Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
		PreparedStatement preparedStatement = connexion.prepareStatement(UPDATE_VEHICLE_QUERY);
		preparedStatement.setString(1, vehicle.getConstructeur());
		preparedStatement.setString(2, vehicle.getModele());
		preparedStatement.setInt(3, vehicle.getNb_places());
		preparedStatement.setInt(4, vehicle.getIdVehicle());
		preparedStatement.execute();
		connexion.close();
	}


}
