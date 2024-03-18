package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	@Autowired
	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}

	/*
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		return instance;
	}
	*/
	
	public long create(Client client) throws ServiceException, DaoException {
		if(!client.getPrenom().isEmpty() && !client.getNom().isEmpty()){
			try {
				return clientDao.create(client);
			} catch (DaoException e) {
				throw new RuntimeException(e);
			}
		}
		else{
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException, DaoException {
		return clientDao.findById(id);
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		return clientDao.findAll();
	}


	public long deleteClient(Client client) throws DaoException {
		return clientDao.delete(client);
	}

	public int count() throws DaoException {
		return clientDao.count();
	}

	public void modifyClient(Client client) throws SQLException {
		clientDao.modify(client);
	}

	public boolean verificationMailExistant(String mail) throws DaoException {
		return clientDao.verificationMailExistant(mail);
	}
}
