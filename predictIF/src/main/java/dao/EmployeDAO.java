/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import modele.Employe;
import modele.EmployeStat;

/**
 *
 * @author aguyonnaud
 */
public class EmployeDAO {

    public static void create(Employe e) {
        JpaUtil.obtenirContextePersistance().persist(e);
    }

    public static Employe save(Employe e) {
        return JpaUtil.obtenirContextePersistance().merge(e);
    }

    public static Employe findOpti(String genre)//retourne null si aucun employé du bon genre n'est dispo
    {
        //amelioration : nb_consultations ce mois
        String s = "SELECT c FROM Employe c WHERE c.genre= :genre AND c.dispo = true ORDER BY c.nb_consult";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("genre", genre);
        
        if (query.getResultList().size() == 0) {
            return null;
        }
        Employe e = (Employe) query.getResultList().get(0);
        return e;

    }

    public static List<EmployeStat> findNbClients() {

        String s = "SELECT c, c.nb_consult FROM Employe c ORDER BY c.nb_consult DESC";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class);

        List<Object[]> results = query.getResultList();
        List<EmployeStat> stats = new ArrayList<>();

        for (Object[] row : results) {
            Employe employe = (Employe) row[0];
            int nbClients = (int) row[1];
            stats.add(new EmployeStat(employe, nbClients));
        }

        return stats;
    }

    public static Employe connecter(String mail, String mdp) {
        String s = "SELECT c FROM Employe c WHERE c.mail= :mail AND c.mdp = :mdp ";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        if (query.getResultList().size() == 0) {
            return null;
        }
        Employe e = (Employe) query.getResultList().get(0);
        return e;
    }

    public static List<Employe> findAll() {
        String s = "SELECT c FROM Employe c ORDER BY c.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        return query.getResultList();
    }

    public static Employe findById(Long id) {
        String s = "SELECT c FROM Employe c WHERE c.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("id", id);
        return (Employe) query.getResultList().get(0);
    }

}
