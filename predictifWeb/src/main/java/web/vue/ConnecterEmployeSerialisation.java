package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Employe;

/**
 * Sérialisation JSON de la connexion employé.
 */
public class ConnecterEmployeSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Employe emp = (Employe) request.getAttribute("employe");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            if (emp != null) {
                json.addProperty("success", true);
            } else {
                json.addProperty("success", false);
                json.addProperty("message", 
                    "Mail ou mot de passe incorrect.");
            }
            String output = gson.toJson(json);
            out.println(output);
            System.out.println(output);
        }
    }
}