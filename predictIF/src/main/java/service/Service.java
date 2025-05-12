/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.maps.model.LatLng;
import dao.ClientDAO;
import dao.ConsultationDAO;
import dao.EmployeDAO;
import dao.JpaUtil;
import dao.MediumDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modele.Astrologue;
import modele.Cartomancien;

import modele.Client;
import modele.ClientStats;
import modele.Consultation;
import modele.Employe;
import modele.EmployeStat;
import modele.Medium;
import modele.MediumStats;
import modele.Prediction;
import modele.ProfilAstral;
import modele.Spirite;
import util.AstroNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author aguyonnaud
 */
public class Service {

    private static String mailExped = "contact@predict.fr";

    public List<Client> listerClients() {
        List<Client> res;

        try {
            JpaUtil.creerContextePersistance();

            res = ClientDAO.findAll();

        } catch (Exception ex) {
            res = null;
            System.out.println("execption###listerClient######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }

    public List<Medium> rechercherListeMedium(boolean Spirite, boolean Astrologue, boolean Carto, String nomMedium, String genre) {
        List<Medium> res;

        try {
            JpaUtil.creerContextePersistance();
//            JpaUtil.ouvrirTransaction();
            res = MediumDAO.find(Spirite, Astrologue, Carto, nomMedium, genre);
//            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            res = null;
            System.out.println("execption###rechercherListeMedium######################");
//            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }

//    public List<LatLng> CoordClients() {
//        List<LatLng> res = new ArrayList<LatLng>();
//
//        JpaUtil.creerContextePersistance();
//
//        try {
//            JpaUtil.ouvrirTransaction(); //besoin d'une transaction ?
//            List<Client> clients = ClientDAO.findAll();
//
//            for (Client c : clients) {
//                LatLng coord = GeoNetApi.getLatLng(c.getAdresse());
//                res.add(coord);
//            }
//
//            JpaUtil.validerTransaction();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            res = null;
//            System.out.println("execption###CoordClients######################");
//            JpaUtil.annulerTransaction();
//
//        } finally {
//            JpaUtil.fermerContextePersistance();
//        }
//
//        return res;
//    }
    public List<ClientStats> listerClientsParEmploye(Employe e) {
        List<ClientStats> res;

        try {
            JpaUtil.creerContextePersistance();

            res = ConsultationDAO.findClientsPerEmployee(e);

        } catch (Exception ex) {
            ex.printStackTrace();
            res = null;
            System.out.println("execption###listerClientsParEmploye######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }

    public boolean initMediums() {
        boolean res = true;

        Spirite m1 = new Spirite("Gwenaëlle", "F", "Spéciatise des conv", "Boule de cristal");
        Spirite m2 = new Spirite("Prof Tran", "H", "Votre avenir est devant vous", "Marc de café, boule de cristal, oreille de lapin");
        Cartomancien m3 = new Cartomancien("Mme Irma", "F", "Comprenez votre entourage");
        Cartomancien m4 = new Cartomancien("Andora", "F", "Mes cartes répondront");
        Astrologue m5 = new Astrologue("Serena", "F", "Eclairez votre passé", "ENS Astro", "2006");
        Astrologue m6 = new Astrologue("Mr M", "H", "Avenir, avenir", "INSAstro", "2010");

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            MediumDAO.create(m1);
            MediumDAO.create(m2);
            MediumDAO.create(m3);
            MediumDAO.create(m4);
            MediumDAO.create(m5);
            MediumDAO.create(m6);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            res = false;
            System.out.println("execption###by initMedium######################");
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;

    }

    public boolean initEmployes() {
        boolean res = true;

        Employe e1 = new Employe("Martinnet", "Alice", "F", "amart@insa-lyon.fr", "0897979797", "coucouCMoi");
        Employe e2 = new Employe("Heniyson", "Hervé", "H", "henher@gmail.com", "0877979797", "coucouCMoi");
        Employe e3 = new Employe("Legros", "Pierre", "H", "grospierre@gmail.com", "0870979797", "coucouCMoi");
        Employe e4 = new Employe("Roux", "Marie", "F", "marieroux@yahoo.com", "0877979737", "coucouCMoi");
        Employe e5 = new Employe("Dubois", "Jeanne", "F", "jeannedubois@gmail.com", "0878979797", "coucouCMoi");
        Employe e6 = new Employe("Schumarer", "Jean", "H", "jeanlesvoitures@gmail.com", "0877919797", "coucouCMoi");

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            EmployeDAO.create(e1);
            EmployeDAO.create(e2);
            EmployeDAO.create(e3);
            EmployeDAO.create(e4);
            EmployeDAO.create(e5);
            EmployeDAO.create(e6);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            res = false;
            System.out.println("execption###by initEmploye######################");
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;

    }

    public void accepterConsultation(Consultation c) {

        try {
            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
            c.setEtat("ACCEPTE");

            c = ConsultationDAO.save(c);

            JpaUtil.validerTransaction();
            
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
            String message = "Bonjour " + c.getClient().getPrenom() + ". J'ai bien recu votre demande de consultation du "+s.format(c.getDate())+". Vous pouvez me contacter au "
                    + c.getEmploye().getTel() + ". À tout de suite! Médiumiquement votre, " + c.getMedium().getNom();//date demande a faire;
            Message.envoyerNotification(c.getClient().getTel(), message);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###by AccepterConsultation######################");
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }

    public void demarrerConsultation(Consultation c) {

        try {
            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
            c.setEtat("EN COURS");
            c.setDate(new Date());
            c = ConsultationDAO.save(c);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###by AccepterConsultation######################");
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }

    public void terminerConsultation(Consultation c, Prediction p, String comm) {

        try {
            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
            c.setEtat("TERMINEE");
            c.getEmploye().setDispo(true);

            c.setPrediction(p);
            c.setCommentaire(comm);

            c.setEmploye(EmployeDAO.save(c.getEmploye()));
            c = ConsultationDAO.save(c);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###by AccepterConsultation######################");
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }

    public List<Consultation> rechercherListeConsultation(Long clientID, boolean spirite, boolean astro, boolean carto, String nomMedium, String dateString) {
        List<Consultation> res;

        try {
            JpaUtil.creerContextePersistance();
            
            res = ConsultationDAO.find(clientID, spirite, astro, carto, nomMedium, dateString);
            
        } catch (Exception ex) {
            ex.printStackTrace   ();
            res = null;
            System.out.println("execption###rechercherListeMedium######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;
    }

    public Consultation prochaineConsultation(Employe e) {
        Consultation c;
        try {
            JpaUtil.creerContextePersistance();
            c = ConsultationDAO.findNext(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            c = null;
            System.out.println("execption###prochaineConsultation######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

    public List<EmployeStat> rechercherNbClientParEmploye() {
        List<EmployeStat> res;

        try {
            JpaUtil.creerContextePersistance();

            res = EmployeDAO.findNbClients();

        } catch (Exception ex) {
            ex.printStackTrace();
            res = null;
            System.out.println("execption###rechercherNbClientParEmploye######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;

    }

    public List<MediumStats> rechercherNbConsultationsMedium() {
        List<MediumStats> res;

        try {
            JpaUtil.creerContextePersistance();

            res = MediumDAO.findNbConsultationsMedium();

        } catch (Exception ex) {
            ex.printStackTrace();
            res = null;
            System.out.println("execption###rechercherNbConsultationsMedium######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;

    }

    public List<ClientStats> rechercherNbConsultationsClient() {
        List<ClientStats> res;

        try {
            JpaUtil.creerContextePersistance();
            res = ConsultationDAO.findNbConsultationsClient();
        } catch (Exception ex) {
            ex.printStackTrace();
            res = null;
            System.out.println("execption###rechercherNbConsultationsClient######################");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;

    }

    public Consultation trouverConsultation(Client c, Medium m) {
        Consultation consul;

        try {
            JpaUtil.creerContextePersistance();
            Employe e = EmployeDAO.findOpti(m.getGenre());
            //Employe e = TrouverEmployeOpti(m.getGenre());
            System.out.println(e);

            if (e == null) {
                String message = "Bonjour " + c.getPrenom() + ". Malheureusement,  " + m.getNom() + " n'est pas disponible pour le moment :( \n Réessayez plus tard !";
                Message.envoyerNotification(c.getTel(), message);
                return null;
            }

            consul = new Consultation(c, e, m);

            JpaUtil.ouvrirTransaction();
            ConsultationDAO.create(consul);

            e.setDispo(false);
            e.setNb_consult(e.getNb_consult() + 1);
            m.setNbConsultations(m.getNbConsultations() + 1);
            e = EmployeDAO.save(e);
            m = MediumDAO.save(m);

            

            JpaUtil.validerTransaction();
            
            String message = "Bonjour " + e.getPrenom() + ". Consultation requise pour " + c.getPrenom() + " " + c.getNom()
                    + ". Medium à incarner : " + m.getNom();//gérer le genre du cluient
            Message.envoyerNotification(e.getTel(), message);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###by TrouverConsultation######################");
            consul = null;
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return consul;

    }

//    public Employe TrouverEmployeOpti(String genre) {
//        JpaUtil.creerContextePersistance();
//        Employe e = EmployeDAO.findOpti(genre);
//
//        JpaUtil.fermerContextePersistance();
//
//        return e;
//
//    }
    public Client ClientParID(Long unId) {
        Client c1;
        try {
            JpaUtil.creerContextePersistance();

            c1 = ClientDAO.findById(unId);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###ClientParID######################");
            c1 = null;
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c1;
    }

    public Employe EmployeParID(Long unId) {
        Employe c1;
        try {
            JpaUtil.creerContextePersistance();

            c1 = EmployeDAO.findById(unId);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###EmployeParID######################");
            c1 = null;
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c1;
    }

    public Medium MediumParID(Long unId) {
        Medium c1;
        try {
            JpaUtil.creerContextePersistance();

            c1 = MediumDAO.findById(unId);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###MediumParID######################");
            c1 = null;
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c1;
    }

    public Consultation ConsultationParID(Long unId) {
        Consultation c1;
        try {
            JpaUtil.creerContextePersistance();

            c1 = ConsultationDAO.findById(unId);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("execption###ConsultationParID######################");
            c1 = null;
            JpaUtil.annulerTransaction();

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c1;
    }

    public static boolean inscrireClient(Client c) throws IOException {
        boolean res = true;

        try {

            ProfilAstral leProfil = new ProfilAstral();
            AstroNetApi astroApi = new AstroNetApi();

            List<String> profil = astroApi.getProfil(c.getPrenom(), c.getDateNaissance());
            leProfil.setZodiaque(profil.get(0));
            leProfil.setSgnChinois(profil.get(1));
            leProfil.setCouleur(profil.get(2));
            leProfil.setAnimal(profil.get(3));

            c.setProfAst(leProfil);

            LatLng coord = GeoNetApi.getLatLng(c.getAdresse());
            c.setCoord(coord);

            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
            ClientDAO.create(c);
            JpaUtil.validerTransaction();

            String msg = "Bonjour " + c.getPrenom() + ", nous vous confirmons votre inscription au service PREDICT'IF. \nRendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos médiums.";

            Message.envoyerMail(mailExped, c.getMail(), "Bienvenue chez PREDICT'IF", msg);

        } catch (Exception ex) {
            ex.printStackTrace();
            res = false;
            System.out.println("execption###by inscrireClient######################");
            JpaUtil.annulerTransaction();

            String msg = "Bonjour " + c.getPrenom() + ", inscription au service PREDICT'IF a malencontreusement échoué...\nMerci de recommencer ultérieurement.";
            Message.envoyerMail(mailExped, c.getMail(), "Echec de l'inscription chez PREDICT'IF", msg);

        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return res;

    }

    public Prediction genererPrediction(Client c, int sante, int amour, int travail) throws IOException {
        List<String> lesPredictions;
        AstroNetApi astro = new AstroNetApi();
        lesPredictions = astro.getPredictions(c.getProfAst().getCouleur(), c.getProfAst().getAnimal(), amour, sante, travail);

        Prediction p = new Prediction(lesPredictions.get(1), lesPredictions.get(0), lesPredictions.get(2));

        return p;

    }

    public Client connecterClient(String mail, String mdp) // return null si pas de correspondance
    {
        JpaUtil.creerContextePersistance();//to do : try-catch 
        Client c = ClientDAO.connecter(mail, mdp);

        JpaUtil.fermerContextePersistance();

        return c;

    }

    public Employe connecterEmploye(String mail, String mdp) // return null si pas de correspondance
    {
        JpaUtil.creerContextePersistance();//to do : try-catch 
        Employe c = EmployeDAO.connecter(mail, mdp);

        JpaUtil.fermerContextePersistance();

        return c;

    }

}
