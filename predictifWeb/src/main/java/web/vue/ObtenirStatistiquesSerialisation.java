package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.EmployeStat;
import modele.MediumStats;

/**
 * Cette Serialisation construit un unique JSON contenant trois tableaux : clients, employes, mediums
 */
public class ObtenirStatistiquesSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        @SuppressWarnings("unchecked")
        List<Client> clients = (List<Client>) request.getAttribute("clients");
        @SuppressWarnings("unchecked")
        List<EmployeStat> employes = (List<EmployeStat>) request.getAttribute("employes");
        @SuppressWarnings("unchecked")
        List<MediumStats> mediums = (List<MediumStats>) request.getAttribute("mediums");

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject root = new JsonObject();

        // Tableau "clients"
        JsonArray arrClients = new JsonArray();
        if (clients != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Client c : clients) {
                JsonObject obj = new JsonObject();
                obj.addProperty("id", c.getId());
                obj.addProperty("prenom", c.getPrenom());
                obj.addProperty("nom", c.getNom());
                obj.addProperty("dateNaissance", sdf.format(c.getDateNaissance()));
                obj.addProperty("tel", c.getTel());
                obj.addProperty("adresse", c.getAdresse());
                if (c.getCoord() != null) {
                    obj.addProperty("coordLat", c.getCoord().lat);
                    obj.addProperty("coordLng", c.getCoord().lng);
                } else {
                    obj.addProperty("coordLat", 0);
                    obj.addProperty("coordLng", 0);
                }
                arrClients.add(obj);
            }
        }
        root.add("clients", arrClients);

        // Tableau "employes"
        JsonArray arrEmployes = new JsonArray();
        if (employes != null) {
            for (EmployeStat es : employes) {
                JsonObject obj = new JsonObject();
                obj.addProperty("id", es.getEmploye().getId());
                obj.addProperty("prenom", es.getEmploye().getPrenom());
                obj.addProperty("nom", es.getEmploye().getNom());
                obj.addProperty("nbClients", es.getNbConsultations());
                arrEmployes.add(obj);
            }
        }
        root.add("employes", arrEmployes);

        // Tableau "mediums"
        JsonArray arrMediums = new JsonArray();
        if (mediums != null) {
            for (MediumStats ms : mediums) {
                JsonObject obj = new JsonObject();
                obj.addProperty("id", ms.getMedium().getId());
                obj.addProperty("nom", ms.getMedium().getNom());
                obj.addProperty("specialite", ms.getMedium().getPresentation());
                obj.addProperty("nbClients", ms.getNbConsultations());
                arrMediums.add(obj);
            }
        }
        root.add("mediums", arrMediums);

        root.addProperty("success", true);

        out.println(gson.toJson(root));
        out.close();
    }
}