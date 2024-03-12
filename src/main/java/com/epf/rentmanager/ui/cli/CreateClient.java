package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateClient {



    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String DATE_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";
    private static final Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private static final Pattern patternDate = Pattern.compile(DATE_REGEX);

    @Autowired
    public CreateClient(ClientDao clientDoa) throws DaoException {
        boolean emailValid = false;
        boolean dateValid = false;
        String email = null;
        LocalDate naissance = null;

        String nom = IOUtils.readString("Veuillez saisir votre nom", true);

        String prenom = IOUtils.readString("Veuillez saisir votre prénom", true);

        while (!emailValid) {
            email = IOUtils.readString("Veuillez saisir votre email", true);
            if (isValidEmail(email)) {
                emailValid = true;
            }
        }

        while (!dateValid) {
            naissance = IOUtils.readDate("Veuillez saisir votre date de naissance", true);
            if (isValidDate(String.valueOf(naissance))) {
                dateValid = true;
            }
        }

        clientDoa.create(new Client(nom, prenom, email, naissance));
        System.out.println("Client "+nom +" ajouté !");
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidDate(String date) {
        Matcher matcher = patternDate.matcher(date);
        return matcher.matches();
    }
}
