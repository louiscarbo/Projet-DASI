package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Consultation;
import modele.Prediction;
import modele.Employe;
import service.Service;

/**
 * Génère une nouvelle prédiction pour la consultation en cours.
 */
public class GenererPredictionAction extends Action {
    public GenererPredictionAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        String aParam = request.getParameter("amour");
        String sParam = request.getParameter("sante");
        String tParam = request.getParameter("travail");
        boolean success = false;
        String message = null;

        try {
            HttpSession session = request.getSession(false);
            Employe e = session!=null ? (Employe)session.getAttribute("employe") : null;
            if (e==null) {
                message = "Employé non connecté";
            } else if (idParam != null && aParam != null && sParam != null && tParam != null) {
                Long id = Long.parseLong(idParam);
                int amour  = Integer.parseInt(aParam);
                int sante  = Integer.parseInt(sParam);
                int travail = Integer.parseInt(tParam);

                Consultation c = service.ConsultationParID(id);
                Prediction p = service.genererPrediction(c.getClient(), sante, amour, travail);
                request.setAttribute("prediction", p);
                success = true;
            } else {
                message = "Paramètres manquants";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Erreur serveur";
        }

        request.setAttribute("success", success);
        if (message!=null) request.setAttribute("message", message);
    }
}