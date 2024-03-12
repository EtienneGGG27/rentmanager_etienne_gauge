package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ListAllClient {

    @Autowired
    public ListAllClient(ClientDao clientDao) throws DaoException {
        List<Client> listClientInterface = clientDao.findAll();
        System.out.println("Voici la liste de tous les clients : "+ listClientInterface);
    }
}
