package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.*;
import modele.Astrologue;
import modele.Cartomancien;
import modele.Consultation;
import modele.Medium;
import modele.Client;
import modele.ProfilAstral;
import modele.Spirite;

/**
 * Sérialisation JSON de la consultation demandée.
 */
public class ConsultationDemandeeSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = (Long) request.getAttribute("consultationId");
        Medium med = (Medium) request.getAttribute("medium");
        Client client = (Client) request.getAttribute("client");
        ProfilAstral astro = (ProfilAstral) request.getAttribute("astro");
        List<Consultation> hist = (List<Consultation>) request.getAttribute("historique");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject root = new JsonObject();

            if (id == null) {
                root.addProperty("success", false);
                root.addProperty("message", "Aucune consultation à afficher.");
            } else {
                root.addProperty("success", true);
                root.addProperty("id", id);

                // medium
                JsonObject jm = new JsonObject();
                jm.addProperty("nom", med.getNom());
                jm.addProperty("genre", med.getGenre());
                jm.addProperty("presentation", med.getPresentation());
                String type = "Medium";
                if (med instanceof Spirite) {
                    type = "Spirite";
                } else if (med instanceof Cartomancien) {
                    type = "Cartomancien";
                } else if (med instanceof Astrologue) {
                    type = "Astrologue";
                }
                jm.addProperty("type", type);
                root.add("medium", jm);

                // client
                JsonObject jc = new JsonObject();
                jc.addProperty("nom", client.getNom());
                jc.addProperty("prenom", client.getPrenom());
                jc.addProperty("dateNaissance",
                    new SimpleDateFormat("dd/MM/yyyy").format(client.getDateNaissance()));
                jc.addProperty("tel", client.getTel());
                root.add("client", jc);

                // profil astral
                JsonObject ja = new JsonObject();
                ja.addProperty("zodiaque", astro.getZodiaque());
                ja.addProperty("sgnChinois", astro.getSgnChinois());
                ja.addProperty("couleur", astro.getCouleur());
                ja.addProperty("animal", astro.getAnimal());
                root.add("astro", ja);

                // historique
                JsonArray arr = new JsonArray();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                for (Consultation e : hist) {
                    JsonObject jo = new JsonObject();
                    jo.addProperty("id", e.getId());
                    jo.addProperty("date", sdf.format(e.getDate()));
                    jo.addProperty("medium", e.getMedium().getNom());
                    jo.addProperty("employe",
                        e.getEmploye().getPrenom() + " " + e.getEmploye().getNom());
                    arr.add(jo);
                }
                root.add("historique", arr);
            }

            out.println(gson.toJson(root));
        }
    }
}