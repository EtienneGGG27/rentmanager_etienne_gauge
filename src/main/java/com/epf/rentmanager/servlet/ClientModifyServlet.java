package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/users/modify_")
public class ClientModifyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findById(id);
            request.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/views/users/modify.jsp").forward(request, response);
        }
        catch (NumberFormatException | ServiceException e) {
            throw new ServletException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid client ID", e);
        }

        try {
            Client client = clientService.findById(id);
            if (client != null) {
                String lastName = request.getParameter("last_name");
                String firstName = request.getParameter("first_name");
                String email = request.getParameter("email");
                LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));

                if (lastName.length()<3 ){
                    request.setAttribute("NomTropCourtError", "Le nom est trop court");
                    request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                    return;
                }

                if (firstName.length()<3 ){
                    request.setAttribute("PrenomTropCourtError", "Le prénom est trop court");
                    request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                    return;
                }

                try {
                    if (clientService.verificationMailExistant(email)){
                        request.setAttribute("EmailExistantError", "L'email existe déjà");
                        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
                        return;
                    }
                } catch (DaoException e) {
                    throw new RuntimeException(e);
                }

                client.setNom(lastName);
                client.setPrenom(firstName);
                client.setEmail(email);
                client.setNaissance(birthday);

                clientService.modifyClient(client);
                response.sendRedirect(request.getContextPath() + "/users");

            } else {
                throw new ServletException("Client with ID " + id + " not found");
            }
        } catch (ServiceException | DaoException | DateTimeParseException e) {
            throw new ServletException(String.valueOf(e));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

