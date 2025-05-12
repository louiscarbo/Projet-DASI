/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modele.Client;
import service.Service;

/**
 *
 * @author iiqdari
 */
public class ConnecterClientAction extends Action {

    public ConnecterClientAction(Service service) {
        super(service);
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("mail");
        String mdp = request.getParameter("mdp");
        
        Client client = service.connecterClient(mail, mdp);
        
        request.setAttribute("client", client);
        
        if (client != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("client", client);
        }
    }
    
   
    
}
