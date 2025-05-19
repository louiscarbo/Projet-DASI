/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Astrologue;
import modele.Cartomancien;
import modele.Medium;
import modele.Spirite;

/**
 *
 * @author iiqdari
 */
public class ChercherMediumSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Medium> listeMediums = (List<Medium>) request.getAttribute("mediums");
        response.setContentType("application/json;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject root = new JsonObject();

            if (listeMediums.isEmpty()) {
                root.addProperty("success", false);
                root.addProperty("message", "Aucun medium à afficher.");
            } else {
                root.addProperty("success", true);

                // mediums
                JsonArray arr = new JsonArray();
                for (Medium medium : listeMediums) {
                    JsonObject jo = new JsonObject();
                    String type = "Medium";
                    if (medium instanceof Spirite) {
                        type = "Spirite";
                    } else if (medium instanceof Cartomancien) {
                        type = "Cartomancien";
                    } else if (medium instanceof Astrologue) {
                        type = "Astrologue";
                    }
                    jo.addProperty("type", type);
                    jo.addProperty("nom", medium.getNom());
                    jo.addProperty("genre", medium.getGenre());
                    jo.addProperty("id", medium.getId());
                    arr.add(jo);
                }
                root.add("mediums", arr);
            }
            out.println(gson.toJson(root));
            System.out.println(gson.toJson(root));
        }
    }
}
