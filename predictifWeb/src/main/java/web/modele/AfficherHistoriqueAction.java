package web.modele;

import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Client;
import modele.Consultation;
import service.Service;

public class AfficherHistoriqueAction extends Action {
    public AfficherHistoriqueAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Client client = session != null
                      ? (Client) session.getAttribute("client")
                      : null;
        if (client == null) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Client non connecté.");
            return;
        }

        // Lire les params
        boolean spirite    = Boolean.parseBoolean(request.getParameter("spirite"));
        boolean astro      = Boolean.parseBoolean(request.getParameter("astrologue"));
        boolean carto      = Boolean.parseBoolean(request.getParameter("cartomancien"));
        String  nomMed     = request.getParameter("nomMedium");
        String  dateString = request.getParameter("dateString");

        List<Consultation> list = service.rechercherListeConsultation(
          client.getId(),
          spirite, astro, carto,
          nomMed != null ? nomMed : "",
          dateString != null ? dateString : "01/01/0000"
        );
        request.setAttribute("consultations", list);
        request.setAttribute("success", true);
    }
}