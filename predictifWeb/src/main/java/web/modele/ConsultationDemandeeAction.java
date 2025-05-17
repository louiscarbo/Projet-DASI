package web.modele;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Client;
import modele.Consultation;
import modele.Employe;
import modele.Medium;
import service.Service;

/**
 * Charge la consultation en attente et son contexte.
 */
public class ConsultationDemandeeAction extends Action {
    public ConsultationDemandeeAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return;

        Employe emp = (Employe) session.getAttribute("employe");
        if (emp == null) return;

        // 1) prochaine consultation
        Consultation c = service.prochaineConsultation(emp);
        request.setAttribute("consultationId", c != null ? c.getId() : null);

        if (c != null) {
            // 2) médium et client
            Medium med    = c.getMedium();
            Client client = c.getClient();

            request.setAttribute("medium", med);
            request.setAttribute("client", client);
            request.setAttribute("astro", client.getProfAst());

            // 3) historique complet du client
            List<Consultation> hist = service.rechercherListeConsultation(
              client.getId(), true, true, true, "", "01/01/1900"
            );
            request.setAttribute("historique", hist);
        }
    }
}