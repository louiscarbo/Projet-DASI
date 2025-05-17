package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import service.Service;

/**
 * Action de déconnexion : invalide la session HTTP.
 */
public class DeconnecterClientAction extends Action {
    public DeconnecterClientAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}