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

/**
 *
 * @author iiqdari
 */
public class ConnecterClientSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = (Client) request.getAttribute("client");
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonContainer = new JsonObject();
        
        if (client != null) {
            jsonContainer.addProperty("success", true);
        } else {
            jsonContainer.addProperty("success", false);
            jsonContainer.addProperty("message", "Les identifiants sont incorrects, ou aucun compte n'existe avec ce mail.");
        }
        
        out.println(gson.toJson(jsonContainer));
        System.out.println(gson.toJson(jsonContainer));
        out.close();
    }
    
}
