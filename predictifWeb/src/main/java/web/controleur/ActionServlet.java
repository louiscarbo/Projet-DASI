/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controleur;

import dao.JpaUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.Service;
import web.modele.AccepterConsultationAction;
import web.modele.CheckSessionAction;
import web.modele.ChercherMediumAction;
import web.modele.ConnecterClientAction;
import web.modele.ConnecterEmployeAction;
import web.modele.ConsultationDemandeeAction;
import web.modele.DeconnecterClientAction;
import web.modele.DeconnecterEmployeAction;
import web.modele.DemarrerConsultationAction;
import web.modele.GenererPredictionAction;
import web.modele.InscrireClientAction;
import web.modele.ProchaineConsultationAction;
import web.modele.ProfilAstralAction;
import web.modele.ReserverConsultationAction;
import web.modele.TerminerConsultationAction;
import web.vue.AccepterConsultationSerialisation;
import web.vue.CheckSessionSerialisation;
import web.vue.ChercherMediumSerialisation;
import web.vue.ConnecterClientSerialisation;
import web.vue.ConnecterEmployeSerialisation;
import web.vue.ConsultationDemandeeSerialisation;
import web.vue.DeconnecterClientSerialisation;
import web.vue.DeconnecterEmployeSerialisation;
import web.vue.DemarrerConsultationSerialisation;
import web.vue.GenererPredictionSerialisation;
import web.vue.InscrireClientSerialisation;
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

        default:
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action inconnue");
            break;
    }
}

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
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
