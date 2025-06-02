package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sérialisation de la réponse à l’action d’inscription d’un client.
 */
public class InscrireClientSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean inscrit = (Boolean) request.getAttribute("inscrit");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonContainer = new JsonObject();

            if (inscrit != null && inscrit) {
                jsonContainer.addProperty("success", true);
            } else {
                jsonContainer.addProperty("success", false);
                jsonContainer.addProperty(
                    "message",
                    "L'inscription a échoué. Veuillez réessayer plus tard."
                );
            }

            String json = gson.toJson(jsonContainer);
            out.println(json);
            System.out.println(json);
        }
    }
}