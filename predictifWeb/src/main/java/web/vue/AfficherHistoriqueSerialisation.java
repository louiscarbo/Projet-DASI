package web.vue;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.*;
import modele.Consultation;

/**
 * Sérialise l'historique des consultations du client.
 */
public class AfficherHistoriqueSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        Boolean success = (Boolean) request.getAttribute("success");
        String  message = (String) request.getAttribute("message");
        @SuppressWarnings("unchecked")
        List<Consultation> list = (List<Consultation>) request.getAttribute("consultations");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JsonObject root = new JsonObject();
            root.addProperty("success", success != null && success);
            if (!success) {
                root.addProperty("message", message);
            } else {
                JsonArray arr = new JsonArray();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                for (Consultation c : list) {
                    JsonObject jc = new JsonObject();
                    jc.addProperty("id", c.getId());
                    jc.addProperty("date", sdf.format(c.getDate()));
                    jc.addProperty("medium", c.getMedium().getNom());
                    // déterminer type à partir du medium
                    String type;
                    if (c.getMedium() instanceof modele.Spirite) {
                        type = "Spirite";
                    } else if (c.getMedium() instanceof modele.Astrologue) {
                        type = "Astrologue";
                    } else {
                        type = "Cartomancien";
                    }
                    jc.addProperty("type", type);
                    arr.add(jc);
                }
                root.add("consultations", arr);
            }
            out.println(new GsonBuilder().setPrettyPrinting().create().toJson(root));
        }
    }
}