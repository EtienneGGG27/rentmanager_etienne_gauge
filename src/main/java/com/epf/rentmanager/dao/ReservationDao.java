package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, reservation.getIdReservation());
			preparedStatement.setInt(2, reservation.getID_Vehicle());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				int ID_Reservation = resultSet.getInt(1);
				connexion.close();
				return ID_Reservation;
			}
			connexion.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return 0;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getIdReservation());
			preparedStatement.execute();
			connexion.close();
			return reservation.getIdReservation();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			List<Reservation> listReservation = new ArrayList<>();
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) clientId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				int vehicule_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, (int) clientId, vehicule_id, debut, fin);
				listReservation.add(reservation);
			}
			connexion.close();
			return listReservation;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try {
			List<Reservation> listReservation = new ArrayList<>();
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setInt(1, (int) vehicleId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, (int) client_id, (int) vehicleId, debut, fin);
				listReservation.add(reservation);
			}
			connexion.close();
			return listReservation;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			List<Reservation> listReservation = new ArrayList<>();
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				int client_id = resultSet.getInt("client_id");
				int vehicule_id = resultSet.getInt("vehicule_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reservation = new Reservation(id, client_id, vehicule_id, debut, fin);
				listReservation.add(reservation);
			}
			connexion.close();
			return listReservation;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
