package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import modele.Consultation;

/**
 * Sérialisation JSON de la prochaine consultation.
 */
public class ProchaineConsultationSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        Boolean connected    = (Boolean) request.getAttribute("connected");
        String  employeName  = (String)  request.getAttribute("employeName");
        Consultation consult = (Consultation) request.getAttribute("consultation");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("connected", connected != null && connected);
            if (employeName != null) {
                json.addProperty("employeName", employeName);
            }
            if (consult != null) {
                json.addProperty("success", true);
                // on peut ajouter d'autres données de Consultation ici si besoin
            } else {
                json.addProperty("success", false);
            }
            out.println(gson.toJson(json));
        }
    }
}