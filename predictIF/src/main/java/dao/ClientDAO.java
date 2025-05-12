/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import modele.Client;
import modele.Employe;

/**
 *
 * @author aguyonnaud
 */
public class ClientDAO {
    
    
     public static Client connecter(String mail,String mdp)
    {
        String s = "SELECT c FROM Client c WHERE c.mail= :mail AND c.mdp = :mdp ";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Client.class);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        if (query.getResultList().size()==0)
        {
            return null;
        }
        Client e =  (Client) query.getResultList().get(0);
        return e;
    }
     
    public static void create(Client c) {
        JpaUtil.obtenirContextePersistance().persist(c);
    }
    
    
    
    public static List<Client> findAll() {
        String s = "SELECT c FROM Client c ORDER BY c.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Client.class);
        return query.getResultList();
    }
    
    public static Client findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
}
