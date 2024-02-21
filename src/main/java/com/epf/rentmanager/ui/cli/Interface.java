package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import com.epf.rentmanager.dao.ClientDao;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interface {

    private static final ClientService clientService = ClientService.getInstance();
    private static final VehicleService vehicleService = VehicleService.getInstance();
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String DATE_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";
    private static final Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private static final Pattern patternDate = Pattern.compile(DATE_REGEX);

    public static void main(String[] args) throws DaoException {
        displayMainMenu();
    }

    private static void displayMainMenu() throws DaoException {
        boolean running = true;
        while (running) {
            IOUtils.print("\n### Menu principal ###");
            IOUtils.print("1. Créer un client");
            IOUtils.print("2. Lister tous les clients");
            IOUtils.print("3. Créer un véhicule");
            IOUtils.print("4. Lister tous les véhicules");
            IOUtils.print("5. Supprimer un client (bonus)");
            IOUtils.print("6. Supprimer un véhicule (bonus)");
            IOUtils.print("7. Quitter");

            int choice = IOUtils.readInt("Choisissez une option : ");

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listClients();
                    break;
                case 3:
                    createVehicle();
                    break;
                case 4:
                    listVehicles();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 6:
                    deleteVehicle();
                    break;
                case 7:
                    running = false;
                    IOUtils.print("Au revoir !");
                    break;
                default:
                    IOUtils.print("Option invalide. Veuillez réessayer.");
            }
        }
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidDate(String date) {
        Matcher matcher = patternDate.matcher(date);
        return matcher.matches();
    }

    private static void createClient() throws DaoException {
        boolean emailValid = false;
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

        naissance = IOUtils.readDate("Veuillez saisir votre date de naissance", true);

        ClientDao.getInstance().create(new Client(nom, prenom, email, naissance));
        System.out.println("Client "+nom +" ajouté !");
    }

    private static void listClients() {
        IOUtils.print("\n### Liste des clients ###");
        try {
            List<Client> clients = clientService.findAll();
            if (!clients.isEmpty()) {
                for (Client client : clients) {
                    IOUtils.print(client.toString());
                }
            } else {
                IOUtils.print("Aucun client trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des clients : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createVehicle() {
        IOUtils.print("\n### Création d'un véhicule ###");
        String constructeur = IOUtils.readString("Constructeur : ", true);
        String modele = IOUtils.readString("modele : ", true);
        int nbPlaces = IOUtils.readInt("Nombre de places : ");

        try {
            long vehicleId = vehicleService.create(new Vehicle(constructeur, nbPlaces));
            IOUtils.print("Véhicule créé avec succès ! (ID : " + vehicleId + ")");
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la création du véhicule : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listVehicles() throws DaoException {
        List<Vehicle> listAllVehicle = VehicleDao.getInstance().findAll();
        System.out.println("Voici la liste de tous les vehicules :"+listAllVehicle);
    }

    private static void deleteClient() throws DaoException {
        long idClient = IOUtils.readInt("Veuillez entrer l'id du client à supprimer :");
        Client client = ClientDao.getInstance().findById(idClient);
        clientService.deleteClient(client);
        System.out.println("Le client "+client.getNom()+" "+client.getPrenom()+" a été supprimé de la base de donnée.");
    }

    private static void deleteVehicle() throws DaoException {
        Vehicle vehicle = VehicleDao.getInstance().findById(
                IOUtils.readInt("Veuillez saisis l'id du véhicule a supprimer :"));
        VehicleService.getInstance().deleteVehicle(vehicle);
        System.out.println("Le vehciule "+vehicle.getConstructeur()+" a bien été supprimé");
    }
}
