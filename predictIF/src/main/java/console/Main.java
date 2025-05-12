
/*
import dao.JpaUtil;
import metier.modele.Client;
import metier.service.Service;



public class Main {

    
    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        // TODO code application logic here
        Client monClient = new Client("Hugo", "Victor", "victor.hugo@gmail.com", "esmeralda");
        System.out.println(monClient);
        if (Service.inscrireClient(monClient)){
            System.out.println("inscription reussie ! ");
        }else{
            System.out.println("echec de l'inscription");
        }
        JpaUtil.fermerFabriquePersistance();
    }
    
}
 */
import dao.JpaUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import modele.Client;
import modele.ClientStats;
import modele.Consultation;
import modele.Employe;
import modele.EmployeStat;
import modele.Medium;
import modele.MediumStats;
import modele.Prediction;
import service.Service;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        //JpaUtil.desactiverLog();
        JpaUtil.creerFabriquePersistance();
        testerInscrireClients();
        testerConsulterListeClients();
        Service service = new Service();
        service.initMediums();
        service.initEmployes();
        
        
        scenarioClient();

        JpaUtil.fermerFabriquePersistance();

    }
    
    private static void scenarioEmploye(Employe e) throws IOException
    {
        Service service = new Service();
        
        Employe e2 = service.connecterEmploye("amart@insa-lyon.fr", "coucouCmoiiii");
        printlnConsoleIHM(e2);
        e2 = service.connecterEmploye(e.getMail(), e.getMdp());
        printlnConsoleIHM(e2);
        
        Consultation consul= service.prochaineConsultation(e2);
        
        printlnConsoleIHM("(IHM employé) inflomations client : ");
        printlnConsoleIHM(consul.getClient().getProfAst());
        printlnConsoleIHM(service.rechercherListeConsultation(consul.getClient().getId(), false, false, false, "", "01/01/0000"));
        
        service.accepterConsultation(consul);
        printlnConsoleIHM(consul);

        service.demarrerConsultation(consul);
        printlnConsoleIHM(consul);

        Prediction p = service.genererPrediction(consul.getClient(), 3, 1, 1);
        service.terminerConsultation(consul, p, "client chiant");
        printlnConsoleIHM(consul);
        
        //stats
        printlnConsoleIHM("(IHM employé) coordonnées clients : \n"+service. listerClients());
        
        printlnConsoleIHM("(IHM employé) consult par mediums : \n"+service.rechercherNbConsultationsMedium());
        
        printlnConsoleIHM("(IHM employé) clients par employes : \n"+service.rechercherNbClientParEmploye());
        
        
        
        
    }
            
            
    private static void scenarioClient() throws ParseException, IOException
    {
        Service service = new Service();
        
        
        Client c1 = new Client("Levino", "Nicolas", "13/04/2004", "Parque Marbella 23, Huixquilucan de Degollado", "0943785620", "levinonicolas@gmail.com", "jefaisdelacrypto");
        service.inscrireClient(c1);
        
        printlnConsoleIHM("(IHM client) Inscription Client C1 : "+c1);
        
        
        Client c = service.connecterClient("levinonicolas@gmail.com", "jefaisdelacrypt");
        printlnConsoleIHM("(IHM client) client connecté : "+c);
        
        c = service.connecterClient("levinonicolas@gmail.com", "jefaisdelacrypto");
        printlnConsoleIHM("(IHM client) client connecté : "+c);
        
        printlnConsoleIHM("(IHM client) profil astral : ");
        printlnConsoleIHM(c.getProfAst());
        
        printlnConsoleIHM("(IHM client) historique consultations : ");
        printlnConsoleIHM(service.rechercherListeConsultation(c.getId(), false, false, false, "", "01/01/0000"));
        
        printlnConsoleIHM("(IHM client) liste des mediums : ");
        List<Medium> laliste = service.rechercherListeMedium(false, true, false, "", "F");
        printlnConsoleIHM(laliste);
        
        
        Consultation consul = service.trouverConsultation(c,laliste.get(0));
        printlnConsoleIHM(consul);
        
        scenarioEmploye(consul.getEmploye());
        
        
        printlnConsoleIHM("(IHM client) historique consultations : ");
        printlnConsoleIHM(service.rechercherListeConsultation(c.getId(), false, false, false, "", "01/01/0000"));
        
        
    }
    
     private static void testerListerClientsParEmploye(Employe e)
     {
         

        Service service = new Service();
        List<ClientStats> employestat = service.listerClientsParEmploye(e);
        if (employestat == null) {
            printlnConsoleIHM("ERREUR du Service testerListerClientsParEmploye");
        } else {
            printlnConsoleIHM("Liste des Clients Stats par employe (" + employestat.size() + ")");
            for (ClientStats emp : employestat) {
                printlnConsoleIHM(emp);
            }
            printlnConsoleIHM("----");
        }
    
     }

    private static void testerProchaineConsul(Employe e) {

        Service service = new Service();

        Consultation c = service.prochaineConsultation(e);
        if (c == null) {
            printlnConsoleIHM("pas de prochaine consultation quoi");
        } else {
            printlnConsoleIHM("Prochaine consultation: " + c);

            printlnConsoleIHM("----");
        }

    }

    private static void testerRechercherNbClientsParEmploye() {

        Service service = new Service();
        List<EmployeStat> employestat = service.rechercherNbClientParEmploye();
        if (employestat == null) {
            printlnConsoleIHM("ERREUR du Service testerRechercherNbClientsParEmploye");
        } else {
            printlnConsoleIHM("Liste des Employes Stats (" + employestat.size() + ")");
            for (EmployeStat e : employestat) {
                printlnConsoleIHM(e);
            }
            printlnConsoleIHM("----");
        }
    }

    private static void testerRechercherNbConsultationsMedium() {

        Service service = new Service();
        List<MediumStats> employestat = service.rechercherNbConsultationsMedium();
        if (employestat == null) {
            printlnConsoleIHM("ERREUR du Service testerRechercherNbConsultationsMedium");
        } else {
            printlnConsoleIHM("Liste des Mediums Stats (" + employestat.size() + ")");
            for (MediumStats e : employestat) {
                printlnConsoleIHM(e);
            }
            printlnConsoleIHM("----");
        }
    }

    private static void testerRechercherNbConsultationsClient() {

        Service service = new Service();
        List<ClientStats> employestat = service.rechercherNbConsultationsClient();
        if (employestat == null) {
            printlnConsoleIHM("ERREUR du Service testerRechercherNbConsultationsClient");
        } else {
            printlnConsoleIHM("Liste des Clients Stats (" + employestat.size() + ")");
            for (ClientStats e : employestat) {
                printlnConsoleIHM(e);
            }
            printlnConsoleIHM("----");
        }
    }

    private static void testerTrouverConsultation() throws ParseException {
        Service service = new Service();
        

        List<Consultation> consultations = service.rechercherListeConsultation((long)2, false, false, false, "", "01/01/0000"); //todo date a partir de

        if (consultations == null) {
            printlnConsoleIHM("ERREUR du Service listerConsultations");
        } else {
            printlnConsoleIHM("Liste des Consultations (" + consultations.size() + ")");
            for (Consultation c : consultations) {
                printlnConsoleIHM(c);
            }
            printlnConsoleIHM("----");

        }
    }

    private static void testerInscrireClients() throws ParseException, IOException {
        Service service = new Service();

        printlnConsoleIHM("Inscription Client C1");
        Client c1 = new Client("Hugo", "Victor", "13/04/2004", "7 rue du Lt Col Driant", "0908785620", "victor.hugo@gmail.com", "esmeralda");
        Boolean resultat1 = service.inscrireClient(c1);
        printlnConsoleIHM(resultat1 + " -> Inscription client C1 " + c1);

        printlnConsoleIHM("Inscription Client C1");
        Client c2 = new Client("aquino", "diego", "13/04/2004", "20 av Albert Einstein", "07654358", "diegogo@gmail.com", "esmeralda");
        Boolean resultat2 = service.inscrireClient(c2);
        printlnConsoleIHM(resultat2 + " -> Inscription client C2 " + c2);

        printlnConsoleIHM("Inscription Client C1");
        Client c3 = new Client("guyonnaud", "agnes", "16/03/2004", "8 rue de Moscou", "0756453454", "agnegnes@gmail.com", "coucoucou");
        Boolean resultat3 = service.inscrireClient(c3);
        printlnConsoleIHM(resultat3 + " -> Inscription client C3 " + c3);

    }

    private static void testerConsulterListeClients() {
        Service service = new Service();
        List<Client> clients = service.listerClients();

        if (clients == null) {
            printlnConsoleIHM("ERREUR du Service listerClients");
        } else {
            printlnConsoleIHM("Liste des Clients (" + clients.size() + ")");
            for (Client c : clients) {
                printlnConsoleIHM("#" + c.getId() + " " + c.getNom().toUpperCase() + " " + c.getPrenom());
            }
            printlnConsoleIHM("----");
        }
    }

    private static void testerConsulterListeMediums() {
        Service service = new Service();
        List<Medium> mediums = service.rechercherListeMedium(false, false, false, "", "F");

        if (mediums == null) {
            printlnConsoleIHM("ERREUR du Service testerConsulterListeMediums");
        } else {
            printlnConsoleIHM("Liste des Mediums (" + mediums.size() + ")");
            for (Medium c : mediums) {
                printlnConsoleIHM("#" + c.getId() + " " + c.getNom().toUpperCase());
            }
            printlnConsoleIHM("----");
        }
    }

    public static void printlnConsoleIHM(Object o) {
        String BG_CYAN = "\u001b[46m";
        String RESET = "\u001B[0m";

        System.out.print(BG_CYAN);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }

}
