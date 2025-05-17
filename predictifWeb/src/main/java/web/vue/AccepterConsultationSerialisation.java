package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sérialisation JSON du résultat de l'acceptation d'une consultation.
 */
public class AccepterConsultationSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        boolean success = Boolean.TRUE.equals(request.getAttribute("success"));
        String  message = (String) request.getAttribute("message");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("success", success);
            if (!success) {
                json.addProperty("message", message != null ? message :
                    "Impossible d'accepter la consultation.");
            }
            out.println(gson.toJson(json));
        }
    }
}