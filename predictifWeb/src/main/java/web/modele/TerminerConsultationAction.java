package web.modele;

import javax.servlet.http.*;
import modele.Consultation;
import modele.Prediction;
import service.Service;

/**
 * Termine une consultation en lui ajoutant le commentaire et la prédiction.
 */
public class TerminerConsultationAction extends Action {
    public TerminerConsultationAction(Service service) {
        super(service);
    }
    @Override
    public void execute(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        String comm    = request.getParameter("commentaire");
        String aText   = request.getParameter("amourTexte");
        String sText   = request.getParameter("santeTexte");
        String tText   = request.getParameter("travailTexte");

        boolean success = false;
        String message = null;

        try {
            if (idParam==null) throw new IllegalArgumentException("id manquant");
            Long id = Long.parseLong(idParam);
            Consultation c = service.ConsultationParID(id);
            if (c==null) throw new IllegalArgumentException("Consultation introuvable");

            // Reconstruire l’objet Prediction
            Prediction p = new Prediction(aText, sText, tText);
            service.terminerConsultation(c, p, comm);
            success = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            message = ex.getMessage();
        }

        request.setAttribute("success", success);
        if (message!=null) request.setAttribute("message", message);
    }
}