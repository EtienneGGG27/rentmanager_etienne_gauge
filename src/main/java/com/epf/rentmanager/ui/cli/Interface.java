package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
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
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String DATE_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";
    private static final Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private static final Pattern patternDate = Pattern.compile(DATE_REGEX);

    public static void main(String[] args) throws DaoException, ServiceException {
        displayMainMenu();
    }

    private static void displayMainMenu() throws DaoException, ServiceException {
        boolean running = true;
        while (running) {
            IOUtils.print("\n### Menu principal ###");
            IOUtils.print("1. Créer un client");
            IOUtils.print("2. Lister tous les clients");
            IOUtils.print("3. Créer un véhicule");
            IOUtils.print("4. Lister tous les véhicules");
            IOUtils.print("5. Supprimer un client (bonus)");
            IOUtils.print("6. Supprimer un véhicule (bonus)");
            IOUtils.print("7. Créer une réservation");
            IOUtils.print("8. Lister toutes les réservation associées à un id de client");
            IOUtils.print("9. Lister toutes les réservations associées à un id de vehicule");
            IOUtils.print("10. Supprimer une réservation");
            IOUtils.print("11. Quitter");

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
                    createReservation();
                    break;
                case 8:
                    listAllReservationbyClient();
                    break;
                case 9:
                    listAllReservationbyVehicle();
                    break;
                case 10:
                    deleteReservation();
                    break;
                case 11:
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
            long vehicleId = vehicleService.create(new Vehicle(constructeur, modele, nbPlaces));
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

    private static void createReservation() throws DaoException, ServiceException {
        List<Client> listClient = clientService.findAll();
        List<Vehicle> listVehicle = vehicleService.findAll();
        boolean idCient = false;
        boolean idVehicle = false;

        int id_client = IOUtils.readInt("Veuillez saisir votre id_client : ");
        if (clientService.findById(id_client) != null) {
            idCient = true;
        }
        while (!idCient) {
            id_client = IOUtils.readInt("Veuillez saisir votre id_client parmis a liste suivante : " + listClient);
            if (clientService.findById(id_client) != null) {
                idCient = true;
            }
        }

        int id_vehicle = IOUtils.readInt("Veuillez saisir votre id de vehicule : ");
        if (vehicleService.findById(id_vehicle) != null) {
            idVehicle = true;
        }
        while (!idVehicle) {
            id_vehicle = IOUtils.readInt("Veuillez saisir votre id de vehicule parmis la liste suiivante : " + listVehicle);
            if (ClientService.getInstance().findById(id_client) != null) {
                idVehicle = true;
            }
        }
        LocalDate debut = IOUtils.readDate("Veuillez saisir la date de début (dans ce format : jj/mm/aaaa) : ", true);
        LocalDate fin = IOUtils.readDate("Veuillez saisir la date de fin (dans ce format : jj/mm/aaaa) : ", true);
        ReservationDao.getInstance().create(new Reservation(id_client, id_vehicle, debut, fin));
    }

    private static void listAllReservationbyClient() throws DaoException, ServiceException {
        List<Client> listClients = clientService.findAll();
        int idClient = IOUtils.readInt("Veuillez entrer l'id du client choisi parmis la list suivante : "+listClients);
        Client client = clientService.findById(idClient);
        List<Reservation> listReservation = reservationService.findByClientId(idClient);
        if (!listReservation.isEmpty()) {
            System.out.println("Voici la liste des réservation pour le client : " + client + " => " + listReservation);
        }
        else {
            System.out.println("Aucune réservation n'a été prise avec ce client ");
        }
    }
    private static void listAllReservationbyVehicle() throws DaoException, ServiceException {
        List<Vehicle> listVehicles = vehicleService.findAll();
        int idVehicle = IOUtils.readInt("Veuillez entrer l'id du vehicule choisi parmis la list suivante : "+listVehicles);
        Vehicle vehicle = vehicleService.findById(idVehicle);
        List<Reservation> listReservation = reservationService.findByVehicleId(idVehicle);
        if (!listReservation.isEmpty()) {
            System.out.println("Voici la liste des réservation pour le vehicule : " + vehicle + " => " + listReservation);
        }
        else {
            System.out.println("Aucune réservation n'a été prise avec ce vehicule ");
        }
    }

    private static void deleteReservation() throws DaoException {
        List<Reservation> listReservation = reservationService.listAll();
        int idRservation = IOUtils.readInt("Veuillez choisir une réservation parmis la liste suivante : "+listReservation);
        reservationService.deleteReservationbyId(idRservation);
        System.out.println("La réservation a bien été supprimé");
    }

}
