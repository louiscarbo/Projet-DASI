package web.modele;

import javax.servlet.http.HttpServletRequest;
import modele.Client;
import service.Service;

public class ClientInfoAction extends Action {

    public ClientInfoAction(Service service) {
        super(service);
    }

    public void execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");
        if (client != null) {
            request.setAttribute("client", client);
        } else {
            request.setAttribute("client", null);
        }
    }
}