package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Employe;
import service.Service;

/**
 * Action de connexion pour les employés.
 */
public class ConnecterEmployeAction extends Action {
    public ConnecterEmployeAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("mail");
        String mdp  = request.getParameter("mdp");
        Employe emp = service.connecterEmploye(mail, mdp);
        request.setAttribute("employe", emp);
        if (emp != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("employe", emp);
        }
    }
}