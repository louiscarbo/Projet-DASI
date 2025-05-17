package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Consultation;
import modele.Employe;
import service.Service;

/**
 * Action pour charger la prochaine consultation de l'employé.
 */
public class ProchaineConsultationAction extends Action {
    public ProchaineConsultationAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean connected = false;
        String name = null;
        Consultation next = null;

        if (session != null && session.getAttribute("employe") != null) {
            connected = true;
            Employe e = (Employe) session.getAttribute("employe");
            name = e.getNom();
            next = service.prochaineConsultation(e);
        }
        request.setAttribute("connected", connected);
        request.setAttribute("employeName", name);
        request.setAttribute("consultation", next);
    }
}