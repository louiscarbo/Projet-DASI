/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import java.util.List;
import modele.Medium;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Client;
import modele.Consultation;
import service.Service;

/**
 *
 * @author iiqdari
 */
public class ReserverConsultationAction extends Action {

    public ReserverConsultationAction(Service service) {
        super(service);
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Client client = (Client) session.getAttribute("client");
        
        Long mediumId = Long.parseLong(request.getParameter("mediumId"));
        Medium medium = service.MediumParID(mediumId);
        
        Consultation consultation = service.trouverConsultation(client, medium);
        
        request.setAttribute("consultation", consultation);
    }
}
