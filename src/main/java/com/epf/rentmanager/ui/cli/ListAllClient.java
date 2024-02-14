package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ListAllClient {

    public ListAllClient() throws DaoException {
        List<Client> listClientInterface = ClientDao.getInstance().findAll();
        System.out.println("Voici la liste de tous les clients : "+ listClientInterface);
    }
}
