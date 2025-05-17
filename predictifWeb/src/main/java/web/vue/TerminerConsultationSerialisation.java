package web.vue;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

/**
 * Sérialisation JSON du résultat de la terminaison de consultation.
 */
public class TerminerConsultationSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        boolean success = Boolean.TRUE.equals(request.getAttribute("success"));
        String  message = (String)request.getAttribute("message");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JsonObject json = new JsonObject();
            json.addProperty("success", success);
            if (!success) {
                json.addProperty("message", message!=null?message:"Erreur term. consul.");
            }
            out.println(new GsonBuilder().setPrettyPrinting().create().toJson(json));
        }
    }
}