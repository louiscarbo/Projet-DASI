/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.Client;
import modele.Consultation;

/**
 *
 * @author iiqdari
 */
public class ReserverConsultationSerialisation extends Serialisation {
    
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonContainer = new JsonObject();
        
        if (consultation != null) {
            jsonContainer.addProperty("success", true);
        } else {
            jsonContainer.addProperty("success", false);
            jsonContainer.addProperty("message", "La consultation n'a pas pu être réservée.");
        }
        
        out.println(gson.toJson(jsonContainer));
        System.out.println(gson.toJson(jsonContainer));
        out.close();
    }
}
