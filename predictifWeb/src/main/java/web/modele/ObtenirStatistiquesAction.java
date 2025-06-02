package web.modele;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Client;
import modele.EmployeStat;
import modele.MediumStats;
import service.Service;

/**
 * Cette Action rassemble les trois listes nécessaires pour la page Statistiques :
 *   - liste des Clients (pour la carte),
 *   - liste des EmployeStat (nb clients par employé),
 *   - liste des MediumStats (nb consultations par médium).
 */
public class ObtenirStatistiquesAction extends Action {

    public ObtenirStatistiquesAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        List<Client> listeClients         = service.listerClients();
        List<EmployeStat> listeEmpStats   = service.rechercherNbClientParEmploye();
        List<MediumStats> listeMedStats   = service.rechercherNbConsultationsMedium();

        request.setAttribute("clients", listeClients);
        request.setAttribute("employes", listeEmpStats);
        request.setAttribute("mediums", listeMedStats);
    }
}