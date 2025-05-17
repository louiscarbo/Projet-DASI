package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Client;
import service.Service;

/**
 * Charge le profil astral du client connecté.
 */
public class ProfilAstralAction extends Action {
    public ProfilAstralAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("client") != null) {
            Client sessClient = (Client) session.getAttribute("client");
            Long id = sessClient.getId();
            Client fullClient = service.ClientParID(id);
            request.setAttribute("client", fullClient);
        }
    }
}