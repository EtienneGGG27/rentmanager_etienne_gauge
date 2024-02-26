package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, client.getNom().toUpperCase());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			if (client.getNaissance()!=null){
				preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));
			}
			else{
				preparedStatement.setDate(4, null);
			}
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				return id;
			}
			connexion.close();
		} catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return 0;
    }
	
	public long delete(Client client) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getIdClient());
			preparedStatement.execute();
			connexion.close();
			return client.getIdClient();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				int id_client = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				Client client = new Client(id_client, nom, prenom, email, naissance);
				connexion.close();
				return client;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
        return null;
    }

	public List<Client> findAll() throws DaoException {
		List<Client> listClient = new ArrayList<Client>();
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				int ID_Client = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				Date naissance_date = resultSet.getDate("naissance");
				LocalDate naissance = null;
				if(naissance_date !=null){
					naissance = naissance_date.toLocalDate();
				}
				Client client = new Client(ID_Client, nom, prenom, email, naissance);
				listClient.add(client);
			}
			connexion.close();
			return listClient;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() throws DaoException {
		return this.findAll().size();
	}

}
