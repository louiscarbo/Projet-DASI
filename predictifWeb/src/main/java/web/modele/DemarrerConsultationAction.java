package web.modele;

import javax.servlet.http.HttpServletRequest;
import modele.Consultation;
import service.Service;

/**
 * Action qui passe une consultation à l'état EN COURS.
 */
public class DemarrerConsultationAction extends Action {
    public DemarrerConsultationAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        boolean success = false;
        String message = null;

        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                Consultation c = service.ConsultationParID(id);
                if (c != null) {
                    service.demarrerConsultation(c);
                    success = true;
                } else {
                    message = "Consultation introuvable.";
                }
            } else {
                message = "Paramètre 'id' manquant.";
            }
        } catch (NumberFormatException nfe) {
            message = "Identifiant invalide.";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "Erreur serveur lors du démarrage.";
        }

        request.setAttribute("success", success);
        if (message != null) {
            request.setAttribute("message", message);
        }
    }
}