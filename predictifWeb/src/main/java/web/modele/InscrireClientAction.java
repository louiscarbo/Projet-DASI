package web.modele;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Client;
import service.Service;

/**
 * Action responsable de l’inscription d’un nouveau client.
 */
public class InscrireClientAction extends Action {

    public InscrireClientAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        String nom           = request.getParameter("nom");
        String prenom        = request.getParameter("prenom");
        String dateNaissance = request.getParameter("dateNaissance");
        String adresse       = request.getParameter("adresse");
        String tel           = request.getParameter("telephone");
        String mail          = request.getParameter("mail");
        String mdp           = request.getParameter("mdp");

        try {
            Client client = new Client(
                nom,
                prenom,
                dateNaissance,
                adresse,
                tel,
                mail,
                mdp
            );

            boolean inscritOk = Service.inscrireClient(client);

            request.setAttribute("inscrit", inscritOk);
            if (inscritOk) {
                request.setAttribute("client", client);
                HttpSession session = request.getSession(true);
                session.setAttribute("client", client);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("inscrit", false);
        }
    }
}