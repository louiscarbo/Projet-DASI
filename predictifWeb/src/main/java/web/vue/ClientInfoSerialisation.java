package web.vue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;

public class ClientInfoSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) {
        Client client = (Client) request.getAttribute("client");

        JsonObject container = new JsonObject();

        if (client != null) {
            container.addProperty("nom", client.getNom());
            container.addProperty("prenom", client.getPrenom());
            container.addProperty("dateNaissance", new java.text.SimpleDateFormat("dd/MM/yyyy").format(client.getDateNaissance()));
            container.addProperty("tel", client.getTel());
            container.addProperty("adresse", client.getAdresse());
            container.addProperty("codePostal", client.getCoord().toString());
        } else {
            container.addProperty("error", "Client non connecté.");
        }

        response.setContentType("application/json;charset=UTF-8");
        try {
            Gson gson = new Gson();
            gson.toJson(container, response.getWriter());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}