package web.controleur;

import dao.JpaUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Consultation;
import modele.Employe;
import modele.Medium;
import modele.Prediction;
import service.Service;
import web.modele.AccepterConsultationAction;
import web.modele.AfficherHistoriqueAction;
import web.modele.CheckSessionAction;
import web.modele.ChercherMediumAction;
import web.modele.ConnecterClientAction;
import web.modele.ConnecterEmployeAction;
import web.modele.ConsultationDemandeeAction;
import web.modele.ConsulterDetailsConsultationAction;
import web.modele.DeconnecterClientAction;
import web.modele.DeconnecterEmployeAction;
import web.modele.DemarrerConsultationAction;
import web.modele.GenererPredictionAction;
import web.modele.InscrireClientAction;
import web.modele.ObtenirStatistiquesAction;
import web.modele.ProchaineConsultationAction;
import web.modele.ProfilAstralAction;
import web.modele.ReserverConsultationAction;
import web.modele.TerminerConsultationAction;
import web.vue.AccepterConsultationSerialisation;
import web.vue.AfficherHistoriqueSerialisation;
import web.vue.CheckSessionSerialisation;
import web.vue.ChercherMediumSerialisation;
import web.vue.ConnecterClientSerialisation;
import web.vue.ConnecterEmployeSerialisation;
import web.vue.ConsultationDemandeeSerialisation;
import web.vue.ConsulterDetailsConsultationSerialisation;
import web.vue.DeconnecterClientSerialisation;
import web.vue.DeconnecterEmployeSerialisation;
import web.vue.DemarrerConsultationSerialisation;
import web.vue.GenererPredictionSerialisation;
import web.vue.InscrireClientSerialisation;
import web.vue.ObtenirStatistiquesSerialisation;
import web.vue.ProchaineConsultationSerialisation;
import web.vue.ProfilAstralSerialisation;
import web.vue.ReserverConsultationSerialisation;
import web.vue.TerminerConsultationSerialisation;

/**
 *
 * @author iiqdari
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    String todo = request.getParameter("todo");
    Service service = new Service();
    
    System.out.println("ActionServlet reçu todo=" + todo);

    switch (todo) {
        case "connecterClient":
            new ConnecterClientAction(service).execute(request);
            new ConnecterClientSerialisation().appliquer(request, response);
            break;

        case "inscrireClient":
            new InscrireClientAction(service).execute(request);
            new InscrireClientSerialisation().appliquer(request, response);
            break;

        case "checkSession":
            new CheckSessionAction(service).execute(request);
            new CheckSessionSerialisation().appliquer(request, response);
            break;

        case "deconnecter":
            new DeconnecterClientAction(service).execute(request);
            new DeconnecterClientSerialisation().appliquer(request, response);
            break;
            
        case "profilAstral":
            new ProfilAstralAction(service).execute(request);
            new ProfilAstralSerialisation().appliquer(request, response);
            break;
            
        case "connecterEmploye":
            new ConnecterEmployeAction(service).execute(request);
            new ConnecterEmployeSerialisation().appliquer(request, response);
            break;
            
        case "prochaineConsultation":
            new ProchaineConsultationAction(service).execute(request);
            new ProchaineConsultationSerialisation().appliquer(request, response);
            break;
            
        case "consultationDemandee":
            new ConsultationDemandeeAction(service).execute(request);
            new ConsultationDemandeeSerialisation().appliquer(request, response);
            break;
            
        case "deconnecterEmploye":
            new DeconnecterEmployeAction(service).execute(request);
            new DeconnecterEmployeSerialisation().appliquer(request, response);
            break;
            
        case "accepterConsultation":
            new AccepterConsultationAction(service).execute(request);
            new AccepterConsultationSerialisation().appliquer(request, response);
            break;

        case "demarrerConsultation":
            new DemarrerConsultationAction(service).execute(request);
            new DemarrerConsultationSerialisation().appliquer(request, response);
            break;
            
        case "genererPrediction":
            new GenererPredictionAction(service).execute(request);
            new GenererPredictionSerialisation().appliquer(request, response);
            break;
        case "terminerConsultation":
            new TerminerConsultationAction(service).execute(request);
            new TerminerConsultationSerialisation().appliquer(request, response);
            break;
            
        case "chercherMediums":
            new ChercherMediumAction(service).execute(request);
            new ChercherMediumSerialisation().appliquer(request, response);
            break;
            
        case "reserverConsultation":
            new ReserverConsultationAction(service).execute(request);
            new ReserverConsultationSerialisation().appliquer(request, response);
            break;
            
        case "afficherHistorique":
            new AfficherHistoriqueAction(service).execute(request);
            new AfficherHistoriqueSerialisation().appliquer(request, response);
            break;
            
        case "consulterDetailsConsultation":
            new ConsulterDetailsConsultationAction(service).execute(request);
            new ConsulterDetailsConsultationSerialisation().appliquer(request, response);
            break;
            
        case "statistiquesPage":
            new ObtenirStatistiquesAction(service).execute(request);
            new ObtenirStatistiquesSerialisation().appliquer(request, response);
            break;

        default:
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue");
            break;
    }
}

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
        
        try {
            testerInscrireClients();
            Service service = new Service();
            service.initMediums();
            service.initEmployes();
            scenarioClient();
            injecterConsultationTest();
        } catch(Exception exc) {
            System.out.println("Erreur lors de l'initialisation");
            exc.printStackTrace();
        }
    }
    
    private static void injecterConsultationTest() throws ParseException, IOException {
        Service service = new Service();

        // 1) Créer ou retrouver un client de test
        Client testClient = new Client(
            "Dupont", "Jean",
            "01/01/1990",
            "123 rue Exemple",
            "0123456789",
            "jean.dupont@example.com",
            "motdepasse"
        );
        Service.inscrireClient(testClient);

        // 2) Récupérer un médium pour la consultation
        List<Medium> mediums = service.rechercherListeMedium(true, true, true, "", "");
        if (mediums.isEmpty()) {
            System.err.println("Aucun médium disponible pour test.");
            return;
        }
        Medium med = mediums.get(0);

        // 3) Générer la consultation « ATTENTE »
        Consultation c = service.trouverConsultation(testClient, med);
        if (c == null) {
            System.err.println("Echec de création de la consultation test.");
        } else {
            System.out.println("✔ Consultation test injectée en base, état=" + c.getEtat()
                + ", id=" + c.getId());
        }
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
    
    public static void printlnConsoleIHM(Object o) {
        String BG_CYAN = "\u001b[46m";
        String RESET = "\u001B[0m";

        System.out.print(BG_CYAN);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
