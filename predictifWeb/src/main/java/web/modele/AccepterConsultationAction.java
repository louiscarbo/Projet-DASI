package web.modele;

import javax.servlet.http.HttpServletRequest;
import modele.Consultation;
import service.Service;

/**
 * Action qui marque une consultation comme ACCEPTÉE.
 */
public class AccepterConsultationAction extends Action {
    public AccepterConsultationAction(Service service) {
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
                // 1) Charger la consultation
                Consultation c = service.ConsultationParID(id);
                if (c != null) {
                    // 2) Appeler le service
                    service.accepterConsultation(c);
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
            message = "Erreur serveur lors de l'acceptation.";
        }

        request.setAttribute("success", success);
        if (message != null) {
            request.setAttribute("message", message);
        }
    }
}