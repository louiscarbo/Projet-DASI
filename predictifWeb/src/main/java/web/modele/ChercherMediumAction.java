/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import java.util.List;
import modele.Medium;
import javax.servlet.http.HttpServletRequest;
import service.Service;

/**
 *
 * @author iiqdari
 */
public class ChercherMediumAction extends Action {

    public ChercherMediumAction(Service service) {
        super(service);
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        boolean spirite = Boolean.parseBoolean(request.getParameter("spirite"));
        boolean astrologue = Boolean.parseBoolean(request.getParameter("astrologue"));
        boolean carto = Boolean.parseBoolean(request.getParameter("cartomancien"));
        String nomMedium = request.getParameter("nomMedium");
        String genre = request.getParameter("genre");
        
        List<Medium> listeMediums = service.rechercherListeMedium(
            spirite,
            astrologue,
            carto,
            nomMedium,
            genre
        );
        
        request.setAttribute("mediums", listeMediums);
    }
}
