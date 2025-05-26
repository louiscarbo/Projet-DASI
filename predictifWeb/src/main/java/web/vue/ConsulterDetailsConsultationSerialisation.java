package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sérialisation JSON des détails d'une consultation.
 */
public class ConsulterDetailsConsultationSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        boolean success = Boolean.TRUE.equals(request.getAttribute("success"));
        String message  = (String) request.getAttribute("message");
        String amour    = (String) request.getAttribute("amour");
        String sante    = (String) request.getAttribute("sante");
        String travail  = (String) request.getAttribute("travail");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JsonObject root = new JsonObject();
            root.addProperty("success", success);
            if (success) {
                root.addProperty("amour",   amour);
                root.addProperty("sante",   sante);
                root.addProperty("travail", travail);
            } else {
                root.addProperty("message", message);
            }
            out.println(new GsonBuilder().setPrettyPrinting().create().toJson(root));
        }
    }
}