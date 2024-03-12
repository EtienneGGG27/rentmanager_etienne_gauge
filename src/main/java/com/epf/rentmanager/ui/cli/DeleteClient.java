package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteClient {

    @Autowired
    public DeleteClient(ClientDao clientDao) throws DaoException {
        long idClient = IOUtils.readInt("Veuillez entrer l'id du client à supprimer :");
        Client client = clientDao.findById(idClient);
        clientDao.delete(client);
        System.out.println("Le client "+client.getNom()+" "+client.getPrenom()+" a été supprimé de la base de donnée.");
    }

}
