package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import modele.Client;
import modele.ProfilAstral;

/**
 * Sérialisation JSON du profil astral.
 */
public class ProfilAstralSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Client client = (Client) request.getAttribute("client");
        ProfilAstral profil = client != null ? client.getProfAst() : null;

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            if (profil != null) {
                json.addProperty("success", true);
                json.addProperty("zodiaque", profil.getZodiaque());
                json.addProperty("sgnChinois", profil.getSgnChinois());
                json.addProperty("couleur", profil.getCouleur());
                json.addProperty("animal", profil.getAnimal());
            } else {
                json.addProperty("success", false);
                json.addProperty("message", "Aucun profil disponible.");
            }
            out.println(gson.toJson(json));
        }
    }
}