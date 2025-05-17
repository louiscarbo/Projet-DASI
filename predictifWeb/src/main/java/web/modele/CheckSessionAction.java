package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import service.Service;

/**
 * Vérifie la présence d'un client en session.
 */
public class CheckSessionAction extends Action {
    public CheckSessionAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean connected = session != null && session.getAttribute("client") != null;
        request.setAttribute("connected", connected);
    }
}
