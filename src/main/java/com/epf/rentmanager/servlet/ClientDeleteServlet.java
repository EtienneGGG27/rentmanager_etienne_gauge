package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/delete_")
public class ClientDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClientService clientService;

    public ClientDeleteServlet() {
        this.clientService = ClientService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));

            // Créer un objet Client avec l'ID récupéré
            Client Testclient = new Client();
            Testclient.setIdClient((int) id);

            clientService.deleteClient(Testclient);
            // Rediriger vers la page des utilisateurs après la suppression
            response.sendRedirect(request.getContextPath() + "/users");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}