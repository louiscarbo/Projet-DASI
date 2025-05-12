/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import javax.servlet.http.HttpServletRequest;
import service.Service;

/**
 *
 * @author iiqdari
 */
public abstract class Action {
    protected Service service;
    
    public Action(Service service) {
        this.service = service;
    }
    
    public abstract void execute(HttpServletRequest request);
    
}
