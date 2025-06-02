package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sérialisation JSON de la déconnexion.
 */
public class DeconnecterClientSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            
            // On décide de renvoyer toujours success:true, on garde deux classes (Action/Serialisation)
            // pour garder la même structure dans tout le projet.
            json.addProperty("success", true);
            String output = gson.toJson(json);
            out.println(output);
            System.out.println(output);
        }
    }
}
