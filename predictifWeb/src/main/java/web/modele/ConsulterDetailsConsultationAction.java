package web.modele;

import javax.servlet.http.HttpServletRequest;
import modele.Consultation;
import modele.Prediction;
import service.Service;

/**
 * Charge les prédictions stockées pour une consultation.
 */
public class ConsulterDetailsConsultationAction extends Action {
    public ConsulterDetailsConsultationAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        boolean success = false;
        String message = null;
        String amour = "", sante = "", travail = "";

        try {
            if (idParam == null) {
                message = "Paramètre 'id' manquant.";
            } else {
                Long id = Long.parseLong(idParam);
                Consultation c = service.ConsultationParID(id);
                if (c != null && c.getPrediction() != null) {
                    Prediction p = c.getPrediction();
                    amour   = p.getAmour();
                    sante   = p.getSante();
                    travail = p.getTravail();
                    success = true;
                } else {
                    message = "Aucune prédiction disponible pour cette consultation.";
                }
            }
        } catch (NumberFormatException nfe) {
            message = "ID invalide.";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Erreur serveur.";
        }

        request.setAttribute("success", success);
        if (success) {
            request.setAttribute("amour",   amour);
            request.setAttribute("sante",   sante);
            request.setAttribute("travail", travail);
        } else {
            request.setAttribute("message", message);
        }
    }
}