package web.vue;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import modele.Prediction;

/**
 * Sérialisation JSON du résultat de génération de prédiction.
 */
public class GenererPredictionSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        boolean success = Boolean.TRUE.equals(request.getAttribute("success"));
        String message = (String)request.getAttribute("message");
        Prediction p = (Prediction)request.getAttribute("prediction");

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            JsonObject json = new JsonObject();
            json.addProperty("success", success);
            if (success && p!=null) {
                json.addProperty("amourTexte",   p.getAmour());
                json.addProperty("santeTexte",   p.getSante());
                json.addProperty("travailTexte", p.getTravail());
            } else {
                json.addProperty("message", message!=null?message:"Erreur génération");
            }
            out.println(new GsonBuilder().setPrettyPrinting().create().toJson(json));
        }
    }
}